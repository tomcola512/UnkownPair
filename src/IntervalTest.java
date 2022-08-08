import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

    @org.junit.jupiter.api.Test
    void contains() {
        List<Integer> integers = List.of(1, 2, 4, 82, 86);
        Interval interval = new Interval(integers, 0, integers.size());
        assertTrue(interval.containsAll(integers));
    }

    @org.junit.jupiter.api.Test
    void happyPath() {
        List<Integer> integers = List.of(1, 2, 4, 82, 86);
        Interval interval = new Interval(integers, 0, integers.size());
        assertTrue(interval.containsAll(integers));
        Oracle oracle = new Oracle(new int[]{2, 86});

        List<Interval> chunks = interval.split(true);
        assertTrue(chunks.get(0).contains(1));
        assertTrue(chunks.get(1).contains(2));
        assertTrue(chunks.get(2).contains(4));
        assertTrue(chunks.get(3).contains(82));
        assertTrue(chunks.get(3).contains(86));
        List<Interval> nextChunks = Solver.combine(oracle, chunks, new AtomicLong(0));
        System.out.println("BEEP");
    }
}