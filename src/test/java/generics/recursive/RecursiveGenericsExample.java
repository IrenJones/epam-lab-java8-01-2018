package generics.recursive;

@SuppressWarnings({"ConstantConditions", "unused"})
public class RecursiveGenericsExample {

    public static void main(String[] args) {
        Stream<String> stream = null;
        Stream<String> distinctStream = stream.distinct();
        Stream<String> sortedStream = distinctStream.sorted();
        Stream<String> parallelStream = sortedStream.parallel();
        Stream<String> sequentialStream = parallelStream.sequential();
        Stream<String> twiceSortedStream = sequentialStream.sorted();
    }
}
