/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LibTest;

import LibNet.NetLibrary;
import PetriObj.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * @author Inna V. Stetsenko
 */
public class TestPetriObjSimulation {
    public static void main(String[] args) throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
//        lab6Task2();
//        lab6Task3();
        lab6Task4();
    }

    public static void lab6Task2() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        PetriObjModel model = getModelLab6Task2();
        model.setIsProtokol(false);
        double timeModeling = 1000;
        model.goWithoutPrintCheck(timeModeling);

        PetriNet net = model.getListObj().get(0).getNet();
        PetriT[] transitions = net.getListT();

        System.out.println("Mean use device 1: " + transitions[1].getMean());
        System.out.println("Mean use device 2: " + transitions[2].getMean());
        System.out.println("Mean use device 3: " + transitions[3].getMean());
        System.out.println("Mean use device 4: " + transitions[4].getMean());
        System.out.println("Mean use device 5: " + transitions[5].getMean());

        int meanNumberOfDevicesInUse =
                (int) (transitions[1].getMean() +
                        transitions[2].getMean() +
                        transitions[3].getMean() +
                        transitions[4].getMean() +
                        transitions[5].getMean());
        System.out.println("Mean number devices in use: " + meanNumberOfDevicesInUse);

        ArrayList<Double> arrivedMoments = new ArrayList<>();

        for (Double arrivedMoment : transitions[0].getOutMoments()) {
            arrivedMoments.addAll(Arrays.asList(arrivedMoment, arrivedMoment, arrivedMoment, arrivedMoment));
        }

        ArrayList<Double> leftMoments = new ArrayList<>();
        leftMoments.addAll(transitions[1].getOutMoments());
        leftMoments.addAll(transitions[2].getOutMoments());
        leftMoments.addAll(transitions[3].getOutMoments());
        leftMoments.addAll(transitions[4].getOutMoments());
        leftMoments.addAll(transitions[5].getOutMoments());


        Collections.sort(leftMoments);

        double timeInSystemSum = 0.0;

        for (int i = 0; i < leftMoments.size(); i++) {
            timeInSystemSum += leftMoments.get(i) - arrivedMoments.get(i);
        }

        System.out.println("Mean time in system: " + timeInSystemSum / (double) leftMoments.size());

        PetriP[] places = net.getListP();

        System.out.println("Processed count: " + places[11].getMark());
    }

    public static PetriObjModel getModelLab6Task2() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        ArrayList<PetriSim> list = new ArrayList<>();
        list.add(new PetriSim(NetLibrary.CreateNetLab6Task2()));

        return new PetriObjModel(list);
    }


    public static void lab6Task3() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        PetriObjModel model = getModelLab6Task3();
        model.setIsProtokol(false);
        double timeModeling = 1000;
        model.goWithoutPrintCheck(timeModeling);

        PetriNet net = model.getListObj().get(0).getNet();
        PetriT[] transitions = net.getListT();
        PetriP[] places = net.getListP();

        System.out.println("Mean number in storage: " + places[5].getMean());

        double diffTime = 0.0;
        ArrayList<Double> leaveMoments = transitions[2].getOutMoments();
        for (int i = 1; i < leaveMoments.size(); i++) {
            double currentMoment = leaveMoments.get(i);
            double previousMoment = leaveMoments.get(i - 1);

            diffTime += currentMoment - previousMoment;
        }

        System.out.println("Mean time between failed sales: " + diffTime / (double) leaveMoments.size());

        System.out.println("Placed orders: " + places[10].getMark());
        System.out.println("Not placed orders: " + places[9].getMark());
        System.out.println("Successful orders: " + places[4].getMark());
        System.out.println("Lost customers: " + places[2].getMark());
    }


    public static PetriObjModel getModelLab6Task3() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        ArrayList<PetriSim> list = new ArrayList<>();
        list.add(new PetriSim(NetLibrary.CreateNetLab6Task3()));

        return new PetriObjModel(list);
    }


    public static void lab6Task4() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        PetriObjModel model = getModelLab6Task4();
        model.setIsProtokol(false);
        double timeModeling = 10000;
        model.goWithoutPrintCheck(timeModeling);

        PetriNet net = model.getListObj().get(0).getNet();
        PetriP[] places = net.getListP();

        System.out.println("Processed main server: " + places[4].getMark());
        System.out.println("Processed server: " + places[5].getMark());
    }


    public static PetriObjModel getModelLab6Task4() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        ArrayList<PetriSim> list = new ArrayList<>();
        list.add(new PetriSim(NetLibrary.CreateNetLab6Task4()));

        return new PetriObjModel(list);
    }

}
