import azure.AzureAnalyzeImage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class main {

    private static String host = "https://westus.api.cognitive.microsoft.com";
    private static String path = "/vision/v1.0/analyze?visualFeatures=Categories&language=en";
    private static String accessKey = "96e7756c7e1741ec9c516810a191ee89";


    public static void main(String[] args) throws Exception
    {
        ArrayList<String> inputUrls = new ArrayList<>();

        String urlinput = "https://17d0a3abnz42sjqxm3ewa9u1-wpengine.netdna-ssl.com/wp-content/uploads/2015/07/Whiteboard-Lead.jpg";
        String urlinput2 ="http://1.bp.blogspot.com/_if-mbCZlM78/SrHkHQfA86I/AAAAAAAAAY4/maWcEeYFHkM/s400/the_nebelung_breeders.jpg";


        inputUrls.add(urlinput);
        inputUrls.add(urlinput2);
        System.out.println(produceAllCleanBoards(inputUrls));


        //AzureAnalyzeImage driver = new AzureAnalyzeImage();
        //String test =  driver.analyzeImage(urlinput);
        //driver = new AzureAnalyzeImage();
        //String test2 = driver.analyzeImage(urlinput2);
 /*
        String matchplz = "[{\"categories\":[{\"name\":\"others_\",\"score\":0.0390625},{\"name\":\"people_\",\"score\":0.3359375}],\"requestId\":\"9700c9fc-ec34-4d56-821f-ae0c5a5b9dfb\",\"metadata\":{\"width\":700,\"height\":366,\"format\":\"Jpeg\"}}";
//        if(!blocked(test))
//            legalimages.add(test);
//        if(!blocked(test2))
//            legalimages.add(test2);


        System.out.println(blocked(matchplz));
        */



    }
    public static boolean blocked (String input){
        return input.length()>input.replaceAll("people","").length();
    }

    /**
     * given am arraylist of legal urls produces all
     * @param urlList
     * @return
     */
    public static ArrayList<String> produceAllCleanBoards (ArrayList<String> urlList){
        //ArrayList<String> result = new ArrayList<>(urlList.stream().map(x-> analyzeImage(x)).filter(x->blocked(x)).collect(Collectors.toList()));
        //urlList.stream().map(x-> analyzeImage(x)).filter(x->blocked(x)).collect(Collectors.toList());

        ArrayList<String> result = new ArrayList<>();
        for (String s:urlList ) {
            String azureOutput = analyzeImage(s);
            if (!blocked(azureOutput))
                result.add(s);


        }
        return result;
    }
    /**
     * Send an image to Azure Cognitive Services for processing
     *
     * @param input for analysis
     * @return a JSON string (URLS) output
     * @throws Exception
     */

    public static String analyzeImage(String input) {


        /*
        String input1 = "{\"url\":\"http://1.bp.blogspot.com/_if-mbCZlM78/SrHkHQfA86I/AAAAAAAAAY4/maWcEeYFHkM/s400/the_nebelung_breeders.jpg\"\n" +
                "}";
           */
        try {


            String inputformatted = "{\"url\":\"" + input + "\"\n" + "}";
            byte[] encoded_text = inputformatted.getBytes("UTF-8");

            URL url = new URL(host + path);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
            connection.setRequestProperty("Content-Length", String.valueOf(encoded_text.length));
            connection.setDoOutput(true);

            OutputStream wr = connection.getOutputStream();

            wr.write(encoded_text, 0, encoded_text.length);

            wr.flush();
            wr.close();

            StringBuilder response = new StringBuilder();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            return response.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
