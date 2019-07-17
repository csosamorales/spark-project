package grailsproject

class Site {

    int id
    String name
    static hasMany = [Category]

    static constraints = {
    }

    Site(Number id, String name) {
        this.id = id
        this.name = name
    }
}
