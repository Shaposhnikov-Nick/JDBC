package LoadModel;

/*
получаем список всех сущностей из таблицы
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoadModelMain {
    // сервер базы данных
    private static final String HOST = "localhost";
    // имя базы данных
    private static final String DATABASENAME = "testDB";
    // имя пользователя
    private static final String USERNAME = "postgres";
    // пароль пользователя
    private static final String PASSWORD = "QWErty1234";


    public static void main(String[] args) {
        // лист для хранения сущностей
        List<LoadModel> data = new ArrayList<>();
        // строка с соединением с БД
        String url = "jdbc:postgresql://" + HOST + "/" + DATABASENAME + "?user=" + USERNAME + "&password=" + PASSWORD;
        // соединение с БД
        try (Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD)) {
            if (connection == null)
                System.out.println("Нет соединения с БД " + DATABASENAME);
            else
                System.out.println("Соединение с БД " + DATABASENAME + " установлено");

            String sql = "Select * from test2";

            // запрос на получение всех данных

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int i = resultSet.getInt("ID");
                        if (resultSet.wasNull())
                            System.out.println("NULL");
                        else
                            System.out.println(i);

                        // добавляем каждый полученный элемент в лист
                        LoadModel loadModel = new LoadModel();
                        loadModel.setI(i);
                        data.add(loadModel);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
