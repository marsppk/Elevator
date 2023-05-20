import java.util.ArrayList;

public class Administration implements Runnable{
    private final int amount_of_floors;
    private final int capacity;
    private final boolean doStop;
    private final ArrayList<Passenger> passengers = new ArrayList<>();
    private final ArrayList<Elevator> elevators = new ArrayList<>();
    private synchronized boolean keepRunning() {
        return !this.doStop;
    }
    Administration(int amount_of_floors, int capacity, int amount_of_elevators){
        this.amount_of_floors = amount_of_floors;
        this.capacity = capacity;
        this.doStop = false;

        for (int i = 1; i <= amount_of_elevators; i++){
            this.elevators.add(new Elevator(i));
        }
    }
    public void run(){
        int step = 0;
        while (keepRunning()) {
            step++;
            int used_elevators = 0;
            System.out.println("=========================");
            System.out.println("Step №" + step);
            System.out.println("--------------------------");
            for (Elevator elevator: elevators) {
                elevator.move();
                int current_floor = elevator.getCurrent_floor();
                int available_capacity = used_elevators * capacity;
                if (capacity - elevator.getPassengers().size() > 0) {
                    ArrayList<Passenger> UP_Request = new ArrayList<>();
                    ArrayList<Passenger> DOWN_Request = new ArrayList<>();
                    ArrayList<Passenger> UP_now = new ArrayList<>();
                    ArrayList<Passenger> DOWN_now = new ArrayList<>();
                    for (Passenger passenger : passengers) {
                        int passenger_s_floor = passenger.getBegin();
                        int passenger_direction = passenger.getDirection();
                        if (passenger_s_floor > current_floor)
                            UP_Request.add(passenger);
                        else if (passenger_s_floor < current_floor)
                            DOWN_Request.add(passenger);
                        if (passenger_s_floor == current_floor && passenger_direction == Constants.UP) {
                            UP_now.add(passenger);
                        } else if (passenger_s_floor == current_floor && passenger_direction == Constants.DOWN) {
                            DOWN_now.add(passenger);
                        }
                    }
                    // изменение направления
                    if (elevator.getPassengers().size() == 0 || current_floor == amount_of_floors) {
                        if (elevator.getDirection() == Constants.UP && (UP_Request.size() < available_capacity
                                || DOWN_Request.size() < available_capacity)) {
                            elevator.setDirection(Constants.WITHOUT_ACTION);
                        } else if (DOWN_Request.size() > available_capacity || current_floor == amount_of_floors) {
                            elevator.setDirection(Constants.DOWN);
                        }
                    } else if (current_floor == 0) {
                        if (UP_Request.size() > available_capacity) {
                            elevator.setDirection(Constants.UP);
                        } else {
                            elevator.setDirection(Constants.WITHOUT_ACTION);
                        }
                    } else {
                        elevator.setDirection(Constants.UP);
                    }
                    if (elevator.getDirection() == Constants.WITHOUT_ACTION) {
                        if (UP_now.size() > DOWN_now.size()
                                && UP_Request.size() > available_capacity) {
                            elevator.setDirection(Constants.UP);
                            
                        } else if (UP_now.size() <= DOWN_now.size()
                                && DOWN_Request.size() > available_capacity) {
                            elevator.setDirection(Constants.DOWN);
                        }
                    }
                    if (current_floor == amount_of_floors) {
                        elevator.setDirection(Constants.DOWN);
                    }
                    if (current_floor == 0) {
                        elevator.setDirection(Constants.UP);
                    }
                    // распределение пассажиров
                    ArrayList<Passenger> priority_passengers = new ArrayList<>();
                    if (elevator.getDirection() == Constants.DOWN) {
                        priority_passengers = DOWN_now;
                    } else if (elevator.getDirection() == Constants.UP) {
                        priority_passengers = UP_now;
                    }
                    // запуск пассажиров (если возможно)
                    while (priority_passengers.size() != 0 && elevator.getPassengers().size() < capacity) {
                        elevator.setPassengers(priority_passengers.get(0));
                        passengers.remove(priority_passengers.get(0));
                        priority_passengers.remove(0);
                    }
                // если в лифте нет мест, делаем проверку на корректность дальнейшего направления лифта
                } else {
                    if (current_floor == amount_of_floors) {
                        elevator.setDirection(Constants.DOWN);
                    }
                    if (current_floor == 0) {
                        elevator.setDirection(Constants.UP);
                    }
                }
                // увеличиваем счетчик рабочих лифтов
                used_elevators++;
                System.out.println(elevator.work_log());
                System.out.println("--------------------------");
            }
            System.out.println("=========================\n");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
    public void setPassengers(Passenger passenger) {
        this.passengers.add(passenger);
    }
}
