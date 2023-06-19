import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class GetVillainsNames {
    private static final String GET_VILLAINS_NAMES = "SELECT\n" +
            "    v.name,\n" +
            "    COUNT(DISTINCT mv.minion_id) AS count\n" +
            "FROM villains AS v\n" +
            "JOIN minions_villains AS mv\n" +
            "ON v.id = mv.villain_id\n" +
            "GROUP BY mv.villain_id\n" +
            "HAVING `count`> ?\n" +
            "ORDER BY `count` DESC ;";

    private static final String PRINT_FORMAT = "%s %d";
    private static final String COLUMN_LABEL_NAME = "v.name";

    private static final String COLUMN_LABEL_COUNT_OF_MINIONS = "count";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getMySQLConnection();

        final PreparedStatement statement = connection.prepareStatement(GET_VILLAINS_NAMES);
        statement.setInt(1, 15);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            print(rs);
        }

        connection.close();

    }

    private static void print(ResultSet rs) throws SQLException {
        final String name = rs.getString(COLUMN_LABEL_NAME);
        final int count = rs.getInt(COLUMN_LABEL_COUNT_OF_MINIONS);

        System.out.printf(PRINT_FORMAT, name, count);
    }


}
