/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Supun
 */
public class Process{
    private static int processCount = 0;
    private static int totalExecutionTime = 0;
    private final String name;
    private final String processId;
    private final int startingDeadline;
    private final int finishingDeadline;
    private int priority;
    private boolean finished;
    private final int executingTime;
    private int remainingTime;
    private final int readyTime;
    private final ArrayList<Integer> runningTimePosition; // This will maintain the times at which the process executed.
    private int lastExecutedDuration;
    private boolean ready;

    public Process(int startingDeadline, int finishingDeadline , String name, int executingTime, int readyTime) {
        processId = ++processCount + "p" ;
        this.startingDeadline = startingDeadline;
        this.finishingDeadline = finishingDeadline;
        this.name = name;
        finished = false;
        this.executingTime = executingTime;
        this.readyTime = readyTime;
        runningTimePosition = new ArrayList<>();
        remainingTime = executingTime;
        ready = false;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getReadyTime() {
        return readyTime;
    }

    public int getLastExecutedDuration() {
        return lastExecutedDuration;
    }

    public ArrayList<Integer> getRunningTimePosition() {
        return runningTimePosition;
    }

    public static int getTotalExecutionTime() {
        return totalExecutionTime;
    }

    public int getExecutingTime() {
        return executingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getFinishingDeadline() {
        return finishingDeadline;
    }
    
    public void execute(int executionTime){
        runningTimePosition.add(totalExecutionTime);
        
        if (executionTime >= remainingTime){
            lastExecutedDuration = remainingTime;
            totalExecutionTime += remainingTime;
            remainingTime = 0;
            finished = true;
        }else{
            remainingTime -= executionTime;
            totalExecutionTime += executionTime;
            lastExecutedDuration = executionTime;
        }
    }
    
    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public void addPriority(int value){
        priority += value;
    }
    
    public void clearPriority(){
        priority = 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public static int getProcessCount() {
        return processCount;
    }

    public String getProcessId() {
        return processId;
    }

    public int getStartingDeadline() {
        return startingDeadline;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
    
    // The following code block is used to sort (Used lambda expressions in java 8)

    /**
     * This sorting will make the processes in a way that process with the highest value comes first
     * Hence apply priorities values starting from the first item. (With first item having the least priority value)
     */
    public static Comparator<Process> finishingTimeValueSort = (Process p1, Process p2) -> {
        // will return in decreasing order
        // total time elapsed is a constant here. So it can be neglected in the value calculation
        int p1Value = p1.getFinishingDeadline() - p1.getRemainingTime();
        int p2Value = p2.getFinishingDeadline() - p2.getRemainingTime();
        
        return p2Value-p1Value;
    };

    /**
     * This sorting will order the processes in a way that process with the last starting deadline comes first
     * Hence apply priorities values starting from the first item. (With first item having the least priority value)
     */
    public static Comparator<Process> startindDeadlineValue = (Process p1, Process p2) -> p2.getStartingDeadline() - p1.getFinishingDeadline();
    
    /**
     * This sorting will order the processes in a manner such that the highest priority process comes first in the arrayList
     * Hence take the first item in the activeProcessesList to execute.
     */
    public static Comparator<Process> priorityValue = (Process p1 , Process p2) -> p2.getPriority() - p1.getPriority();
    
}
