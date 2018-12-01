package azure;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class AzureAnalyzeImage {

    private static String host = "https://westus.api.cognitive.microsoft.com";
    private static String path = "/vision/v1.0/analyze";
    private static String accessKey = "96e7756c7e1741ec9c516810a191ee89";

    /**
     * @param host
     */
    private static void setAzureHost(String host) {
        AzureAnalyzeImage.host = host;
    }

    /**
     * Send an image to Azure Cognitive Services for processing
     *
     * @param input for analysis
     * @return a JSON string (URLS) output
     * @throws Exception
     */
    private static String azureSndRcv(String input) throws Exception {
        //String text = new Gson().toJson(textCollection);
        byte[] encoded_text = text.getBytes("UTF-8");

        URL url = new URL(host + path);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/json");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", accessKey);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
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

        return "no implement";
    }

    /**
     * Package JSON data received from Azure Cognitive Services into a
     * more usable list
     *
     * @param json_text obtained from Azure Cognitive Services as a request
     *                  response
     * @return a list of SentimentResponses after parsing the JSON data
     */
    private static List<SentimentResponse> getSentiments(String json_text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        List<SentimentResponse> responses = new LinkedList<>();
        Type collectionType = new TypeToken<List<SentimentResponse>>() {
        }.getType();
        responses = (new Gson()).fromJson(json.get("documents"), collectionType);
        return responses;
    }

    /**
     * Primary interface method for processing a sentiment analysis request
     *
     * @param textCollection is not null
     * @return the sentiment scores obtained from Azure Cognitive Services
     */
    public static List<SentimentResponse> getSentiments(TextCollection textCollection) {
        List<SentimentResponse> scores = new LinkedList<>();
        try {
            scores = azureSndRcv(textCollection);
        } catch (Exception e) {
            System.out.println(e);
        }
        return scores;
    }
    private Gson AnalyzeImage(String imageUrl) {
        try (CloseableHttpClient httpclient = HttpClientBuilder.create().build())
        {
            // Create the URI to access the REST API call for Analyze Image.
            String uriString = uriBasePreRegion +
                    String.valueOf(subscriptionRegionComboBox.getSelectedItem()) +
                    uriBasePostRegion + uriBaseAnalyze;
            URIBuilder builder = new URIBuilder(uriString);

            // Request parameters. All of them are optional.
            builder.setParameter("visualFeatures", "Categories,Description,Color,Adult");
            builder.setParameter("language", "en");

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKeyTextField.getText());

            // Request body.
            StringEntity reqEntity = new StringEntity("{\"url\":\"" + imageUrl + "\"}");
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            // If we got a response, parse it and display it.
            if (entity != null)
            {
                // Return the JSONObject.
                String jsonString = EntityUtils.toString(entity);
                return new JSONObject(jsonString);
            } else {
                // No response. Return null.
                return null;
            }
        }
        catch (Exception e)
        {
            // An error occurred. Print error message and return null.
            System.out.println(e.getMessage());
            return null;
        }
    }
}