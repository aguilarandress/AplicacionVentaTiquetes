package ventatiquetes.views;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import ventatiquetes.models.Teatro;

import java.util.ArrayList;

public class MainView {
    // Consultar cartelera
    private JFrame frame;
    private JPanel mainJPanel;
    private JTabbedPane tabbedPane;
    private JLabel consultarCarteleraTitle;
    private JPanel fechaInicialJPanel;
    private JPanel fechaFinalJPanel;
    private JTable carteleraTable;
    private JButton buscarCarteleraBtn;
    private JScrollPane carteleraScrollPane;
    private JTable bloquePreciosTable;
    private JScrollPane bloquePreciosScrollPane;
    private JButton obtenerPreciosCarteleraBtn;
    private JPanel consultarCarteleraTab;
    // Constular asientos disponibles
    private JPanel ConsultaAsientos;
    private JComboBox teatroComboAsientos;
    private JScrollPane HolderProdAsientos;
    private JTable tablaProdAsientos;
    private JTable tablaPresAsientos;
    private JTable tablaAsientosAsientos;
    private JComboBox comboBloqueAsientos;
    private JComboBox comboFilaAsientos;
    private JLabel consultarAsientosTitle;
    private JLabel teatroAsientosDisponiblesLabel;
    private JLabel produccionAsientosDisponiblesLabel;
    private JLabel presentacionesAsientosDisponiblesLabel;
    private JLabel bloquesAsientosDisponiblesLabel;
    private JLabel filasAsientosDisponiblesLabel;
    private JLabel asientosAsientosDisponiblesLabel;
    private JComboBox comboTeatros;
    private JTable tablaProds;
    private JTable tablaPresent;
    private JTable tablaAsientos;
    private JList listaAsientos;
    private JButton BoletosButton;
    private JButton realizarCompraButton;
    private JLabel totalL;
    private JLabel montoTotal;
    private JComboBox comboBLQ;
    private JComboBox comboFl;
    private JPanel comprarBoletos;
    private JDateChooser fechaInicialChooser = new JDateChooser();
    private JDateChooser fechaFinallChooser = new JDateChooser();
    private DefaultListModel valoresLista;

    public MainView() {
        this.frame = new JFrame("Publico General");
        this.frame.setContentPane(this.mainJPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        // Config date choosers
        this.fechaInicialChooser.setDateFormatString("yyyy-MM-dd");
        this.fechaInicialJPanel.add(fechaInicialChooser);
        this.fechaFinallChooser.setDateFormatString("yyyy-MM-dd");
        this.fechaFinalJPanel.add(fechaFinallChooser);
        this.frame.setSize(700, 500);
        DefaultListModel model =new DefaultListModel();
        this.listaAsientos.setModel(model);
        this.valoresLista= model;
    }

    public void setVisible() {
        this.frame.setVisible(true);
    }

    public void displayMessage(String message, boolean success) {
        JOptionPane.showMessageDialog(this.frame, message, success ? "EXITO" : "ERROR",
                success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JDateChooser getFechaInicialChooser() {
        return fechaInicialChooser;
    }

    public JDateChooser getFechaFinallChooser() {
        return fechaFinallChooser;
    }

    public JButton getBuscarCarteleraBtn() {
        return buscarCarteleraBtn;
    }

    public JTable getCarteleraTable() {
        return carteleraTable;
    }

    public JTable getBloquePreciosTable() {
        return bloquePreciosTable;
    }

    public JButton getObtenerPreciosCarteleraBtn() {
        return obtenerPreciosCarteleraBtn;
    }

    public JPanel getConsultaAsientos() {
        return ConsultaAsientos;
    }

    public JComboBox getTeatroComboAsientos() {
        return teatroComboAsientos;
    }

    public JScrollPane getHolderProdAsientos() {
        return HolderProdAsientos;
    }

    public JTable getTablaProdAsientos() {
        return tablaProdAsientos;
    }

    public JTable getTablaPresAsientos() {
        return tablaPresAsientos;
    }

    public JTable getTablaAsientosAsientos() {
        return tablaAsientosAsientos;
    }

    public JComboBox getComboBloqueAsientos() {
        return comboBloqueAsientos;
    }

    public JComboBox getComboFilaAsientos() {
        return comboFilaAsientos;
    }

    public void setComboTeatrosAsientos(ArrayList<Teatro> teatros)
    {
        for (Teatro t:teatros
        ) {
            this.teatroComboAsientos.addItem(t);
        }
    }

    public void setComboTeatros(ArrayList<Teatro> teatros)
    {
        for (Teatro t:teatros
        ) {
            this.comboTeatros.addItem(t);
        }
    }

    public JComboBox getComboTeatros() {
        return comboTeatros;
    }

    public JTable getTablaProds() {
        return tablaProds;
    }

    public JTable getTablaPresent() {
        return tablaPresent;
    }

    public JTable getTablaAsientos() {
        return tablaAsientos;
    }

    public JList getListaAsientos() {
        return listaAsientos;
    }

    public JButton getBoletosButton() {
        return BoletosButton;
    }

    public JButton getRealizarCompraButton() {
        return realizarCompraButton;
    }

    public JLabel getTotalL() {
        return totalL;
    }

    public Double getMontoTotal() {
        return Double.parseDouble(montoTotal.getText());
    }

    public JComboBox getComboBLQ() {
        return comboBLQ;
    }

    public JComboBox getComboFl() {
        return comboFl;
    }

    public JPanel getComprarBoletos() {
        return comprarBoletos;
    }

    public DefaultListModel getValoresLista() { return valoresLista; }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal.setText(montoTotal.toString());
    }
}
