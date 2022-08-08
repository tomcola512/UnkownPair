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
    @org.junit.jupiter.api.Test
    void splongus() {
        List<Integer> integers = List.of(0, 1, 2, 4, 5, 7, 9, 10, 11, 12, 13, 14, 15, 18, 20, 22, 24, 25, 26, 27, 31, 34, 35, 37, 38, 39, 40, 41, 42, 43, 44, 47, 50, 52, 54, 55, 58, 60, 61, 63, 66, 71, 72, 73, 75, 78, 79, 85, 88, 91, 93, 96, 100, 104, 105, 106, 107, 108, 109, 110, 111, 114, 117, 118, 122, 123, 129, 130, 136, 137, 138, 139, 145, 147, 149, 150, 151, 155, 159, 160, 162, 164, 165, 169, 175, 177, 178, 182, 183, 184, 185, 186, 188, 189, 190, 191, 192, 197, 199);
        Interval interval = new Interval(integers, 0, integers.size());
        assertTrue(interval.containsAll(integers));
        Oracle oracle = new Oracle(new int[]{66, 189});

        List<Interval> chunks = interval.split(true);
        assertTrue(chunks.get(3).contains(189));
        oracle.test(List.of(chunks.get(1), chunks.get(3)));
        List<Interval> nextChunks = Solver.combine(oracle, chunks, new AtomicLong(0));
        System.out.println("BEEP");
    }
}