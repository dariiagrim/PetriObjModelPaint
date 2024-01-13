package PetriObj;

import java.util.ArrayList;

public class FloorDecisionMaker extends PetriSim {

    public FloorDecisionMaker(
            int floorNumber,
            PetriP elevatorAvailableOnFloorPlace,
            PetriPWithFloorNumber elevatorAvailableOnPreviousFloorPlace,
            PetriPWithFloorNumber elevatorAvailableOnNextFloorPlace,
            ArrayList<PetriPWithFloorNumber> passengersToUpPositions,
            ArrayList<PetriPWithFloorNumber> passengersToDownPositions,
            ArrayList<PetriPWithFloorNumber> passengersWaitingUpPositions,
            ArrayList<PetriPWithFloorNumber> passengersWaitingDownPositions,
            ArrayList<PetriP> sharedPlaces
    ) throws ExceptionInvalidTimeDelay {
        super(createNet(
                floorNumber,
                elevatorAvailableOnFloorPlace,
                elevatorAvailableOnPreviousFloorPlace,
                elevatorAvailableOnNextFloorPlace,
                passengersToUpPositions,
                passengersToDownPositions,
                passengersWaitingUpPositions,
                passengersWaitingDownPositions,
                sharedPlaces
        ));
    }


    public static PetriNet createNet(
            int floorNumber,
            PetriP elevatorAvailableOnFloorPlace,
            PetriPWithFloorNumber elevatorAvailableOnPreviousFloorPlace,
            PetriPWithFloorNumber elevatorAvailableOnNextFloorPlace,
            ArrayList<PetriPWithFloorNumber> passengersToUpPositions,
            ArrayList<PetriPWithFloorNumber> passengersToDownPositions,
            ArrayList<PetriPWithFloorNumber> passengersWaitingUpPositions,
            ArrayList<PetriPWithFloorNumber> passengersWaitingDownPositions,
            ArrayList<PetriP> sharedPlaces
    ) throws ExceptionInvalidTimeDelay {
        boolean isLastFloor = floorNumber == 5;
        boolean isFirstFloor = floorNumber == 1;

        PetriP.setNext(sharedPlaces.size());

        ArrayList<PetriP> d_P = new ArrayList<>(sharedPlaces);
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();

        PetriT decideTransition = new PetriT(String.format("Decide%dFloor", floorNumber), 0.0);
        d_T.add(decideTransition);
        d_In.add(new ArcIn(elevatorAvailableOnFloorPlace, decideTransition, 1));

        PetriT finishDecisionTransition = new PetriT(String.format("FinishDecision%dFloor", floorNumber), 0.0);
        d_T.add(finishDecisionTransition);
        d_Out.add(new ArcOut(finishDecisionTransition, elevatorAvailableOnFloorPlace, 1));

        PetriP toDecidePlace = new PetriP(String.format("ToDecide%dFloor", floorNumber), 0);
        d_P.add(toDecidePlace);
        d_Out.add(new ArcOut(decideTransition, toDecidePlace, 1));

        PetriP toFinishDecisionPlace = new PetriP(String.format("ToFinishDecision%dFloor", floorNumber), 0);
        d_P.add(toFinishDecisionPlace);
        d_In.add(new ArcIn(toFinishDecisionPlace, finishDecisionTransition, 1));


        if (!isFirstFloor) {
            PetriP moveDownPlace = new PetriP(String.format("MoveDown%dFloor", floorNumber), 0);
            d_P.add(moveDownPlace);
            PetriT moveDownTransition = new PetriT(String.format("Move%dTo%d", floorNumber, floorNumber - 1), 0.4);
            moveDownTransition.setPriority(2);
            d_T.add(moveDownTransition);
            d_In.add(new ArcIn(moveDownPlace, moveDownTransition, 1));
            d_In.add(new ArcIn(elevatorAvailableOnFloorPlace, moveDownTransition, 1));
            d_Out.add(new ArcOut(moveDownTransition, elevatorAvailableOnPreviousFloorPlace, 1));

            for (PetriPWithFloorNumber p : passengersToDownPositions) {
                PetriT decideMoveDownTransition = new PetriT(String.format("DecideMoveDown%dFloorPassengersTo%dFloor", floorNumber, p.getFloorNumber()), 0);
                decideMoveDownTransition.setPriority(5);
                decideToMoveActions(
                        d_T,
                        d_In,
                        d_Out,
                        decideMoveDownTransition,
                        toDecidePlace,
                        toFinishDecisionPlace,
                        p,
                        moveDownPlace
                );
            }

            for (PetriPWithFloorNumber p : passengersWaitingDownPositions) {
                PetriT decideMoveDownTransition = new PetriT(String.format("DecideMoveDown%dFloorWaiting%dFloor", floorNumber, p.getFloorNumber()), 0);
                decideToMoveActions(
                        d_T,
                        d_In,
                        d_Out,
                        decideMoveDownTransition,
                        toDecidePlace,
                        toFinishDecisionPlace,
                        p,
                        moveDownPlace
                );
            }
        }

        if (!isLastFloor) {
            PetriP moveUpPlace = new PetriP(String.format("MoveUp%dFloor", floorNumber), 0);
            d_P.add(moveUpPlace);
            PetriT moveUpTransition = new PetriT(String.format("Move%dTo%d", floorNumber, floorNumber + 1), 0.4);
            moveUpTransition.setPriority(2);
            d_T.add(moveUpTransition);
            d_In.add(new ArcIn(moveUpPlace, moveUpTransition, 1));
            d_In.add(new ArcIn(elevatorAvailableOnFloorPlace, moveUpTransition, 1));
            d_Out.add(new ArcOut(moveUpTransition, elevatorAvailableOnNextFloorPlace, 1));

            for (PetriPWithFloorNumber p : passengersToUpPositions) {
                PetriT decideMoveUpTransition = new PetriT(String.format("DecideMoveUp%dFloorPassengersTo%dFloor", floorNumber, p.getFloorNumber()), 0);
                decideMoveUpTransition.setPriority(5);
                decideToMoveActions(
                        d_T,
                        d_In,
                        d_Out,
                        decideMoveUpTransition,
                        toDecidePlace,
                        toFinishDecisionPlace,
                        p,
                        moveUpPlace
                );
            }

            for (PetriPWithFloorNumber p : passengersWaitingUpPositions) {
                PetriT decideMoveUpTransition = new PetriT(String.format("DecideMoveUp%dFloorWaiting%dFloor", floorNumber, p.getFloorNumber()), 0);
                decideToMoveActions(
                        d_T,
                        d_In,
                        d_Out,
                        decideMoveUpTransition,
                        toDecidePlace,
                        toFinishDecisionPlace,
                        p,
                        moveUpPlace
                );
            }
        }

        PetriNet d_Net = new PetriNet(String.format("%dFloorDecisionMaker", floorNumber), d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    private static void decideToMoveActions(
            ArrayList<PetriT> d_T,
            ArrayList<ArcIn> d_In,
            ArrayList<ArcOut> d_Out,
            PetriT decideMoveTransition,
            PetriP toDecidePlace,
            PetriP toFinishDecisionPlace,
            PetriP infoPlace,
            PetriP movePlace
    ) {
        d_T.add(decideMoveTransition);
        d_In.add(new ArcIn(toDecidePlace, decideMoveTransition, 1));
        d_In.add(new ArcIn(infoPlace, decideMoveTransition, 1, true));
        d_Out.add(new ArcOut(decideMoveTransition, toFinishDecisionPlace, 1));
        d_Out.add(new ArcOut(decideMoveTransition, movePlace, 1));
    }
}
