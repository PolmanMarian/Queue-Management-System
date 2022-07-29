package com.example.assigment2.Model;

public class Statistics {

    public static int totalWaitingTime=0;
    public static float averageWaitingTime;
    public static int numberOfTask = 0;

    public static int totalServiceTime = 0;

    public static int peakHour=0;
    public static int maxNumberOfTasksPerHour=0;

    public synchronized static void updateAverageWaitingTime(int time){
        totalWaitingTime+=time;
        numberOfTask++;
        averageWaitingTime = (float)totalWaitingTime / numberOfTask;
    }

    public synchronized static void updateTotalServiceTime(int time){
        totalServiceTime+=time;
    }

    public synchronized static void updatePeakHour(int hour,int tasksThisHour){
        if(tasksThisHour>maxNumberOfTasksPerHour){
            maxNumberOfTasksPerHour=tasksThisHour;
            peakHour=hour;
        }
    }

    public static void reset(){
        totalWaitingTime=0;
        averageWaitingTime=0;
        numberOfTask=0;

        totalServiceTime=0;

        peakHour=0;
        maxNumberOfTasksPerHour=0;
    }

}
