import java.util.Set;

public interface DetectionStrategy {
    public Set<Integer> solve(Set<Integer> tests, Oracle oracle);
}
