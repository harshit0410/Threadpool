package org.example;

class Task implements Runnable {

    private int num;

    public Task(int n) {
        num = n;
    }

    public void run() {
        System.out.println("Task " + num + " is running.");
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        MyThreadPool myThreadPool = new MyThreadPool(7);

        for (int i=0; i<5; i++) {
            myThreadPool.execute(new Task(i));
        }
    }
}