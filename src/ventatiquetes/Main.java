package ventatiquetes;

import ventatiquetes.authmanager.AuthenticationManager;
import ventatiquetes.controllers.MainController;
import ventatiquetes.database.DatabaseConnection;

public class Main {

    public static void main(String[] args) {
        // Load authentication users
        AuthenticationManager.loadAuthenticationUsers();
        // Connec to DB
        DatabaseConnection.createConnection();
        // Init controller
        MainController mainController = new MainController();
    }
}
