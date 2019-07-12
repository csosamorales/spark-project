import com.google.gson.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemServiceMapImpl implements ItemService {

    private HashMap<String,Item> itemsMap;
    private HashMap<String,Currency> currencies;

    public ItemServiceMapImpl() {
        this.itemsMap = new HashMap<>();
        currencies = new HashMap<>();
        getCurrencies();
    }

    public List<Item> getItems(String q, String title, String sort, String orderBy, String minPriceRange, String maxPriceRange, String tag) throws IOException {


        List<Item> listaItems = new ArrayList<>();

        boolean priceRange = minPriceRange != null && !minPriceRange.isEmpty() && maxPriceRange != null && !maxPriceRange.isEmpty();
        boolean sortKey = sort != null && !sort.isEmpty() && orderBy != null && !orderBy.isEmpty();

        if(q != null && !q.isEmpty()){

            itemsMap.clear();

            boolean filterTag = false;

            q =  q.replace(" ","%20");
            String url = "https://api.mercadolibre.com/sites/MLA/search?q="+q;

            if(sortKey){
                if(sort.equals("price")){
                    url += "&sort=" + sort + "_" + orderBy;
                } else {
                    url += "&sort=" + sort;
                }
            }

            if(priceRange){
                if(tag != null && !tag.isEmpty()){
                    //tengo que filtrar por tag "a mano"
                    filterTag = true;
                }
                else {
                    url += "&price="+minPriceRange+"-"+maxPriceRange;
                }

            }
            else if(tag != null && !tag.isEmpty()){
                url += "tag=good_quality_thumbnail";
            }

            String result = Connection.getResultApi(url);

            listaItems = parseResponseToJson(result);

            if (filterTag){
                listaItems = listaItems.stream()
                        .filter(x -> x.getTags().contains("good_quality_thumbnail"))
                        .collect(Collectors.toList());
            }
        }
        else{

            //trabajo sobre el hashmap

            if(tag != null && !tag.isEmpty()){
                listaItems = itemsMap.values().stream()
                        .filter(x -> x.getTags().contains("good_quality_thumbnail"))
                        .collect(Collectors.toList());
            }

            if(priceRange){
                listaItems = listaItems.stream()
                        .filter(x -> x.getPrice() >= Float.parseFloat(minPriceRange) && x.getPrice() <= Float.parseFloat(maxPriceRange))
                        .collect(Collectors.toList());
            }

            if(sortKey){
                //determinar criterio de ordenamiento y columna de filtro
                if(sort.equals("listing_type")){
                    if(orderBy.equals("asc")){
                        listaItems = listaItems.stream()
                                .sorted(Comparator.comparing(Item::getListing_type_id))
                                .collect(Collectors.toList());
                    }
                    else if(orderBy.equals("desc")){
                        listaItems = listaItems.stream()
                                .sorted(Comparator.comparing(Item::getListing_type_id).reversed())
                                .collect(Collectors.toList());
                    }
                }
                else if(sort.equals("price")){
                    if(orderBy.equals("asc")){
                        listaItems = listaItems.stream()
                                .sorted(Comparator.comparing(Item::getPrice))
                                .collect(Collectors.toList());
                    }
                    else if(orderBy.equals("desc")){
                        listaItems = listaItems.stream()
                                .sorted(Comparator.comparing(Item::getPrice).reversed())
                                .collect(Collectors.toList());
                    }
                }

            }

        }


        return  listaItems;
    }

    public List<Item> parseResponseToJson(String result){

        List<Item> listaItems = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(result);

        tree = tree.getAsJsonObject().get("results");
        JsonArray array = tree.getAsJsonArray();

        Currency currency;
        Item item;

        for (JsonElement element : array) {

            if (element.isJsonObject()) {

                JsonObject itemJson = element.getAsJsonObject();

                currency = currencies.get(itemJson.get("currency_id").getAsString());
                ArrayList<String> listTags = new ArrayList<String>();
                JsonArray tags = itemJson.getAsJsonArray("tags");

                tags.forEach(tag -> {
                    listTags.add(tag.toString().replace("\"",""));
                });

                //new Currency(currency.getSymbol(),currency.getDescription(),currency.getDecimal_places())
                item = new Item(itemJson.get("id").getAsString(),itemJson.get("site_id").getAsString(),
                        itemJson.get("title").getAsString(),itemJson.get("price").getAsFloat(),currency,
                        itemJson.get("listing_type_id").getAsString(),itemJson.get("stop_time").getAsString(),
                        itemJson.get("thumbnail").getAsString(),listTags);

                listaItems.add(item);
                itemsMap.put(item.getId(),item);
            }
        }

        return  listaItems;
    }

    public List<String> getItemsTitle(List<Item> itemList){

        List<String> titulos = itemList.stream().map(item -> item.getTitle()).collect(Collectors.toList());

        return  titulos;
    }


    @Override
    public void getCurrencies() {

        String url = "https://api.mercadolibre.com/currencies";
        String result = null;
        try {
            result = Connection.getResultApi(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonElement tree = parser.parse(result);

        JsonArray array = tree.getAsJsonArray();

        Gson gson = new Gson();
        Currency currencyL;

        for (JsonElement currency: array) {

            JsonObject item = currency.getAsJsonObject();

            currencyL = new Gson().fromJson(currency,Currency.class);
            currencies.put(item.get("id").getAsString(),currencyL);

        }

    }

    @Override
    public Item getItem(String id) {
        return itemsMap.get(id);
    }

    @Override
    public Item addItem(Item item) {
       return itemsMap.put(item.getId(),item);
    }

    @Override
    public Item editItem(Item item) {
        return itemsMap.put(item.getId(),item);
    }

    @Override
    public Item deleteItem(String id) {
        return itemsMap.remove(id);
    }
}
