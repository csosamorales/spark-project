package main

import (
	"./controller/miapi"
	"github.com/gin-gonic/gin"
	"time"
)

const port = ":8080"

var router = gin.Default()

func main() {

	reloj := make(chan time.Time)
	go miapi.Limitter(reloj)

	router.GET("/user/:userID", func(c *gin.Context) { miapi.GetUserLimitter(c, reloj) })

	router.GET("/site/:siteID", func(c *gin.Context) { miapi.GetSiteLimitter(c, reloj) })

	router.GET("/country/:countryID", func(c *gin.Context) { miapi.GetCountryLimitter(c, reloj) })

	router.GET("/result/:userID", func(c *gin.Context) { miapi.GetResultLimitter(c, reloj) })

	router.GET("/resultch/:userID", miapi.GetResultChannel)

	//router.GET("/sites", miapi.GetSites)

	//router.GET("/categories/:siteID", )

	router.Run(port)
}
