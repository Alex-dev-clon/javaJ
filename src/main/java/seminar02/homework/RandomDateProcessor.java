package seminar02.homework;

import java.lang.reflect.Field;
import java.time.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateProcessor {
    private static final long MILLISECONDS_IN_DAY = 86400000L;
    private static final long MILLISECONDS_IN_SEC = 1000L;

    public static void processRandomDate(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(RandomDate.class)) {
                RandomDate annotation = field.getAnnotation(RandomDate.class);
                long min = annotation.min();
                long max = annotation.max();

                field.setAccessible(true);
                try {
                    if (field.getType().equals(Date.class)) {
                        field.set(obj, new Date(ThreadLocalRandom.current().nextLong(min, max)));
                    } else if (field.getType().equals(LocalDate.class)) {
                        // Объект LocalDate получаем из количества дней, прошедших с начала эпохи java
                        field.set(obj, LocalDate.ofEpochDay(
                                ThreadLocalRandom.current().nextLong(min, max) / MILLISECONDS_IN_DAY));
                    } else if (field.getType().equals(LocalDateTime.class)) {
                        // Объект LocalDateTime получаем из количества секунд, прошедших с начала эпохи java
                        field.set(obj, LocalDateTime.ofEpochSecond(
                                ThreadLocalRandom.current().nextLong(min, max) / MILLISECONDS_IN_SEC,
                                0, ZoneOffset.ofHours(-3)));
                    } else if (field.getType().equals(Instant.class)) {
                        // Объект Instant получаем из количества миллисекунд, прошедших с начала эпохи java
                        field.set(obj, Instant.ofEpochMilli(ThreadLocalRandom.current().nextLong(min, max)));
                    } else if (field.getType().equals(ZonedDateTime.class)) {
                        // Объект ZonedDateTime получаем из объекта Instant и системного ID для временной зоны
                        field.set(obj, ZonedDateTime.ofInstant(
                                Instant.ofEpochMilli(ThreadLocalRandom.current().nextLong(min, max)),
                                ZoneId.systemDefault()));
                    } else if (field.getType().equals(OffsetDateTime.class)) {
                        // Объект OffsetDateTime получаем из объекта Instant и системного ID для временной зоны
                        field.set(obj, OffsetDateTime.ofInstant(
                                Instant.ofEpochMilli(ThreadLocalRandom.current().nextLong(min, max)),
                                ZoneId.systemDefault()));
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Failed to set random value to date field: " + e);
                }
                field.setAccessible(false);
            }
        }
    }
}