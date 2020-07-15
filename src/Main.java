import org.jsoup.nodes.Document;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter start date");
        String startDate = scan.nextLine();
        System.out.println("Enter end date");
        String endDate = scan.nextLine();
        System.out.println("Enter wait time (Seconds)");
        int waitTime = Integer.parseInt(scan.nextLine());

        WebReader webReader = new WebReader(startDate, endDate, waitTime);
    }
}



