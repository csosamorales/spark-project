package grailsproject

import grails.transaction.Transactional

@Transactional
class ItemService {

    def saveItem(Category category, int id, String title, String subtitle, int price, String imageUrl) {
        new Item(id: id, title: title, subtitle: subtitle, categoryId: category, price: price, imageUrl: imageUrl).save()
    }

    List<Item> getItems(){
        return Item.findAll()
    }
}
