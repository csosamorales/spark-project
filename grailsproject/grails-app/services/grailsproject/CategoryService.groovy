package grailsproject

import grails.transaction.Transactional

@Transactional
class CategoryService {

    def saveCategory(Site site, int id, String name) {
        new Category(id:id, name: name, siteId: site).save()

    }
}
