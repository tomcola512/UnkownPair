import java.time.Duration;
import java.time.Instant;
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

        List<List<Interval>> lists = combinationStream.toList();

        return lists.stream().filter(tests -> {
            ops.incrementAndGet();
            return oracle.test(tests);
        }).findAny().orElseThrow(); // TODO doesn't work when actually lensing
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
        int size = Integer.MAX_VALUE >> 6;
//        int size = 200;
        AtomicLong ops = new AtomicLong(0);
        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        }
        System.out.println("SEARCH SPACE: " + size);

        Instant startSetup = Instant.now();
        List<Integer> providedList = IntStream.range(0, size).parallel().filter(l -> Math.random() > .5).boxed().toList();

        Instant endSetup = Instant.now();

        System.out.println("WALL TIME TO RENDER LIST: " + Duration.between(startSetup, endSetup));
//        System.out.println(providedList);
        System.out.println("TOTAL TESTS: " + providedList.size());
        Interval allNodes = new Interval(providedList, 0, providedList.size());

        Oracle oracle = new Oracle(allNodes.chooseTwo());
        Instant start = Instant.now();

        List<Interval> chunks = allNodes.split(true);
        assert(oracle.test(chunks));
        while (chunks.size() > 2 || !chunks.stream().allMatch(chunk -> chunk.length == 1)) { // second test unnecessary?
            chunks = combine(oracle, chunks, ops);
            chunks = split(oracle, chunks);
        }
        Instant end = Instant.now();

        System.out.println(chunks.get(0).get(0));
        System.out.println(chunks.get(1).get(0));
        System.out.println("NON-GUARD TESTS: " + ops.get());
        System.out.println("WALL TIME TO VECTOR BISECT: " + Duration.between(start, end));
    }
}
