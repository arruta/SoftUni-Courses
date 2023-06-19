import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {

    final private static String CHECK_IF_COUNTRY_EXIST =
            "SELECT country FROM towns WHERE country = ?;";
    final private static String GET_TOWNS_NAME_BY_COUNTRY =
            "SELECT name FROM towns WHERE country = ?;";
    final private static String UPDATE_TOWNS_NAME =
            "UPDATE towns SET name = UPPER(name) WHERE country = ?;";

    final private static String PRINT_IF_NO_TOWN_NAME_AFFECTED = "No town names were affected.";
    final private static String PRINT_AFTER_TOWN_NAME_UPDATE =
            "%d town names were affected.%n%s";

    public static void main(String[] args) throws SQLException {
        Connection connection = Utils.getMySQLConnection();

        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();

        boolean isCountryExist = isCountryExist(connection, CHECK_IF_COUNTRY_EXIST, country);

        if(!isCountryExist){
            System.out.printf(PRINT_IF_NO_TOWN_NAME_AFFECTED);
        }else {
            updateTownNames(connection, UPDATE_TOWNS_NAME, country);

            List <String> newTownsNames = getUpdatedTowns(connection, GET_TOWNS_NAME_BY_COUNTRY, country);

            System.out.printf(PRINT_AFTER_TOWN_NAME_UPDATE, newTownsNames.size(), newTownsNames);
        }

        connection.close();
    }

    private static void updateTownNames(Connection connection, String updateQuery, String country) throws SQLException {
        PreparedStatement statementUpdateTownsNames = connection.prepareStatement(updateQuery);
        statementUpdateTownsNames.setString(1, country);
        statementUpdateTownsNames.executeUpdate();
    }

    private static List<String> getUpdatedTowns(Connection connection, String getQuery, String country) throws SQLException {
        List<String> towns = new ArrayList<>();
        PreparedStatement statementGetTownsName = connection.prepareStatement(getQuery);
        statementGetTownsName.setString(1, country);
        ResultSet resultSetTowns = statementGetTownsName.executeQuery();

        while (resultSetTowns.next()) {
            towns.add(resultSetTowns.getString("name"));
        }

        return towns;
    }

    private static boolean isCountryExist (Connection connection, String checkQuery, String country) throws SQLException {
        PreparedStatement statementGetCountry = connection.prepareStatement(checkQuery);
        statementGetCountry.setString(1, country);
        ResultSet resultSetGetCountry = statementGetCountry.executeQuery();

        return resultSetGetCountry.next();
    }
}
