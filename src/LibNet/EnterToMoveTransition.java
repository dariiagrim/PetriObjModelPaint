package LibNet;

import PetriObj.PetriT;

public class EnterToMoveTransition {
    PetriT transition;
    boolean isUp;
    int resultingFloor;

    EnterToMoveTransition(PetriT t, boolean up, int resFloor) {
        transition = t;
        isUp = up;
        resultingFloor = resFloor;
    }
}
