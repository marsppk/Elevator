public class Request implements Runnable{
    private final int amount_of_floors;
    private final int number_of_people_on_floor;
    private final Administration admin;
    private final boolean doStop;
    int frequency;
    Request (int amount_floors, int number_of_people_on_floor, Administration admin, int frequency){
        this.amount_of_floors = amount_floors;
        this.number_of_people_on_floor = number_of_people_on_floor;
        this.admin = admin;
        this.doStop = false;
        this.frequency = frequency;
    }
    private synchronized boolean keepRunning() {
        return !this.doStop;
    }
    public void run(){
        Random random = new Random();
        while (keepRunning()) {
            int number_people = random.random(1, number_of_people_on_floor + 1);
            int begin = random.random(1, amount_of_floors + 1);
            for (int i = 0; i < number_people; i++) {
                int end = random.random(1, amount_of_floors + 1);
                while (begin == end) {
                    end = random.random(1, amount_of_floors + 1);
                }
                admin.setPassengers(new Passenger(begin, end));
            }
            try {
                Thread.sleep(frequency);
            } catch (InterruptedException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
