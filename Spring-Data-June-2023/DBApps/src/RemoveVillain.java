import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveVillain {
    final private static String GET_VILLAIN_NAME_BY_ID =
            "SELECT name FROM villains WHERE id = ?;";
    final private static String GET_COUNT_OF_MINIONS_BY_VILLAIN_ID =
            "SELECT COUNT(*) AS m_count FROM minions_villains WHERE villain_id = ?;";
    final private static String DELETE_MINIONS_VILLAINS_BY_VILLAI_ID =
            "DELETE FROM minions_villains WHERE villain_id = ?;";
    final private static String DELETE_VILLAIN_BY_ID =
            "DELETE FROM villains WHERE id = ?;";
    final private static String PRINT_IF_VILLAIN_ID_NOT_EXIST =
            "No such villain was found\n";

    final private static String PRINT_RELEASED_MINIONS_FORMAT =
            "%d minions released\n";
    final private static String PRINT_VILLAIN_DELETED_FORMAT =
            "%s was deleted\n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getMySQLConnection();

        Scanner scanner = new Scanner(System.in);
        final int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement statementGetVillainById = connection.prepareStatement(GET_VILLAIN_NAME_BY_ID);
        statementGetVillainById.setInt(1, villainId);

        ResultSet villainResultSet = statementGetVillainById.executeQuery();

        if (!villainResultSet.next()) {
            System.out.printf(PRINT_IF_VILLAIN_ID_NOT_EXIST);
            connection.close();
            return;
        }

        String villainName = villainResultSet.getString("name");

        PreparedStatement statementCountMinions = connection.prepareStatement(GET_COUNT_OF_MINIONS_BY_VILLAIN_ID);
        statementCountMinions.setInt(1, villainId);
        ResultSet countMinionsResultSet = statementCountMinions.executeQuery();

        countMinionsResultSet.next();

        int countOfMinions = countMinionsResultSet.getInt("m_count");

        connection.setAutoCommit(false);

        try (PreparedStatement deleteMinionsStatement =
                     connection.prepareStatement(DELETE_MINIONS_VILLAINS_BY_VILLAI_ID);
             PreparedStatement deleteVillainsStatement =
                     connection.prepareStatement(DELETE_VILLAIN_BY_ID)) {

            deleteMinionsStatement.setInt(1, villainId);
            deleteMinionsStatement.executeUpdate();

            deleteVillainsStatement.setInt(1, villainId);
            deleteVillainsStatement.executeUpdate();

            connection.commit();

            System.out.printf(PRINT_VILLAIN_DELETED_FORMAT, villainName);
            System.out.printf(PRINT_RELEASED_MINIONS_FORMAT, countOfMinions);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

            connection.rollback();
        }

    }
}
