package lambda.part1.example;

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import lambda.data.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Теория.
 *
 * Функциональное программирование:
 *    - Парадигма программирования, в которой процесс вычисления трактуется как вычисление значений функций в математическом понимании последних;
 *    - Противопоставляется парадигме императивного программирования, которая описывает процесс вычислений как последовательное изменение состояний;
 *    - Предлагает обходиться вычислением результатов функций от исходных данных и результатов других функций;
 *    - Не предполагает явного хранения состояния программы.
 *
 * Объект первого порядка (в контексте языка программирования) может быть:
 *    - Сохранен в переменной или структуре данных;
 *    - Передан в функцию как аргумент;
 *    - Возвращен из функции как результат;
 *    - Создан во время выполнения программы.
 *
 * Функция высшего порядка:
 *    - Функция, которая может принимать в качестве аргументов и возвращать другие функции;
 *    - Математики такую функцию называют оператором (оператор суммирования, возведения в степень, интегрирования...);
 *    - Передача функции в качестве параметра подразумевает написание и отложенное выполнение некоторого кода.
 *
 * Чистая функция:
 *    - Функция, которая не имеет побочных эффектов ввода/вывода и работы с памятью;
 *    - Если результат такой функции не используется, её вызов может быть удален без вреда для других выражений;
 *    - Результат вызова чистой функции может быть кеширован, то есть сохранен в таблице значений вместе с аргументами вызова;
 *    - Если нет никакой зависимости по данным между двумя чистыми функциями, то порядок их вычисления можно поменять или распараллелить;
 *    - Если весь язык не допускает побочных эффектов, то можно использовать любую политику вычисления.
 *
 * Рекурсивная функция:
 *    - Рекурсивно заданная функция определяет своё значение через обращение к себе самой с другими аргументами;
 *    - Выделяется прямая и косвенная.
 *
 * Закон Мура:
 *    - Количество транзисторов, размещаемых на кристалле интегральной схемы, удваивается каждые 24 месяца;
 *    - С середине первого десятилетия 2000-х годов начался рост "вширь" (увеличение количества ядер) а не "вверх" (увеличение количества транзисторов на ядре);
 *    - Программы перестали работать быстрее на новых процессорах, если они не заточены под многопоточное исполнение.
 *
 * Закон Амдала:
 *    - При разделении на несколько частей, суммарное время выполнения задачи на параллельной системе не может быть меньше времени выполнения самого длинного фрагмента;
 *
 * @see <a href="https://habrahabr.ru/post/142351">Функциональное программирование для всех</a>
 * @see <a href="https://ru.wikipedia.org/wiki/Закон_Мура">Закон Мура (wiki)</a>
 * @see <a href="https://ru.wikipedia.org/wiki/Закон_Амдала">Закон Амдала (wiki)</a>
 */
@SuppressWarnings({"Guava", "Convert2MethodRef", "ComparatorCombinators", "SimplifiableIfStatement"})
public class Example3 {

    @Test
    public void sortPersonsByNameUsingArraysLambda() {
        Person[] persons = getPersons();

        // Expression-lambda
        Arrays.sort(persons, (left, right) -> left.getLastName().compareTo(right.getLastName()));

        // Без контекста лямбда-выражение бессмысленно
        // (left, right) -> left.getLastName().compareTo(right.getLastName())
//        Comparator<Person> personComparator = (Person left, Person right) -> left.getLastName().compareTo(right.getLastName());

        assertArrayEquals(new Person[]{
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30),
            new Person("Иван", "Мельников", 20)
        }, persons);
    }

    @Test
    public void findFirstAlexUsingForeach() {
        List<Person> persons = Arrays.asList(getPersons());

        Optional<Person> result = FluentIterable.from(persons)
                                                // Statement-lambda
                                                .firstMatch(person -> {
                                                    if (person == null) {
                                                        return false;
                                                    } else {
                                                        return "Алексей".equals(person.getFirstName());
                                                    }
                                                });

        if (result.isPresent()) {
            System.out.println(result.get());
            assertEquals(new Person("Алексей", "Доренко", 40), result.get());
        }
    }

    @Test
    public void lastNamesSet() {
        List<Person> persons = Arrays.asList(getPersons());

        Map<String, Person> personByLastName = FluentIterable.from(persons)
                                                             .uniqueIndex(person -> person == null ? null : person.getLastName());

        Map<String, Person> expected = new HashMap<>(persons.size());
        expected.put("Мельников", new Person("Иван", "Мельников", 20));
        expected.put("Доренко", new Person("Алексей", "Доренко", 40));
        expected.put("Зимов", new Person("Николай", "Зимов", 30));
        assertEquals(expected, personByLastName);
    }

    private Person[] getPersons() {
        return new Person[]{
            new Person("Иван", "Мельников", 20),
            new Person("Алексей", "Доренко", 40),
            new Person("Николай", "Зимов", 30)
        };
    }
}
