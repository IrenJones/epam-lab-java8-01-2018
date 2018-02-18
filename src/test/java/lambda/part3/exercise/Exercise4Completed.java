package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4Completed {

    private static class LazyCollectionHelper<T, R> {

        private List<T> source;
        private Function<T, List<R>> remapping;

        private LazyCollectionHelper(List<T> source, Function<T, List<R>> remapping) {
            this.source = source;
            this.remapping = remapping;
        }

        public static <T> LazyCollectionHelper<T, T> from(List<T> list) {
            return new LazyCollectionHelper<>(list, Collections::singletonList);
        }

        public <U> LazyCollectionHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyCollectionHelper<>(source, remapping.andThen(transformUsing(flatMapping)));
        }

        public <U> LazyCollectionHelper<T, U> map(Function<R, U> flatMapping) {
            return new LazyCollectionHelper<>(source, remapping.andThen(transformUsing(flatMapping.andThen(Collections::singletonList))));
        }

        public List<R> force() {
            return transformUsing(remapping).apply(source);
        }

        private <FROM, TO> Function<List<FROM>, List<TO>> transformUsing(Function<FROM, List<TO>> mapper) {
            return source -> {
                List<TO> result = new ArrayList<>();
                source.forEach(mapper.andThen(result::addAll)::apply);
                return result;
            };
        }
    }


    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyCollectionHelper.from(employees)
                                                  .flatMap(Employee::getJobHistory)
                                                  .map(JobHistoryEntry::getPosition)
                                                  .flatMap(position -> {
                                                      List<Character> letters = new ArrayList<>(position.length());
                                                      for (char letter : position.toCharArray()) {
                                                          letters.add(letter);
                                                      }
                                                      return letters;
                                                  }).map(Integer::valueOf)
                                                  .force();

        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static List<Integer> calcCodes(String...strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}
