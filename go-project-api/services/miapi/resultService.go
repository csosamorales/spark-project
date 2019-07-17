package miapi

import (
	"../../domain/miapi"
	"../../utils/apierrors"
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

	wg.Add(1)
	site, apiError := GetSiteResultFromAPI(user.SiteID, &wg)
	wg.Wait()
	if apiError != nil {
		return nil, apiError
	}

	wg.Add(1)
	country, apiError := GetCountryResultFromAPI(site.CountryID, &wg)
	wg.Wait()
	if apiError != nil {
		return nil, apiError
	}

	result := &miapi.Result{
		UserResult:    *user,
		SiteResult:    *site,
		CountryResult: *country,
	}

	return result, nil

}
