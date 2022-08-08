import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Interval implements List<Integer>{
    public final int startInclusive;
    public final int endExclusive;

    public final int length;
    private final List<Integer> backingList;

    public Interval(List<Integer> backingList, int startInclusive, int endExclusive) {
        this.backingList = backingList;
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.length = endExclusive - startInclusive;
    }

    public boolean contains(Integer i) {
        try {
            return IntStream
                    .range(0, length)
                    .mapToObj(this::get)
                    .anyMatch(z -> Objects.equals(z, i)); // Why didn't == work above certain values?
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public List<Interval> split(boolean initial) {
        int chunks = initial ? 4 : (length > Integer.MAX_VALUE >> 2 ? 6 : 2); // dumb AND bad heuristic
        if(length < chunks) {
            //kludgy fix
            return List.of(this);
        }
        int chunkSize = length / chunks;
        List<Interval> chunkList = new ArrayList<>();
        for(int i = 0; i < chunks; i++) {
            int start = startInclusive + i * chunkSize;
            int end = start + chunkSize;
            if(i == chunks - 1) {
                end = endExclusive;
            }
            chunkList.add(new Interval(backingList, start, end));
        }
        return chunkList;
    }

    public int[] chooseTwo() {
        int a = (int) Math.floor(Math.random() * length - 1);
        int b = (int) Math.floor(Math.random() * (length - a)) + a;
        return new int[]{get(a), get(b)};
    }

    @Override
    public String toString() {
        return "Interval{" + startInclusive +
                ", " + endExclusive +
                '}';
    }

    @Override
    public int size() {
        return this.length;
    }

    @Override
    public boolean isEmpty() {
        return this.length <= 0;
    }

    @Override
    public boolean contains(Object o) {
        if(o instanceof Integer l) {
            return contains(l);
        }
        return false;
    }

    public Stream<Integer> stream() {
        return IntStream
                .range(startInclusive, endExclusive)
                .mapToObj(backingList::get);
    }

    @Override
    public Iterator<Integer> iterator() {
        return stream().iterator();
    }


    @Override
    public Object[] toArray() {
        return stream().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean add(Integer aInteger) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean remove(Object o) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean addAll(int index, Collection<? extends Integer> c) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public void clear() {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public Integer get(int index) {
        return this.backingList.get(startInclusive + index);
    }

    @Override
    public Integer set(int index, Integer element) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public void add(int index, Integer element) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public Integer remove(int index) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public int indexOf(Object o) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public ListIterator<Integer> listIterator() {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public ListIterator<Integer> listIterator(int index) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }

    @Override
    public List<Integer> subList(int fromIndex, int toIndex) {
        throw new IllegalStateException("NOT IMPLEMENTED");
    }
}
