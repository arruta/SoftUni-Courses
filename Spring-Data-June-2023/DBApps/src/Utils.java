import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

enum Utils {
    ;

    static Connection getMySQLConnection() throws SQLException {

        final Properties userPass = new Properties();
        userPass.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        userPass.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        final Connection connection = DriverManager.getConnection(Constants.JDBC_MYSQL_URL, userPass);

        return connection;
    }
}
