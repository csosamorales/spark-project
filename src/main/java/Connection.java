import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Connection {

    public static String getResultApi(String urlML) throws IOException {

        String result = "";

        URL url = new URL(urlML);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection connection = null;

        if(urlConnection instanceof HttpURLConnection){
            connection = (HttpURLConnection) urlConnection;
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String current = null;

        while ((current = in.readLine()) != null){
            result += current;
        }

        return result;
    }
}
