import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// Sorry if you think its ugly
public class Solver {
    public static List<Interval> combine(Oracle oracle, List<Interval> chunks, AtomicLong ops) {
        Stream<List<Interval>> combinationStream = IntStream
                .range(0, chunks.size())
                .boxed()
                .flatMap((Integer a) -> IntStream
                        .range(0, chunks.size())
                        .filter((int b) -> b != a)
                        .mapToObj((int b) -> List.of(chunks.get(a), chunks.get(b))));

        return combinationStream.filter(tests -> {
            ops.incrementAndGet();
            return oracle.test(tests);
        }).findAny().orElseThrow();
    }

    public static List<Interval> split(Oracle oracle, List<Interval> chunks) {
        if(chunks.size() == 2) {
            List<Interval> nextChunks = new ArrayList<>();
            if (chunks.get(0).length > 1) {
                nextChunks.addAll(chunks.get(0).split(false));
            } else {
                nextChunks.add(chunks.get(0));
            }
            if(chunks.get(1).length > 1) {
                nextChunks.addAll(chunks.get(1).split(false));
            } else {
                nextChunks.add(chunks.get(1));
            }
            if(!oracle.test(nextChunks)) {
                System.out.println("oof");
            }
            return nextChunks;
        } else {
            throw new RuntimeException("FIXME");
        }
    }

    public static void main(String[] args) {
        int size = Integer.MAX_VALUE >> 5;
        AtomicLong ops = new AtomicLong(0);
        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        }
        System.out.println("SEARCH SPACE: " + size);
        List<Integer> providedList = IntStream.range(0, size).parallel().filter(l -> Math.random() > .9).boxed().toList();
        System.out.println("TOTAL TESTS: " + providedList.size());
        Interval allNodes = new Interval(providedList, 0, providedList.size());

        Oracle oracle = new Oracle(allNodes.chooseTwo());

        List<Interval> chunks = allNodes.split(true);
        while (chunks.size() > 2 || !chunks.stream().allMatch(chunk -> chunk.length == 1)) { // second test unnecessary?
            chunks = combine(oracle, chunks, ops);
            chunks = split(oracle, chunks);
        }

        System.out.println(chunks.get(0).startInclusive);
        System.out.println(chunks.get(1).startInclusive);
        System.out.println("NON-GUARD TESTS: " + ops.get());
    }
}
