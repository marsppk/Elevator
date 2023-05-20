public class Passenger {
    private final int direction;
    private final int begin;
    private final int end;
    Passenger(int begin, int end){
        int d = Constants.WITHOUT_ACTION;
        if (begin > end) {
            d = Constants.DOWN;
        }
        else if (begin < end)
            d = Constants.UP;

        this.direction = d;
        this.begin = begin;
        this.end = end;
    }
    public int getDirection() {
        return direction;
    }
    public int getBegin() {
        return begin;
    }
    public int getEnd() {
        return end;
    }
}
