/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LibTest;

import LibNet.NetLibrary;
import PetriObj.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * @author Inna V. Stetsenko
 */
public class TestPetriObjSimulation {
    public static void main(String[] args) throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
//        ElevatorObjModel model = new ElevatorObjModel(
//                1,
//                1,
//                1,
//                0.7,
//                0.1,
//                0.4,
//                6
//        );
//
//
//        model.go(10000);
//
//        model.calcStats(100);
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

        double meanNumberOfDevicesInUse = transitions[1].getMean() +
                transitions[2].getMean() +
                transitions[3].getMean() +
                transitions[4].getMean() +
                transitions[5].getMean();
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

    public static void coursework() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        PetriObjModel model = getModelCoursework();
        model.setIsProtokol(false);
        double timeModeling = 1000;
        model.go(timeModeling);
    }

    public static PetriObjModel getModelCoursework() throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        PetriP passengersUpPlace = new PetriP("PassengersUp", 0);
        PetriP passengersDownPlace = new PetriP("PassengersDown", 0);
        PetriP passengersTo1FloorPlace = new PetriP("PassengersTo1Floor", 0);
        PetriP passengersTo2FloorPlace = new PetriP("PassengersTo2Floor", 0);
        PetriP passengersTo3FloorPlace = new PetriP("PassengersTo3Floor", 0);
        PetriP passengersTo4FloorPlace = new PetriP("PassengersTo4Floor", 0);
        PetriP passengersTo5FloorPlace = new PetriP("PassengersTo5Floor", 0);
        PetriP availablePlacesPlace = new PetriP("AvailablePlaces", 6);
        PetriP directionUpPlace = new PetriP("DirectionUp", 1);
        PetriP directionDownPlace = new PetriP("DirectionDown", 0);
        PetriP elevatorOn1FloorPlace = new PetriP("ElevatorOn1Floor", 1);
        PetriP elevatorOn2FloorPlace = new PetriP("ElevatorOn2Floor", 0);
        PetriP elevatorOn3FloorPlace = new PetriP("ElevatorOn3Floor", 0);
        PetriP elevatorOn4FloorPlace = new PetriP("ElevatorOn4Floor", 0);
        PetriP elevatorOn5FloorPlace = new PetriP("ElevatorOn5Floor", 0);

        ArrayList<PetriSim> list = new ArrayList<>();
        list.add(new PetriSim(NetLibrary.CreateNetFloorObject(
                passengersUpPlace,
                passengersDownPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                availablePlacesPlace,
                directionUpPlace,
                directionDownPlace,
                null,
                elevatorOn2FloorPlace,
                elevatorOn1FloorPlace,
                1
        )));

        list.add(new PetriSim(NetLibrary.CreateNetFloorObject(
                passengersUpPlace,
                passengersDownPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                availablePlacesPlace,
                directionUpPlace,
                directionDownPlace,
                elevatorOn1FloorPlace,
                elevatorOn3FloorPlace,
                elevatorOn2FloorPlace,
                2
        )));

        list.add(new PetriSim(NetLibrary.CreateNetFloorObject(
                passengersUpPlace,
                passengersDownPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                availablePlacesPlace,
                directionUpPlace,
                directionDownPlace,
                elevatorOn2FloorPlace,
                elevatorOn4FloorPlace,
                elevatorOn3FloorPlace,
                3
        )));

        list.add(new PetriSim(NetLibrary.CreateNetFloorObject(
                passengersUpPlace,
                passengersDownPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                availablePlacesPlace,
                directionUpPlace,
                directionDownPlace,
                elevatorOn3FloorPlace,
                elevatorOn5FloorPlace,
                elevatorOn4FloorPlace,
                4
        )));

        list.add(new PetriSim(NetLibrary.CreateNetFloorObject(
                passengersUpPlace,
                passengersDownPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                availablePlacesPlace,
                directionUpPlace,
                directionDownPlace,
                elevatorOn4FloorPlace,
                null,
                elevatorOn5FloorPlace,
                5
        )));

        return new PetriObjModel(list);
    }


//    public static PetriObjModel getCourseworkModel() throws ExceptionInvalidTimeDelay {
//        ArrayList<PetriSim> list = new ArrayList<>();
//
//
//        PetriPWithFloorNumber elevatorAvailableOn1FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn1Floor", 1, 1);
//        PetriPWithFloorNumber elevatorAvailableOn2FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn2Floor", 0, 2);
//        PetriPWithFloorNumber elevatorAvailableOn3FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn3Floor", 0, 3);
//        PetriPWithFloorNumber elevatorAvailableOn4FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn4Floor", 0, 4);
//        PetriPWithFloorNumber elevatorAvailableOn5FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn5Floor", 0, 5);
//        PetriPWithFloorNumber passengersTo1FloorPlace = new PetriPWithFloorNumber("PassengersTo1Floor", 0, 1);
//        PetriPWithFloorNumber passengersTo2FloorPlace = new PetriPWithFloorNumber("PassengersTo2Floor", 0, 2);
//        PetriPWithFloorNumber passengersTo3FloorPlace = new PetriPWithFloorNumber("PassengersTo3Floor", 0, 3);
//        PetriPWithFloorNumber passengersTo4FloorPlace = new PetriPWithFloorNumber("PassengersTo4Floor", 0, 4);
//        PetriPWithFloorNumber passengersTo5FloorPlace = new PetriPWithFloorNumber("PassengersTo5Floor", 0, 5);
//        PetriPWithFloorNumber passengersWaiting1FloorPlace = new PetriPWithFloorNumber("PassengersWaiting1Floor", 1, 1);
//        PetriPWithFloorNumber passengersWaiting2FloorPlace = new PetriPWithFloorNumber("PassengersWaiting2Floor", 0, 2);
//        PetriPWithFloorNumber passengersWaiting3FloorPlace = new PetriPWithFloorNumber("PassengersWaiting3Floor", 0, 3);
//        PetriPWithFloorNumber passengersWaiting4FloorPlace = new PetriPWithFloorNumber("PassengersWaiting4Floor", 0, 4);
//        PetriPWithFloorNumber passengersWaiting5FloorPlace = new PetriPWithFloorNumber("PassengersWaiting5Floor", 0, 5);
//
//        PetriP availablePlaces = new PetriP("AvailablePlaces", 6);
//
//        ArrayList<PetriP> sharedPlaces = new ArrayList<>(Arrays.asList(
//                elevatorAvailableOn1FloorPlace,
//                elevatorAvailableOn2FloorPlace,
//                elevatorAvailableOn3FloorPlace,
//                elevatorAvailableOn4FloorPlace,
//                elevatorAvailableOn5FloorPlace,
//                passengersTo1FloorPlace,
//                passengersTo2FloorPlace,
//                passengersTo3FloorPlace,
//                passengersTo4FloorPlace,
//                passengersTo5FloorPlace,
//                passengersWaiting1FloorPlace,
//                passengersWaiting2FloorPlace,
//                passengersWaiting3FloorPlace,
//                passengersWaiting4FloorPlace,
//                passengersWaiting5FloorPlace,
//                availablePlaces
//        ));
//
//
//        list.add(new FloorDecisionMaker(
//                1,
//                elevatorAvailableOn1FloorPlace,
//                null,
//                elevatorAvailableOn2FloorPlace,
//                new ArrayList<>(Arrays.asList(
//                        passengersTo2FloorPlace,
//                        passengersTo3FloorPlace,
//                        passengersTo4FloorPlace,
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(),
//                new ArrayList<>(Arrays.asList(
//                        passengersWaiting2FloorPlace,
//                        passengersWaiting3FloorPlace,
//                        passengersWaiting4FloorPlace,
//                        passengersWaiting5FloorPlace
//                )),
//                new ArrayList<>(),
//                sharedPlaces
//        ));
//
//        list.add(new FloorDecisionMaker(
//                2,
//                elevatorAvailableOn2FloorPlace,
//                elevatorAvailableOn1FloorPlace,
//                elevatorAvailableOn3FloorPlace,
//                new ArrayList<>(Arrays.asList(
//                        passengersTo3FloorPlace,
//                        passengersTo4FloorPlace,
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(Collections.singletonList(
//                        passengersTo1FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersWaiting3FloorPlace,
//                        passengersWaiting4FloorPlace,
//                        passengersWaiting5FloorPlace
//                )),
//                new ArrayList<>(Collections.singletonList(
//                        passengersWaiting1FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//        list.add(new FloorDecisionMaker(
//                3,
//                elevatorAvailableOn3FloorPlace,
//                elevatorAvailableOn2FloorPlace,
//                elevatorAvailableOn4FloorPlace,
//                new ArrayList<>(Arrays.asList(
//                        passengersTo4FloorPlace,
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersTo1FloorPlace,
//                        passengersTo2FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersWaiting4FloorPlace,
//                        passengersWaiting5FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersWaiting1FloorPlace,
//                        passengersWaiting2FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//        list.add(new FloorDecisionMaker(
//                4,
//                elevatorAvailableOn4FloorPlace,
//                elevatorAvailableOn3FloorPlace,
//                elevatorAvailableOn5FloorPlace,
//                new ArrayList<>(Collections.singletonList(
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersTo1FloorPlace,
//                        passengersTo2FloorPlace,
//                        passengersTo3FloorPlace
//                )),
//                new ArrayList<>(Collections.singletonList(
//                        passengersWaiting5FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersWaiting1FloorPlace,
//                        passengersWaiting2FloorPlace,
//                        passengersWaiting3FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//        list.add(new FloorDecisionMaker(
//                5,
//                elevatorAvailableOn5FloorPlace,
//                elevatorAvailableOn4FloorPlace,
//                null,
//                new ArrayList<>(),
//                new ArrayList<>(Arrays.asList(
//                        passengersTo1FloorPlace,
//                        passengersTo2FloorPlace,
//                        passengersTo3FloorPlace,
//                        passengersTo4FloorPlace
//                )),
//                new ArrayList<>(),
//                new ArrayList<>(Arrays.asList(
//                        passengersWaiting1FloorPlace,
//                        passengersWaiting2FloorPlace,
//                        passengersWaiting3FloorPlace,
//                        passengersWaiting4FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//
//        list.add(new FloorActivity(
//                1,
//                elevatorAvailableOn1FloorPlace,
//                availablePlaces,
//                passengersTo1FloorPlace,
//                passengersWaiting1FloorPlace,
//                new ArrayList<>(Arrays.asList(
//                        passengersTo2FloorPlace,
//                        passengersTo3FloorPlace,
//                        passengersTo4FloorPlace,
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(),
//                sharedPlaces
//        ));
//
//
//        list.add(new FloorActivity(
//                2,
//                elevatorAvailableOn2FloorPlace,
//                availablePlaces,
//                passengersTo2FloorPlace,
//                passengersWaiting2FloorPlace,
//                new ArrayList<>(Arrays.asList(
//                        passengersTo3FloorPlace,
//                        passengersTo4FloorPlace,
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(Collections.singletonList(
//                        passengersTo1FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//        list.add(new FloorActivity(
//                3,
//                elevatorAvailableOn3FloorPlace,
//                availablePlaces,
//                passengersTo3FloorPlace,
//                passengersWaiting3FloorPlace,
//                new ArrayList<>(Arrays.asList(
//                        passengersTo4FloorPlace,
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersTo1FloorPlace,
//                        passengersTo2FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//        list.add(new FloorActivity(
//                4,
//                elevatorAvailableOn4FloorPlace,
//                availablePlaces,
//                passengersTo4FloorPlace,
//                passengersWaiting4FloorPlace,
//                new ArrayList<>(Collections.singletonList(
//                        passengersTo5FloorPlace
//                )),
//                new ArrayList<>(Arrays.asList(
//                        passengersTo1FloorPlace,
//                        passengersTo2FloorPlace,
//                        passengersTo3FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//        list.add(new FloorActivity(
//                5,
//                elevatorAvailableOn5FloorPlace,
//                availablePlaces,
//                passengersTo5FloorPlace,
//                passengersWaiting5FloorPlace,
//                new ArrayList<>(),
//                new ArrayList<>(Arrays.asList(
//                        passengersTo1FloorPlace,
//                        passengersTo2FloorPlace,
//                        passengersTo3FloorPlace,
//                        passengersTo4FloorPlace
//                )),
//                sharedPlaces
//        ));
//
//
//        return new PetriObjModel(list);
//    }
}



