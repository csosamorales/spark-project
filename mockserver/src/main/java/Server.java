import com.google.gson.Gson;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class Server {

    static MockServerClient mockServerClient = startClientAndServer(8081);
    static Gson gson;

    public static void consulta(String method, String path, int statusCode, String content, String body, int delay) {

        mockServerClient.when(HttpRequest.request()
                .withMethod(method)
                .withPath(path)
        ).respond(HttpResponse.response()
                .withStatusCode(statusCode)
                .withHeader(new Header("Content-type", content))
                .withBody(body)
                .withDelay(new Delay(TimeUnit.MILLISECONDS, delay))
        );

    }

    public static void main(String[] args) {

        gson = new Gson();
        Item item = new Item(123456);
        User user = new User();
        Site site = new Site();
        Country country = new Country();

        consulta("GET", "/items/.*", 200,
                "application/json; charset=utf-8", gson.toJson(item), 1000);

        consulta("GET", "/users/.*", 200,
                "application/json; charset=utf-8", user.toString(), 1000);

        consulta("GET", "/sites/.*", 200,
                "application/json; charset=utf-8", site.toString(), 1000);

        consulta("GET", "/countries/.*", 200,
                "application/json", country.toString(), 1000);

    }
}
