package generics.nonrecursive;

public interface Stream<T> extends BaseStream<T> {

    Stream<T> distinct();
    Stream<T> sorted();
}