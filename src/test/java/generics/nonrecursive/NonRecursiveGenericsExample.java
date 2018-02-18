package generics.nonrecursive;

@SuppressWarnings("ConstantConditions")
public class NonRecursiveGenericsExample {

    public static void main(String[] args) {
        Stream<String> stream = null;

        Stream<String> distinctStream = stream.distinct();
        Stream<String> sortedStream = distinctStream.sorted();

        BaseStream<String> parallelStream = sortedStream.parallel();
        BaseStream<String> sequentialStream = parallelStream.sequential();

        // FIXME compile error
        // FIXME методы базового интерфейса недоступны
        //sequentialStream.sorted()
    }
}
