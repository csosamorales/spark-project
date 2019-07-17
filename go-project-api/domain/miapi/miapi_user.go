package miapi

/*
type User struct {
	Id               int64  `json:"id"`
	Name             string `json:"nickname"`
	RegistrationDate string `json:"registration_date"`
	CountryId        string `json:"country_id"`
	SiteID           string `json:"site_id"`
}

const urlUsers = "https://api.mercadolibre.com/users/"

func (user *User) Get() *apierrors.ApiError {
	if user.Id == 0 {
		return &apierrors.ApiError{
			Message: "El id del usuario no puede estar vacio",
			Status:  http.StatusBadRequest,
		}
	}

	url := fmt.Sprintf("%s%d", urlUsers, user.Id)

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

	if err := json.Unmarshal(data, &user); err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status:  http.StatusInternalServerError,
		}
	}

	return nil

}

 */
