import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    final private static String CALL_GET_OLDER_PROCEDURE = "CALL usp_get_older(?);";
    final private static String GET_MINION_NAME_AND_AGE =
            "SELECT name, age FROM minions WHERE id = ?;";
    final private static String PRINT_MINION_NAME_AND_UPDATED_AGE_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getMySQLConnection();

        Scanner scanner = new Scanner(System.in);
        final int minionId = Integer.parseInt(scanner.nextLine());

        final ResultSet resultSetMinionInfo = minionInfo(connection, GET_MINION_NAME_AND_AGE, minionId);

        if (!resultSetMinionInfo.next()) {
            System.out.println("No such minion was found");
        } else {
            PreparedStatement statementCallProcedure = connection.prepareStatement(CALL_GET_OLDER_PROCEDURE);
            statementCallProcedure.setInt(1, minionId);
            statementCallProcedure.executeUpdate();

            final ResultSet resultSetAfterUpdate = minionInfo(connection, GET_MINION_NAME_AND_AGE, minionId);

            resultSetAfterUpdate.next();

            System.out.printf(PRINT_MINION_NAME_AND_UPDATED_AGE_FORMAT,
                    resultSetAfterUpdate.getString("name"),
                    resultSetAfterUpdate.getInt("age"));
        }

    }

    private static ResultSet minionInfo(Connection connection, String getQuery, int minionId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getQuery);
        statement.setInt(1, minionId);
        return statement.executeQuery();
    }
}
