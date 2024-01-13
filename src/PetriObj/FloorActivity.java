package PetriObj;

import java.util.ArrayList;

public class FloorActivity extends PetriSim {
    public FloorActivity(
            int floorNumber,
            PetriP elevatorAvailableOnFloorPlace,
            PetriP availablePlacesPlace,
            PetriP passengersToThisFloorPlace,
            PetriP passengerWaitingOnThisFloorToMoveUp,
            PetriP passengerWaitingOnThisFloorToMoveDown,
            ArrayList<PetriPWithFloorNumber> passengersToUpPlaces,
            ArrayList<PetriPWithFloorNumber> passengersToDownPlaces
    ) throws ExceptionInvalidTimeDelay {
        super(createNet(
                floorNumber,
                elevatorAvailableOnFloorPlace,
                availablePlacesPlace,
                passengersToThisFloorPlace,
                passengerWaitingOnThisFloorToMoveUp,
                passengerWaitingOnThisFloorToMoveDown,
                passengersToUpPlaces,
                passengersToDownPlaces
        ));
    }


    public static PetriNet createNet(
            int floorNumber,
            PetriP elevatorAvailableOnFloorPlace,
            PetriP availablePlacesPlace,
            PetriP passengersToThisFloorPlace,
            PetriP passengerWaitingOnThisFloorToMoveUp,
            PetriP passengerWaitingOnThisFloorToMoveDown,
            ArrayList<PetriPWithFloorNumber> passengersToUpPlaces,
            ArrayList<PetriPWithFloorNumber> passengersToDownPlaces
    ) throws ExceptionInvalidTimeDelay {
        boolean isLastFloor = floorNumber == 5;
        boolean isFirstFloor = floorNumber == 1;

        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();


        PetriT exitTransition = new PetriT(String.format("Exit%dFloor", floorNumber), 0.0);
        exitTransition.setPriority(5);
        d_T.add(exitTransition);
        d_In.add(new ArcIn(elevatorAvailableOnFloorPlace, exitTransition, 1));
        d_Out.add(new ArcOut(exitTransition, elevatorAvailableOnFloorPlace, 1));
        d_In.add(new ArcIn(passengersToThisFloorPlace, exitTransition, 1));
        d_Out.add(new ArcOut(exitTransition, availablePlacesPlace, 1));

        PetriP exitedPlace = new PetriP(String.format("Exited%dFloor", floorNumber), 0);
        d_P.add(exitedPlace);
        d_Out.add(new ArcOut(exitTransition, exitedPlace, 1));


        if (!isFirstFloor) {
            PetriT spendTimeTransition = new PetriT(String.format("SpendTime%dFloor", floorNumber), 120);
            spendTimeTransition.setDistribution("unif", spendTimeTransition.getTimeServ());
            spendTimeTransition.setParamDeviation(15.0);
            d_T.add(spendTimeTransition);
            d_In.add(new ArcIn(exitedPlace, spendTimeTransition, 1));

            if (!isLastFloor) {
                PetriP readyToLeavePlace = new PetriP(String.format("ReadyToLeave%dFloor", floorNumber), 0);
                d_P.add(readyToLeavePlace);
                d_Out.add(new ArcOut(spendTimeTransition, readyToLeavePlace, 1));

                PetriT readyToMoveDownTransition = new PetriT(String.format("ReadyToMoveDown%dFloor", floorNumber), 0.0);
                exitTransition.setProbability(getProbabilityToMoveDownProbabilityForFloor(floorNumber));
                d_T.add(readyToMoveDownTransition);
                d_In.add(new ArcIn(readyToLeavePlace, readyToMoveDownTransition, 1));
                d_Out.add(new ArcOut(readyToMoveDownTransition, passengerWaitingOnThisFloorToMoveDown, 1));

                PetriT readyToMoveUpTransition = new PetriT(String.format("ReadyToMoveUp%dFloor", floorNumber), 0.0);
                exitTransition.setProbability(getProbabilityToMoveUpProbabilityForFloor(floorNumber));
                d_T.add(readyToMoveUpTransition);
                d_In.add(new ArcIn(readyToLeavePlace, readyToMoveUpTransition, 1));
                d_Out.add(new ArcOut(readyToMoveUpTransition, passengerWaitingOnThisFloorToMoveUp, 1));
            } else {
                d_Out.add(new ArcOut(spendTimeTransition, passengerWaitingOnThisFloorToMoveDown, 1));
            }
        } else {
            PetriP toArrivePlace = new PetriP(String.format("ToArrive%dFloor", floorNumber), 0);
            d_P.add(toArrivePlace);

            PetriT arriveTransition = new PetriT(String.format("Arrive%dFloor", floorNumber), 1.0);
            arriveTransition.setDistribution("exp", arriveTransition.getTimeServ());

            d_In.add(new ArcIn(toArrivePlace, arriveTransition, 1));
            d_Out.add(new ArcOut(arriveTransition, toArrivePlace, 1));

            d_Out.add(new ArcOut(arriveTransition, passengerWaitingOnThisFloorToMoveUp, 1));
        }


        if (!isLastFloor) {
            PetriT enterToMoveUpTransition = new PetriT(String.format("EnterToMoveUp%dFloor", floorNumber), 1.0);
            d_T.add(enterToMoveUpTransition);
            d_In.add(new ArcIn(elevatorAvailableOnFloorPlace, enterToMoveUpTransition, 1));
            d_In.add(new ArcIn(availablePlacesPlace, enterToMoveUpTransition, 1));
            d_In.add(new ArcIn(passengerWaitingOnThisFloorToMoveUp, enterToMoveUpTransition, 1));

            PetriP moveUpPlace = new PetriP(String.format("MoveUp%dFloor", floorNumber), 0);
            d_P.add(moveUpPlace);

            d_Out.add(new ArcOut(enterToMoveUpTransition, moveUpPlace, 1));

            for (PetriPWithFloorNumber p : passengersToUpPlaces) {
                PetriT chooseFloorTransition = new PetriT(String.format("%dFloorChoose%dFloor", floorNumber, p.getFloorNumber()), 0);
                chooseFloorTransition.setProbability(getProbabilityToMoveFromFloorToFloor(floorNumber, p.getFloorNumber()));
                d_T.add(chooseFloorTransition);

                d_In.add(new ArcIn(moveUpPlace, chooseFloorTransition, 1));
                d_Out.add(new ArcOut(chooseFloorTransition, p, 1));
                d_Out.add(new ArcOut(chooseFloorTransition, elevatorAvailableOnFloorPlace, 1));
            }
        }

        if (!isLastFloor) {
            PetriT enterToMoveDownTransition = new PetriT(String.format("EnterToMoveDown%dFloor", floorNumber), 1.0);
            d_T.add(enterToMoveDownTransition);
            d_In.add(new ArcIn(elevatorAvailableOnFloorPlace, enterToMoveDownTransition, 1));
            d_In.add(new ArcIn(availablePlacesPlace, enterToMoveDownTransition, 1));
            d_In.add(new ArcIn(passengerWaitingOnThisFloorToMoveDown, enterToMoveDownTransition, 1));

            PetriP moveDownPlace = new PetriP(String.format("MoveDown%dFloor", floorNumber), 0);
            d_P.add(moveDownPlace);

            d_Out.add(new ArcOut(enterToMoveDownTransition, moveDownPlace, 1));

            for (PetriPWithFloorNumber p : passengersToDownPlaces) {
                PetriT chooseFloorTransition = new PetriT(String.format("%dFloorChoose%dFloor", floorNumber, p.getFloorNumber()), 0);
                chooseFloorTransition.setProbability(getProbabilityToMoveFromFloorToFloor(floorNumber, p.getFloorNumber()));
                d_T.add(chooseFloorTransition);

                d_In.add(new ArcIn(moveDownPlace, chooseFloorTransition, 1));
                d_Out.add(new ArcOut(chooseFloorTransition, p, 1));
                d_Out.add(new ArcOut(chooseFloorTransition, elevatorAvailableOnFloorPlace, 1));
            }
        }

        PetriNet d_Net = new PetriNet(String.format("%dFloorActivity", floorNumber), d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    private static double getProbabilityToMoveFromFloorToFloor(int floorNumber, int toFloorNumber) {
        switch (floorNumber) {
            case 1:
                return 0.25;
            case 2:
                if (toFloorNumber == 1) {
                    return 1;
                }
                return 0.33;
            case 3:
                switch (toFloorNumber) {
                    case 1:
                        return 0.875;
                    case 2:
                        return 0.125;
                    default:
                        return 0.5;
                }
            case 4:
                switch (toFloorNumber) {
                    case 1:
                        return 0.778;
                    case 2:
                    case 3:
                        return 0.111;
                    case 5:
                        return 1;
                }
            case 5:
                if (toFloorNumber == 1) {
                    return 0.7;
                }

                return 0.1;

            default:
                return 1;
        }
    }

    private static double getProbabilityToMoveUpProbabilityForFloor(int floorNumber) {
        switch (floorNumber) {
            case 1:
                return 1;
            case 2:
                return 0.3;
            case 3:
                return 0.2;
            case 4:
                return 0.1;
            default:
                return 0;
        }
    }

    private static double getProbabilityToMoveDownProbabilityForFloor(int floorNumber) {
        switch (floorNumber) {
            case 2:
                return 0.7;
            case 3:
                return 0.8;
            case 4:
                return 0.9;
            case 5:
                return 1;
            default:
                return 0;
        }
    }
}
