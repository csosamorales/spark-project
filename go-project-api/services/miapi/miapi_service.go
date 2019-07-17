package miapi

import (
	"../../domain/miapi"
	"sync"
)
import "../../utils/apierrors"

func GetUserFromAPI(userID int64) (*miapi.User, *apierrors.ApiError) {

	user := &miapi.User{
		ID: userID,
	}

	if err := user.GetUser(); err != nil {
		return nil, err
	}

	return user, nil

}

func GetUserResultFromAPI(userID int64, wg *sync.WaitGroup) (*miapi.User, *apierrors.ApiError) {

	user := &miapi.User{
		ID: userID,
	}

	if err := user.GetUser(); err != nil {
		return nil, err
	}
	wg.Done()
	return user, nil

}

/*
func GetSitesFromAPI() (*miapi.Sites, *apierrors.ApiError) {

	sites := &miapi.Sites{}

	if err := sites.GetSites(); err != nil {
		return nil, err
	}

	return sites, nil
}
 */
