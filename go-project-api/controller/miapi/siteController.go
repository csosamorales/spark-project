package miapi

import (
"../../services/miapi"
"github.com/gin-gonic/gin"
"net/http"

)

const (
	paraSiteID = "siteID"
)

func GetSite(c *gin.Context) {

	siteID := c.Param(paraSiteID)

	country, apiError := miapi.GetSiteFromAPI(siteID)
	if apiError != nil {
		c.JSON(apiError.Status, apiError)
		return
	}
	c.JSON(http.StatusOK, country)
}

/*
func GetSites(c *gin.Context)  {

	sites, apiError := miapi.GetSitesFromAPI()
	if apiError != nil {
		c.JSON(apiError.Status, apiError)
		return
	}
	c.JSON(http.StatusOK, sites)
}
*/

