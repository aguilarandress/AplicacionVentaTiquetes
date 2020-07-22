package ventatiquetes.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import ventatiquetes.authmanager.AuthenticationManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase para realizar la conexion a la base de datos
 */
public class DatabaseConnection {
    private static Connection connection;
    private static SQLServerDataSource dataSource = new SQLServerDataSource();

    /**
     * Crea la conexion a la base de datos
     */
    public static void createConnection() {
        // Database configuration
        dataSource.setServerName(AuthenticationManager.getHost());
        dataSource.setPortNumber(AuthenticationManager.getPort());
        dataSource.setDatabaseName(AuthenticationManager.getDatabaseName());
        // Get database user credentials
        dataSource.setUser(AuthenticationManager.getDbUser().getUsername());
        dataSource.setPassword(AuthenticationManager.getDbUser().getPassword());
        try {
            connection = dataSource.getConnection();
            System.out.println("Connected successfuly...");
        } catch (SQLException e) {
            System.out.println("**ERROR** En la xonexion a la base de datos");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la conexion de la base de datos
     * @return La conexion a la BD
     */
    public static Connection getConnection() {
        return connection;
    }
}
