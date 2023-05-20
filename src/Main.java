import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int number_of_elevators = 2;
        Scanner scan = new Scanner(System.in);
        System.out.println("Введите максимально возможное количество пассажиров в лифте: ");
        int capacity = scan.nextInt();
        System.out.println("Введите максимально возможное количество этажей: ");
        int amount_of_floors = scan.nextInt();
        System.out.println("Введите максимально возможное количество пассажиров на этаже: ");
        int number_of_people_on_floor = scan.nextInt();
        System.out.println("Введите частоту генерирования новых заявок в миллисекундах: ");
        int frequency = scan.nextInt();
        Administration admin = new Administration(amount_of_floors, capacity, number_of_elevators);
        Request request = new Request(amount_of_floors, number_of_people_on_floor, admin, frequency);
        Thread requestsThread = new Thread(request);
        Thread elevatorsThread = new Thread(admin);
        requestsThread.start();
        elevatorsThread.start();
    }
}
