package Interfaces;

import java.sql.*;

public class Main {

    // строка с адресом БД для подключения
    private static final String URL = "jdbc:postgresql://localhost/postgres?user=postgres&password=QWErty12345";

    private static String conok = "Соединение с БД установлено";
    private static String conerr = "Произошла ошибка подключения к бд";

    public static void main(String[] args) {
        // Connection - средство для создания сессий, отвечает за физическое подключение к базе данных.
        try (Connection connection = DriverManager.getConnection(URL)) {
            // при удачном подключении к БД выводим:
            System.out.println(String.format("%s", conok));

            // запрос в БД
            String sql = "Select * from users";

            /* Интерфейс Statement позволяет делать запросы к БД, которые определены как константы,
            и не принимают никаких параметров.
             */
            try (Statement statement = connection.createStatement()) {
                boolean isExecuted = statement.execute(sql);
                // если запрос успешен
                if (isExecuted)
                    System.out.println("Select executed");

                // executeQuery() возвращает объект ResultSet и используется для получения множества результатов,
                // обычно с Select запросом.
                ResultSet resultSet = statement.executeQuery(sql);

                System.out.println("ID");

                // в цикле получаем ID  из таблицы
                while (resultSet.next())
                    System.out.println(resultSet.getInt("ID"));
            }


            /*
             Интерфейс PreparedStatement - В случае, когда нам нужно передать в выражение какие-нибудь значения,
             и также требуется несколько раз вызывать один и тот же запрос
             */
            String sql2 = "Select * from users WHERE id = ?;";
            int count = 2;

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                // устанавливаем в нужную позицию (на места знаков ?) значения нужного типа
                preparedStatement.setInt(1, count);

                // ResultSet executeQuery(String SQL), возвращает объект ResultSet и используется для получения
                // множества результатов, обычно с Select запросом.
                ResultSet resultSet1 = preparedStatement.executeQuery();

                while (resultSet1.next())
                    System.out.println(resultSet1.getInt("ID"));

                // добавление данных в таблицу с помощью интерфейса PreparedStatement

                // позиция
                int ourint = 4;
                // имя
                String ourname = "testname";

                // операция добавления строки в таблицу
                String sql3 = "Insert into users (id, name) Values (?, ?)";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql3);
                preparedStatement2.setInt(1, ourint);
                preparedStatement2.setString(2, ourname);

                // int executeUpdate (String SQL), возвращает число, которое показывает, на сколько столбцов в таблице
                // повлиял наш запрос
                int rows = preparedStatement2.executeUpdate();
                System.out.println(rows + " Строк добавлено");
            }


            /*
            Интерфейс CallableStatement позволяет нашему приложению вызвать хранимую на сервере DB процедуру.
            Так же как и в preparedStatement с помощью маркеров мы можем определить операторы,
            но есть и отличие — мы можем использовать не только порядковое местоположение, но и указание по имени.
             */
            String sql4 = "Insert into users (id, name) Values (?, ?)"; // здесь должна быть функция из БД
            // Создаем подключение
            try (CallableStatement callableStatement = connection.prepareCall(sql4)) {
                // Добавляем 2 значения
                callableStatement.setInt(1, 123);
                callableStatement.setString(2, "name");
                // вызываем функцию, которая лежит у нас в базе
                callableStatement.executeUpdate();
            }

            // обязательно закрываем запросы через метод close(), если не использовался try-with-resource

        } catch (SQLException throwables) {
            // при ошибке подключения к БД выводим:
            System.out.println(String.format("%s", conerr));
            throwables.printStackTrace();
        }
    }
}
