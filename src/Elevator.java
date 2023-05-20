import java.util.ArrayList;

public class Elevator {
    private final int id;
    private int current_floor;
    private int direction;
    private final ArrayList<Passenger> passengers = new ArrayList<>();
    Elevator(int id){
        this.id = id;
    }
    public void move(){
        current_floor += direction;
        passengers.removeIf(passenger -> (passenger.getEnd() == current_floor));
    }
    public int getCurrent_floor() {
        return current_floor;
    }
    public int getDirection() {
        return direction;
    }
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setPassengers(Passenger passenger) {
        this.passengers.add(passenger);
    }
    public String work_log() {
        String direction_str;
        if (direction == -1)
            direction_str = "DOWN";
        else if (direction == 1)
            direction_str = "UP";
        else
            direction_str = "WITHOUT ACTION";
        return "Elevator id: " + id + "\nCurrent floor: " + current_floor +
                "\nDirection: " + direction_str + "\nNumber of passengers: " + passengers.size();
    }
}
