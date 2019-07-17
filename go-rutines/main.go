package main

import (
	"fmt"
	"net/http"
	"sync"
)

/*

func miFuncion(wg *syncWaitGroup) {
	fmt.Println("Dentro de la go routine")
	wg.Done()
}

*/

var urls = [] string{
	"https://www.google.com/",
	"https://www.lavoz.com.ar/",
	"https://www.mercadolibre.com.ar/",
}

func recuperar(url string, group *sync.WaitGroup) {

	fmt.Println(url)
	res, err := http.Get(url)

	if err != nil {
		fmt.Println(err)
	}

	fmt.Println(res.Status)
	group.Done()

}

func consultaApi(w http.ResponseWriter, r *http.Request) {
	fmt.Println("Consulto a la api")

	var wg sync.WaitGroup
	for _, url := range urls {
		wg.Add(1)
		go recuperar(url, &wg)
		wg.Wait()
	}

	fmt.Println("Devuelve una respuesta")
	fmt.Fprintf(w, "Proceso terminado")
}

func hanndleRequest() {
	http.HandleFunc("/", consultaApi)
	http.ListenAndServe(":8080", nil)
}
func main() {

	hanndleRequest()

}
