package com.example.assigment2.BusinessLogic;
import com.example.assigment2.Model.Server;
import com.example.assigment2.Model.Task;
import java.util.ArrayList;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public synchronized void addTask(ArrayList<Server> servers, Task t) {
        int minWaitingPeriod = Integer.MAX_VALUE;
        Server toInsert= null;
        for(Server entry: servers)
        {
            int waitingPeriod = entry.getWaitingPeriod().get();
            if(waitingPeriod < minWaitingPeriod){
                minWaitingPeriod = waitingPeriod;
                toInsert = entry;
            }
        }
        toInsert.addTask(t);
    }
}
