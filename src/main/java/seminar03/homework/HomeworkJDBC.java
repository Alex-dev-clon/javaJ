package seminar03.homework;
/* Повторить все, что было на семинаре на таблице Student с полями
1. id - bigint
2. first_name - varchar(256)
3. second_name - varchar(256)
4. group - varchar(128)
Написать запросы:
1. Создать таблицу
2. Наполнить таблицу данными (insert)
3. Поиск всех студентов
4. Поиск всех студентов по имени группы
Доп. задания:
1. ** Создать таблицу group(id, name); в таблице student сделать внешний ключ на group
2. ** Все идентификаторы превратить в UUID
Замечание: можно использовать ЛЮБУЮ базу данных: h2, postgres, mysql, ...*/

import java.sql.*;
import java.util.Arrays;
import java.util.UUID;

public class HomeworkJDBC {
    // константы с параметрами подключения к БД
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "Iamcoder777!";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Создаём новую базу данных
            createSchema(connection);
            // Создаём две таблицу: группы студентов и студенты
            createTableGroup(connection);
            createTableStudent(connection);
            // Записываем данные о группах студентов
            insertDataGroups(connection);
            // Записываем данные о студентах, где ссылаемся на данные таблицы с группами студентов
            insertDataStudents(connection);
            // Получаем и выводим данные о всех студентах в БД
            selectAllStudents(connection);
            System.out.println("_____".repeat(10));
            // Выбираем данные о студентах по названию группы
            selectStudentsByGroup(connection, "'Developers'");
            System.out.println("_____".repeat(10));
        } catch (SQLException e) {
            System.err.println("Incorrect processing of database data: \n" + e.getMessage() + "\n" +
                    Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Получает данные о студентах по имени группы
     */
    static void selectStudentsByGroup(Connection connection, String groupName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT `s`.`id`, `first_name`, `last_name`, `group_name`
                    FROM `DB_students`.`students` AS `s`
                    JOIN `DB_students`.`groups` AS `g`
                    ON s.`group_id` = g.`id`
                    """ +
                    "WHERE group_name = " + groupName + ";");
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String group = resultSet.getString("group_name");
                System.out.printf("Прочитана строка: %s, %s, %s, %s\n", id, firstName, lastName, group);
            }
            resultSet.close();
        }
    }

    /**
     * Получает и выводит на печать данные о всех студентах
     */
    static void selectAllStudents(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT `s`.`id`, `first_name`, `last_name`, `group_name`
                    FROM `DB_students`.`students` AS `s`
                    JOIN `DB_students`.`groups` AS `g`
                    ON `s`.`group_id` = `g`.`id`;
                    """);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String group = resultSet.getString("group_name");
                System.out.printf("Прочитана строка: %s, %s, %s, %s\n", id, firstName, lastName, group);
            }
            resultSet.close();
        }
    }

    /**
     * Записывает данные в таблицу групп студентов
     */
    static void insertDataGroups(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate("""
                    INSERT INTO `DB_students`.`groups` (`id`,`group_name`) VALUES
                        (UUID(), 'Developers'),
                        (UUID(), 'Analysts'),
                        (UUID(), 'Testers');
                    """);
            System.out.println("Количество вставленных строк в таблицу groups: " + count);
        }
    }

    /**
     * Вспомогательный метод для получения по названию группы её идентификатора,
     * представляющего строковое представление UUID
     */
    static String getGroupUUID(Connection connection, String groupName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT `id` FROM `DB_students`.`groups` " +
                    "WHERE `group_name` = " + groupName + ";");
            String id = "";
            if (resultSet.next()) {
                id = resultSet.getString("id");
            }
            resultSet.close();
            return id;
        }
    }

    /**
     * Записывает данные в таблицу студентов
     */
    static void insertDataStudents(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate(String.format("""
                            INSERT INTO `DB_students`.`students` (`id`,`first_name`, `last_name`, `group_id`) 
                            VALUES
                                (UUID(), 'Oleg', 'Ivanov', '%s'),
                                (UUID(), 'Mark', 'Popov', '%s'),
                                (UUID(), 'Nickolay', 'Denisov', '%s'),
                                (UUID(), 'Mikhail', 'Petrov', '%s');
                            """,
                    getGroupUUID(connection, "'Developers'"),
                    getGroupUUID(connection, "'Analysts'"),
                    getGroupUUID(connection, "'Testers'"),
                    getGroupUUID(connection, "'Developers'")
            ));
            System.out.println("Количество вставленных строк в таблицу students: " + count);
        }
    }

    /**
     * Создаёт таблицу для хранения данных о группах студентов
     */
    static void createTableGroup(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE `DB_students`.`groups` (
                        `id` VARCHAR(36) PRIMARY KEY,
                        `group_name` VARCHAR(256) NOT NULL
                    );
                    """);
        }
    }

    /**
     * Создаёт таблицу для хранения данных о студентах
     */
    static void createTableStudent(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE `DB_students`.`students` (
                        `id` VARCHAR(36) PRIMARY KEY,
                        `first_name` VARCHAR(256) NOT NULL,
                        `last_name` VARCHAR(256) NOT NULL,
                        `group_id` VARCHAR(128) NOT NULL,
                        FOREIGN KEY (group_id) REFERENCES `groups` (`id`)
                    );
                    """);
        }
    }

    /**
     * Создаёт новую базу данных
     */
    static void createSchema(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP SCHEMA `DB_students` ;");
            statement.execute("CREATE SCHEMA `DB_students` ;");
        }
    }
}