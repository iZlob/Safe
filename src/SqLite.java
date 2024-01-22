import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqLite implements AutoCloseable {
    public Connection _dbConnection;

    public  SqLite() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        _dbConnection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
    }

    public void ExecuteWithoutResult(String sqlQuery) throws SQLException {
        var statement = _dbConnection.createStatement();
        statement.execute(sqlQuery);
    }


    @Override
    public void close() throws Exception {
        _dbConnection.close();
    }
}
