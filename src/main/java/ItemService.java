import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface ItemService {

    public List<Item> getItems(String q, String title, String sort, String orderBy, String minPriceRange, String maxPriceRange, String tag) throws IOException;
    public void getCurrencies() throws IOException;

}
