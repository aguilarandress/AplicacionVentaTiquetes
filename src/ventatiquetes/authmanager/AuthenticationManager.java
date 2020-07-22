package ventatiquetes.authmanager;

import io.github.cdimascio.dotenv.Dotenv;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.jasypt.salt.RandomSaltGenerator;
import ventatiquetes.models.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase para leer los usuarios del archivo de configuracion
 */
public class AuthenticationManager {

    private static String host;
    private static int port;
    private static String databaseName;
    private static User dbUser;

    /**
     * Carga los usuarios para la base de datos
     */
    public static void loadAuthenticationUsers() {
        // Get secret key
        Dotenv dotenv = Dotenv.load();
        String secretkey = dotenv.get("SECRET_KEY");
        // Create encryptor
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretkey);
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        // Load properties file
        Properties props = new EncryptableProperties(encryptor);
        try {
            props.load(new FileInputStream("./config/application.properties"));
            // Get server properties
            host = props.getProperty("datasource.host");
            databaseName = props.getProperty("datasource.name");
            port = Integer.parseInt(props.getProperty("datasource.port"));
            // Get user credentials
            dbUser = new User();
            dbUser.setUsername(props.getProperty("datasource.dbusername"));
            dbUser.setPassword(props.getProperty("datasource.dbpassword"));
        } catch (FileNotFoundException e) {
            System.out.println("**ERROR** File not found...");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("**ERROR** IOException...");
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el puerto del servidor de la base de datos
     * @return El puerto del servidor de la base de datos
     */
    public static int getPort() {
        return port;
    }

    /**
     * Obtiene el nombre de la base de datos
     * @return El nombre de la base de datos
     */
    public static String getDatabaseName() {
        return databaseName;
    }

    /**
     * Obtiene el host de la base de datos
     * @return El host de la base de datos
     */
    public static String getHost() {
        return host;
    }

    /**
     * Obtiene el usuario de la base de datos
     * @return El usuario de la base de datos
     */
    public static User getDbUser() {
        return dbUser;
    }
}
