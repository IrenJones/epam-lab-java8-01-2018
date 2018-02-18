package lambda.part1.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "ConstantConditions", "UnnecessaryLocalVariable"})
public class Example10 extends Thread {

    private List<String> strings = Arrays.asList("1", "2", "3");

    private byte[] veryBigField = new byte[100_000_000];

    public static void main(String[] args) throws InterruptedException {
        Example10 deadly = new Example10();
        new Checker(deadly.getPredicateEnclosingField()).start();

        Example10 zombie = new Example10();
        new Checker(zombie.getPredicateEnclosingThis()).start();

        TimeUnit.SECONDS.sleep(7);

        deadly = null;
        System.out.println(deadly);

        zombie = null;
        System.out.println(zombie);

        System.gc();
        TimeUnit.SECONDS.sleep(7);
    }

    @SuppressWarnings("Convert2MethodRef")
    private Predicate<String> getPredicateEnclosingThis() {
        List<String> localStrings = strings;
        Predicate<String> enclosingThis = string -> localStrings.contains(string);
        return enclosingThis;
    }

    private Predicate<String> getPredicateEnclosingField() {
        Predicate<String> enclosingField = strings::contains;
        return enclosingField;
    }
}

class Checker extends Thread {

    private final Predicate<String> predicate;

    Checker(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(10);
            System.out.println("Check: " + predicate + ", result: " + predicate.test("4"));
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}