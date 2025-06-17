import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.*;
import java.io.*;

public class RideSharingSystem {
    static BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();
    static List<String> result = Collections.synchronizedList(new ArrayList<>());  // Synchronized list for thread safety

    public static void main(String[] args) {
        // Create a thread pool with 4 worker threads
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        // Add tasks to the queue
        for (int i = 0; i < 10; i++) {
            taskQueue.add("Task " + i);
            System.out.println("Added: Task " + i);  // Debug print to show task addition
        }
        
        // Submit worker threads to process tasks
        for (int i = 0; i < 4; i++) {
            executor.submit(new Worker(taskQueue, result));
        }
        
        // Shutdown the executor after submitting all tasks
        executor.shutdown();
        
        // Wait for all tasks to finish before printing results, without timeout
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);  // Wait indefinitely until all tasks finish
        } catch (InterruptedException e) {
            System.out.println("Executor interrupted.");
        }
        
        // Print all processed results
        System.out.println("\nResults:");
        for (String res : result) {
            System.out.println(res);  // Output the processed tasks
        }
    }
}

class Worker implements Runnable {
    private BlockingQueue<String> queue;
    private List<String> result;

    Worker(BlockingQueue<String> queue, List<String> result) {
        this.queue = queue;
        this.result = result;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String task = queue.take();  // Retrieve a task from the queue
                System.out.println(Thread.currentThread().getName() + " starting: " + task);  // Debug print
                processTask(task);
                result.add("Processed: " + task);  // Add the processed task to the result list
                System.out.println(Thread.currentThread().getName() + " completed: " + task);  // Debug print
            }
        } catch (InterruptedException e) {
            // Gracefully handle interruption and terminate worker thread
            System.out.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();  // Preserve interruption status
        }
    }

    private void processTask(String task) {
        // Simulating task processing with a delay (500 ms)
        try {
            Thread.sleep(500);  // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Preserve interruption status
        }
    }
}
