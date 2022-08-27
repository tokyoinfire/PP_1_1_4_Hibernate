package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private String sql;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {
            sql = "CREATE TABLE IF NOT EXISTS users(id int NOT NULL AUTO_INCREMENT, name varchar(50), " +
                    "lastName varchar(50), age int, PRIMARY KEY (id))";

            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {

        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {
            sql = "DROP TABLE IF EXISTS users";

            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }



    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createQuery("delete from User where id = " + id).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

        }

    }

    @Override
    public List<User> getAllUsers() {

        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        ResultSet rs;

        try(Session session = getSessionFactory().openSession()) {
            sql = "SELECT * FROM users";

            transaction = session.beginTransaction();
            users = (List<User>) session.createSQLQuery(sql).addEntity(User.class).list();
            transaction.commit();


        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {

            sql = "DELETE FROM User";
            transaction = session.beginTransaction();
            session.createQuery(sql).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

        }

    }
}
