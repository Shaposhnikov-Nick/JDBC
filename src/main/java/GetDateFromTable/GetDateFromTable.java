package GetDateFromTable;

/*
Получаем все данные из таблицы в БД
 */

import java.sql.*;

public class GetDateFromTable {
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

        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "Select * from test2";
            // выполним запрос
            try (Statement statement = connection.createStatement()) {
                // проверяем, выполнится ли запрос
                boolean isExecuted = statement.execute(sql);
                if (isExecuted)
                    System.out.println("Select executed");
                else
                    System.out.println("Select not executed");

                //result указывает на первую строку с выборки
                //чтобы вывести данные используем метод next(),
                // с помощью которого переходим к следующему элементу
                ResultSet resultSet = statement.getResultSet();

                // в цикле выводим все данные из таблицы
                while (resultSet.next()) {
                    System.out.printf("ID %d, name %s, dayOfBirth %s\n", resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("dayOfBirth"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}