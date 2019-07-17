package grailsproject

class Category {

    int id
    String name
    static belongsTo = [siteId: Site]
    static hasMany = [Item]


    static constraints = {
    }

    Category(int id, String name) {
        this.id = id
        this.name = name
    }
}
