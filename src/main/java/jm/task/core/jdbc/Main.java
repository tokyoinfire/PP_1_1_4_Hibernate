package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Петр", "Петров", (byte) 14);
        String name1 = userService.getAllUsers().get(0).getName();
        System.out.println("User с именем – " + name1 + " добавлен в базу данных");

        userService.saveUser("Иван", "Иванов", (byte) 45);
        String name2 = userService.getAllUsers().get(1).getName();
        System.out.println("User с именем – " + name2 + " добавлен в базу данных");

        userService.saveUser("Михаил", "Михаилов", (byte) 23);
        String name3 = userService.getAllUsers().get(2).getName();
        System.out.println("User с именем – " + name3 + " добавлен в базу данных");

        userService.saveUser("Алексей", "Воняев", (byte) 34);
        String name4 = userService.getAllUsers().get(3).getName();
        System.out.println("User с именем – " + name4 + " добавлен в базу данных");

        System.out.println("-------------------");

        userService.cleanUsersTable();


        userService.dropUsersTable();

    }

}

