package miapi

import (
	"../../utils"
	"../../utils/apierrors"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

type Country struct {
	ID                string `json:"id"`
	Name              string `json:"name"`
	Locale            string `json:"locale"`
	CurrencyID        string `json:"currency_id"`
	DecimalSeparator  string `json:"decimal_separator"`
	ThousandSeparator string `json:"thousand_separator"`
	TimeZone          string `json:"time_zone"`
	GeoInformation    interface{} `json:"geo_information"`
	States []States `json:"states"`
}

type States struct {
	ID   string `json:"id"`
	Name string `json:"name"`
}

func (country *Country) GetCountry() *apierrors.ApiError {

	if country.ID == "" {
		return &apierrors.ApiError{
			Message: "El id del pais es 0",
			Status:  http.StatusBadRequest,
		}
	}

	url := fmt.Sprint(utils.UrlCountry, country.ID)

	response, err := http.Get(url)
	if err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	data, err := ioutil.ReadAll(response.Body)
	if err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	if err := json.Unmarshal(data, &country); err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	return nil
}
