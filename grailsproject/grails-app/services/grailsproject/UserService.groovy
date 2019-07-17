package grailsproject

import grails.transaction.Transactional

@Transactional
class UserService {

    def saveUser(int id, String visitDetail, String name) {
        Date fechaInicio = new Date()
        Date fechaFin = new Date()
        new User(id: id, name: name,dateFrom: fechaInicio, dateTo: fechaFin, totalVisits: 0, visitDetail: visitDetail).save()

    }
}
