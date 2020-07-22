package ventatiquetes;

import ventatiquetes.authmanager.AuthenticationManager;

public class Main {

    public static void main(String[] args) {
        // Load authentication users
        AuthenticationManager.loadAuthenticationUsers();
    }
}
