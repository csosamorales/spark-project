import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ItemServiceFromJsonImpl implements ItemService {

    private HashMap<String, Currency> currencies;
    private String fileName = "src/main/resources/items.json";


    public ItemServiceFromJsonImpl() {
        currencies = new HashMap<>();
        getCurrencies();
    }

    @Override
    public List<Item> getItems(String q, String sort, String orderBy, String minPriceRange, String maxPriceRange, String tag) throws IOException {
        List<Item> listaItems = new ArrayList<>();

        boolean priceRange = minPriceRange != null && !minPriceRange.isEmpty() && maxPriceRange != null && !maxPriceRange.isEmpty();
        boolean sortKey = sort != null && !sort.isEmpty() && orderBy != null && !orderBy.isEmpty();

        if (q != null && !q.isEmpty()) {

            q = q.replace(" ", "%20");
            String url = "https://api.mercadolibre.com/sites/MLA/search?q=" + q;

            String result = Connection.getResultApi(url);

            listaItems = parseResponseToJson(result);

            writeOnJsonFile(listaItems);

        } else {

            //leer del archivo
            listaItems = readJsonFile();
        }

        //trabajo sobre el hashmap

        if (tag != null && !tag.isEmpty()) {
            listaItems = listaItems.stream()
                    .filter(x -> x.getTags().contains("good_quality_thumbnail"))
                    .collect(Collectors.toList());
        }

        if (priceRange) {
            listaItems = listaItems.stream()
                    .filter(x -> x.getPrice() >= Float.parseFloat(minPriceRange) && x.getPrice() <= Float.parseFloat(maxPriceRange))
                    .collect(Collectors.toList());
        }

        if (sortKey) {
            //determinar criterio de ordenamiento y columna de filtro
            if (sort.equals("listing_type")) {
                if (orderBy.equals("asc")) {
                    listaItems = listaItems.stream()
                            .sorted(Comparator.comparing(Item::getListing_type_id))
                            .collect(Collectors.toList());
                } else if (orderBy.equals("desc")) {
                    listaItems = listaItems.stream()
                            .sorted(Comparator.comparing(Item::getListing_type_id).reversed())
                            .collect(Collectors.toList());
                }
            } else if (sort.equals("price")) {
                if (orderBy.equals("asc")) {
                    listaItems = listaItems.stream()
                            .sorted(Comparator.comparing(Item::getPrice))
                            .collect(Collectors.toList());
                } else if (orderBy.equals("desc")) {
                    listaItems = listaItems.stream()
                            .sorted(Comparator.comparing(Item::getPrice).reversed())
                            .collect(Collectors.toList());
                }
            }


        }


        return listaItems;

    }

    public List<Item> parseResponseToJson(String result) {

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
                    listTags.add(tag.toString().replace("\"", ""));
                });

                //new Currency(currency.getSymbol(),currency.getDescription(),currency.getDecimal_places())
                item = new Item(itemJson.get("id").getAsString(), itemJson.get("site_id").getAsString(),
                        itemJson.get("title").getAsString(), itemJson.get("price").getAsFloat(), currency,
                        itemJson.get("listing_type_id").getAsString(), itemJson.get("stop_time").getAsString(),
                        itemJson.get("thumbnail").getAsString(), listTags);

                listaItems.add(item);

            }
        }

        return listaItems;
    }

    private void writeOnJsonFile(List<Item> items){

        try {
            try (FileOutputStream fos = new FileOutputStream(fileName);
                 OutputStreamWriter isr = new OutputStreamWriter(fos,
                         StandardCharsets.UTF_8)) {

                Gson gson = new Gson();

                gson.toJson(items, isr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Item> readJsonFile() {

        Path path = new File(fileName).toPath();
        List<Item> itemList = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(path,
                StandardCharsets.UTF_8)) {

            itemList = Arrays.asList(new Gson().fromJson(reader, Item[].class));

        } catch (IOException e) {
            return null;
        }

        return itemList;
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

        for (JsonElement currency : array) {

            JsonObject item = currency.getAsJsonObject();

            currencyL = new Gson().fromJson(currency, Currency.class);
            currencies.put(item.get("id").getAsString(), currencyL);

        }

    }


    @Override
    public Item getItem(String id) {
        Item item = new Item();

        List<Item> items = readJsonFile();
        item = items.stream()
                .filter(itemStream -> itemStream.getId()
                        .toUpperCase() == id.toUpperCase())
                .findAny()
                .orElse(null);


        return item;
    }

    @Override
    public Item addItem(Item item) {
        boolean added = false;

        List<Item> items = readJsonFile();
        if (items != null) {
            added = items.add(item);
        } else {
            return null;
        }
        return item;
    }

    @Override
    public Item editItem(Item item) {
        List<Item> itemList = readJsonFile();
        itemList.remove(item);
        itemList.add(item);
        writeOnJsonFile(itemList);
        return item;
    }

    @Override
    public Item deleteItem(String id) {
        List<Item> itemsList = readJsonFile();
        Item item = itemsList.stream()
                .filter(itemList -> itemList.getId().toUpperCase() == id.toUpperCase())
                .findAny()
                .orElse(null);
        itemsList.remove(item);
        writeOnJsonFile(itemsList);
        return item;
        
    }
}
