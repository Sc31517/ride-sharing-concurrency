# ride-sharing-concurrency

# Ride-Sharing Concurrency - Data Processing System

This project implements a multi-threaded data processing system using both **Java** and **Go**. The system simulates multiple worker threads processing tasks in parallel from a shared queue, demonstrating concurrency techniques, error handling, and synchronization in both programming languages.

## Table of Contents
1. [Java Implementation](#java-implementation)
2. [Go Implementation](#go-implementation)
3. [Concurrency Techniques](#concurrency-techniques)
4. [Running the Project](#running-the-project)
5. [License](#license)

---

## Java Implementation

### Setup & Run

1. **Install Java JDK**  
   Ensure that you have the Java Development Kit (JDK) installed. If not, download it from [here](https://www.oracle.com/java/technologies/javase-downloads.html).

2. **Compile & Run Java Code**

   - Open your terminal and navigate to the `java/` directory.
   - Compile the Java file using `javac`:
     ```bash
     javac RideSharingSystem.java
     ```
   - Run the program:
     ```bash
     java RideSharingSystem
     ```

### Code Details
- The program uses **`BlockingQueue`** for managing tasks in the shared queue.
- It employs **`ExecutorService`** to manage a fixed pool of worker threads.
- **Thread synchronization** is handled with `Collections.synchronizedList()` to safely update shared results.

---

## Go Implementation

### Setup & Run

1. **Install Go**  
   Make sure you have Go installed. If not, you can download it from [here](https://golang.org/dl/).

2. **Run Go Code**

   - Open your terminal and navigate to the `go/` directory.
   - Run the Go program using:
     ```bash
     go run ride_sharing.go
     ```

### Code Details
- The Go implementation uses **goroutines** for concurrent task processing.
- **`chan`** is used for task management, ensuring safe concurrent access to the shared queue.
- **`sync.Mutex`** is used to protect the results list from race conditions when appending results.

---

## Concurrency Techniques

### Java
- **Threads & ExecutorService**: The Java version uses threads managed by an `ExecutorService`. The tasks are retrieved from a `BlockingQueue`, which supports thread-safe operations.
- **Synchronization**: The `result` list is synchronized using `Collections.synchronizedList()` to ensure safe concurrent updates.
- **Error Handling**: Exceptions like `InterruptedException` are caught and handled gracefully to ensure that the threads complete their work without premature termination.

### Go
- **Goroutines & Channels**: Go's concurrency model is based on goroutines (lightweight threads) and channels. A buffered channel is used to safely queue tasks, and goroutines process them concurrently.
- **Synchronization**: A `sync.Mutex` ensures thread-safe access to the shared results list.
- **Error Handling**: The Go implementation is based on checking errors where needed (e.g., handling `InterruptedException` via `defer`), though file operations aren’t included in this example.

---
