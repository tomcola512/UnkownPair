import java.util.Arrays;
import java.util.List;

public class Oracle {
    private final int[] edge;

    public Oracle(int[] edge) {
        this.edge = edge;
        System.out.println("BAD PAIR: " + this.edge[0] + ", " + this.edge[1]);
    }

    public boolean test(List<Interval> tests) {
        boolean val = Arrays.stream(edge)
                .allMatch(i ->
                        tests.stream()
                                .anyMatch(interval -> interval.contains(i)));
        return val;
    }
}
