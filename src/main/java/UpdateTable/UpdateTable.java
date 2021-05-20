package UpdateTable;

/*
Изменение таблицы (добавление столбца)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTable {
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
        // соединение с БД
        try (Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD)) {
            if (connection == null)
                System.out.println("Не удалось подключиться к БД " + DATABASENAME);
            else
                System.out.println("Соединение с БД " + DATABASENAME + " установлено");
            // добавление столбца dayOfBirth тип varchar
            String sql = "Alter table test2 add column dayOfBirth varchar";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
                System.out.println("Столбец успешно создан");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
