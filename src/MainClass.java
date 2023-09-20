import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
        CustomHashTable hash = new CustomHashTable();
        double ans = hash.doCosineSimilarity(wordsInLink,sample);
        // System.out.println(ans);
        // System.out.println(Arrays.stream(wordsInLink).toList());
    }
}
