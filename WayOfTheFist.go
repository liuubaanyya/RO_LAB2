package main

import (
	"fmt"
	"sort"
)

func main() {
	energies := []int{10, 15, 7, 25, 30}
	winner := findWinner(energies)
	fmt.Printf("Ченець з енергією %d переміг і виграв статую боддісатви.\n", winner)
}

func findWinner(energies []int) int {
	sort.Sort(sort.Reverse(sort.IntSlice(energies))) // Сортування енергій у спадаючому порядку

	for len(energies) > 1 {
		mid := len(energies) / 2
		for i := 0; i < mid; i++ {
			energies[i] -= energies[len(energies)-i-1] // Зменшуємо енергію перших половини ченців на енергію других половини
		}
		energies = energies[:mid] // Зберігаємо лише першу половину ченців
		sort.Sort(sort.Reverse(sort.IntSlice(energies))) // Повторно сортуємо
	}

	return energies[0] // Залишилася лише одна особа, вона переможець
}
