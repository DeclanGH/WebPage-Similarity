import javax.json.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


public class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainClass::compareLinks);
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

    private static void compareLinks() {
        WebScraper ws1 = new WebScraper();
        WebScraper ws2 = new WebScraper();
        double maxSimilarity = -1000; // initialize with a random low score less than zero
        int indexOfMaxSimilarity = -1;
        try {
            JsonArray arrayOfLinks = readFile().getJsonArray("Links");
            String userLink = JOptionPane.showInputDialog(null, "Paste your website's link here");
            String[] wordsInUserLink = ws1.webScrape(userLink);
            for(int i=0; i<10; i++){
                String[] wordsInMyLinks = ws2.webScrape(arrayOfLinks.getString(i));
                double currSimilarityScore = doCosineSimilarity(wordsInUserLink,wordsInMyLinks);
                if(currSimilarityScore > maxSimilarity){
                    maxSimilarity = currSimilarityScore;
                    indexOfMaxSimilarity = i;
                }
            }
            JOptionPane.showMessageDialog(null,"We found a "+ (int)(maxSimilarity*100)
                    +"% match with this link " + arrayOfLinks.getString(indexOfMaxSimilarity),"Congrats!",JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null,"The link you provided does not exist.","ERROR!",JOptionPane.ERROR_MESSAGE);
            //compareLinks();
        }
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

        // Tweaked the formula to give only necessary data
        similarity = numerator/(Math.sqrt(denominatorA)*Math.sqrt(denominatorB));

        return similarity;
    }
}
