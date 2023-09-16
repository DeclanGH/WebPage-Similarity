import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

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
                webScraper(userLink);
            }
        } catch (IOException e) {
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame,"The link you provided does not exist.","ERROR!",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private static void webScraper(String userLink) throws IOException {
        Document doc = Jsoup.connect(userLink).get();
        Pattern pattern = Pattern.compile("[-/()—.\"',:;!?]"); // Filtering out these characters and replacing with nothing
        String title = doc.text().replaceAll(pattern.pattern(), "").toLowerCase();
        //System.out.println(title);
    }
}
