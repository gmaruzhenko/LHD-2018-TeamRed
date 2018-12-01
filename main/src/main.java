import azure.AzureAnalyzeImage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;

public class main {

    //temp driver

    public static void main(String[] args)
    {
        String urlinput = "https://17d0a3abnz42sjqxm3ewa9u1-wpengine.netdna-ssl.com/wp-content/uploads/2015/07/Whiteboard-Lead.jpg";
        AzureAnalyzeImage driver = new AzureAnalyzeImage();
        JSONObject test =  driver.AnalyzeImage(urlinput);
        
        System.out.println(test);



    }
}
