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
    private JDateChooser fechaInicialChooser = new JDateChooser();
    private JDateChooser fechaFinallChooser = new JDateChooser();


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
}
