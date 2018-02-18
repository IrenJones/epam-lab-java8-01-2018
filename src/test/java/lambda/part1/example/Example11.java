package lambda.part1.example;

import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertSame;

@SuppressWarnings({"unused", "ComparatorCombinators"})
public class Example11 {

    public static void method(String str) {
        System.out.println(str);
    }

    @SuppressWarnings({"Convert2Lambda", "Anonymous2MethodRef"})
    public static Consumer<String> anonymousConsumer() {
        return new Consumer<String>() {
            @Override
            public void accept(String string) {
                method(string);
            }
        };
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Consumer<String> lambdaConsumer() {
        return string -> method(string);
    }

    public static Consumer<String> referenceConsumer() {
        return Example11::method;
    }

    @SuppressWarnings("Convert2MethodRef")
    public static Supplier<Integer> lengthExtractor(String str) {
        return () -> str.length();
    }

    @Test
    public void test() {
        Consumer<String> consumer1 = anonymousConsumer();
        Consumer<String> consumer2 = anonymousConsumer();

        assertNotSame(consumer1, consumer2);

        Consumer<String> consumer3 = lambdaConsumer();
        Consumer<String> consumer4 = lambdaConsumer();
        assertSame(consumer3, consumer4);

        Consumer<String> consumer5 = lambdaConsumer();
        Consumer<String> consumer6 = lambdaConsumer();
        assertSame(consumer5, consumer6);

        Supplier<Integer> supplier1 = lengthExtractor("qwe");
        Supplier<Integer> supplier2 = lengthExtractor("abc");
        assertNotSame(supplier1, supplier2);
    }
}
