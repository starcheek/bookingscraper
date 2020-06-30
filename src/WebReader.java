import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebReader extends Thread{
	String[] urls = {
			"https://www.booking.com/hotel/lv/jolanta.lv.html?aid=397594;label=gog235jc-1DCAEoggI46AdIGlgDaIoBiAEBmAEauAEXyAEM2AED6AEB-AECiAIBqAIDuAKwlrn3BcACAdICJDI4NmVlOTQ5LTAxMTAtNDE0NC05MDQ5LWU2MmEwN2ZjM2ViN9gCBOACAQ;sid=6bbd274b5a3f49d20ce45a093af8d8bf;all_sr_blocks=26630904_241064272_0_1_0;checkin=2020-10-16&checkout=2020-10-17;dest_id=-3206721;dest_type=city;dist=0;group_adults=1;group_children=0;hapos=1;highlighted_blocks=26630904_241064272_0_1_0;hpos=1;no_rooms=1;req_adults=1;req_children=0;room1=A;sb_price_type=total;sr_order=popularity;sr_pri_blocks=26630904_241064272_0_1_0__5500;srepoch=1592675124;srpvid=78bd7cda18c20161;type=total;ucfs=1&#hotelTmplDELEMETERjolanta",
			"https://www.booking.com/hotel/lv/bahnhofs.lv.html?aid=397594;label=gog235jc-1DCAEoggI46AdIGlgDaIoBiAEBmAEauAEXyAEM2AED6AEB-AECiAIBqAIDuAKSibr3BcACAdICJGMyMjMwYTI1LWU1NmYtNDE4Yi1iZGVjLTBmZjkyMzIxZGRmZdgCBOACAQ;sid=6bbd274b5a3f49d20ce45a093af8d8bf;all_sr_blocks=494880606_232022328_1_2_0;checkin=2020-10-16&checkout=2020-10-17;dest_id=-3206721;dest_type=city;dist=0;group_adults=1;group_children=0;hapos=6;highlighted_blocks=494880606_232022328_1_2_0;hpos=6;no_rooms=1;req_adults=1;req_children=0;room1=A;sb_price_type=total;sr_order=popularity;sr_pri_blocks=494880606_232022328_1_2_0__6000;srepoch=1592689821;srpvid=7937998e0f2d0086;type=total;ucfs=1&#hotelTmplDELEMETERbahnhofs",
		//	"https://www.booking.com/hotel/lv/ierulle.lv.html?aid=397594;label=gog235jc-1DCAEoggI46AdIGlgDaIoBiAEBmAEauAEXyAEM2AED6AEB-AECiAIBqAIDuAKSibr3BcACAdICJGMyMjMwYTI1LWU1NmYtNDE4Yi1iZGVjLTBmZjkyMzIxZGRmZdgCBOACAQ;sid=6bbd274b5a3f49d20ce45a093af8d8bf;all_sr_blocks=46907105_205808313_1_1_0;checkin=2020-10-16&checkout=2020-10-17;dest_id=-3206721;dest_type=city;dist=0;group_adults=1;group_children=0;hapos=7;highlighted_blocks=46907105_205808313_1_1_0;hpos=7;no_rooms=1;req_adults=1;req_children=0;room1=A;sb_price_type=total;sr_order=popularity;sr_pri_blocks=46907105_205808313_1_1_0__3000;srepoch=1592689821;srpvid=7937998e0f2d0086;type=total;ucfs=1&#hotelTmplDELEMETERierulle",
			"https://www.booking.com/hotel/lv/jaunsetas.lv.html?aid=397594;label=gog235jc-1DCAEoggI46AdIGlgDaIoBiAEBmAEauAEXyAEM2AED6AEB-AECiAIBqAIDuAK4xLz3BcACAdICJDM2YWRjZjk1LTlmZmMtNGIyZC1hYmY0LWU4Zjc5OTJlNzFkNNgCBOACAQ;sid=6bbd274b5a3f49d20ce45a093af8d8bf;all_sr_blocks=26291204_242572212_0_1_0;checkin=2020-10-16&checkout=2020-10-17;dest_id=-3206721;dest_type=city;dist=0;group_adults=1;group_children=0;hapos=4;highlighted_blocks=26291204_242572212_0_1_0;hpos=4;no_rooms=1;req_adults=1;req_children=0;room1=A;sb_price_type=total;sr_order=popularity;sr_pri_blocks=26291204_242572212_0_1_0__1500;srepoch=1593267503;srpvid=a92064975802001f;type=total;ucfs=1&#hotelTmplDELEMETERjaunsetas"
	};

	HashMap<String, RunScraper> hotelMap = new HashMap<String, RunScraper>();

	String startDate;
	String endDate;

	public WebReader(String startDate, String endDate){
		this.startDate = startDate;
		this.endDate = endDate;
		start();


	}
	public void run(){

		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);


		for(int i = 0; i < urls.length ; i++){
			RunScraper scraper = new RunScraper(start, end, i, this, urls[i].split("DELEMETER")[0],urls[i].split("DELEMETER")[1]);
			hotelMap.put(scraper.hotelName,scraper);
			scraper.start();
		}

		synchronized (this){
			try {
				this.wait();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		 new FileWriter(hotelMap, startDate);

	}




	static Document getPage(String urlString) {
		Document doc = null;
		pause(100);
		try {
			doc = Jsoup.connect(urlString).get(); //.userAgent("Mozilla/5.0")
			System.out.println(doc);
		} catch (Exception e) {
			System.out.println("KĻŪDA ar web request");
			e.printStackTrace();
		}
		return doc;
	}


	static void pause (int millis) { //1000ms = 1s
		try {
			Thread.sleep(millis);
		} catch (Exception e) {

		}
	}
}







