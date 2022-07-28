import java.util.ArrayList;
import java.util.List;

public class Interval {
    public final int startInclusive;
    public final int endExclusive;

    public final int length;

    public Interval(int startInclusive, int endExclusive) {
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.length = endExclusive - startInclusive;
    }

    public Interval to(int newEndExclusive) {
        return new Interval(startInclusive, newEndExclusive);
    }
    public Interval from(int newStartInclusive) {
        return new Interval(newStartInclusive, endExclusive);
    }

    public boolean contains(int i) {
        return i >= startInclusive && i < endExclusive;
    }

    public List<Interval> split(int chunks) {
        if(length < chunks) {
            //kludgy fix
            return List.of(this);
//            throw new RuntimeException("CHUNKING FAILED");
        }
        int chunkSize = length / chunks;
        List<Interval> chunkList = new ArrayList<>();
        for(int i = 0; i < chunks; i++) {
            int start = startInclusive + i * chunkSize;
            int end = start + chunkSize;
            if(i == chunks - 1) {
                end = endExclusive;
            }
            chunkList.add(new Interval(start, end));
        }
        return chunkList;
    }

    public int[] chooseTwo() {
        int a = (int) Math.floor(Math.random() * length - 1);
        int b = (int) Math.floor(Math.random() * length - a) + a;
        return new int[]{a + startInclusive, b + startInclusive};
    }

    @Override
    public String toString() {
        return "Interval{" + startInclusive +
                ", " + endExclusive +
                '}';
    }
}
