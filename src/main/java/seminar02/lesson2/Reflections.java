package seminar02.lesson2;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

public class Reflections {

  public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    Person person = new Person("Igor");
    System.out.println(person);

    Class<? extends Person> aClass = Person.class; // person.getClass();
    Constructor<? extends Person> constructor = aClass.getConstructor(String.class);
    Person anotherPerson = constructor.newInstance("Igor");
    System.out.println(anotherPerson.toString());

    Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
    int i = 1;
    for (Constructor<?> declaredConstructor : declaredConstructors) {;
      System.out.println("Constructor #" + i++);
      for (Parameter parameter : declaredConstructor.getParameters()) {
        System.out.println(parameter.getType());
      }
    }

//    System.out.println(Arrays.toString(Reflections.class.getNestMembers()));



    System.out.println(person);
    person.setName("Updated");

    Method setName = Person.class.getMethod("setName", String.class);
    setName.invoke(person, "Updated #2");
    System.out.println(person);

    Method getName = Person.class.getMethod("getName");
    System.out.println(getName.invoke(person));

    System.out.println(Person.class.getMethod("getCounter").invoke(null));

    System.out.println();
    System.out.println();
    System.out.println();

    System.out.println(Person.class.getDeclaredMethods().length);
    System.out.println(Person.class.getMethods().length);
    System.out.println();
    System.out.println(ExtPerson.class.getDeclaredMethods().length);
    System.out.println(ExtPerson.class.getMethods().length);

    Method method = PrivateMethodHolder.class.getDeclaredMethod("method");
    method.setAccessible(true);
    method.invoke(new PrivateMethodHolder());

    Field name = Person.class.getDeclaredField("name");
    name.set(person, "New name");

    System.out.println(person);

    System.out.println(person.unmodifiableField);

    Field unmodifiableField = Person.class.getDeclaredField("unmodifiableField");
    unmodifiableField.setAccessible(true);
    unmodifiableField.set(person, "asdfamldfa;kd");
    System.out.println(person.unmodifiableField);


  }

  private abstract static class ExtPerson extends Person {

    public ExtPerson(int age) {
    }

    private void foo() {
      System.out.println("private method is called");
    }

    @Override
    public String toString() {
      return super.toString();
    }
  }

  private static class Person {

    private static long counter = 0L;

    private String name;
    private final String unmodifiableField;

    public Person() {
      this("unnamed");
    }

    public Person(String name) {
      this.name = name;
      unmodifiableField = "VALUE";
      counter++;
    }

    private void bar() {

    }

    public static long getCounter() {
      return counter;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "Person{" +
        "name='" + name + '\'' +
        '}';
    }
  }

}
