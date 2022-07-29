package com.example.assigment2.BusinessLogic;
import com.example.assigment2.Model.Server;
import com.example.assigment2.Model.Task;

import java.util.ArrayList;

public class ConcreteStrategyQueue implements Strategy{

    @Override
    public synchronized void addTask(ArrayList<Server> servers, Task t) {
        int minLength = Integer.MAX_VALUE;
        Server toInsert= null;

        for(Server entry: servers)
        {
            int taskLength=entry.getLenght();
                if(taskLength < minLength){
                    minLength = taskLength;
                    toInsert = entry;
                }
        }

        toInsert.addTask(t);
    }
}
