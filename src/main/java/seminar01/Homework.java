package seminar01;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Homework {
    public static void main(String[] args) {
        List<Department> departments = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            departments.add(new Department("Department #" + i));
        }

        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            int randomDepartmentIndex = ThreadLocalRandom.current().nextInt(departments.size());
            Department department = departments.get(randomDepartmentIndex);
            Person person = new Person();
            person.setName("Person #" + i);
            person.setAge(ThreadLocalRandom.current().nextInt(20, 65));
            person.setSalary(ThreadLocalRandom.current().nextInt(20_000, 100_000) * 1.0);
            person.setDepartment(department);
            persons.add(person);
        }

        /**
         * Проверка корректности работы методов на тестовых данных
         */

        System.out.println("Количество работников старше 55 лет, имеющих зарплату более 70 т.р.");
        System.out.println(countPersons(persons, 55, 75000));
        System.out.println();

        System.out.println("\nСредняя зарплата работников департамента № 3");
        System.out.println(averageSalary(persons, 3).orElse(0));
        System.out.println();

        System.out.println("\nРаспределение работников по департаментам");
        for (var entry : groupByDepartment(persons).entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue().size());
        }

        System.out.println("\nМаксимальные зарплаты работников по департаментам");
        for (var entry : maxSalaryByDepartment(persons).entrySet()) {
            System.out.print(entry.getKey().getName() + ": ");
            System.out.println(entry.getValue());
        }

        System.out.println("\nИмена работников по департаментам");
        for (var entry : groupPersonNamesByDepartment(persons).entrySet()) {
            System.out.print(entry.getKey().getName() + ": ");
            System.out.println(entry.getValue());
        }

        System.out.println("\nРаботники с минимальной зарплатой в каждом департаменте");
        minSalaryPersons(persons)
                .forEach(p -> System.out.println(p.getDepartment().getName() + ": " + p));
    }

    /**
     * Используя классы Person и Department, реализовать методы ниже:
     */

    /**
     * Найти количество сотрудников, старше x лет с зарплатой больше, чем d
     */
    static int countPersons(List<Person> persons, int x, double d) {
        return (int) persons.stream()
                .filter(it -> it.age > x)
                .filter(it -> it.salary > d)
                .peek(System.out::println) // Выводим на печать отфильтрованных сотрудников для проверки расчётов
                .count();
    }

    /**
     * Найти среднюю зарплату сотрудников, которые работают в департаменте X
     */
    static OptionalDouble averageSalary(List<Person> persons, int x) {
        return persons.stream()
                .filter(it -> it.getDepartment().getName().contains("#" + x))
                .mapToDouble(Person::getSalary)
                .average();
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getDepartment));
    }

    /**
     * Найти максимальные зарплаты по отделам
     */
    static Map<Department, Double> maxSalaryByDepartment(List<Person> persons) {
        return persons.parallelStream()
                .collect(Collectors.toMap(
                        Person::getDepartment,
                        Person::getSalary,
                        Double::max));
    }

    /**
     * ** Сгруппировать имена сотрудников по департаментам
     */
    static Map<Department, List<String>> groupPersonNamesByDepartment(List<Person> persons) {
        return persons.stream()
                .collect(
                        Collectors.groupingBy(Person::getDepartment,
                                Collectors.mapping(
                                        Person::getName,
                                        Collectors.toList())));
    }

    /**
     * ** Найти сотрудников с минимальными зарплатами в своем отделе
     */
    static List<Person> minSalaryPersons(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.toMap(Person::getDepartment, p -> p,
                        (p1, p2) -> p1.getSalary() <= p2.getSalary() ? p1 : p2))
                .values()
                .stream()
                .toList();
    }

    static class Person {
        private String name;
        private int age;
        private double salary;
        private Department department;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", department=" + department +
                    '}';
        }
    }

    static class Department {
        private final String name;

        public Department(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
