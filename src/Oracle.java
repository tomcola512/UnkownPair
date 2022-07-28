import java.util.Arrays;
import java.util.List;

public class Oracle {
    private final int[] edge;

    public Oracle(int[] edge) {
        this.edge = edge;
//        this.edge = new int[]{9, 95}; // REMOVEME
        System.out.println("REMOVEME: " + this.edge[0] + ", " + this.edge[1]);
    }

    public boolean test(List<Interval> tests) {
        return Arrays.stream(edge)
                .allMatch(i ->
                        tests.stream()
                                .anyMatch(interval -> interval.contains(i)));
    }

    public boolean test(Interval a, Interval b) {
        return Arrays.stream(edge)
                .allMatch(i -> a.contains(i) || b.contains(i));
    }
}
