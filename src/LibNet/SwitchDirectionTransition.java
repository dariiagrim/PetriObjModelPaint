package LibNet;

import PetriObj.PetriT;

public class SwitchDirectionTransition {
    PetriT transition;
    boolean isUp;

    SwitchDirectionTransition(PetriT t, boolean up) {
        transition = t;
        isUp = up;
    }
}
