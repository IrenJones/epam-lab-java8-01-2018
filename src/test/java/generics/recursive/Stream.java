package generics.recursive;

public interface Stream<T> extends BaseStream<T, Stream<T>> {

    Stream<T> distinct();
    Stream<T> sorted();
}