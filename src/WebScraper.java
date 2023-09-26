/**
 * Author: Declan ONUNKWO
 * College: SUNY Oswego
 * CSC 365 Project 1
 * Fall 2023
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebScraper {

    public String[] webScrape(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        // Getting the elements that form the main body of the wikipedia page
        Elements h3 = doc.select("div.mw-parser-output > h3");
        Elements h2 = doc.select("div.mw-parser-output > h2");
        Elements p = doc.select("div.mw-parser-output > p");
        String words = h3.text() + " " + h2.text() + " " + p.text();

        // Creating an array of strings containing the below
        return words.toLowerCase().split("[^a-z_'-]+");
    }
}
