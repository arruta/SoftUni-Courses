import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GetMinionNames {
    private static final String GET_MINIONS_NAMES = "SELECT m.name,\n" +
            "       m.age\n" +
            "FROM minions AS m\n" +
            "         JOIN minions_villains AS mv\n" +
            "              ON m.id = mv.minion_id\n" +
            "WHERE mv.villain_id = ?;";

    private static final String GET_VILLAIN_NAME = "SELECT v.name " +
            "FROM villains AS v " +
            "WHERE v.id = ?;";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getMySQLConnection();

        Scanner scanner = new Scanner(System.in);
        final int villainId = Integer.parseInt(scanner.nextLine());

        final PreparedStatement statementVillain = connection.prepareStatement(GET_VILLAIN_NAME);
        statementVillain.setInt(1, villainId);
        final ResultSet resultSetVillain = statementVillain.executeQuery();

        if (!resultSetVillain.next()) {
            System.out.printf("No villain with ID %d exists in the database.", villainId);
            connection.close();
            return;
        }

        final PreparedStatement statementMinions = connection.prepareStatement(GET_MINIONS_NAMES);
        statementMinions.setInt(1, villainId);
        final ResultSet resultSetMinions = statementMinions.executeQuery();

        print(resultSetVillain, resultSetMinions);

        connection.close();
    }

    private static void print(ResultSet resultSetVillain, ResultSet resultSetMinions) throws SQLException {
        final String villainName = resultSetVillain.getString("name");
        System.out.printf("Villain: %s%n", villainName);

        int currentNumber = 1;
        while (resultSetMinions.next()) {
            final String minionName = resultSetMinions.getString("name");
            final int minionAge = resultSetMinions.getInt("age");
            System.out.printf("%d. %s %d%n", currentNumber, minionName, minionAge);
            currentNumber++;
        }
    }
}