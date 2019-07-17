package miapi

import (
	"../../services/miapi"
	"github.com/gin-gonic/gin"
	"net/http"

)

const (
	paramCountryID = "countryID"
)

func GetCountry(c *gin.Context) {

	countryID := c.Param(paramCountryID)

	country, apiError := miapi.GetCountryFromAPI(countryID)
	if apiError != nil {
		c.JSON(apiError.Status, apiError)
		return
	}
	c.JSON(http.StatusOK, country)
}
