package ventatiquetes.controllers;

import ventatiquetes.database.DatabaseConnection;
import ventatiquetes.jdbc.*;
import ventatiquetes.models.*;
import ventatiquetes.validators.ClienteValidator;
import ventatiquetes.views.MainView;
import ventatiquetes.mappers.*;
import ventatiquetes.views.FormularioCliente;

import javax.swing.*;
import javax.swing.event.*;
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
    FormularioCliente formularioCliente;
    private ArrayList<PresentacionCartelera> presentaciones = new ArrayList<>();

    public MainController() {
        this.mainView = new MainView();
        this.mainView.setVisible();
        // Set action listeners
        this.mainView.getTabbedPane().addChangeListener(new ChangeTabListener());
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

        // Listeners de compra
        this.mainView.getComboTeatros().addItemListener(new CompraTeatroListener());

        this.mainView.getTablaProds().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel = this.mainView.getTablaProds().getSelectionModel();
        selectionModel.addListSelectionListener(new CompraTeatroProdsListener());

        this.mainView.getTablaPresent().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel = this.mainView.getTablaPresent().getSelectionModel();
        selectionModel.addListSelectionListener(new CompraTeatroPresentListener());

        this.mainView.getComboBLQ().addItemListener(new CompraTeatroBloqueListener());

        this.mainView.getComboFl().addItemListener(new CompraTeatroFilaListener());

        this.mainView.getTablaAsientos().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.mainView.getBoletosButton().addActionListener(new CompraBoletosBtnListener());
        this.mainView.getRealizarCompraButton().addActionListener(new CompraDatosBtnListener());

        // Popup listeners
        this.mainView.getComboTeatros().addPopupMenuListener(new CompraTeatroListener2());
        this.mainView.getComboBLQ().addPopupMenuListener(new CompraTeatroBloqueListener2());
        this.mainView.getComboFl().addPopupMenuListener(new CompraTeatroFilaListener2());
        this.mainView.getTeatroComboAsientos().addPopupMenuListener(new ConsultaTeatroListener2());
        this.mainView.getComboBloqueAsientos().addPopupMenuListener(new ConsultaTeatroBloqueListener2());
        this.mainView.getComboFilaAsientos().addPopupMenuListener(new ConsultaTeatroFilaListener2());

    }

    private class ChangeTabListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (mainView.getTabbedPane().getSelectedIndex() == 1) {
                mainView.getTeatroComboAsientos().removeAllItems();
                TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
                teatrosJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Teatro> teatros = teatrosJDBC.getTeatros();
                mainView.setComboTeatrosAsientos(teatros);
                ModelTablaProd model= TablaProdMapper.mapRows(new ArrayList<Produccion>());
                mainView.getTablaProdAsientos().setModel(model);
                ModelTablaProd model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
                mainView.getTablaPresAsientos().setModel(model2);

                mainView.getComboBloqueAsientos().removeAllItems();
                mainView.getComboFilaAsientos().removeAllItems();


                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientosAsientos().setModel(model5);
                //Limpieza tab compra
                model= TablaProdMapper.mapRows(new ArrayList<Produccion>());
                mainView.getTablaProds().setModel(model);
                model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
                mainView.getTablaPresent().setModel(model2);
                mainView.getComboBLQ().removeAllItems();
                mainView.getComboFl().removeAllItems();
                model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientos().setModel(model5);
            } else if (mainView.getTabbedPane().getSelectedIndex() == 2) {
                mainView.getComboTeatros().removeAllItems();
                TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
                teatrosJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Teatro> teatros = teatrosJDBC.getTeatros();
                mainView.setComboTeatros(teatros);
                ModelTablaProd model= TablaProdMapper.mapRows(new ArrayList<Produccion>());
                mainView.getTablaProds().setModel(model);
                ModelTablaProd model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
                mainView.getTablaPresent().setModel(model2);

                mainView.getComboBLQ().removeAllItems();
                mainView.getComboFl().removeAllItems();


                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientos().setModel(model5);
                //Limpieza tab consulta
                model= TablaProdMapper.mapRows(new ArrayList<Produccion>());
                mainView.getTablaProdAsientos().setModel(model);
                model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
                mainView.getTablaPresAsientos().setModel(model2);
                mainView.getComboBloqueAsientos().removeAllItems();
                mainView.getComboFilaAsientos().removeAllItems();
                model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientosAsientos().setModel(model5);
            }

        }
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
                PresentacionesJDBC presentacionesJDBC = new PresentacionesJDBC();
                presentacionesJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Bloque> bloques = presentacionesJDBC.getBloquePreciosByProdId(presentacion.getId());
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
                for (int i = 0; i < asientos.size(); i++) {
                    System.out.println(asientos.get(i).getAsientoId());
                }
                ModelTablaProd model = TablaAsientosMapper.mapRows(asientos);
                mainView.getTablaAsientosAsientos().setModel(model);
            }
        }
    }

    // LISTENERS PARA REALIZAR LA COMPRA
    /**
     * Listener para cargar producciones en base a un teatro
     */
    private class CompraTeatroListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Teatro teatro = (Teatro) mainView.getComboTeatros().getSelectedItem();
                ProduccionesJDBC produccionesJDBC = new ProduccionesJDBC();
                produccionesJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList <Produccion> producciones = produccionesJDBC.getProdTIdView(teatro.getId());
                ModelTablaProd model= TablaProdMapper.mapRows(producciones);
                mainView.getTablaProds().setModel(model);
                ModelTablaProd model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
                mainView.getTablaPresent().setModel(model2);
                mainView.getComboBLQ().removeAllItems();
                mainView.getComboFl().removeAllItems();
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientos().setModel(model5);
                mainView.getTablaProds().setEnabled(true);
                mainView.getTablaPresent().setEnabled(true);
            }
        }
    }

    /**
     * Listener para cargar presentaciones en base a una producción
     */
    private class CompraTeatroProdsListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (mainView.getTablaProds().getRowCount() > 0) {
                Produccion produccion = (Produccion) mainView.getTablaProds().getValueAt(mainView.getTablaProds().getSelectedRow(), 0);
                PresentacionesJDBC presentacionesJDBC = new PresentacionesJDBC();
                presentacionesJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Presentacion> presentacions = presentacionesJDBC.getPresentByProdIdView(produccion);
                ModelTablaProd model = TablaPresenMapper.mapRows(presentacions);
                mainView.getTablaPresent().setModel(model);
                mainView.getComboBLQ().removeAllItems();
                mainView.getComboFl().removeAllItems();
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientos().setModel(model5);
            }
        }
    }

    /**
     * Listener para cargar bloques en base a una producción-presentación
     */
    private class CompraTeatroPresentListener implements  ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

            if(mainView.getTablaPresent().getRowCount()>0)
            {
                Presentacion presentacion = (Presentacion) mainView.getTablaPresent().getValueAt(mainView.getTablaPresent().getSelectedRow(),0);
                AgentesJDBC agentesJDBC = new AgentesJDBC();
                agentesJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Bloque> bloques = agentesJDBC.getBloquePreciosByProdId(presentacion.getId());
                mainView.getComboBLQ().removeAllItems();
                TablaBloquePreciosMapper.mapRows(bloques,mainView.getComboBLQ());
                mainView.getComboFl().removeAllItems();
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientos().setModel(model5);
            }
        }
    }

    private class CompraTeatroBloqueListener implements  ItemListener {
        /**
         * Listener para cargar filas en base a un bloque
         * @param e
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Bloque bloque = (Bloque) mainView.getComboBLQ().getSelectedItem();
                TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
                teatrosJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Fila> filas = teatrosJDBC.getFilasByBloque(bloque);
                mainView.getComboFl().removeAllItems();
                TablaFilasMapper.mapRows(filas,mainView.getComboFl());
                ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
                mainView.getTablaAsientos().setModel(model5);
                mainView.getTablaProds().setEnabled(false);
                mainView.getTablaPresent().setEnabled(false);
            }
        }
    }

    /**
     * Listener para cargar los asientos disponibles en base a la combinación de producción,presentación ,bloque y fila
     */
    private class CompraTeatroFilaListener implements  ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {

                Fila fila = (Fila) mainView.getComboFl().getSelectedItem();
                Presentacion presentacion = (Presentacion) mainView.getTablaPresent().getValueAt(mainView.getTablaPresent().getSelectedRow(),0);
                TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
                teatrosJDBC.setConnection(DatabaseConnection.getConnection());
                ArrayList<Asiento> asientos = teatrosJDBC.getAsientosByFila(fila,presentacion);
                ModelTablaProd model = TablaAsientosMapper.mapRows(asientos);
                mainView.getTablaAsientos().setModel(model);
            }
        }
    }

    /**
     * Realiza el proceso de añadir asientos a una reservación
     */
    private class CompraBoletosBtnListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            int [] selectedRows = mainView.getTablaAsientos().getSelectedRows();
            if(mainView.getTablaAsientos().getRowCount() < 1)
            {
                mainView.displayMessage("No hay asientos seleccionados",false);
                return;
            }
            else if (selectedRows.length == 0)
            {
                mainView.displayMessage("No hay asientos seleccionados",false);
                return;
            }
            else if  (mainView.getValoresLista().getSize()+selectedRows.length > 8)
            {
                mainView.displayMessage("La cantidad de asientos supera el máximo de 8",false);
                return;
            }
            boolean repetido = false;
            for (int i=0 ; i<selectedRows.length ; i++)
            {
                repetido=false;
                Asiento asiento = (Asiento) mainView.getTablaAsientos().getValueAt(selectedRows[i],0);
                for(int o=0 ; o<mainView.getValoresLista().getSize() ;o++ )
                {
                    Asiento asiento2 = (Asiento) mainView.getValoresLista().get(o);
                    if (asiento.getBloqueId() == asiento2.getBloqueId() && asiento.getFilaId().equals(asiento2.getFilaId())
                            && asiento.getAsientoId() == asiento2.getAsientoId())
                    {
                        repetido=true;

                    }
                }
                if (repetido)
                    continue;
                Produccion produccion =  (Produccion)(mainView.getTablaProds().getValueAt(mainView.getTablaProds().getSelectedRow(),0));
                TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
                teatrosJDBC.setConnection(DatabaseConnection.getConnection());
                Bloque bloque = teatrosJDBC.getBloquePreciosByIDS(asiento.getBloqueId(),produccion.getId());
                mainView.setMontoTotal(mainView.getMontoTotal()+bloque.getPrecio());
                mainView.getValoresLista().addElement(asiento);
            }
            if (repetido)
            {
                mainView.displayMessage("No se agregaron los asientos repetidos",false);
            }

        }
    }

    private class CompraDatosBtnListener implements  ActionListener
    {
        /**
         * Encargado de mostrar el formulario de datos del cliente
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!(mainView.getValoresLista().getSize()>0))
            {
                mainView.displayMessage("Debe agregar asientos",false);
                return;
            }
            FormularioCliente view = new FormularioCliente();
            view.setVisible();
            formularioCliente=view;
            formularioCliente.getComprarButton().addActionListener(new ComprarFinalBtn());
        }
    }

    private class ComprarFinalBtn implements ActionListener
    {
        /**
         * Obtiene todos los datos del cliente y realiza la transacción
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            Reservacion reservacion = new Reservacion();
            reservacion.setNombreCliente(formularioCliente.getNombre().getText());
            reservacion.setCorreo(formularioCliente.getCorreo().getText());
            reservacion.setTelefono(formularioCliente.getTelefono().getText());
            reservacion.setEfectivo(true);
            if(!formularioCliente.esEfectivo())
            {
                reservacion.setNumeroTarjeta(formularioCliente.getTarjeta().getText());
                try
                {
                    reservacion.setCVC(Integer.parseInt(formularioCliente.getCvc().getText()));
                }
                catch (Exception o)
                {
                    reservacion.setCVC(0);
                }
                reservacion.setExpiracion(formularioCliente.getDate().getDate());
                reservacion.setEfectivo(false);
            }
            reservacion.setMonto((Double) mainView.getMontoTotal()) ;
            Produccion produccion = (Produccion) mainView.getTablaProds().getValueAt(mainView.getTablaProds().getSelectedRow(),0);
            Presentacion presentacion = (Presentacion) mainView.getTablaPresent().getValueAt(mainView.getTablaPresent().getSelectedRow(),0);
            ArrayList<String> errores = ClienteValidator.validarCliente(reservacion);
            ArrayList<Asiento> asientos = new ArrayList<Asiento>();
            for (int i = 0 ; i < mainView.getValoresLista().getSize();i++)
            {
                asientos.add((Asiento) mainView.getValoresLista().getElementAt(i));
            }
            if(errores.size()>0)
            {
                formularioCliente.displayMessage(errores.get(0),false);
                return;
            }
            Object[] resultados;
            AgentesJDBC agentesJDBC = new AgentesJDBC();
            agentesJDBC.setConnection(DatabaseConnection.getConnection());
            if(!reservacion.isEfectivo()) {
                resultados = agentesJDBC.procesarCompraTarjeta(reservacion, asientos, produccion.getId(), presentacion.getPresentId());
                if ((Boolean)resultados[0]) {
                    mainView.displayMessage("Compra exitosa, número de orden: "+(Integer)resultados[1],true);
                } else {
                    mainView.displayMessage("Tarjeta rechazada , por favor verifique que cuente con el dinero necesario",false);
                }
            }
            else {
                resultados = agentesJDBC.procesarCompraEfectivo(reservacion,asientos,produccion.getId(),presentacion.getPresentId());
                mainView.displayMessage("Compra exitosa, número de orden: "+(Integer)resultados[1],true);
            }
            //Limpieza tablas
            ModelTablaProd model= TablaProdMapper.mapRows(new ArrayList<Produccion>());
            mainView.getTablaProds().setModel(model);
            ModelTablaProd model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
            mainView.getTablaPresent().setModel(model2);
            mainView.getComboBLQ().removeAllItems();
            mainView.getComboFl().removeAllItems();
            ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
            mainView.getTablaAsientos().setModel(model5);
            formularioCliente.cerrar();
            mainView.getValoresLista().clear();
            mainView.setMontoTotal(0.0);
            mainView.getTablaProds().setEnabled(true);
            mainView.getTablaPresent().setEnabled(true);
        }
    }

    // POP UP LISTENERS
    private class CompraTeatroListener2 implements PopupMenuListener
    {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            Teatro teatro = (Teatro) mainView.getComboTeatros().getSelectedItem();
            ProduccionesJDBC produccionesJDBC = new ProduccionesJDBC();
            produccionesJDBC.setConnection(DatabaseConnection.getConnection());
            ArrayList<Produccion> producciones = produccionesJDBC.getProdTIdView(teatro.getId());
            ModelTablaProd model = TablaProdMapper.mapRows(producciones);
            mainView.getTablaProds().setModel(model);
            ModelTablaProd model2 = TablaPresenMapper.mapRows(new ArrayList<Presentacion>());
            mainView.getTablaPresent().setModel(model2);
            mainView.getComboBLQ().removeAllItems();
            mainView.getComboFl().removeAllItems();
            ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
            mainView.getTablaAsientos().setModel(model5);
            mainView.getTablaProds().setEnabled(true);
            mainView.getTablaPresent().setEnabled(true);
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    }

    private class CompraTeatroBloqueListener2 implements  PopupMenuListener
    {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            Bloque bloque = (Bloque) mainView.getComboBLQ().getSelectedItem();
            TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
            teatrosJDBC.setConnection(DatabaseConnection.getConnection());
            ArrayList<Fila> filas = teatrosJDBC.getFilasByBloque(bloque);
            mainView.getComboFl().removeAllItems();
            TablaFilasMapper.mapRows(filas,mainView.getComboFl());
            ModelTablaProd model5 = TablaAsientosMapper.mapRows(new ArrayList<Asiento>());
            mainView.getTablaAsientos().setModel(model5);
            mainView.getTablaProds().setEnabled(false);
            mainView.getTablaPresent().setEnabled(false);
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    }

    private class CompraTeatroFilaListener2 implements PopupMenuListener
    {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            Fila fila = (Fila) mainView.getComboFl().getSelectedItem();
            Presentacion presentacion = (Presentacion) mainView.getTablaPresent().getValueAt(mainView.getTablaPresent().getSelectedRow(),0);
            TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
            teatrosJDBC.setConnection(DatabaseConnection.getConnection());
            ArrayList<Asiento> asientos = teatrosJDBC.getAsientosByFila(fila,presentacion);
            ModelTablaProd model = TablaAsientosMapper.mapRows(asientos);
            mainView.getTablaAsientos().setModel(model);
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    }

    private class ConsultaTeatroListener2 implements PopupMenuListener
    {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
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

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    }

    private class ConsultaTeatroBloqueListener2 implements  PopupMenuListener
    {


        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
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

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    }

    private class ConsultaTeatroFilaListener2 implements PopupMenuListener
    {


        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            Fila fila = (Fila) mainView.getComboFilaAsientos().getSelectedItem();
            Presentacion presentacion = (Presentacion) mainView.getTablaPresAsientos().getValueAt(mainView.getTablaPresAsientos().getSelectedRow(),0);
            TeatrosJDBC teatrosJDBC = new TeatrosJDBC();
            teatrosJDBC.setConnection(DatabaseConnection.getConnection());
            ArrayList<Asiento> asientos = teatrosJDBC.getAsientosByFila(fila,presentacion);
            ModelTablaProd model = TablaAsientosMapper.mapRows(asientos);
            mainView.getTablaAsientosAsientos().setModel(model);
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {

        }
    }
}
