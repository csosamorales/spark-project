package miapi


import (
	"../../domain/miapi"
	"sync"
)
import "../../utils/apierrors"

func GetCountryFromAPI(countryID string) (*miapi.Country, *apierrors.ApiError) {

	country := &miapi.Country{
		ID: countryID,
	}

	if err := country.GetCountry(); err != nil {
		return nil, err
	}

	return country, nil

}

func GetCountryResultFromAPI(countryID string, wg *sync.WaitGroup) (*miapi.Country, *apierrors.ApiError) {

	country := &miapi.Country{
		ID: countryID,
	}

	if err := country.GetCountry(); err != nil {
		return nil, err
	}

	wg.Done()

	return country, nil

}
