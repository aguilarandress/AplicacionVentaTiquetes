package ventatiquetes.controllers;

import ventatiquetes.database.DatabaseConnection;
import ventatiquetes.jdbc.PresentacionesCarteleraJDBC;
import ventatiquetes.models.PresentacionCartelera;
import ventatiquetes.views.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

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
            // Obtener fechas
            Date fechaInicial = mainView.getFechaInicialChooser().getDate();
            Date fechaFinal = mainView.getFechaFinallChooser().getDate();
            // Obtener presentaciones de la cartelera
            PresentacionesCarteleraJDBC presentacionesCarteleraJDBC = new PresentacionesCarteleraJDBC();
            presentacionesCarteleraJDBC.setConnection(DatabaseConnection.getConnection());
            ArrayList<PresentacionCartelera> presentaciones = presentacionesCarteleraJDBC.getPresentacionesCarteleraByFechas(fechaInicial, fechaFinal);
            if (presentaciones.size() == 0) {
                // TODO: Desplegar mensaje
            }
        }
    }

}
