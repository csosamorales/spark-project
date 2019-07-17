import grailsproject.Category
import grailsproject.CategoryService
import grailsproject.ItemService
import grailsproject.Site
import grailsproject.SiteService
import grailsproject.User
import grailsproject.UserService

class BootStrap {

    UserService userService = new UserService()
    SiteService siteService = new SiteService()
    CategoryService categoryService = new CategoryService()
    ItemService itemService = new ItemService()

    def init = { servletContext ->
        userService.saveUser(1,"vacio","Camila")
        userService.saveUser(2,"vacio","Melisa")

        siteService.saveSite(1,"MLA")
        siteService.saveSite(2,"MLB")
        siteService.saveSite(3, "MPE")

        categoryService.saveCategory(new Site(1,"MLA"),1,"Ropa")
        categoryService.saveCategory(new Site(1,"MLA"),2,"Telefonos")
        categoryService.saveCategory(new Site(1,"MLA"),3,"Autos")
        categoryService.saveCategory(new Site(2,"MLB"),4,"Casas")

        itemService.saveItem(new Category(2,"Telefonos"),1,"Iphone X","Iphone X 64 GB",40000,"http://mla-s1-p.mlstatic.com/994758-MLA31278644911_062019-I.jpg")
        itemService.saveItem(new Category(2,"Telefonos"),1,"Samsung S10","Samsung S10 128 GB",50000,"http://mla-s2-p.mlstatic.com/887244-MLA31063569198_062019-I.jpg")
        itemService.saveItem(new Category(4,"Casas"),1,"Casa emprendimiento","Casa de emprendimiento a la venta",14567900,"http://mla-s1-p.mlstatic.com/782362-MLA30424832611_052019-I.jpg")

    }
    def destroy = {
    }
}
