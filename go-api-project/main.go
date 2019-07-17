package main

import (
	"encoding/json"
	"errors"
	"fmt"
	"io/ioutil"
	"net/http"
)

type Site struct {
	Id   string `json:"id"`
	Name string `json:"name"`
}

type ListingType struct {
	Id                string        `json:"id"`
	ConfigurationList Configuration `json:"configuration"`
	NotCategoriesIn   []    string  `json:"not_available_in_categories"`
}

type Configuration struct {
	Name string `json:"name"`
}

type Category struct {
	Id    string `json:"id"`
	Name  string `json:"name"`
	Items int    `json:"total_items_in_this_category"`
}

func main() {

	var siteId string

	fmt.Print("Por favor, ingrese el id de site que quiere consutlar: ")

	fmt.Scan(&siteId)

	var listing_type string

	fmt.Print("Por favor, ingrese el listing type: ")

	fmt.Scan(&listing_type)

	//validacion de parametros

	if siteId == "" {
		fmt.Println("No ha ingresado un id de site")
		return
	}

	if listing_type == "" {
		fmt.Println("No ha ingresado un id de listing type")
		return
	}

	//CONSULTA SITE

	urlSite := fmt.Sprintf("https://api.mercadolibre.com/sites/%s", siteId)

	dataSite, errSite := getInfo(urlSite)

	if errSite != nil {
		fmt.Println(fmt.Sprint("ERROR: ", errSite))
		return
	}

	//CONSULTA LISTING TYPE

	urlSite = fmt.Sprintf(urlSite + "/listing_types/" + listing_type)

	dataListing, errListing := getInfo(urlSite)

	if errListing != nil {
		fmt.Println(fmt.Sprint("ERROR: ", errListing))
		return
	}

	printSite(dataSite)
	printListingType(dataListing)

}

func getInfo(url string) ([]byte, error) {

	res, err := http.Get(url)

	if err != nil {
		return nil, err
	}

	data, err := ioutil.ReadAll(res.Body)

	if res.StatusCode == 404 {
		return nil, errors.New("Hubo un error al consultar la api")
	}

	if err != nil {
		return nil, err
	}

	return data, nil
}

func printSite(data []byte) {

	var site Site

	err := json.Unmarshal(data, &site)

	if err != nil {
		fmt.Println(fmt.Sprint("ERROR: ", err))
		return
	}

	fmt.Println("Site id: ", site.Id, "- Name: ", site.Name)

}

func printListingType(data []byte) {

	var listingType ListingType

	err := json.Unmarshal(data, &listingType)

	if err != nil {
		fmt.Println(fmt.Sprint("ERROR: ", err))
		return
	}

	fmt.Println("Listing type id: ", listingType.Id, "- Name: ", listingType.ConfigurationList.Name)
	for i := 0; i < len(listingType.NotCategoriesIn); i++ {
		getCategoryDetails(listingType.NotCategoriesIn[i])
	}

}

func getCategoryDetails(categoryId string) {

	urlCategory := fmt.Sprintf("https://api.mercadolibre.com/categories/%s", categoryId)

	res, err := getInfo(urlCategory)

	if err != nil {
		fmt.Println(fmt.Sprint("ERROR: ", err))
		return
	}
	printCategory(res)

}

func printCategory(data []byte) {

	var category Category

	err := json.Unmarshal(data, &category)

	if err != nil {
		fmt.Println(fmt.Sprint("ERROR: ", err))
		return
	}

	fmt.Println("ID: ", category.Id, "- Name: ", category.Name, "- Total items: ", category.Items)

}
