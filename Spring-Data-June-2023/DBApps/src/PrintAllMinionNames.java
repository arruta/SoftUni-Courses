import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {
    final static private String GET_ALL_MINIONS_NAMES =
            "SELECT name FROM minions;";

    public static void main(String[] args) throws SQLException {

        Connection connection = Utils.getMySQLConnection();

        PreparedStatement statement =
                connection.prepareStatement(GET_ALL_MINIONS_NAMES,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

        ResultSet resultSet = statement.executeQuery();

        List<String> names = new ArrayList<>();

        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }

        if (names.size() % 2 == 0) {
            printListWithEvenSize(names);
        } else {
            printListWithEvenSize(names);
            System.out.println(names.get(names.size() / 2));
        }

    }

    private static void printListWithEvenSize(List<String> list) {
        for (int i = 0; i < list.size() / 2; i++) {
            System.out.println(list.get(i));
            System.out.println(list.get(list.size() - 1 - i));
        }
    }
}
