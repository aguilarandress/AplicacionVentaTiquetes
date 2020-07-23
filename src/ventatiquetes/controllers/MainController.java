package ventatiquetes.controllers;

import ventatiquetes.database.DatabaseConnection;
import ventatiquetes.jdbc.PresentacionesCarteleraJDBC;
import ventatiquetes.models.PresentacionCartelera;
import ventatiquetes.views.MainView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Main application controller
 */
public class MainController {

    private MainView mainView;
    private ArrayList<PresentacionCartelera> presentaciones = new ArrayList<>();

    public MainController() {
        this.mainView = new MainView();
        this.mainView.setVisible();
        // Set action listeners
        this.mainView.getBuscarCarteleraBtn().addActionListener(new BuscarCarteleraListener());
    }

    /**
     * Event listener para cuando se busca en la cartelera
     */
    private class BuscarCarteleraListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener fechas
            Date fechaInicial = mainView.getFechaInicialChooser().getDate();
            Date fechaFinal = mainView.getFechaFinallChooser().getDate();
            // Validar que las fechas sean validas
            if (fechaInicial == null || fechaFinal == null || fechaInicial.compareTo(fechaFinal) > 0) {
                mainView.displayMessage("Fechas invalidas", false);
                return;
            }
            // Obtener presentaciones de la cartelera
            PresentacionesCarteleraJDBC presentacionesCarteleraJDBC = new PresentacionesCarteleraJDBC();
            presentacionesCarteleraJDBC.setConnection(DatabaseConnection.getConnection());
            presentaciones = presentacionesCarteleraJDBC.getPresentacionesCarteleraByFechas(fechaInicial, fechaFinal);
            if (presentaciones.size() == 0) {
                mainView.displayMessage("No se encontraron presentaciones en estas fechas", false);
                return;
            }
            // Crear tabla
            Object filas[][] = new Object[presentaciones.size()][7];
            for (int i = 0; i < presentaciones.size(); i++) {
                filas[i][0] = presentaciones.get(i).getNombreProduccion();
                filas[i][1] = presentaciones.get(i).getNombreTeatro();
                filas[i][2] = presentaciones.get(i).getTipo();
                filas[i][3] = presentaciones.get(i).getFecha();
                filas[i][4] = presentaciones.get(i).getHora().substring(0, presentaciones.get(i).getHora().indexOf('.'));
                filas[i][5] = presentaciones.get(i).getEstado();
                filas[i][6] = presentaciones.get(i).getDescripcion();
            }
            String columnNames[] = new String[] {"Produccion", "Teatro", "Tipo",
            "Fecha", "Hora", "Estado", "Descripcion"};
            DefaultTableModel tableModel = new DefaultTableModel(filas, columnNames);
            mainView.getCarteleraTable().setModel(tableModel);
        }
    }

}
