package main

import "fmt"

func generador(nums ...int) <-chan int {
	salida := make(chan int)
	go func() {
		for _, n := range nums {
			salida <- n
		}
		close(salida)
	}()

	return salida
}

func cuadrado(in <-chan int) <-chan int {
	salida := make(chan int)
	go func() {
		for n := range in {
			n = n * n
			salida <- n
		}
		close(salida)
	}()

	return salida
}

func main() {

	c1 := generador(1, 2, 3, 4, 5)

	c2 := cuadrado(c1)

	for n := range c2{
		fmt.Println(n)
	}
}
