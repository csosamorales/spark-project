package grailsproject

class ItemController {

    ItemService itemService = new ItemService()

    def index() {
        List<Item> items = itemService.getItems()
        [listaItems:items]
    }
}
