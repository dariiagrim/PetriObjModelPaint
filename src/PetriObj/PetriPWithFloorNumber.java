package PetriObj;

public class PetriPWithFloorNumber extends PetriP {

    int floorNumber;

    public PetriPWithFloorNumber(String n, int m, int fNumber) {
        super(n, m);
        floorNumber = fNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
