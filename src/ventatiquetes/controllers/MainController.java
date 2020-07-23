package ventatiquetes.controllers;

import ventatiquetes.views.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main application controller
 */
public class MainController {

    private MainView mainView;

    public MainController() {
        this.mainView = new MainView();
        this.mainView.setVisible();
        // Set action listeners
        this.mainView.getBuscarCarteleraBtn().addActionListener(new BuscarCarteleraListener());
    }

    private class BuscarCarteleraListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Funciona...");
        }
    }

}
