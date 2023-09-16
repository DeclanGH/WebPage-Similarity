import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createGUI());
    }

    private static void createGUI(){
        String userLink = JOptionPane.showInputDialog(null, "Paste your website's link here");

        try{
            URL url = new URL(userLink); // Creating a URL from the user input
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

    private static void webScraper(String userLink) {
    }
}
