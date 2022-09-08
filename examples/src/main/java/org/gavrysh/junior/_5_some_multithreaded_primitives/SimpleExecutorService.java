package org.gavrysh.junior._5_some_multithreaded_primitives;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class SimpleExecutorService {

    private final ThreadGroup group = new ThreadGroup("");
    private final Collection<Thread> workersPool = new ArrayList<>();
    private final BlockingQueue<Callable> taskQueue;

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Callable nextTask = taskQueue.take();
                    nextTask.call();
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SimpleExecutorService(int threadCount, final BlockingQueue<Callable> taskQueue) {
        this.taskQueue = taskQueue;
        for (int k = 0; k < threadCount; k++) {
            Thread worker = new Thread(group, new Worker()) ;
            worker.start();
            workersPool.add(worker);
        }
    }

    public <T> void submit(Callable<T> task) throws InterruptedException {
        taskQueue.put(task);
    }

    public void shutdown() {
        group.interrupt();
    }
}
