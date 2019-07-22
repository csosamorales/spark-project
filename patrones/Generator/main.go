package main

import (
	"fmt"
	"net/http"
	"sync"
)

type Result struct {
	Url        string
	StatusCode int
}

func PingUrl(url string, wg *sync.WaitGroup) {

	res, err := http.Get(url)
	if err != nil {
		print(err.Error())
	}
	fmt.Printf("url: %s - status code: %d\n", url, res.StatusCode)
	wg.Done()
}

func GetResults(urls []string) *sync.WaitGroup {

	var wg sync.WaitGroup

	//wg.Add(len(urls))
	for _, url := range urls {
		wg.Add(1)
		go PingUrl(url, &wg)

	}

	wg.Wait()

	return &wg

}

func main() {
	urls := []string{
		"https://www.google.com/",
		"https://www.mercadolibre.com.ar/",
		"https://www.facebook.com/",
	}

	wg := GetResults(urls)

	wg.Wait()

	fmt.Println("Proceso terminado")
}
