package LibNet;

import PetriObj.PetriT;

public class MoveTransition {
    PetriT transition;
    boolean isUp;

    MoveTransition(PetriT t, boolean up) {
        transition = t;
        isUp = up;
    }
}
