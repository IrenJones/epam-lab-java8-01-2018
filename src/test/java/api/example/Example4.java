package api.example;

import org.junit.Test;

import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Example4 {

    @Test
    public void arraysParallelSortUsingJava8() {
        int[] values = {1, 6, 9, 4, -1, -4, 0, 2, 3};

        Arrays.parallelSort(values);

        assertArrayEquals(new int[]{-4, -1, 0, 1, 2, 3, 4, 6, 9}, values);
    }

    @Test
    public void arraysParallelPrefixUsingJava8() {
        int[] values = {1, 6, 9, 4, -1, -4, 0, 2, 3};

        Arrays.parallelPrefix(values, Integer::sum);

        assertArrayEquals(new int[]{1, 7, 16, 20, 19, 15, 15, 17, 20}, values);
    }

    @Test
    public void arraysParallelSetAllUsingJava8() {
        int[] values = new int[5];

        for (int i = 0; i < values.length; ++i) {
            values[i] = i;
        }

        Arrays.parallelSetAll(values, IntUnaryOperator.identity());

        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, values);
    }
}
