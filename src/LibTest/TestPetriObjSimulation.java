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

        PetriObjModel model = getCourseworkModel();
        model.setIsProtokol(false);

        model.go(1000);


        PetriNet floorActivity1 = model.getListObj().get(5).getNet();
        PetriNet floorActivity2 = model.getListObj().get(6).getNet();
        PetriNet floorActivity3 = model.getListObj().get(7).getNet();
        PetriNet floorActivity4 = model.getListObj().get(8).getNet();
        PetriNet floorActivity5 = model.getListObj().get(9).getNet();


        ArrayList<PetriNet> floorActivities = new ArrayList<>(Arrays.asList(
                floorActivity1,
                floorActivity2,
                floorActivity3,
                floorActivity4,
                floorActivity5
        ));

        for (int i = 0; i < floorActivities.size(); i++) {
            PetriT[] transitions = floorActivities.get(i).getListT();
            ArrayList<Double> startWaitingUpMoments = null;
            ArrayList<Double> finishWaitingUpMoments = null;
            ArrayList<Double> startWaitingDownMoments = null;
            ArrayList<Double> finishWaitingDownMoments = null;

            int floorNumber = i + 1;

            switch (floorNumber) {
                case 1:
                    startWaitingUpMoments = transitions[1].getOutMoments();
                    finishWaitingUpMoments = transitions[2].getInMoments();
                    break;
                case 2:
                    startWaitingUpMoments = transitions[3].getOutMoments();
                    finishWaitingUpMoments = transitions[4].getInMoments();
                    startWaitingDownMoments = transitions[2].getOutMoments();
                    finishWaitingDownMoments = transitions[8].getInMoments();
                    break;
                case 3:
                    startWaitingUpMoments = transitions[3].getOutMoments();
                    finishWaitingUpMoments = transitions[4].getInMoments();
                    startWaitingDownMoments = transitions[2].getOutMoments();
                    finishWaitingDownMoments = transitions[7].getInMoments();
                    break;
                case 4:
                    startWaitingUpMoments = transitions[3].getOutMoments();
                    finishWaitingUpMoments = transitions[4].getInMoments();
                    startWaitingDownMoments = transitions[2].getOutMoments();
                    finishWaitingDownMoments = transitions[6].getInMoments();
                    break;
                case 5:
                    startWaitingDownMoments = transitions[1].getOutMoments();
                    finishWaitingDownMoments = transitions[2].getInMoments();
                    break;
            }

            if (startWaitingDownMoments != null && finishWaitingDownMoments != null) {
                double diffSum = 0.0;

                for (int j = 0; j < finishWaitingDownMoments.size(); j++) {
                    diffSum += finishWaitingDownMoments.get(i) - startWaitingDownMoments.get(i);
                }

                System.out.printf("Mean waiting time down %d floor: %f%n", i + 1, diffSum / (double) finishWaitingDownMoments.size());
            }

            if (startWaitingUpMoments != null && finishWaitingUpMoments != null) {
                double diffSum = 0.0;

                int startIndex = 0;
                double divider = finishWaitingUpMoments.size();

                if (i == 0) {
                    startIndex = 1;
                    divider--;
                }


                for (int j = startIndex; j < finishWaitingUpMoments.size(); j++) {
                    int startWaitingUpMomentIndex = j;
                    if (i == 0) {
                        startWaitingUpMomentIndex = j - 1;
                    }
                    diffSum += finishWaitingUpMoments.get(j) - startWaitingUpMoments.get(startWaitingUpMomentIndex);
                }

                System.out.printf("Mean waiting time up %d floor: %f%n", i + 1, diffSum / divider);
            }
        }

        PetriP availablePlacesPlace = model.getListObj().get(5).getNet().getListP()[15];

        System.out.printf("Max number passengers: %d%n", 6 - availablePlacesPlace.getObservedMin());
        System.out.printf("Mean number passengers: %f%n", 6 - availablePlacesPlace.getMean());

        ArrayList<Double> allFloorsEnterElevatorMoments = new ArrayList<>();
        ArrayList<Double> allFloorsExitElevatorMoments = new ArrayList<>();

        for (int i = 0; i < floorActivities.size(); i++) {
            PetriT[] transitions = floorActivities.get(i).getListT();
            ArrayList<Double> enterElevatorMoments = new ArrayList<>();
            ArrayList<Double> exitElevatorMoments = new ArrayList<>(transitions[0].getOutMoments());

            int floorNumber = i + 1;

            switch (floorNumber) {
                case 1:
                case 5:
                    enterElevatorMoments.addAll(transitions[2].getInMoments());
                    break;
                case 2:
                    enterElevatorMoments.addAll(transitions[4].getInMoments());
                    enterElevatorMoments.addAll(transitions[8].getInMoments());
                    break;
                case 3:
                    enterElevatorMoments.addAll(transitions[4].getInMoments());
                    enterElevatorMoments.addAll(transitions[7].getInMoments());
                    break;
                case 4:
                    enterElevatorMoments.addAll(transitions[4].getInMoments());
                    enterElevatorMoments.addAll(transitions[6].getInMoments());
                    break;
            }

            allFloorsEnterElevatorMoments.addAll(enterElevatorMoments);
            allFloorsExitElevatorMoments.addAll(exitElevatorMoments);
        }

        Collections.sort(allFloorsEnterElevatorMoments);
        Collections.sort(allFloorsExitElevatorMoments);


        PetriNet floor1DecisionMaker = model.getListObj().get(0).getNet();
        PetriNet floor2DecisionMaker = model.getListObj().get(1).getNet();
        PetriNet floor3DecisionMaker = model.getListObj().get(2).getNet();
        PetriNet floor4DecisionMaker = model.getListObj().get(3).getNet();
        PetriNet floor5DecisionMaker = model.getListObj().get(4).getNet();


        ArrayList<PetriNet> floorDecisionMakers = new ArrayList<>(Arrays.asList(
                floor1DecisionMaker,
                floor2DecisionMaker,
                floor3DecisionMaker,
                floor4DecisionMaker,
                floor5DecisionMaker
        ));


        ArrayList<Double> allFloorsStartMoveMoments = new ArrayList<>();
        ArrayList<Double> allFloorsFinishMoveMoments = new ArrayList<>();


        for (int i = 0; i < floorDecisionMakers.size(); i++) {
            PetriT[] transitions = floorDecisionMakers.get(i).getListT();

            ArrayList<Double> startMoveMoments = new ArrayList<>(transitions[2].getInMoments());
            ArrayList<Double> finishMoveMoments = new ArrayList<>(transitions[2].getOutMoments());

            int floorNumber = i + 1;

            switch (floorNumber) {
                case 2:
                    startMoveMoments.addAll(transitions[5].getInMoments());
                    finishMoveMoments.addAll(transitions[5].getOutMoments());
                    break;
                case 3:
                    startMoveMoments.addAll(transitions[7].getInMoments());
                    finishMoveMoments.addAll(transitions[7].getOutMoments());
                    break;
                case 4:
                    startMoveMoments.addAll(transitions[9].getInMoments());
                    finishMoveMoments.addAll(transitions[9].getOutMoments());
                    break;
            }

            allFloorsStartMoveMoments.addAll(startMoveMoments);
            allFloorsFinishMoveMoments.addAll(finishMoveMoments);

        }


        Collections.sort(allFloorsStartMoveMoments);
        Collections.sort(allFloorsFinishMoveMoments);

        System.out.println(allFloorsStartMoveMoments);
        System.out.println(allFloorsFinishMoveMoments);


        int startMoveIndex = 0;
        int finishMoveIndex = 0;
        int enterElevatorIndex = 0;
        int exitElevatorIndex = 0;

        int availablePlaces = 6;

        double currentTime = 0;

        double timeMoveWithPassengers = 0;
        double timeMoveWithoutPassengers = 0;
        double timeDoNotMove = 0;

        double timeMove =0;


        double lastStartMoveTime = 0;
        double lastFinishMoveTime = 0;


        while (true) {
            if (startMoveIndex == allFloorsStartMoveMoments.size() ||
                    finishMoveIndex == allFloorsFinishMoveMoments.size() ||
                    enterElevatorIndex == allFloorsEnterElevatorMoments.size() ||
                    exitElevatorIndex == allFloorsExitElevatorMoments.size()) {
                break;
            }

            double enterElevatorTime = allFloorsEnterElevatorMoments.get(enterElevatorIndex);
            double startMoveTime = allFloorsStartMoveMoments.get(startMoveIndex);
            double finishMoveTime = allFloorsFinishMoveMoments.get(finishMoveIndex);
            double exitElevatorTime = allFloorsExitElevatorMoments.get(exitElevatorIndex);


            if(finishMoveTime <= startMoveTime && finishMoveTime <= enterElevatorTime && finishMoveTime <= exitElevatorTime) {
                lastFinishMoveTime = finishMoveTime;
                finishMoveIndex++;


                if (availablePlaces == 6) {
                    timeMoveWithoutPassengers += lastFinishMoveTime - lastStartMoveTime;
                } else {
                    timeMoveWithPassengers += lastFinishMoveTime - lastStartMoveTime;
                }


                continue;
            }

            if(exitElevatorTime <= finishMoveTime && exitElevatorTime <= startMoveTime && exitElevatorTime <= enterElevatorTime) {
                exitElevatorIndex++;

                availablePlaces++;

                continue;
            }

            if(enterElevatorTime <= finishMoveTime && enterElevatorTime <= startMoveTime && enterElevatorTime <= exitElevatorTime) {
                enterElevatorIndex++;

                availablePlaces--;

                continue;
            }


            if(startMoveTime <= finishMoveTime && startMoveTime <= enterElevatorTime && startMoveTime <= exitElevatorTime) {
                lastStartMoveTime = startMoveTime;
                startMoveIndex++;

                timeDoNotMove += lastStartMoveTime - lastFinishMoveTime;
            }
        }

        System.out.println("Time do not move: " + timeDoNotMove/1000.0);
        System.out.println("Time move with passengers: " + timeMoveWithPassengers/1000.0);
        System.out.println("Time move without passengers: " + timeMoveWithoutPassengers/1000.0);

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


    public static PetriObjModel getCourseworkModel() throws ExceptionInvalidTimeDelay {
        ArrayList<PetriSim> list = new ArrayList<>();


        PetriPWithFloorNumber elevatorAvailableOn1FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn1Floor", 1, 1);
        PetriPWithFloorNumber elevatorAvailableOn2FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn2Floor", 0, 2);
        PetriPWithFloorNumber elevatorAvailableOn3FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn3Floor", 0, 3);
        PetriPWithFloorNumber elevatorAvailableOn4FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn4Floor", 0, 4);
        PetriPWithFloorNumber elevatorAvailableOn5FloorPlace = new PetriPWithFloorNumber("ElevatorAvailableOn5Floor", 0, 5);
        PetriPWithFloorNumber passengersTo1FloorPlace = new PetriPWithFloorNumber("PassengersTo1Floor", 0, 1);
        PetriPWithFloorNumber passengersTo2FloorPlace = new PetriPWithFloorNumber("PassengersTo2Floor", 0, 2);
        PetriPWithFloorNumber passengersTo3FloorPlace = new PetriPWithFloorNumber("PassengersTo3Floor", 0, 3);
        PetriPWithFloorNumber passengersTo4FloorPlace = new PetriPWithFloorNumber("PassengersTo4Floor", 0, 4);
        PetriPWithFloorNumber passengersTo5FloorPlace = new PetriPWithFloorNumber("PassengersTo5Floor", 0, 5);
        PetriPWithFloorNumber passengersWaiting1FloorPlace = new PetriPWithFloorNumber("PassengersWaiting1Floor", 1, 1);
        PetriPWithFloorNumber passengersWaiting2FloorPlace = new PetriPWithFloorNumber("PassengersWaiting2Floor", 0, 2);
        PetriPWithFloorNumber passengersWaiting3FloorPlace = new PetriPWithFloorNumber("PassengersWaiting3Floor", 0, 3);
        PetriPWithFloorNumber passengersWaiting4FloorPlace = new PetriPWithFloorNumber("PassengersWaiting4Floor", 0, 4);
        PetriPWithFloorNumber passengersWaiting5FloorPlace = new PetriPWithFloorNumber("PassengersWaiting5Floor", 0, 5);

        PetriP availablePlaces = new PetriP("AvailablePlaces", 6);

        ArrayList<PetriP> sharedPlaces = new ArrayList<>(Arrays.asList(
                elevatorAvailableOn1FloorPlace,
                elevatorAvailableOn2FloorPlace,
                elevatorAvailableOn3FloorPlace,
                elevatorAvailableOn4FloorPlace,
                elevatorAvailableOn5FloorPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                passengersWaiting1FloorPlace,
                passengersWaiting2FloorPlace,
                passengersWaiting3FloorPlace,
                passengersWaiting4FloorPlace,
                passengersWaiting5FloorPlace,
                availablePlaces
        ));


        list.add(new FloorDecisionMaker(
                1,
                elevatorAvailableOn1FloorPlace,
                null,
                elevatorAvailableOn2FloorPlace,
                new ArrayList<>(Arrays.asList(
                        passengersTo2FloorPlace,
                        passengersTo3FloorPlace,
                        passengersTo4FloorPlace,
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        passengersWaiting2FloorPlace,
                        passengersWaiting3FloorPlace,
                        passengersWaiting4FloorPlace,
                        passengersWaiting5FloorPlace
                )),
                new ArrayList<>(),
                sharedPlaces
        ));

        list.add(new FloorDecisionMaker(
                2,
                elevatorAvailableOn2FloorPlace,
                elevatorAvailableOn1FloorPlace,
                elevatorAvailableOn3FloorPlace,
                new ArrayList<>(Arrays.asList(
                        passengersTo3FloorPlace,
                        passengersTo4FloorPlace,
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(Collections.singletonList(
                        passengersTo1FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersWaiting3FloorPlace,
                        passengersWaiting4FloorPlace,
                        passengersWaiting5FloorPlace
                )),
                new ArrayList<>(Collections.singletonList(
                        passengersWaiting1FloorPlace
                )),
                sharedPlaces
        ));

        list.add(new FloorDecisionMaker(
                3,
                elevatorAvailableOn3FloorPlace,
                elevatorAvailableOn2FloorPlace,
                elevatorAvailableOn4FloorPlace,
                new ArrayList<>(Arrays.asList(
                        passengersTo4FloorPlace,
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersTo1FloorPlace,
                        passengersTo2FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersWaiting4FloorPlace,
                        passengersWaiting5FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersWaiting1FloorPlace,
                        passengersWaiting2FloorPlace
                )),
                sharedPlaces
        ));

        list.add(new FloorDecisionMaker(
                4,
                elevatorAvailableOn4FloorPlace,
                elevatorAvailableOn3FloorPlace,
                elevatorAvailableOn5FloorPlace,
                new ArrayList<>(Collections.singletonList(
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersTo1FloorPlace,
                        passengersTo2FloorPlace,
                        passengersTo3FloorPlace
                )),
                new ArrayList<>(Collections.singletonList(
                        passengersWaiting5FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersWaiting1FloorPlace,
                        passengersWaiting2FloorPlace,
                        passengersWaiting3FloorPlace
                )),
                sharedPlaces
        ));

        list.add(new FloorDecisionMaker(
                5,
                elevatorAvailableOn5FloorPlace,
                elevatorAvailableOn4FloorPlace,
                null,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        passengersTo1FloorPlace,
                        passengersTo2FloorPlace,
                        passengersTo3FloorPlace,
                        passengersTo4FloorPlace
                )),
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        passengersWaiting1FloorPlace,
                        passengersWaiting2FloorPlace,
                        passengersWaiting3FloorPlace,
                        passengersWaiting4FloorPlace
                )),
                sharedPlaces
        ));


        list.add(new FloorActivity(
                1,
                elevatorAvailableOn1FloorPlace,
                availablePlaces,
                passengersTo1FloorPlace,
                passengersWaiting1FloorPlace,
                new ArrayList<>(Arrays.asList(
                        passengersTo2FloorPlace,
                        passengersTo3FloorPlace,
                        passengersTo4FloorPlace,
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(),
                sharedPlaces
        ));


        list.add(new FloorActivity(
                2,
                elevatorAvailableOn2FloorPlace,
                availablePlaces,
                passengersTo2FloorPlace,
                passengersWaiting2FloorPlace,
                new ArrayList<>(Arrays.asList(
                        passengersTo3FloorPlace,
                        passengersTo4FloorPlace,
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(Collections.singletonList(
                        passengersTo1FloorPlace
                )),
                sharedPlaces
        ));

        list.add(new FloorActivity(
                3,
                elevatorAvailableOn3FloorPlace,
                availablePlaces,
                passengersTo3FloorPlace,
                passengersWaiting3FloorPlace,
                new ArrayList<>(Arrays.asList(
                        passengersTo4FloorPlace,
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersTo1FloorPlace,
                        passengersTo2FloorPlace
                )),
                sharedPlaces
        ));

        list.add(new FloorActivity(
                4,
                elevatorAvailableOn4FloorPlace,
                availablePlaces,
                passengersTo4FloorPlace,
                passengersWaiting4FloorPlace,
                new ArrayList<>(Collections.singletonList(
                        passengersTo5FloorPlace
                )),
                new ArrayList<>(Arrays.asList(
                        passengersTo1FloorPlace,
                        passengersTo2FloorPlace,
                        passengersTo3FloorPlace
                )),
                sharedPlaces
        ));

        list.add(new FloorActivity(
                5,
                elevatorAvailableOn5FloorPlace,
                availablePlaces,
                passengersTo5FloorPlace,
                passengersWaiting5FloorPlace,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList(
                        passengersTo1FloorPlace,
                        passengersTo2FloorPlace,
                        passengersTo3FloorPlace,
                        passengersTo4FloorPlace
                )),
                sharedPlaces
        ));


        return new PetriObjModel(list);
    }
}



