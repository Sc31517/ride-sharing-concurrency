package main

import (
	"fmt"
	"log"
	"sync"
	"time"
)

var taskQueue = make(chan string, 10)
var result []string
var mu sync.Mutex

func main() {
	// Add tasks to the queue
	for i := 0; i < 10; i++ {
		taskQueue <- fmt.Sprintf("Task %d", i)
		fmt.Println("Added:", fmt.Sprintf("Task %d", i))
	}

	// Start worker goroutines
	for i := 0; i < 4; i++ {
		go worker()
	}

	// Wait for all goroutines to finish
	time.Sleep(5 * time.Second)

	// Print final results
	fmt.Println("\nResults:")
	for _, res := range result {
		fmt.Println(res)
	}
}

func worker() {
	for task := range taskQueue {
		log.Printf("Worker starting: %s", task)
		processTask(task)
		mu.Lock()
		result = append(result, "Processed: "+task)
		mu.Unlock()
		log.Printf("Worker completed: %s", task)
	}
}

func processTask(task string) {
	// Simulating task processing with a delay (500 ms)
	time.Sleep(500 * time.Millisecond)
}
