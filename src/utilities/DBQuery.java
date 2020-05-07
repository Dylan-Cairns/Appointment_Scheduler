package utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//this class handles prepared statements
public class DBQuery {
    private static PreparedStatement statement;

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    public static void setPreparedStatementReturnKeys(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
    }

    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

}