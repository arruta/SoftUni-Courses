import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IncreaseMinionsAge {

    final private static String UPDATE_MINIONS_AGE_BY_ID =
            "UPDATE minions SET age = age + 1, name = LOWER(name) WHERE id IN (?);";
    final private static String GET_ALL_MINIONS_NAME_AND_AGE =
            "SELECT name, age FROM minions;";

    final private static String PRINT_ALL_MINIONS_FORM =
            "%s %d\n";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getMySQLConnection();

        Scanner scanner = new Scanner(System.in);
        int [] minionsId = Arrays.stream(scanner.nextLine().split("\\s"))
                .mapToInt(Integer::parseInt)
                .toArray();

        PreparedStatement statementUpdateMinions = connection.prepareStatement(UPDATE_MINIONS_AGE_BY_ID);
        for (int i = 0; i < minionsId.length; i++) {
            statementUpdateMinions.setInt(1,minionsId[i]);
            statementUpdateMinions.executeUpdate();
        }

        PreparedStatement statementGetAllMinions = connection.prepareStatement(GET_ALL_MINIONS_NAME_AND_AGE);
        ResultSet resultSetAllMinions = statementGetAllMinions.executeQuery();

        while (resultSetAllMinions.next()) {
            String minionName = resultSetAllMinions.getString("name");
            int minionAge = resultSetAllMinions.getInt("age");
            System.out.printf(PRINT_ALL_MINIONS_FORM, minionName,minionAge);
        }
    }
}
