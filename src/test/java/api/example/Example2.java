package api.example;

import lambda.data.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Java8ListSort", "Convert2Lambda", "Anonymous2MethodRef"})
public class Example2 {

    @Test
    public void sortWhitComparatorUsingJava7() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6);

        Collections.sort(values, new Comparator<Integer>() {
            @Override
            public int compare(Integer left, Integer right) {
                return Integer.compare(right, left);
            }
        });

        assertEquals(Arrays.asList(6, 5, 4, 3, 2, 1), values);
    }

    @Test
    public void sortWhitLambdaComparatorUsingJava8() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6);

        Collections.sort(values, (left, right) -> Integer.compare(right, left));

        assertEquals(Arrays.asList(6, 5, 4, 3, 2, 1), values);
    }

    @Test
    @SuppressWarnings("RedundantTypeArguments")
    public void sortWhitIntegerComparatorUsingJava8() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6);

        Collections.sort(values, ((Comparator<Integer>) Integer::compare).reversed());
        Collections.sort(values, Comparator.comparing(Function.<Integer>identity(), Comparator.reverseOrder()));

        assertEquals(Arrays.asList(6, 5, 4, 3, 2, 1), values);
    }

    @Test
    public void sortWhitKeyExtractorComparatorUsingJava8() {
        List<Person> values = getPersons();

        Collections.sort(values, Comparator.comparing(Person::getFirstName));

        assertEquals(Arrays.asList(
            new Person("Иван", "Литовцев", 15),
            new Person("Ирина", "Семенченко", 27),
            new Person("Кирилл", "Литовцев", 41),
            new Person("Николай", "Дмитриев", 60)
        ), values);
    }

    @Test
    public void sortWhitCombinedKeyExtractorComparatorUsingJava8() {
        List<Person> values = getPersons();

        Collections.sort(values, Comparator.comparing(Person::getLastName)
                                           .thenComparing(Person::getFirstName)
                                           .thenComparing(Person::getAge));

        assertEquals(Arrays.asList(
            new Person("Николай", "Дмитриев", 60),
            new Person("Иван", "Литовцев", 15),
            new Person("Кирилл", "Литовцев", 41),
            new Person("Ирина", "Семенченко", 27)
        ), values);
    }

    @Test
    public void sortWhitIntValueKeyExtractorComparatorUsingJava8() {
        List<Person> values = getPersons();

        Collections.sort(values, Comparator.comparingInt(Person::getAge));

        assertEquals(Arrays.asList(
            new Person("Иван", "Литовцев", 15),
            new Person("Ирина", "Семенченко", 27),
            new Person("Кирилл", "Литовцев", 41),
            new Person("Николай", "Дмитриев", 60)
        ), values);
    }

    @Test
    public void sortWhitFirstNullValuesComparatorUsingJava8() {
        List<Person> values = getPersons();
        values.set(2, null);

        Collections.sort(values, Comparator.nullsFirst(Comparator.comparingInt(Person::getAge)));

        assertEquals(Arrays.asList(
                null,
                new Person("Иван", "Литовцев", 15),
                new Person("Кирилл", "Литовцев", 41),
                new Person("Николай", "Дмитриев", 60)
        ), values);
    }

    public List<Person> getPersons() {
        return Arrays.asList(
            new Person("Иван", "Литовцев", 15),
            new Person("Кирилл", "Литовцев", 41),
            new Person("Ирина", "Семенченко", 27),
            new Person("Николай", "Дмитриев", 60)
        );
    }
}
