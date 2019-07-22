package miapi

import (
	"../../domain/miapi"
	"../../utils/apierrors"
	"os"
	"sync"
)

func GetResultFromAPI(userID int64) (*miapi.Result, *apierrors.ApiError) {

	var wg sync.WaitGroup

	wg.Add(1)
	user, apiError := GetUserResultFromAPI(userID, &wg)
	wg.Wait()
	if apiError != nil {
		return nil, apiError
	}

	wg.Add(2)
	site, apiError := GetSiteResultFromAPI(user.SiteID, &wg)
	if apiError != nil {
		return nil, apiError
	}

	//wg.Add(1)
	country, apiError := GetCountryResultFromAPI(user.CountryID, &wg)
	wg.Wait()
	if apiError != nil {
		return nil, apiError
	}

	result := &miapi.Result{
		UserResult:    user,
		SiteResult:    site,
		CountryResult: country,
	}

	return result, nil

}

func GetResultChannelFromAPI(userID int64) (*miapi.Result, *apierrors.ApiError) {

	resultChannel := make(chan *miapi.Result)
	defer close(resultChannel)

	errorChannel := make(chan *apierrors.ApiError)
	defer close(errorChannel)

	go ControlarErrorChannel(errorChannel)

	user, apiError := GetUserFromAPI(userID)
	if apiError != nil {
		return nil, apiError
	}

	result := miapi.Result{ UserResult: user}

	go GetSiteResultChannelFromAPI(user.SiteID, resultChannel, errorChannel)
	go GetCountryResultChannelFromAPI(user.CountryID, resultChannel, errorChannel)

	for true {
		if result.SiteResult != nil && result.CountryResult != nil && result.UserResult != nil{
			break
		}

		channelWrite := <- resultChannel

		if channelWrite.CountryResult != nil {
			result.CountryResult = channelWrite.CountryResult
		} else if channelWrite.SiteResult != nil{
			result.SiteResult = channelWrite.SiteResult
		}

	}

	errorChannel <- &apierrors.ApiError{Status: 200, Message:""}

	return &result, nil

}

func ControlarErrorChannel( errorChannel chan *apierrors.ApiError)  {


	for true {
		error := <- errorChannel
		if error != nil && error.Status != 200{
			os.Exit(1)
		}

	}

}