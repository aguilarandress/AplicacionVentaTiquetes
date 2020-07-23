package ventatiquetes.jdbc;

import ventatiquetes.daos.PresentacionesCarteleraDAO;
import ventatiquetes.models.PresentacionCartelera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase JDBC para las presentaciones de la cartelera
 */
public class PresentacionesCarteleraJDBC implements PresentacionesCarteleraDAO {

    private Connection connection;

    /**
     * Configura la conexion que debe usar
     * @param connection La conexion de la base de datos
     */
    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<PresentacionCartelera> getPresentacionesCarteleraByFechas(Date fechaInicial, Date fechaFinal) {
        try {
            // Prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetPresentacionesDisponibles ?, ?");
            preparedStatement.setDate(1, new java.sql.Date(fechaInicial.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(fechaFinal.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            // Get results
            ArrayList<PresentacionCartelera> presentaciones = new ArrayList<>();
            while (resultSet.next()) {
                PresentacionCartelera presentacionCartelera = new PresentacionCartelera();
                presentacionCartelera.setProduccionId(resultSet.getInt("ProduccionId"));
                presentacionCartelera.setPresentacionId(resultSet.getInt("PresentacionId"));
                presentacionCartelera.setNombreProduccion(resultSet.getString("NombreProduccion"));
                presentacionCartelera.setTeatroId(resultSet.getInt("IdTeatro"));
                presentacionCartelera.setNombreTeatro(resultSet.getString("NombreTeatro"));
                presentacionCartelera.setEstado(resultSet.getString("Estado"));
                presentacionCartelera.setTipo(resultSet.getString("Tipo"));
                presentacionCartelera.setFecha(resultSet.getString("Fecha"));
                presentacionCartelera.setHora(resultSet.getString("Hora"));
                presentacionCartelera.setDescripcion(resultSet.getString("Descripcion"));
                presentaciones.add(presentacionCartelera);
            }
            return presentaciones;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
