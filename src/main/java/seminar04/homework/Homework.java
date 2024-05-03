package seminar04.homework;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Перенести структуру дз третьего урока на JPA:
 * 1. Описать сущности Student и Group
 * 2. Написать запросы: Find, Persist, Remove
 * 3. ... поупражняться с разными запросами ...
 */
public class Homework {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSession()) {
            createStudent(session);
            System.out.println(selectStudentByID(session, 5L));
            System.out.println(selectGroupByID(session, 1L));
            updateStudent(session, 5L);
            deleteStudent(session, 5L);
            selectAllGroupsAndStudents(session);
            selectStudentsByGroup(session, 1L);
            selectStudentByLastname(session, "Danilov");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // CRUD
    // CREATE / INSERT
    private static void createStudent(Session session) {
        Student student = new Student(5L, "Denis", "Utkin", 1L);
        session.beginTransaction();
        session.persist(student);
        session.getTransaction().commit();
    }

    private static void createGroup(Session session) {
        Group groupPM = new Group(5L, "PM", "Project managers and product managers");
        session.beginTransaction();
        session.persist(groupPM);
        session.getTransaction().commit();
    }

    // READ / SELECT
    private static Student selectStudentByID(Session session, Long id) {
        return session.find(Student.class, id);
    }

    private static Group selectGroupByID(Session session, Long id) {
        return session.find(Group.class, id);
    }

    // UPDATE
    private static void updateStudent(Session session, Long id) {
        Student updatedStudent = selectStudentByID(session, id);
        updatedStudent.setFirstName("Anna");
        updatedStudent.setLastName("Goncharova");
        Transaction tx = session.beginTransaction();
        session.merge(updatedStudent);
        tx.commit();
        System.out.println(selectStudentByID(session, id));
    }

    // DELETE
    private static void deleteStudent(Session session, Long id) {
        Student student = selectStudentByID(session, id);
        Transaction tx = session.beginTransaction();
        session.remove(student);
        tx.commit();
    }

    // * 3. ... поупражняться с разными запросами ...

    private static void selectAllGroupsAndStudents(Session session) {
        List<Group> groups = session.createQuery("FROM Group", Group.class).getResultList();
        groups.forEach(System.out::println);

        List<Student> students = session.createQuery("FROM Student", Student.class).getResultList();
        students.forEach(System.out::println);
    }

    private static void selectStudentsByGroup(Session session, Long groupId) {
        TypedQuery<Student> query = session.createQuery("SELECT s FROM Student s WHERE s.groupId = :studentGroup"
                , Student.class);
        query.setParameter("studentGroup", groupId);
        List<Student> students = query.getResultList();
        students.forEach(System.out::println);
    }

    private static void selectStudentByLastname(Session session, String lastname) {
        TypedQuery<Student> query = session.createQuery("SELECT s FROM Student s WHERE s.lastName = :studentName"
                , Student.class);
        query.setParameter("studentName", lastname);
        Student student = query.getSingleResult();
        System.out.println(student);
    }
}
