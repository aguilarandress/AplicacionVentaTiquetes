package ventatiquetes.controllers;

import ventatiquetes.database.DatabaseConnection;
import ventatiquetes.jdbc.PresentacionesCarteleraJDBC;
import ventatiquetes.jdbc.PresentacionesJDBC;
import ventatiquetes.jdbc.ProduccionesJDBC;
import ventatiquetes.jdbc.TeatrosJDBC;
import ventatiquetes.models.*;
import ventatiquetes.views.MainView;
import ventatiquetes.mappers.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
        this.mainView.getObtenerPreciosCarteleraBtn().addActionListener(new ObtenerPreciosCarteleraListener());
        // Listeners para conultar asientos disponibles
        this.mainView.getTeatroComboAsientos().addItemListener(new ConsultaTeatroListener());
        this.mainView.getTablaProdAsientos().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = this.mainView.getTablaProdAsientos().getSelectionModel();
        selectionModel.addListSelectionListener(new ConsultaTeatroProdsListener());

        this.mainView.getTablaPresAsientos().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel = this.mainView.getTablaPresAsientos().getSelectionModel();
        selectionModel.addListSelectionListener(new ConsultaTeatroPresentListener());

        this.mainView.getComboBloqueAsientos().addItemListener(new ConsultaTeatroBloqueListener());

        this.mainView.getComboFilaAsientos().addItemListener(new ConsultaTeatroFilaListener());

        this.mainView.getTablaPresAsientos().setEnabled(true);
        this.mainView.getTablaPresAsientos().setEnabled(true);
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

    /**
     * Listener para cuando se bucan los precios de los bloques
     */
    private class ObtenerPreciosCarteleraListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Verificar que una fila este seleccionada
            if (mainView.getCarteleraTable().getSelectedRow() != -1) {
                int filaSeleccionada = mainView.getCarteleraTable().getSelectedRow();
                // Obtener id de la produccion
                int produccionId = presentaciones.get(filaSeleccionada).getProduccionId();
                // Obtener los precios de los bloques
                PresentacionesCarteleraJDBC presentacionesCarteleraJDBC = new PresentacionesCarteleraJDBC();
                presentacionesCarteleraJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Bloque> bloquePrecios = presentacionesCarteleraJDBC.getBloquePreciosByProduccionId(produccionId);
                Object filas[][] = new Object[bloquePrecios.size()][2];
                for (int i = 0; i < bloquePrecios.size(); i++) {
                    filas[i][0] = bloquePrecios.get(i).getNombre();
                    filas[i][1] = bloquePrecios.get(i).getPrecio();
                }
                String columnNames[] = new String[] {"Bloque", "Precio"};
                DefaultTableModel tableModel = new DefaultTableModel(filas, columnNames);
                mainView.getBloquePreciosTable().setModel(tableModel);
            }
        }
    }

    // LISTENERS PARA CONSULTAR ASIENTOS DISPONIBLES

    /**
     * Listener para obtener los datos de las presentaciones de los teatros
     */
    private class ConsultaTeatroListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Teatro teatro = (Teatro) mainView.getTeatroComboAsientos().getSelectedItem();
                ProduccionesJDBC produccionesJDBC = new ProduccionesJDBC();
                produccionesJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList <Produccion> producciones = produccionesJDBC.getProdTIdView(teatro.getId());
                ModelTablaProd model= TablaProdMapper.mapRows(producciones);
                mainView.getTablaProdAsientos().setModel(model);
                ModelTablaProd model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
                mainView.getTablaPresAsientos().setModel(model2);
                mainView.getComboBloqueAsientos().removeAllItems();
                mainView.getComboFilaAsientos().removeAllItems();
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientosAsientos().setModel(model5);
                mainView.getTablaProdAsientos().setEnabled(true);
                mainView.getTablaPresAsientos().setEnabled(true);
            }
        }
    }

    /**
     * Listener para cargar presentaciones en base a una presentacion
     */
    private class ConsultaTeatroProdsListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(mainView.getTablaProdAsientos().getRowCount()>0) {
                Produccion produccion = (Produccion) mainView.getTablaProdAsientos().getValueAt(mainView.getTablaProdAsientos().getSelectedRow(), 0);
                PresentacionesJDBC presentacionesJDBC = new PresentacionesJDBC();
                presentacionesJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Presentacion> presentacions = presentacionesJDBC.getPresentByProdIdView(produccion);
                ModelTablaProd model = TablaPresenMapper.mapRows(presentacions);
                mainView.getTablaPresAsientos().setModel(model);
                mainView.getComboBloqueAsientos().removeAllItems();
                mainView.getComboFilaAsientos().removeAllItems();
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientosAsientos().setModel(model5);

            }
        }
    }

    /**
     * Listener para cargar bloques en base a una producción-presentación
     */
    private class ConsultaTeatroPresentListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(mainView.getTablaPresAsientos().getRowCount()>0)
            {
                Presentacion presentacion = (Presentacion) mainView.getTablaPresAsientos().getValueAt(mainView.getTablaPresAsientos().getSelectedRow(),0);
                PresentacionesCarteleraJDBC presentacionesCarteleraJDBC = new PresentacionesCarteleraJDBC();
                presentacionesCarteleraJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Bloque> bloques = presentacionesCarteleraJDBC.getBloquePreciosByProduccionId(presentacion.getId());
                mainView.getComboBloqueAsientos().removeAllItems();
                TablaBloquePreciosMapper.mapRows(bloques,mainView.getComboBloqueAsientos());
                mainView.getComboFilaAsientos().removeAllItems();
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientosAsientos().setModel(model5);
            }
        }
    }

    /**
     * Listener para cargar filas en base a un bloque
     */
    private class ConsultaTeatroBloqueListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Bloque bloque = (Bloque) mainView.getComboBloqueAsientos().getSelectedItem();
                TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
                teatrosJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Fila> filas = teatrosJDBC.getFilasByBloque(bloque);
                mainView.getComboFilaAsientos().removeAllItems();
                TablaFilasMapper.mapRows(filas,mainView.getComboFilaAsientos());
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientosAsientos().setModel(model5);
                mainView.getTablaProdAsientos().setEnabled(false);
                mainView.getTablaPresAsientos().setEnabled(false);
            }
        }
    }

    /**
     * Listener para cargar los asientos disponibles en base a la combinación de producción,presentación ,bloque y fila
     */
    private class ConsultaTeatroFilaListener implements  ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Fila fila = (Fila) mainView.getComboFilaAsientos().getSelectedItem();
                Presentacion presentacion = (Presentacion) mainView.getTablaPresAsientos().getValueAt(mainView.getTablaPresAsientos().getSelectedRow(),0);
                TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
                teatrosJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Asiento> asientos = teatrosJDBC.getAsientosByFila(fila,presentacion);
                ModelTablaProd model = TablaAsientosMapper.mapRows(asientos);
                mainView.getTablaAsientosAsientos().setModel(model);
            }
        }
    }
}
