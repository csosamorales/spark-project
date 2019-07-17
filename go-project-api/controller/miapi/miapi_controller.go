package miapi

import (
	"../../services/miapi"
	"../../utils/apierrors"
	"github.com/gin-gonic/gin"
	"net/http"
	"strconv"
)

const (
	paramUserID = "userID"
)

func GetUser(c *gin.Context) {

	userID := c.Param(paramUserID)
	id, err := strconv.ParseInt(userID, 10, 64)

	if err != nil {
		apiError := &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusBadRequest,
		}
		c.JSON(apiError.Status, apiError)
		return
	}

	user, apiError := miapi.GetUserFromAPI(id)
	if apiError != nil {
		c.JSON(apiError.Status, apiError)
		return
	}
	c.JSON(http.StatusOK, user)
}
