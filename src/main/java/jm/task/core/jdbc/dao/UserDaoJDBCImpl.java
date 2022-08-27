package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.connectToDB();
    Statement statement;
    String sql;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS users(id int NOT NULL AUTO_INCREMENT, name varchar(50), " +
                    "lastName varchar(50), age int, PRIMARY KEY (id))";
            statement.executeUpdate(sql);
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при создании базы данных!");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Возникла ошибка при откате!");
            }
        }
    }

    public void dropUsersTable() {
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            sql = "DROP TABLE IF EXISTS users;";
            statement.execute(sql);
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при очищении таблицы!");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Возникла ошибка при откате!");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            connection.setAutoCommit(false);
            sql = "INSERT INTO users(name, lastName, age) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            connection.commit();
            connection.setAutoCommit(true);
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при добавлении пользователя!");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Возникла ошибка при откате!");
            }
        }


    }

    public void removeUserById(long id) {
        try {
            connection.setAutoCommit(false);
            sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (int) id);
            connection.commit();
            connection.setAutoCommit(true);
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при удалении пользователя!");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Возникла ошибка при откате!");
            }
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        ResultSet rs;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            sql = "SELECT * FROM users";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(Long.parseLong(rs.getString(1)));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(Byte.parseByte(rs.getString(4)));
                users.add(user);
            }
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при извлечении пользователей!");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Возникла ошибка при откате!");
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            sql = "TRUNCATE TABLE users";
            statement.executeUpdate(sql);
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при удалении таблицы users!");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Возникла ошибка при откате!");
            }
        }
    }
}
