package com.example.assigment2.BusinessLogic;
import com.example.assigment2.Model.Server;
import com.example.assigment2.Model.Statistics;
import com.example.assigment2.Model.Task;
import com.example.assigment2.QueueManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationManager implements Runnable {

    public int timeLimit;

    public int maxArrivalTime;
    public int minArrivalTime;

    public int minServiceTime;
    public int maxServiceTime;

    public int numberOfServers;
    public int numberOfClient;

    public SelectionPolicy selectionPolicy;
    private Scheduler scheduler;

    private LinkedBlockingQueue<Task> generatedTasks;

    public SimulationManager(SelectionPolicy selectionPolicy,int timeLimit, int maxArrivalTime, int minArrivalTime, int minServiceTime, int maxServiceTime, int numberOfServers, int numberOfClient){
        this.timeLimit = timeLimit;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.numberOfClient = numberOfClient;
        this.numberOfServers = numberOfServers;
        this.scheduler=new Scheduler(numberOfServers);
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomTasks();
    }

    private synchronized void generateNRandomTasks(){

        ArrayList<Task> generatedTasks=new ArrayList<>();
        Task task=null;
        Random random= new Random();
        for(int i=0 ; i<numberOfClient ; i++) {
            task = new Task(i,minArrivalTime+random.nextInt(maxArrivalTime-minArrivalTime),minServiceTime+random.nextInt(maxServiceTime-minServiceTime));
            Statistics.updateTotalServiceTime(task.getServiceTime());
            generatedTasks.add(task);
        }
        Collections.sort(generatedTasks);

        this.generatedTasks = new LinkedBlockingQueue<>(generatedTasks);

    }

    private synchronized String getCurrentState(int currentTime){
        int tasksAtThisHour=0;
        String state="";
        for(Task task:generatedTasks){
            state+="["+task.getID()+"]";
            state+=":"+task.getArrivalTime();
            state+=" "+task.getServiceTime();
            state+="\n";
        }
        int i=0;
        for(Server entry: scheduler.getServers())
        {
            state +="Server"+i+": ";
            if(entry.getCurrentTask()!=null){
                state +="[" + entry.getCurrentTask().getID()+"] ";
                tasksAtThisHour++;
            }

            for(Task task:entry.getTasks()){
                state +="[" + task.getID()+"] ";
                tasksAtThisHour++;
            }
            state+="\n";
            i++;
        }
        Statistics.updatePeakHour(currentTime,tasksAtThisHour);
        return state;
    }

    @Override
    public void run() {
        int currentTime = 0;
        while (currentTime < timeLimit){
            while(true){
                Task task= generatedTasks.peek();
                if(task!=null && task.getArrivalTime() == currentTime){
                    this.scheduler.dispatchTask(task);
                    generatedTasks.poll();
                }
                else
                    break;
            }
            try {
                Thread.sleep(250l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            QueueManager.getController().print("\nTime: "+currentTime+"\n"+this.getCurrentState(currentTime));
            currentTime++;
        }
        QueueManager.getController().print("\n Average Waiting Time "+Statistics.averageWaitingTime+"\n Average Service Time "+(float)Statistics.totalServiceTime/this.numberOfClient+"\n PeakHour "+Statistics.peakHour);
        try {
            QueueManager.getFile().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
