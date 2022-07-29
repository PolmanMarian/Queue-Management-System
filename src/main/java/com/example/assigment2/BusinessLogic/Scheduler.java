package com.example.assigment2.BusinessLogic;
import com.example.assigment2.Model.Server;
import com.example.assigment2.Model.Task;
import java.util.ArrayList;

public class Scheduler {

        private final ArrayList<Server> servers= new ArrayList <Server>();
        private int maxNoServers;
        private Strategy strategy = new ConcreteStrategyTime();

        public Scheduler(int maxNoServers){

            this.maxNoServers=maxNoServers;

            for(int i=0; i < this.maxNoServers;i++){
               Server server=new Server();
               servers.add(server);
               Thread thread = new Thread(server);
               thread.start();
            }
        }

        public void changeStrategy(SelectionPolicy policy) {

            if (policy == SelectionPolicy.SHORTEST_QUEUE) {
                strategy = new ConcreteStrategyQueue();
            }

            if (policy == SelectionPolicy.SHORTEST_TIME) {
                strategy = new ConcreteStrategyTime();
            }
        }

        public synchronized void dispatchTask(Task task){
            strategy.addTask(servers,task);
        }

        public ArrayList<Server> getServers(){
            return servers;
        }

}
