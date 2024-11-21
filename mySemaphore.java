public class mySemaphore {
        private int permits;       // The number of available permits (resources).
        private final int maxPermits; // The maximum number of permits allowed (fixed limit).
    
        
        public mySemaphore(int permits) {
            this.permits = permits;       // Set the initial number of permits.
            this.maxPermits = permits;    // Store the maximum number of permits for reference.
        }
    
        /**
         * Acquire a permit. If no permits are available, the thread will wait until one is released.
         * This method blocks the thread until a permit becomes available.
         */
        public synchronized void acquire() throws InterruptedException {
            while (permits == 0) { // If no permits are available, wait.
                wait();            // The thread goes into a waiting state.
            }
            permits--; // Consume a permit once it's available.
        }
    
        /**
         * Try to acquire a permit without blocking.
         * return true if a permit is successfully acquired, false otherwise.
         */
        public synchronized boolean tryAcquire() {
            if (permits > 0) { // Check if any permits are available.
                permits--;     // Consume a permit if available.
                return true;   // Indicate success.
            }
            return false; // Return false if no permits are available.
        }
    
        /**
         * Release a permit, increasing the available permits.
         * If threads are waiting for permits, one of them is notified.
         */
        public synchronized void release() {
            if (permits < maxPermits) { // Ensure we don't exceed the maximum permits.
                permits++;              // Return a permit to the semaphore.
                notify();               // Notify one waiting thread (if any).
            }
        }
    
        public synchronized int availablePermits() {
            return permits; // Return the current number of permits.
        }
    }
    



