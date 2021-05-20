package PreparedStatement;

/*
Проверка нахождения в таблице искомого числа, введенного в консоль,
добавление нового числа
 */

import java.sql.*;
import java.util.Scanner;

public class PreparedStatementMain {
    // сервер базы данных
    private static final String HOST = "localhost";
    // имя базы данных
    private static final String DATABASENAME = "testDB";
    // имя пользователя
    private static final String USERNAME = "postgres";
    // пароль пользователя
    private static final String PASSWORD = "QWErty1234";
    // подкючение к БД
    static Connection connection;

    public static void main(String[] args) {
        // строка с соединением с БД
        String url = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;
        try {
            // соединение с БД
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            if (connection == null)
                System.err.println("Нет соединения с БД");
            else
                System.out.println("Соединение с БД установлено");

            // поиск числа в таблице
            if (checkValue(new Scanner(System.in).nextInt()))
                System.out.println("Число есть в таблице");
            else
                System.out.println("Число отсутствует в таблице");

            // добавление числа в таблицу
            if (insertValue(new Scanner(System.in).nextInt()))
                System.out.println("Число успешно добавлено в таблицу");
            else
                System.out.println("Число не добавлено в таблицу");

            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // метод, выполняющий поиск необходимого числа в таблице
    public static boolean checkValue(int checkedValue) {
        String sql = "Select * from test2 where ID = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, checkedValue);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    // метод, выполняющий добавление числа в таблицу
    public static boolean insertValue(int insertedValue) {
        String sql = "insert into test2(id) values (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // устанавливаем в нужную позицию (на места знаков ?) значения нужного типа
            preparedStatement.setInt(1, insertedValue);
            // выполняем запрос
            int rows = preparedStatement.executeUpdate();
            // проверяем, добавилась ли строка
            if (rows >= 1)
                return true;
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
