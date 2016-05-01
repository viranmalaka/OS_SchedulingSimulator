/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author Supun
 */
public class Simulator {
    // For testing
    public Process lastExecuted;
    
    private final ArrayList<Process> processList;
    private final ArrayList<Process> activeProcessList;
    private final int finishingDeadlinePriority;
    private final int startingDeadlinePriority;
    private final int timeQuantum;
    private String currentlyExecutingPID;
    private boolean doneExecuting;

    public Simulator(int finishingDeadlinePriority, int startingDeadlinePriority, int timeQuantum) {
        this.finishingDeadlinePriority = finishingDeadlinePriority;
        this.startingDeadlinePriority = startingDeadlinePriority;
        processList = new ArrayList<>();
        activeProcessList = new ArrayList<>();
        this.timeQuantum = timeQuantum;
        doneExecuting = false;
    }
    
    
    public void addProcess(String name, int startingDeadline, int finishingDeadline, int executingTime, int readyTime){
        // Create a new processID
        Process newProcess = new Process(startingDeadline, finishingDeadline, name, executingTime, readyTime);
        processList.add(newProcess);
        
    }
    
    public void updateProcessState(){
        
        doneExecuting = true;
        
        for (Process temp : processList) {
            if((!activeProcessList.contains(temp)) && (temp.getReadyTime() <= Process.getTotalExecutionTime())){
                activeProcessList.add(temp);
            }
            if(!temp.isFinished()){
                doneExecuting = false;
            }
        }

        for(Iterator<Process> iterator = activeProcessList.iterator() ; iterator.hasNext();){
            Process temp = iterator.next();
            if (temp.isFinished())
                 iterator.remove();
        }
        setPriorityFromSelectionAlgorithm();
    }
    
    private void setPriorityFromSelectionAlgorithm(){
        // Allocate finishingTime priority
        Collections.sort(activeProcessList, Process.finishingTimeValueSort);
        for (int i = 0; i < activeProcessList.size() ; i++){
            activeProcessList.get(i).clearPriority();
            activeProcessList.get(i).addPriority((i+1) * finishingDeadlinePriority);
        }
        
        // Allcate startingTime priority
        Collections.sort(activeProcessList, Process.startindDeadlineValue);
        
        for (int i=0 ; i < activeProcessList.size() ; i++)
            activeProcessList.get(i).addPriority((i+1)*startingDeadlinePriority);
        
    }
    
    public boolean executeNextProcess(){        
        updateProcessState();
        
        if (!doneExecuting){
            return false;
        }
        
        // Sort by priority value
        Collections.sort(activeProcessList, Process.priorityValue);
        
        if (activeProcessList.isEmpty()){
            Process.incrementExecutionTime();
            currentlyExecutingPID = "none";
            lastExecuted = null;
        }else{
        // Now execute the process
        activeProcessList.get(0).execute(timeQuantum);
        // Set currentlyExecuting processID
        currentlyExecutingPID = activeProcessList.get(0).getProcessId();
        // For testing
        lastExecuted = activeProcessList.get(0);
        }
        return true;
    }

    public String getCurrentlyExecutingPID() {
        return currentlyExecutingPID;
    }

    public ArrayList<Process> getActiveProcessList() {
        return activeProcessList;
    }

    public ArrayList<Process> getProcessList() {
        return processList;
    }
    
    
}
