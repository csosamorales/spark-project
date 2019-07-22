package miapi

import (
	"github.com/gin-gonic/gin"
	"time"
)


func Limitter(reloj chan time.Time) {

	for tick := range time.Tick(10* time.Second){
		reloj<-tick
	}
}

func GetUserLimitter(c *gin.Context, reloj chan time.Time) {

	<- reloj

	GetUser(c)
}

func GetSiteLimitter(c *gin.Context, reloj chan time.Time) {

	<- reloj

	GetSite(c)
}

func GetCountryLimitter(c *gin.Context, reloj chan time.Time) {

	<- reloj

	GetCountry(c)
}

func GetResultLimitter(c *gin.Context, reloj chan time.Time) {

	<- reloj

	GetResult(c)
}




