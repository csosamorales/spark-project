package main

import (
	"./controller/miapi"
	"github.com/gin-gonic/gin"
)

const port = ":8080"

var router = gin.Default()

func main() {

	router.GET("/user/:userID", miapi.GetUser)

	router.GET("/site/:siteID", miapi.GetSite)

	router.GET("/country/:countryID", miapi.GetCountry)

	router.GET("/result/:userID", miapi.GetResult)

	//router.GET("/sites", miapi.GetSites)

	//router.GET("/categories/:siteID", )

	router.Run(port)
}
