public class Random {
    public int random(int begin, int end) {
        return (int) (Math.random() * (end - begin) + begin);
    }
}
