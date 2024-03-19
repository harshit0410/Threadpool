package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {
    private final int nThreads;
    private final PoolWorker [] workers;
    private  final BlockingQueue  <Runnable> queue;

    public MyThreadPool(int nThreads) {
        this.nThreads = nThreads;
        this.workers = new PoolWorker[nThreads];
        this.queue = new LinkedBlockingQueue <> ();

        for(int i=0; i<nThreads; i++) {
            workers[i] = new PoolWorker();
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {

        @Override
        public void run() {
            Runnable task;

            while(true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        }
                        catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    task = queue.poll();
                }

                try {
                    task.run();
                }
                catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }
}
