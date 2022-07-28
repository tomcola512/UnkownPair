import java.util.Arrays;
import java.util.List;

public class Oracle {
    private final long[] edge;

    public Oracle(long[] edge) {
        this.edge = edge;
        System.out.println("REMOVEME: " + this.edge[0] + ", " + this.edge[1]);
    }

    public boolean test(List<Interval> tests) {
        return Arrays.stream(edge)
                .allMatch(i ->
                        tests.stream()
                                .anyMatch(interval -> interval.contains(i)));
    }
}
