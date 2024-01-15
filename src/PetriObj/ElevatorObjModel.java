package PetriObj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ElevatorObjModel extends PetriObjModel {
    public ElevatorObjModel(
            double firstFloorArrivalFrequency,
            double spendOnFloorTimeLowerLimit,
            double spendOnFloorTimeUpperLimit,
            double choose1FloorProbability,
            double chooseOtherFloorProbability,
            double floorMoveTime,
            int liftCapacity
    ) throws ExceptionInvalidTimeDelay {
        super(getObjects(
                firstFloorArrivalFrequency,
                spendOnFloorTimeLowerLimit,
                spendOnFloorTimeUpperLimit,
                choose1FloorProbability,
                chooseOtherFloorProbability,
                floorMoveTime,
                liftCapacity
        ));
    }

    private static ArrayList<PetriSim> getObjects(
            double firstFloorArrivalFrequency,
            double spendOnFloorTimeLowerLimit,
            double spendOnFloorTimeUpperLimit,
            double choose1FloorProbability,
            double chooseOtherFloorProbability,
            double floorMoveTime,
            int elevatorCapacity
    ) throws ExceptionInvalidTimeDelay {
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

        PetriP availablePlaces = new PetriP("AvailablePlaces", elevatorCapacity);

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
                sharedPlaces,
                floorMoveTime
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
                sharedPlaces,
                floorMoveTime
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
                sharedPlaces,
                floorMoveTime
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
                sharedPlaces,
                floorMoveTime
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
                sharedPlaces,
                floorMoveTime
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
                sharedPlaces,
                firstFloorArrivalFrequency,
                spendOnFloorTimeLowerLimit,
                spendOnFloorTimeUpperLimit,
                choose1FloorProbability,
                chooseOtherFloorProbability
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
                sharedPlaces,
                firstFloorArrivalFrequency,
                spendOnFloorTimeLowerLimit,
                spendOnFloorTimeUpperLimit,
                choose1FloorProbability,
                chooseOtherFloorProbability
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
                sharedPlaces,
                firstFloorArrivalFrequency,
                spendOnFloorTimeLowerLimit,
                spendOnFloorTimeUpperLimit,
                choose1FloorProbability,
                chooseOtherFloorProbability
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
                sharedPlaces,
                firstFloorArrivalFrequency,
                spendOnFloorTimeLowerLimit,
                spendOnFloorTimeUpperLimit,
                choose1FloorProbability,
                chooseOtherFloorProbability
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
                sharedPlaces,
                firstFloorArrivalFrequency,
                spendOnFloorTimeLowerLimit,
                spendOnFloorTimeUpperLimit,
                choose1FloorProbability,
                chooseOtherFloorProbability
        ));


        return list;
    }


    public void calcStats(double firstFloorArrivalFrequency,
                          double choose1FloorProbability,
                          double floorMoveTime,
                          int elevatorCapacity) {
        PetriNet floorActivity1 = getListObj().get(5).getNet();
        PetriNet floorActivity2 = getListObj().get(6).getNet();
        PetriNet floorActivity3 = getListObj().get(7).getNet();
        PetriNet floorActivity4 = getListObj().get(8).getNet();
        PetriNet floorActivity5 = getListObj().get(9).getNet();


        ArrayList<PetriNet> floorActivities = new ArrayList<>(Arrays.asList(
                floorActivity1,
                floorActivity2,
                floorActivity3,
                floorActivity4,
                floorActivity5
        ));

        ArrayList<Double> allFloorsEnterElevatorMoments = new ArrayList<>();
        ArrayList<Double> allFloorsExitElevatorMoments = new ArrayList<>();

        ArrayList<Double> meanWaitingTimes = new ArrayList<>();
        int maxPassengersNumber;
        double meanPassengersNumber;
        double timeDoNotMoveStats;
        double timeMoveWithPassengersStats;
        double timeMoveWithoutPassengersStats;
        double overflowStats;

        int overflowCount = 0;
        int leftWaitingForElevatorBecauseOfOverflow = 0;

        for (int i = 0; i < floorActivities.size(); i++) {
            PetriT[] transitions = floorActivities.get(i).getListT();
            ArrayList<Double> startWaitingUpMoments = null;
            ArrayList<Double> finishWaitingUpMoments = null;
            ArrayList<Double> startWaitingDownMoments = null;
            ArrayList<Double> finishWaitingDownMoments = null;
            ArrayList<Double> enterElevatorMoments = new ArrayList<>();
            ArrayList<Double> exitElevatorMoments = new ArrayList<>(transitions[0].getOutMoments());

            int floorNumber = i + 1;

            switch (floorNumber) {
                case 1:
                    startWaitingUpMoments = transitions[1].getOutMoments();
                    finishWaitingUpMoments = transitions[2].getInMoments();
                    enterElevatorMoments.addAll(transitions[2].getInMoments());
                    break;
                case 2:
                    startWaitingUpMoments = transitions[3].getOutMoments();
                    finishWaitingUpMoments = transitions[4].getInMoments();
                    startWaitingDownMoments = transitions[2].getOutMoments();
                    finishWaitingDownMoments = transitions[8].getInMoments();
                    enterElevatorMoments.addAll(transitions[4].getInMoments());
                    enterElevatorMoments.addAll(transitions[8].getInMoments());
                    break;
                case 3:
                    startWaitingUpMoments = transitions[3].getOutMoments();
                    finishWaitingUpMoments = transitions[4].getInMoments();
                    startWaitingDownMoments = transitions[2].getOutMoments();
                    finishWaitingDownMoments = transitions[7].getInMoments();
                    enterElevatorMoments.addAll(transitions[4].getInMoments());
                    enterElevatorMoments.addAll(transitions[7].getInMoments());
                    break;
                case 4:
                    startWaitingUpMoments = transitions[3].getOutMoments();
                    finishWaitingUpMoments = transitions[4].getInMoments();
                    startWaitingDownMoments = transitions[2].getOutMoments();
                    finishWaitingDownMoments = transitions[6].getInMoments();
                    enterElevatorMoments.addAll(transitions[4].getInMoments());
                    enterElevatorMoments.addAll(transitions[6].getInMoments());
                    break;
                case 5:
                    startWaitingDownMoments = transitions[1].getOutMoments();
                    finishWaitingDownMoments = transitions[2].getInMoments();
                    enterElevatorMoments.addAll(transitions[2].getInMoments());
                    break;
            }

            allFloorsEnterElevatorMoments.addAll(enterElevatorMoments);
            allFloorsExitElevatorMoments.addAll(exitElevatorMoments);

            if (startWaitingDownMoments != null && finishWaitingDownMoments != null) {
                double diffSum = 0.0;

                for (int j = 0; j < finishWaitingDownMoments.size(); j++) {
                    diffSum += finishWaitingDownMoments.get(i) - startWaitingDownMoments.get(i);
                }

                System.out.printf("Mean waiting time down %d floor: %f%n", i + 1, diffSum / (double) finishWaitingDownMoments.size());

                meanWaitingTimes.add(diffSum / (double) finishWaitingDownMoments.size());
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
                meanWaitingTimes.add(diffSum / divider);


                // calculate not enter because overflow

                ArrayList<Double> startWaitingMoments = new ArrayList<>();
                if (floorNumber != 5) {
                    startWaitingMoments.addAll(startWaitingUpMoments);
                }
                if (floorNumber != 1) {
                    startWaitingMoments.addAll(startWaitingDownMoments);
                }


                int oldWaitingForElevatorNumber = 0;
                int waitingForElevatorNumber = 0;
                int enterElevatorIndex = 1;

                Collections.sort(startWaitingMoments);
                Collections.sort(enterElevatorMoments);

                for (double startWaitingMoment : startWaitingMoments) {
                    if (enterElevatorIndex >= enterElevatorMoments.size()) {
                        break;
                    }

                    double enterElevatorTime = enterElevatorMoments.get(enterElevatorIndex);

                    waitingForElevatorNumber++;

                    if (startWaitingMoment >= enterElevatorTime) {
                        while (startWaitingMoment >= enterElevatorTime) {
                            if (oldWaitingForElevatorNumber > 0) {
                                oldWaitingForElevatorNumber--;
                            } else {
                                waitingForElevatorNumber--;
                            }

                            enterElevatorIndex++;
                            if (enterElevatorIndex == enterElevatorMoments.size()) {
                                break;
                            }
                            enterElevatorTime = enterElevatorMoments.get(enterElevatorIndex);
                        }

                        overflowCount += waitingForElevatorNumber - 1;
                        oldWaitingForElevatorNumber += waitingForElevatorNumber;
                        waitingForElevatorNumber = 0;
                    }

                }

                leftWaitingForElevatorBecauseOfOverflow += oldWaitingForElevatorNumber;

            }
        }


        ArrayList<Double> allFloorsStartMoveMoments = new ArrayList<>();
        ArrayList<Double> allFloorsFinishMoveMoments = new ArrayList<>();


        PetriP availablePlacesPlace = getListObj().get(5).getNet().getListP()[15];

        System.out.printf("Max number passengers: %d%n", elevatorCapacity - availablePlacesPlace.getObservedMin());
        maxPassengersNumber = elevatorCapacity - availablePlacesPlace.getObservedMin();
        System.out.printf("Mean number passengers: %f%n", elevatorCapacity - availablePlacesPlace.getMean());
        meanPassengersNumber = elevatorCapacity - availablePlacesPlace.getMean();


        Collections.sort(allFloorsEnterElevatorMoments);
        Collections.sort(allFloorsExitElevatorMoments);


        PetriNet floor1DecisionMaker = getListObj().get(0).getNet();
        PetriNet floor2DecisionMaker = getListObj().get(1).getNet();
        PetriNet floor3DecisionMaker = getListObj().get(2).getNet();
        PetriNet floor4DecisionMaker = getListObj().get(3).getNet();
        PetriNet floor5DecisionMaker = getListObj().get(4).getNet();


        ArrayList<PetriNet> floorDecisionMakers = new ArrayList<>(Arrays.asList(
                floor1DecisionMaker,
                floor2DecisionMaker,
                floor3DecisionMaker,
                floor4DecisionMaker,
                floor5DecisionMaker
        ));

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

        int startMoveIndex = 0;
        int finishMoveIndex = 0;
        int enterElevatorIndex = 0;
        int exitElevatorIndex = 0;

        int availablePlaces = elevatorCapacity;

        double timeMoveWithPassengers = 0;
        double timeMoveWithoutPassengers = 0;
        double timeDoNotMove = 0;

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


            if (finishMoveTime <= startMoveTime && finishMoveTime <= enterElevatorTime && finishMoveTime <= exitElevatorTime) {
                lastFinishMoveTime = finishMoveTime;
                finishMoveIndex++;


                if (availablePlaces == elevatorCapacity) {
                    timeMoveWithoutPassengers += lastFinishMoveTime - lastStartMoveTime;
                } else {
                    timeMoveWithPassengers += lastFinishMoveTime - lastStartMoveTime;
                }


                continue;
            }

            if (exitElevatorTime <= finishMoveTime && exitElevatorTime <= startMoveTime && exitElevatorTime <= enterElevatorTime) {
                exitElevatorIndex++;

                availablePlaces++;

                continue;
            }

            if (enterElevatorTime <= finishMoveTime && enterElevatorTime <= startMoveTime && enterElevatorTime <= exitElevatorTime) {
                enterElevatorIndex++;

                availablePlaces--;

                continue;
            }


            if (startMoveTime <= finishMoveTime && startMoveTime <= enterElevatorTime && startMoveTime <= exitElevatorTime) {
                lastStartMoveTime = startMoveTime;
                startMoveIndex++;

                timeDoNotMove += lastStartMoveTime - lastFinishMoveTime;
            }
        }

        System.out.println("Time do not move: " + (1 - (timeMoveWithPassengers / getCurrentTime() + timeMoveWithoutPassengers / getCurrentTime())));
        timeDoNotMoveStats = (1 - (timeMoveWithPassengers / getCurrentTime() + timeMoveWithoutPassengers / getCurrentTime()));
        System.out.println("Time move with passengers: " + timeMoveWithPassengers / getCurrentTime());
        timeMoveWithPassengersStats = timeMoveWithPassengers / getCurrentTime();
        System.out.println("Time move without passengers: " + timeMoveWithoutPassengers / getCurrentTime());
        timeMoveWithoutPassengersStats = timeMoveWithoutPassengers / getCurrentTime();

        System.out.println("Overflow: " + overflowCount / (double) (allFloorsEnterElevatorMoments.size() + leftWaitingForElevatorBecauseOfOverflow));


        System.out.print(firstFloorArrivalFrequency + ",");
        System.out.print(choose1FloorProbability + ",");
        System.out.print(floorMoveTime + ",");
        System.out.print(elevatorCapacity + ",");

       for (Double meanWaitingTime : meanWaitingTimes) {
           System.out.print(meanWaitingTime + ",");
       }

        System.out.print(maxPassengersNumber + ",");
        System.out.print(meanPassengersNumber + ",");
        System.out.print(timeDoNotMoveStats + ",");
        System.out.print(timeMoveWithPassengersStats + ",");
        System.out.print(timeMoveWithoutPassengersStats + ",");

    }

}
