package seminar02.lesson2.rand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RandomInteger {

  int min() default 0;

  int max() default 100;

  // все примитивы
  // Class
  // String
  // Enum
  // массивы всего, что написано выше

}
