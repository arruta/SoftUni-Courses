import java.util.Scanner;

enum Constants {
    ;
    static final String USER_KEY = "user";
    static final String USER_VALUE = returnUser();
    static final String PASSWORD_KEY = "password";
    static final String PASSWORD_VALUE = returnPassword();
    static final String JDBC_MYSQL_URL = "jdbc:mysql://localhost:3306/minions_db";

    private static String returnUser(){
        Scanner scanner = new Scanner(System.in);
        String user = "Enter user: ";
        System.out.print(user);
        user = scanner.nextLine();

        return user;
    }

    private static String returnPassword(){
        Scanner scanner = new Scanner(System.in);
        String password = "Enter password: ";
        System.out.print(password);
        password = scanner.nextLine();

        return password;
    }
}
