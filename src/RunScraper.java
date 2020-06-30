import org.apache.commons.compress.utils.Lists;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RunScraper extends Thread{


    private final WebReader webReader;
    LocalDate start;
    LocalDate end;
    String hotelName;
    String link;
    LinkedHashMap<String,DayData> dayMap = new LinkedHashMap<String, DayData>();

    public RunScraper(LocalDate start, LocalDate end, int index, WebReader webReader, String link, String hotelName){
        this.hotelName = hotelName;
        this.start = start;
        this.end = end;
        this.webReader = webReader;
        this.link = link;

    }

        public void run(){
            while (!start.isAfter(end)) {
                dayMap.put(start+"",new DayData(start+"", hotelName, fetchPage(start)));
                start = start.plusDays(1);
            }

            synchronized (webReader){
                webReader.notify();
            }

        }



    private  Document fetchPage(LocalDate date) {

      String dateString = date+"";
      String checkOut = date.plusDays(1)+"";

      Document doc = WebReader.getPage(link.replace("checkin=2020-10-16&checkout=2020-10-17", "checkin="+dateString+"&checkout="+checkOut));
      return doc;
    }

}
