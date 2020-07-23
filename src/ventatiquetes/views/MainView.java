package ventatiquetes.views;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

public class MainView {
    private JFrame frame;
    private JPanel mainJPanel;
    private JTabbedPane consultarCarteleraTab;
    private JLabel consultarCarteleraTitle;
    private JPanel fechaInicialJPanel;
    private JPanel fechaFinalJPanel;
    private JTable carteleraTable;
    private JButton buscarCarteleraBtn;
    private JScrollPane carteleraScrollPane;
    private JTable bloquePreciosTable;
    private JScrollPane bloquePreciosScrollPane;
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
}
