package PetriObj;

public class Main {
    public static void main(String[] args) throws ExceptionInvalidTimeDelay, ExceptionInvalidNetStructure {
        int elevatorCapacity = 6;
        ElevatorObjModel model = new ElevatorObjModel(
                1,
                15,
                120,
                0.7,
                0.1,
                0.4,
                elevatorCapacity
        );


        model.go(1000);

        model.calcStats(1,
//                15,
//                120,
                0.7,
//                0.1,
                0.4,
                elevatorCapacity);
    }
}
