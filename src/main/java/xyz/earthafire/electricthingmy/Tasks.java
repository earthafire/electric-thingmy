package xyz.earthafire.electricthingmy;

import kotlin.Pair;
import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.taskstarters.TaskStarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

public class Tasks {
    private HashMap<String, Pair<TaskStarter, Integer>> tasks;
    private int totalWeight;
    public Tasks(){
        tasks = new HashMap<String, Pair<TaskStarter, Integer>>();
        totalWeight = 0;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void addTask(String name, TaskStarter starter, int weight){
        tasks.put(name,
                new Pair<>(
                        starter,
                        weight));
        totalWeight += weight;
    }

    public boolean has(String name){
        return tasks.containsKey(name);
    }

    public void startTask(String nameOfTask, ArrayList<Player> players, String nameOfSender){
        tasks.get(nameOfTask).getFirst().run(players, nameOfSender);
    }

    public ConcurrentSkipListSet<String> random(int size){
        Random r = new Random();

        ConcurrentSkipListSet<String> answer = new ConcurrentSkipListSet<String>();
        while(answer.size() < size){
            double index = r.nextDouble() * totalWeight;
            answer.add(getIndex(index));
        }

        return answer;
    }

    private String getIndex(double index){
        int total = 0;

        for(String next: tasks.keySet()){
            Pair<TaskStarter, Integer> temp = tasks.get(next);
            total += temp.getSecond();
            if(total >= index){
                return next;
            }
        }
        return "oopsies";
    }

    public String getVerb(String title) {
        return tasks.get(title).getFirst().getTaskVerb();
    }
}
