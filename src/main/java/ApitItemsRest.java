import com.google.gson.Gson;
import java.util.List;

import static spark.Spark.*;

public class ApitItemsRest {

    public static void main(String[] args){

        final ItemService itemService = new ItemServiceMapImpl();

        get("/items", (request, response) -> {
            response.type("application/json");
            String query = request.queryParams("q");
            String title = request.queryParams("title");
            String sort = request.queryParams("sort");
            String order = request.queryParams("order");
            String tag = request.queryParams("tag");
            String minPrice = request.queryParams("minPrice");
            String maxPrice = request.queryParams("maxPrice");


            List<Item> itemList = itemService.getItems(query,title,sort,order,minPrice,maxPrice,tag);


            if(itemList != null){
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemList)));
            }
            else {
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJsonTree("error al buscar items")));
            }

        });




    }
}
