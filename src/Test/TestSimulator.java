/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import model.Simulator;
import model.Process;

/**
 *
 * @author vikum
 */
public class TestSimulator {
    public static void main(String[] args) {
        Simulator sim1 = new Simulator(5, 1, 3);
        sim1.addProcess("A", 0, 2, 20, 0);
        sim1.addProcess("B", 3, 5, 22, 1);
        sim1.addProcess("C", 4, 7, 23, 2);
        while(sim1.executeNextProcess()) {
            Process temp = sim1.getActiveProcessList().get(0);
            System.out.println(temp.getName()+" Last executed "+temp.getLastExecutedDuration()+" Remaining "+temp.getRemainingTime());
        }
    }
}
