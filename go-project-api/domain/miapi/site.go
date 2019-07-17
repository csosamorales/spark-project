package miapi

import (
	"../../utils"
	"../../utils/apierrors"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

type Site struct {
	ID                 string   `json:"id"`
	Name               string   `json:"name"`
	CountryID          string   `json:"country_id"`
	SalesFeedMode      string   `json:"sales_feed_mode"`
	MercadopagoVersion int      `json:"mercadopago_version"`
	DefaultCurrencyID  string   `json:"default_currency_id"`
	ImmediatePayment   string   `json:"inmmediate_payment"`
	PaymentMethodIDS   []string `json:"payment_method_ids"`
	Settings           struct {
		IdentificationTypes      []string    `json:"identification_types"`
		TaxpayerTypes            []string    `json:"taxpayer_types"`
		IdentificationTypesRules interface{} `json:"identification_types_rules"`
	}
	Currencies []struct {
		ID     string `json:"id"`
		Symbol string `json:"symbol"`
	}
	Categories []struct {
		ID   string `json:"id"`
		Name string `json:"name"`
	}
}

//type Sites [] Site

func (site *Site) GetSite() *apierrors.ApiError {

	if site.ID == "" {
		return &apierrors.ApiError{
			Message: "El id del site es nulo",
			Status:  http.StatusInternalServerError,
		}

	}
	url := fmt.Sprintf(utils.UrlSite, site.ID)

	res, err := http.Get(url)

	if err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	data, err := ioutil.ReadAll(res.Body)

	if err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	if err := json.Unmarshal(data, &site); err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	return nil

}

/*
func (sites *Sites) GetSites() *apierrors.ApiError {

	res, err := http.Get(urlSites)

	if err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	data, err := ioutil.ReadAll(res.Body)

	if err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	if err := json.Unmarshal(data, &sites); err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	return nil
}
*/
