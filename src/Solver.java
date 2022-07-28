import java.util.ArrayList;
import java.util.List;

// Sorry if you think its ugly
public class Solver {
    public static void main(String[] args) {
        long size = Long.MAX_VALUE;
        long ops = 0;
        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        }
        System.out.println("SEARCH SPACE: " + size);
        Interval allNodes = new Interval(0, size);
        Oracle oracle = new Oracle(allNodes.chooseTwo());

        List<Interval> chunks = allNodes.split(4);
        // [A B C D]
        // A B
        // A C
        // A D
        // B C
        // B D
        // C D
        while (chunks.size() > 2) {
            if(chunks.size() == 4) {
                if (oracle.test(chunks.get(0), chunks.get(1))) {
                    ops += 1;
                    List<Interval> nextChunks = List.of(chunks.get(0), chunks.get(1));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else if (oracle.test(chunks.get(0), chunks.get(2))) {
                    ops += 2;
                    List<Interval> nextChunks = List.of(chunks.get(0), chunks.get(2));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else if (oracle.test(chunks.get(0), chunks.get(3))) {
                    ops += 3;
                    List<Interval> nextChunks = List.of(chunks.get(0), chunks.get(3));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else if (oracle.test(chunks.get(1), chunks.get(2))) {
                    ops += 4;
                    List<Interval> nextChunks = List.of(chunks.get(1), chunks.get(2));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else if (oracle.test(chunks.get(1), chunks.get(3))) {
                    ops += 5;
                    List<Interval> nextChunks = List.of(chunks.get(1), chunks.get(3));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else if (oracle.test(chunks.get(2), chunks.get(3))) {
                    ops += 6;
                    List<Interval> nextChunks = List.of(chunks.get(2), chunks.get(3));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else {
                    throw new RuntimeException("uuuh");
                }
            } else if(chunks.size() == 3) { // I think this is what he meant by "edge case"
                if (oracle.test(chunks.get(0), chunks.get(1))) {
                    ops += 1;
                    List<Interval> nextChunks = List.of(chunks.get(0), chunks.get(1));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else if (oracle.test(chunks.get(0), chunks.get(2))) {
                    ops += 2;
                    List<Interval> nextChunks = List.of(chunks.get(0), chunks.get(2));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                } else if (oracle.test(chunks.get(1), chunks.get(2))) {
                    ops += 3;
                    List<Interval> nextChunks = List.of(chunks.get(1), chunks.get(2));
                    if (!oracle.test(nextChunks)) {
                        System.out.println("oof");
                    }
                    chunks = nextChunks;
                }
            } else {
                System.out.println("FIXME");
            }

            if(chunks.size() == 2) {
                // TODO split into bigger groups if there are enough remaining nodes based
                // TODO on some heuristic
                List<Interval> nextChunks = new ArrayList<>();
                if (chunks.get(0).length > 1) {
                    nextChunks.addAll(chunks.get(0).split(2));
                } else {
                    nextChunks.add(chunks.get(0));
                }
                if(chunks.get(1).length > 1) {
                    nextChunks.addAll(chunks.get(1).split(2));
                } else {
                    nextChunks.add(chunks.get(1));
                }
                if(!oracle.test(nextChunks)) {
                    System.out.println("oof");
                }
                chunks = nextChunks;
            } else {
                System.out.println("FIXME");
            }
        }

        System.out.println(chunks.get(0).startInclusive);
        System.out.println(chunks.get(1).startInclusive);
        System.out.println("NON-GUARD TESTS: " + ops);
    }
}
