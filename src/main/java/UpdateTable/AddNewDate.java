package UpdateTable;

/*
Добавление данных, введенных с клавиатуры,  в новую строку в БД
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddNewDate {
    // сервер базы данных
    private static final String HOST = "localhost";
    // имя базы данных
    private static final String DATABASENAME = "testDB";
    // имя пользователя
    private static final String USERNAME = "postgres";
    // пароль пользователя
    private static final String PASSWORD = "QWErty1234";

    public static void main(String[] args) {
        // строка с соединением с БД
        String url = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;

        try (Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD)) {
            if (connection == null)
                System.out.println("Не удалось подкючиться к БД " + DATABASENAME);
            else
                System.out.println("Успешное подключение к БД " + DATABASENAME);

            System.out.println("Введите данные (ID, name, dayOfBirth):");
            String sql = "Insert into test2(id, name, dayOfBirth) values(?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // устанавливаем в нужную позицию (на места знаков ?) значения нужного типа
                preparedStatement.setInt(1, new Scanner(System.in).nextInt()); // первый знак ?
                preparedStatement.setString(2, new Scanner(System.in).nextLine()); // второй знак ?
                preparedStatement.setString(3, new Scanner(System.in).nextLine()); // третий знак ?
                // выполняем запрос (возвращает количество измененных или добавленных строк
                int rows = preparedStatement.executeUpdate();
                // проверяем, добавилась ли строка
                if (rows >= 1)
                    System.out.println("Добавлено строк: " + rows);
                else
                    System.out.println("Строка не была добавлена");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
