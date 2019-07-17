package grailsproject

class Item {

    int id
    String title
    String subtitle
    static belongsTo = [categoryId: Category]
    int price
    String imageUrl

    static constraints = {
    }

    List<Item> getItems(){
        return Item.findAll()
    }
}
