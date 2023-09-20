import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainClass::createGUI);
    }

    private static void createGUI(){
        String userLink = JOptionPane.showInputDialog(null, "Paste your website's link here");

        try{ // Checking if URL exists before proceeding
            URL url = new URL(userLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK){
                getLinkText(userLink);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"The link you provided does not exist.","ERROR!",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private static void getLinkText(String userLink) throws IOException {
        WebScraper webscraper = new WebScraper();
        WebScraper weber = new WebScraper();
        String[] wordsInLink = webscraper.webScrape(userLink);
        String[] sample = weber.webScrape("https://en.wikipedia.org/wiki/Love");
        double ans = doCosineSimilarity(wordsInLink,sample);
        System.out.println(ans);
        // System.out.println(Arrays.stream(wordsInLink).toList());
    }

    private static double doCosineSimilarity(String[] a, String[] b){

        // Declaring variables necessary for the math (cosine similarity)
        double similarity;
        int numerator = 0;
        int denominatorA;
        int denominatorB;

        CustomHashTable ht1 = new CustomHashTable();
        CustomHashTable ht2 = new CustomHashTable();
        ArrayList<Object> lst = new ArrayList<>();

        // Each repeat in ht1 and ht2 forms the numerator and the sizes forms the denominator
        for(Object obj : a){
            if(ht1.add(obj)) lst.add(obj);
        }
        denominatorA = ht1.size;
        for(Object obj : b){
            ht2.add(obj);
        }
        denominatorB = ht2.size;
        for(Object obj : lst){
            if(ht2.contains(obj)) numerator += 1;
        }

        // Tweaked the formula to only necessary data
        similarity = numerator/(Math.sqrt(denominatorA)*Math.sqrt(denominatorB));

        return similarity;
    }
}
