import java.util.ArrayList;
import java.util.List;

public class Interval {
    public final long startInclusive;
    public final long endExclusive;

    public final long length;

    public Interval(long startInclusive, long endExclusive) {
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.length = endExclusive - startInclusive;
    }

    public boolean contains(long i) {
        return i >= startInclusive && i < endExclusive;
    }

    public List<Interval> split(boolean initial) {
        long chunks = initial ? 5 : (length > Long.MAX_VALUE >> 2 ? 6 : 2); // dumb AND bad heuristic
        if(length < chunks) {
            //kludgy fix
            return List.of(this);
        }
        long chunkSize = length / chunks;
        List<Interval> chunkList = new ArrayList<>();
        for(int i = 0; i < chunks; i++) {
            long start = startInclusive + i * chunkSize;
            long end = start + chunkSize;
            if(i == chunks - 1) {
                end = endExclusive;
            }
            chunkList.add(new Interval(start, end));
        }
        return chunkList;
    }

    public long[] chooseTwo() {
        long a = (long) Math.floor(Math.random() * length - 1);
        long b = (long) Math.floor(Math.random() * (length - a)) + a;
        return new long[]{a + startInclusive, b + startInclusive};
    }

    @Override
    public String toString() {
        return "Interval{" + startInclusive +
                ", " + endExclusive +
                '}';
    }
}
