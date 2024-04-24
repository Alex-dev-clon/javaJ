package seminar02.homework;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Homework {
    public static void main(String[] args) {

        TestClass testClass = new TestClass();
        RandomDateProcessor.processRandomDate(testClass);

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println("______________".repeat(10));
        System.out.println(format.format(testClass.dateDate1) + " -> по умолчанию весь 2024 год");
        System.out.println(format.format(testClass.dateDate2) + " -> 01.01.2000 - 31.21.2000");
        System.out.println(format.format(testClass.dateDate3) + " -> с 19.04.2024 до конца 2024 года");
        System.out.println(format.format(testClass.dateDate4) + " -> с 01.01.1970 до 01.01.2000");
        System.out.println("______________".repeat(10));

        DateTimeFormatter localFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println(testClass.localDate1.format(localFormat) + " -> по умолчанию весь 2024 год");
        System.out.println(testClass.localDate2.format(localFormat) + " -> 01.01.2000 - 31.21.2000");
        System.out.println(testClass.localDate3.format(localFormat) + " -> с 19.04.2024 до конца 2024 года");
        System.out.println(testClass.localDate4.format(localFormat) + " -> с 01.01.1970 до 01.01.2000");
        System.out.println("______________".repeat(10));

        DateTimeFormatter localTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        System.out.println(testClass.localDateTime1.format(localTimeFormat) + " -> по умолчанию весь 2024 год");
        System.out.println(testClass.localDateTime2.format(localTimeFormat) + " -> 01.01.2000 - 31.21.2000");
        System.out.println(testClass.localDateTime3.format(localTimeFormat) + " -> с 19.04.2024 до конца 2024 года");
        System.out.println(testClass.localDateTime4.format(localTimeFormat) + " -> с 01.01.1970 до 01.01.2000");
        System.out.println("______________".repeat(10));

        System.out.println(testClass.instant1 + " -> по умолчанию весь 2024 год");
        System.out.println(testClass.instant2 + " -> 01.01.2000 - 31.21.2000");
        System.out.println(testClass.instant3 + " -> с 19.04.2024 до конца 2024 года");
        System.out.println(testClass.instant4 + " -> с 01.01.1970 до 01.01.2000");
        System.out.println("______________".repeat(10));

        System.out.println(testClass.zonedDate1 + " -> по умолчанию весь 2024 год");
        System.out.println(testClass.zonedDate2 + " -> 01.01.2000 - 31.21.2000");
        System.out.println(testClass.zonedDate3 + " -> с 19.04.2024 до конца 2024 года");
        System.out.println(testClass.zonedDate4 + " -> с 01.01.1970 до 01.01.2000");
        System.out.println("______________".repeat(10));

        System.out.println(testClass.offsetDate1 + " -> по умолчанию весь 2024 год");
        System.out.println(testClass.offsetDate2 + " -> 01.01.2000 - 31.21.2000");
        System.out.println(testClass.offsetDate3 + " -> с 19.04.2024 до конца 2024 года");
        System.out.println(testClass.offsetDate4 + " -> с 01.01.1970 до 01.01.2000");
        System.out.println("______________".repeat(10));
    }

    private static class TestClass {

        @RandomDate
        private Date dateDate1; // По умолчанию весь 2024 год

        @RandomDate(min = 946674000000L, max = 978210000000L) // 01.01.2000 - 31.21.2000
        private Date dateDate2;

        @RandomDate(min = 1713474000000L) // с 19.04.2024 до конца 2024 года
        private Date dateDate3;

        @RandomDate(min = 0L, max = 946674000000L) // с 01.01.1970 до 01.01.2000
        private Date dateDate4;


        @RandomDate
        private LocalDate localDate1; // По умолчанию весь 2024 год

        @RandomDate(min = 946674000000L, max = 978210000000L) // 01.01.2000 - 31.21.2000
        private LocalDate localDate2;

        @RandomDate(min = 1713474000000L) // с 19.04.2024 до конца 2024 года
        private LocalDate localDate3;

        @RandomDate(min = 0L, max = 946674000000L) // с 01.01.1970 до 01.01.2000
        private LocalDate localDate4;


        @RandomDate
        private LocalDateTime localDateTime1; // По умолчанию весь 2024 год

        @RandomDate(min = 946674000000L, max = 978210000000L) // 01.01.2000 - 31.21.2000
        private LocalDateTime localDateTime2;

        @RandomDate(min = 1713474000000L) // с 19.04.2024 до конца 2024 года
        private LocalDateTime localDateTime3;

        @RandomDate(min = 0L, max = 946674000000L) // с 01.01.1970 до 01.01.2000
        private LocalDateTime localDateTime4;


        @RandomDate
        private Instant instant1; // По умолчанию весь 2024 год

        @RandomDate(min = 946674000000L, max = 978210000000L) // 01.01.2000 - 31.21.2000
        private Instant instant2;

        @RandomDate(min = 1713474000000L) // с 19.04.2024 до конца 2024 года
        private Instant instant3;

        @RandomDate(min = 0L, max = 946674000000L) // с 01.01.1970 до 01.01.2000
        private Instant instant4;


        @RandomDate
        private ZonedDateTime zonedDate1; // По умолчанию весь 2024 год

        @RandomDate(min = 946674000000L, max = 978210000000L) // 01.01.2000 - 31.21.2000
        private ZonedDateTime zonedDate2;

        @RandomDate(min = 1713474000000L) // с 19.04.2024 до конца 2024 года
        private ZonedDateTime zonedDate3;

        @RandomDate(min = 0L, max = 946674000000L) // с 01.01.1970 до 01.01.2000
        private ZonedDateTime zonedDate4;


        @RandomDate
        private OffsetDateTime offsetDate1; // По умолчанию весь 2024 год

        @RandomDate(min = 946674000000L, max = 978210000000L) // 01.01.2000 - 31.21.2000
        private OffsetDateTime offsetDate2;

        @RandomDate(min = 1713474000000L) // с 19.04.2024 до конца 2024 года
        private OffsetDateTime offsetDate3;

        @RandomDate(min = 0L, max = 946674000000L) // с 01.01.1970 до 01.01.2000
        private OffsetDateTime offsetDate4;

        // Некорректное применение аннотаций к полям других типов данных, отличных от дат
        @RandomDate
        private String string;

        @RandomDate(min = 946674000000L, max = 978210000000L) // 01.01.2000 - 31.21.2000
        private LocalTime time;

    }
}
