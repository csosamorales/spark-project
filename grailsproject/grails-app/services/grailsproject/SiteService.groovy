package grailsproject

import grails.transaction.Transactional

@Transactional
class SiteService {

    def saveSite(int id, String name) {
        new Site(id:id, name: name).save()
    }
}
