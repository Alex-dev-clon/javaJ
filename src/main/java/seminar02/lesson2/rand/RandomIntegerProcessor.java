package seminar02.lesson2.rand;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

public class RandomIntegerProcessor {

  public static void processRandomInteger(Object obj) {
    for (Field declaredField : obj.getClass().getDeclaredFields()) {
      RandomInteger annotation = declaredField.getAnnotation(RandomInteger.class);
      if (annotation != null) {
//      if (declaredField.isAnnotationPresent(RandomInteger.class)) {
        int min = annotation.min();
        int max = annotation.max();

        declaredField.setAccessible(true);

        try {
          declaredField.set(obj, ThreadLocalRandom.current().nextInt(min, max));
        } catch (IllegalAccessException e) {
          System.err.println("Не удалось подставить рандомное значение: " + e);
        }
      }
    }
  }

}
