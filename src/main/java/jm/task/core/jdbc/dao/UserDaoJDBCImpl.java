package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS \n" +
                "(\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    lastName VARCHAR(30) NOT NULL,\n" +
                "    age int NOT NULL\n" +
                "    \n" +
                ")";
        try (Statement statement = Util.getConnection().createStatement()) {
            if (statement.execute("SHOW TABLES LIKE 'yourtable';"))

                statement.execute(sql);


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS;";
        try (Statement statement = Util.getConnection().createStatement()) {

            statement.execute(sql);

        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {


        String sql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // использовать prepareStatement для работы с данными внутри таблицы
        // DDL -  \DML - язык манипулирования данными SELECT – выборка данных
        //INSERT – вставка новых данных
        //UPDATE – обновление данных
        //DELETE – удаление данных
        //MERGE – слияние данных
    }

    public void removeUserById(long id) {

        String sql = "DELETE FROM USERS WHERE ID=?";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT NAME, LASTNAME, AGE FROM USERS";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {

        String sql = "DELETE FROM USERS;";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {

            preparedStatement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
