package com.example.assigment2.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private BlockingQueue<Task> tasks= new LinkedBlockingQueue<>();
    private AtomicInteger waitingPeriod;
    private Task currentTask;

    public Server(){
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask){
        try {
            this.tasks.put(newTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    public int getLenght(){
        if(currentTask!=null)
            return 1+tasks.size();
        else
            return tasks.size();
    }

    public void run(){
        while(true){
            this.currentTask=null;
            try {
                this.currentTask = tasks.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(250L *this.currentTask.getServiceTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Statistics.updateAverageWaitingTime(this.waitingPeriod.get());

            this.waitingPeriod.addAndGet(-currentTask.getServiceTime());

        }
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

}
