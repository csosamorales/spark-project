package miapi

import (
	"../../domain/miapi"
	"../../utils/apierrors"
	"sync"
)

func GetSiteFromAPI(siteID string) (*miapi.Site, *apierrors.ApiError) {

	site := &miapi.Site{
		ID: siteID,
	}

	if err := site.GetSite(); err != nil {
		return nil, err
	}

	return site, nil

}

func GetSiteResultFromAPI(siteID string, wg *sync.WaitGroup) (*miapi.Site, *apierrors.ApiError) {

	site := &miapi.Site{
		ID: siteID,
	}

	if err := site.GetSite(); err != nil {
		return nil, err
	}

	wg.Done()

	return site, nil

}

func GetSiteResultChannelFromAPI(siteID string, resultChannel chan *miapi.Result, errorChannel chan *apierrors.ApiError) {

	site := &miapi.Site{
		ID: siteID,
	}

	result := miapi.Result{SiteResult: site}

	if err := site.GetSite(); err != nil {
		errorChannel <- err
	}

	resultChannel <- &result

}
