import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AddMinion {

    final private static String PRINT_VILLAIN_ADD_FORMAT = "Villain %s was added to the database.%n";
    final private static String GET_VILLAIN_BY_NAME = "SELECT id FROM villains WHERE name = ?;";
    final private static String INSERT_INTO_VILLAINS = "INSERT INTO villains (name, evilness_factor) VALUE (?, ?);";
    final private static String EVILNESS_FACTOR = "evil";
    final private static String PRINT_TOWN_ADD_FORMAT = "Town %s was added to the database.%n";
    final private static String GET_TOWN_BY_NAME = "SELECT id FROM towns WHERE name = ?;";
    final private static String INSERT_INTO_TOWNS = "INSERT INTO towns (name) VALUE (?);";
    final private static String GET_LAST_MINION_BY_NAME =
            "SELECT id FROM minions WHERE name = ? ORDER BY id DESC LIMIT 1;";
    final private static String INSERT_INTO_MINIONS = "INSERT INTO minions (name, age, town_id) VALUES (?, ?, ?);";
    final private static String PRINT_MINION_ADD_FORMAT = "Successfully added %s to be minion of %s.%n";

    final private static String INSERT_INTO_MINIONS_VILLAINS = "INSERT INTO minions_villains (minion_id, villain_id)  VALUES (?,?);";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Utils.getMySQLConnection();

        Scanner scanner = new Scanner(System.in);
        String[] minionInfo = (scanner.nextLine().split(": ")[1]).split("\\s");
        String villainName = scanner.nextLine().split(": ")[1];
        String minionName = minionInfo[0];
        int minionAge = Integer.parseInt(minionInfo[1]);
        String minionTown = minionInfo[2];

        int townId = getEntryId(connection,
                List.of(minionTown),
                GET_TOWN_BY_NAME,
                INSERT_INTO_TOWNS,
                PRINT_TOWN_ADD_FORMAT);

        int villainId = getEntryId(connection,
                List.of(villainName, EVILNESS_FACTOR),
                GET_VILLAIN_BY_NAME,
                INSERT_INTO_VILLAINS,
                PRINT_VILLAIN_ADD_FORMAT);

        PreparedStatement statementInsertMinions = connection.prepareStatement(INSERT_INTO_MINIONS);
        statementInsertMinions.setString(1, minionName);
        statementInsertMinions.setInt(2, minionAge);
        statementInsertMinions.setInt(3, townId);

        statementInsertMinions.executeUpdate();

        PreparedStatement statementGetLastMinion = connection.prepareStatement(GET_LAST_MINION_BY_NAME);
        statementGetLastMinion.setString(1, minionName);
        ResultSet resultSetLastMinionId = statementGetLastMinion.executeQuery();

        resultSetLastMinionId.next();

        int lastMinionId = resultSetLastMinionId.getInt("id");

        PreparedStatement statementInsertIntoMinionsVillains = connection.prepareStatement(INSERT_INTO_MINIONS_VILLAINS);
        statementInsertIntoMinionsVillains.setInt(1, lastMinionId);
        statementInsertIntoMinionsVillains.setInt(2, villainId);
        statementInsertIntoMinionsVillains.executeUpdate();

        System.out.printf(PRINT_MINION_ADD_FORMAT, minionName, villainName);

        connection.close();
    }

    private static int getEntryId(Connection connection, List<String> arguments, String getQuery, String insertQuery, String print) throws SQLException {
        PreparedStatement statementGetId = connection.prepareStatement(getQuery);
        String name = arguments.get(0);
        statementGetId.setString(1, name);

        final ResultSet resultSetGetId = statementGetId.executeQuery();

        if (!resultSetGetId.next()) {
            final PreparedStatement statementInsert = connection.prepareStatement(insertQuery);

            for (int i = 1; i <= arguments.size(); i++) {
                statementInsert.setString(i, arguments.get(i - 1));
            }

            statementInsert.executeUpdate();

            ResultSet statementAfterUpdate = statementGetId.executeQuery();

            statementAfterUpdate.next();

            System.out.printf(print, name);

            return statementAfterUpdate.getInt("id");
        }

        return resultSetGetId.getInt("id");
    }
}
