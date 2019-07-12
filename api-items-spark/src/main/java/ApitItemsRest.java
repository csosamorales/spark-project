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

        put("/item", (request, response) -> {
            response.type("application/json");
            //Mapeamos lo que viene desde el json a una clase Item
            Item item = new Gson().fromJson(request.body(),Item.class);
            Item itemE = itemService.editItem(item);
            if(itemE != null){
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemE)));
            }
            else {
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJsonTree("error al editar")));
            }

        });

        post("/item", (request, response) -> {
            response.type("application/json");
            //Mapeamos lo que viene desde el json a una clase Item
            Item item = new Gson().fromJson(request.body(),Item.class);
            Item itemA = itemService.addItem(item);
            if(itemA != null){
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemA)));
            }
            else {
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJsonTree("error al insertar")));
            }

        });

        get("/item/:id" , (request, response) -> {
            String id = request.params("id");
            Item item = itemService.getItem(id);

            if(item != null){
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(item)));
            }
            else {
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJsonTree("error al editar")));
            }

        });

        delete("/item/:id", (request, response) -> {

            String id = request.params("id");
            Item item = itemService.deleteItem(id);

            if(item != null){
                return  new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(item)));
            }
            else {
                return  new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJsonTree("error al borrar item")));
            }
        });


    }
}
