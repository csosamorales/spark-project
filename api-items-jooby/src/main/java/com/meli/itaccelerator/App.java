package com.meli.itaccelerator;

import org.jooby.*;
import org.jooby.apitool.ApiTool;
import org.jooby.json.Jackson;

import java.util.List;

/**
 * @author jooby generator
 */
public class App extends Jooby {

  {

    use(new Jackson());


    /**
     * Lo referido a un item
     */
    path("/api/items" , () -> {
      /**
       * Lista los items ordenados por id
       *
       * @param min inicio del listado (usado para paginacion).
       * @param max fin del listado (usado para paginacion).
       * @return Devuelve los items ordenados por id.
       */
      get(req -> {
        int min  = req.param("min").intValue(0);
        int max  = req.param("max").intValue(50);

        Service service = req.require(Service.class);
        List<Item> items = service.findall(min,max);

        return items;
      });

      /**
       * Encuentra un item pro id.
       *
       * @param id El identeificador del item
       * @return Devuelve el codigo <code>200</code> con un solo item o un codigo <code>400</code>
       * cuando no se encuentra el item
       */
      get("/:id", req -> {

        int id = req.param("id").intValue(0);
        Service service = req.require(Service.class);
        Item item = service.find(id);

        if(item == null){
          throw  new Err(Status.NOT_FOUND);
        }

        return item;

      });

      /**
       * Agrega un item a la coleccion
       *
       * @param body El item que se agregara a la coleccion.
       * @return El item agregado a la coleccion
       */
      post(req -> {
        Item item = req.body().to(Item.class);
        Service service = req.require(Service.class);
        service.save(item);

        return item;
      });

      /**
       * Actualiza un item de la coleccion
       *
       * @param body El item que se actualiza de la coleccion.
       * @return El item actualizado a la coleccion
       */
      put(req -> {
        Item item = req.body().to(Item.class);
        Service service = req.require(Service.class);
        service.save(item);

        return item;
      });

      /**
       * Elimina un item de la coleccion
       *
       * @param id El item id del item que se  borra de la coleccion.
       * @return Devuelve un codigo <code>204</code>
       */
      delete( "/:id" , req -> {
        int id = req.param("id").intValue(0);
        Service service = req.require(Service.class);
        service.delete(id);

        return Results.noContent();
      });

    });

    use(new ApiTool().swagger("/swagger"));


  }

  public static void main(final String[] args) {

    run(App::new, args);

  }

}
