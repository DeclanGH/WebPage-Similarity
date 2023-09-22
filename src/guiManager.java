import org.jetbrains.annotations.NotNull;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class guiManager {
    private JFormattedTextField userInput;
    private JPanel panel;
    private JButton runButton;
    private JButton copyButton1;
    private JTextField outputLink1;
    private JTextField outputLink2;
    private JButton copyButton2;
    private JTextPane textPane1;
    private JTextPane textPane2;

    public JPanel getPanel() {
        return this.panel;
    }

    public guiManager() {
        runButton.addActionListener(e -> {
            try {
                String userInput = this.userInput.getText();
                compareLinks(userInput);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        copyButton1.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(outputLink1.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection,null);
        });
        copyButton2.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(outputLink2.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection,null);
        });
    }

    private static JsonObject readFile() throws IOException {

        // Properties
        Properties properties = new Properties();
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("config.properties");
        }catch (FileNotFoundException e){
            System.out.println("change the filepath in `config.properties` to match yours.");
        }
        properties.load(fis);

        // GET json file location (change the .json file name below to match yours)
        String filePath = properties.getProperty("filepath") + File.separator + "MyLinks.json";
        File inputFile = new File(filePath);

        // READ json file
        InputStream inputStream = null;
        try{
            inputStream = new FileInputStream(inputFile);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        JsonReader reader = Json.createReader(inputStream);
        JsonObject object = reader.readObject();
        inputStream.close();
        reader.close();

        return object;
    }

    public void compareLinks(String userInput) throws IOException {
        WebScraper ws1 = new WebScraper();
        WebScraper ws2 = new WebScraper();

        // initialize with a random low score less than zero
        double maxSimilarity = -1000;
        double maxSimilarity2 = -500;
        int indexOfMaxSimilarity = -1;
        int indexOfMaxSimilarity2 = -1;

        JsonArray arrayOfLinks = null;
        try {
            arrayOfLinks = readFile().getJsonArray("Links");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] wordsInUserLink = ws1.webScrape(userInput);

        for(int i=0; i<10; i++){
            String[] wordsInMyLinks = ws2.webScrape(arrayOfLinks.getString(i));
            double currSimilarityScore = doCosineSimilarity(wordsInUserLink,wordsInMyLinks);
            if(currSimilarityScore > maxSimilarity2){ // score must be greater than the smallest max to be considered
                maxSimilarity2 = currSimilarityScore;
                indexOfMaxSimilarity2 = i;
                if(maxSimilarity2 > maxSimilarity){ // swap if greater than our main max (maxSimilarity)
                    double temp = maxSimilarity2;
                    maxSimilarity2 = maxSimilarity;
                    maxSimilarity = temp;
                    int temp1 = indexOfMaxSimilarity2; // swap indexes too...
                    indexOfMaxSimilarity2 = indexOfMaxSimilarity;
                    indexOfMaxSimilarity = temp1;
                }
            }
        }

        // Convert to string and display
        textPane1.setText((int)(maxSimilarity * 100)+"%");
        textPane2.setText((int)(maxSimilarity2 * 100)+"%");

        outputLink1.setText(arrayOfLinks.getString(indexOfMaxSimilarity));
        outputLink2.setText(arrayOfLinks.getString(indexOfMaxSimilarity2));
    }

    private static double doCosineSimilarity(String @NotNull [] a, String[] b){

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

        // Tweaked the formula to give only necessary data
        similarity = numerator/(Math.sqrt(denominatorA)*Math.sqrt(denominatorB));

        return similarity;
    }
}
