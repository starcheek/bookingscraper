import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.util.*;

public class DayData {
        public String[] jolanta = {
                "Standarta divvietīgs numurs (1 gulta)",
                "Standarta divvietīgs numurs (1 gulta)",
                "\"Junior suite\" numurs",
                "\"Suite\" numurs",
                "Standarta divvietīgs numurs (2 gultas)"
        };
        public String[] bahnhofs = {
                "Standarta divvietīgs numurs (1 gulta)",
                "Standarta divvietīgs numurs (1 gulta)",
                "Standarta divvietīgs numurs (2 gultas)",
                "Standarta divvietīgs numurs (2 gultas)",
                "\"Superior\" klases numurs ar \"king\" izmēra gultu",
                "\"Superior\" klases numurs ar \"king\" izmēra gultu",
                "\"Superior\" klases numurs ar \"king\" izmēra gultu"
        };

        public String[] ierulle = {
                "Divvietīgs numurs (2 gultas) ar koplietošanas vannas istabu",
                "Divvietīgs numurs (1 gulta)",
                "Divvietīgs numurs (2 gultas)",
                "Luksusa divvietīgs numurs ar balkonu"
        };
        public String[] jaunsetas = {
                "Trīsvietīgs numurs ar koplietošanas vannas istabu",
                "Trīsvietīgs numurs ar koplietošanas vannas istabu",
                "Trīsvietīgs numurs ar koplietošanas vannas istabu",
                "Divvietīgs numurs (1 gulta) ar koplietošanas vannas istabu",
                "Standarta divvietīgs numurs (1 gulta)",
                "Standarta divvietīgs numurs (2 gultas)",
                "Standarta divvietīgs numurs (2 gultas)",
                "Liels vienvietīgs numurs",
                "Vienvietīgs numurs",
                "Komforta divvietīgs numurs (1 gulta)",
                "Vienguļamā gulta jauktā kopmītņu tipa numurā",


        };

        String date;
        String hotelName;
        HashMap<String, String[]> arrayList = new HashMap<>();
        ArrayList<Room> roomList = new ArrayList<>();


        public DayData(String date,String hotelName, Document doc){
            this.hotelName = hotelName;
            this.date = date;
            arrayList.put("jolanta", jolanta);
            arrayList.put("bahnhofs", bahnhofs);
            arrayList.put("ierulle", ierulle);
            arrayList.put("jaunsetas", jaunsetas);



            for(String arrayName : arrayList.keySet()){
                if(arrayName.equals(hotelName)){
                    System.out.println(date);
                    createRooms(doc, arrayList.get(hotelName));

                }
            }
        }

    private void createRooms(Document doc, String[] rooms) {

        LinkedHashMap<String, Integer> roomMap = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> roomMapAfter = new LinkedHashMap<>();

        for (String room : rooms) {
            if (roomMap.containsKey(room)) {
                roomMap.put(room, roomMap.get(room) + 1);
                roomMapAfter.put(room, roomMapAfter.get(room) + 1);
            } else {
                roomMap.put(room, 1);
                roomMapAfter.put(room, 1);

            }
        }

        Elements options = doc.getElementsByClass("hprt-nos-select");
        Elements names = doc.getElementsByClass("hprt-roomtype-link");


        outerLoup:
        for (Element nameElement : names) {
            String roomId = nameElement.attr("data-room-id");
            for (Element opt : options) {
                String optNumber = opt.attr("data-room-id");
                if (optNumber.equals(roomId)) {

                    for (int i = 0; i < opt.getElementsByTag("option").size()-1; i++) {

                        try {

                            roomMapAfter.put(nameElement.text(), roomMapAfter.get(nameElement.text()) - 1);
                        } catch (NullPointerException e){
                            System.out.println(nameElement.text());
                        }
                        continue outerLoup;
                    }
                }
            }
        }

        roomList = new ArrayList<>();
        for(String beforeKey : roomMapAfter.keySet()){

            for(int i = 0; i < roomMap.get(beforeKey) - roomMapAfter.get(beforeKey); i++){
                roomList.add(new Room(beforeKey, false));
            }

            for(int i = 0; i < roomMapAfter.get(beforeKey); i++){
                roomList.add(new Room(beforeKey, true));
            }
        }

    }


}




