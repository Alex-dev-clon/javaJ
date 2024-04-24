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
import java.util.UUID;

public class HomeworkJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "Iamcoder777!";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            createSchema(connection);
            createTableGroup(connection);
            createTableStudent(connection);
            insertData(connection);
//            selectAllStudents(connection);
            selectStudentsByGroup(connection, "33");
        } catch (SQLException e) {
            System.err.println("Ошибка в работе с БД: " + e);
        }
    }

    static void selectStudentsByGroup(Connection connection, String groupNumber) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT `id`, `first_name`, `second_name`, `group_id`" +
                    "FROM `DB_students`.`students` WHERE `group_id` = " + groupNumber + ";");
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String first_name = resultSet.getString("first_name");
                String second_name = resultSet.getString("second_name");
                String group = resultSet.getString("group_id");
                System.out.printf("Прочитана строка: %s, %s, %s, %s\n", id, first_name, second_name, group);
            }
        }
    }

    static void selectAllStudents(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("""
                    SELECT `id`, `first_name`, `second_name`, `group_id`
                    FROM `DB_students`.`students`;
                    """);
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String first_name = resultSet.getString("first_name");
                String second_name = resultSet.getString("second_name");
                String group = resultSet.getString("group_id");
                System.out.printf("Прочитана строка: %s, %s, %s, %s\n", id, first_name, second_name, group);
            }
        }
    }

    static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate("""
                    INSERT INTO `DB_students`.`groups` (`id`,`group_name`) VALUES
                    (UUID(), '33'),
                    (UUID(), '77'),
                    (UUID(), '44');
                    """);
            System.out.println("Количество вставленных строк: " + count);
        }
        try (Statement statement = connection.createStatement()) {
            int count = statement.executeUpdate("""
                    INSERT INTO `DB_students`.`students` (`id`,`first_name`, `second_name`, `group_id`) VALUES
                    (UUID(), 'Oleg', 'Ivanov', '33'),
                    (UUID(), 'Tom', 'Popov', '77'),
                    (UUID(), 'Nick', 'Denisov', '33'),
                    (UUID(), 'Mikhail', 'Donskov', '44');
                    """);
            System.out.println("Количество вставленных строк: " + count);
        }
    }

    static void createTableGroup(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE `DB_students`.`groups` (
                    `id` VARCHAR(36) PRIMARY KEY,
                    `group_name` VARCHAR(128) NOT NULL
                    );
                    """);
        }
    }

    static void createTableStudent(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE `DB_students`.`students` (
                    `id` VARCHAR(36) PRIMARY KEY,
                    `first_name` VARCHAR(256) NOT NULL,
                    `second_name` VARCHAR(256) NOT NULL,
                    `group_id` VARCHAR(128) NOT NULL,
                    FOREIGN KEY (group_id)
                    REFERENCES `groups` (`id`)
                    );
                    """);
        }
    }

    static void createSchema(Connection connection) throws SQLException {
        boolean isCreatedDB;
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP SCHEMA `DB_students` ;");
            isCreatedDB = statement.execute("CREATE SCHEMA `DB_students` ;");
        }
        if (isCreatedDB) {
            System.out.println("Database is created!");
        }
    }
}