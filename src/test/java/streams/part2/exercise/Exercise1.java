package streams.part2.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

@SuppressWarnings({"ConstantConditions", "unused"})
public class Exercise1 {

    @Test
    public void calcTotalYearsSpentInEpam() {
        List<Employee> employees = Example1.getEmployees();

        Long hours = employees.stream()
                .map(Employee::getJobHistory)
                .flatMap(Collection::stream)
                .filter(e -> "EPAM".equals(e.getEmployer()))
                .mapToLong(JobHistoryEntry::getDuration)
                .sum();

        assertEquals(19, hours.longValue());
    }

    @Test
    public void findPersonsWithQaExperience() {
        List<Employee> employees = Example1.getEmployees();

        Set<Person> workedAsQa = employees.stream()
                .filter(e -> e.getJobHistory().stream().map(JobHistoryEntry::getPosition).anyMatch(p -> "QA".equals(p)))
                .map(Employee::getPerson)
                .collect(toSet());

        assertEquals(new HashSet<>(Arrays.asList(
                employees.get(2).getPerson(),
                employees.get(4).getPerson(),
                employees.get(5).getPerson()
        )), workedAsQa);
    }

    @Test
    public void composeFullNamesOfEmployeesUsingLineSeparatorAsDelimiter() {
        List<Employee> employees = Example1.getEmployees();

        String result = employees.stream()
                .map(Employee::getPerson)
                .map(Person::getFullName)
                .collect(Collectors.joining("\n"));

        assertEquals("Иван Мельников\n"
                + "Александр Дементьев\n"
                + "Дмитрий Осинов\n"
                + "Анна Светличная\n"
                + "Игорь Толмачёв\n"
                + "Иван Александров", result);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void groupPersonsByFirstPositionUsingToMap() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                .collect(Collectors.toMap(
                        e -> e.getJobHistory().get(0).getPosition(),
                        e -> {
                            Set<Person> res = new HashSet<>();
                            res.add(e.getPerson());
                            return res;
                        },
                        (p1, p2) -> {
                            p1.addAll(p2);
                            return p1;
                        }));


        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("QA", new HashSet<>(Arrays.asList(employees.get(2).getPerson(), employees.get(5).getPerson())));
        expected.put("dev", Collections.singleton(employees.get(0).getPerson()));
        expected.put("tester", new HashSet<>(Arrays.asList(
                employees.get(1).getPerson(),
                employees.get(3).getPerson(),
                employees.get(4).getPerson()))
        );
        assertEquals(expected, result);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void groupPersonsByFirstPositionUsingGroupingByCollector() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Set<Person>> result = employees.stream()
                .collect(Collectors.groupingBy(e -> e.getJobHistory().get(0).getPosition(),
                        Collectors.mapping(Employee::getPerson, toSet())));

        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("QA", new HashSet<>(Arrays.asList(employees.get(2).getPerson(), employees.get(5).getPerson())));
        expected.put("dev", Collections.singleton(employees.get(0).getPerson()));
        expected.put("tester", new HashSet<>(Arrays.asList(
                employees.get(1).getPerson(),
                employees.get(3).getPerson(),
                employees.get(4).getPerson()))
        );
        assertEquals(expected, result);
    }
}
