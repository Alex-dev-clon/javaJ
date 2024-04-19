package seminar02.lesson2;

import seminar02.lesson2.rand.RandomInteger;
import seminar02.lesson2.rand.RandomIntegerProcessor;

public class Annotations {

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        RandomIntegerProcessor.processRandomInteger(myClass);

        System.out.println(myClass.integer);
    }

    private static class MyClass {

        @RandomInteger(min = 2, max = 50)
        private int integer;

    }
}
