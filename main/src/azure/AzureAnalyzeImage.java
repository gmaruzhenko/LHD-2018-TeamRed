
package azure;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class AzureAnalyzeImage {

    private static String host = "https://westus.api.cognitive.microsoft.com";
    private static String path = "/vision/v1.0/analyze?visualFeatures=Categories&language=en";
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

    public static Gson azureSndRcv(String input) throws Exception {


        String input1 = "{\"url\":\"http://1.bp.blogspot.com/_if-mbCZlM78/SrHkHQfA86I/AAAAAAAAAY4/maWcEeYFHkM/s400/the_nebelung_breeders.jpg\"\n" +
                "}";
        byte[] encoded_text = input1.getBytes("UTF-8");

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
        System.out.println(response);

        Gson output = new Gson();
       // output.fromJson(response);

        return output;
    }

    

}