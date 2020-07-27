package ventatiquetes.jdbc;

import ventatiquetes.daos.PresentacionesDAO;
import ventatiquetes.models.Presentacion;
import ventatiquetes.models.Produccion;
import ventatiquetes.models.Bloque;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PresentacionesJDBC implements PresentacionesDAO {
    private Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Presentacion> getPresentByProdId(int Id) {
        ArrayList<Presentacion> presentacions = new ArrayList<Presentacion>();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByProduccionIdPresentaciones ?");
            preparedStatement.setInt(1, Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Presentacion presentacion = new Presentacion();
                presentacion.setId(resultSet.getInt("ProduccionId"));
                presentacion.setPresentId(resultSet.getInt("PresentacionId"));
                presentacion.setFecha(resultSet.getDate("Fecha"));
                presentacion.setHora(resultSet.getString("Hora"));
                presentacions.add(presentacion);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return presentacions;
    }

    @Override
    public ResultSet getRawPresentByProdId(int Id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByProduccionIdPresentaciones ?");
            preparedStatement.setInt(1, Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addPresentacion(Presentacion presentacion) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC CreatePresentacion ?,?,?,?");
            preparedStatement.setInt(1, presentacion.getId());
            preparedStatement.setInt(2, presentacion.getPresentId());

            java.sql.Date date = new java.sql.Date(presentacion.getFecha().getTime());
            preparedStatement.setDate(3, date);

            try {
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                Date hora = parser.parse(presentacion.getHora());
                preparedStatement.setTime(4, new Time(hora.getTime()));
                preparedStatement.execute();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validateDate(Presentacion presentacion) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC ValidateDatePresentaciones ?,?,?");
            preparedStatement.setInt(1, presentacion.getId());
            java.sql.Date date = new java.sql.Date(presentacion.getFecha().getTime());
            preparedStatement.setDate(2, date);

            try {
                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                Date hora = parser.parse(presentacion.getHora());
                preparedStatement.setTime(3, new Time(hora.getTime()));
                ResultSet resultSet = preparedStatement.executeQuery();
                boolean presenFound = resultSet.next();
                if (!presenFound) {

                    return false;
                }
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Presentacion> getPresentByProdIdView(Produccion produccion) {
        ArrayList<Presentacion> presentacions = new ArrayList<Presentacion>();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByProduccionIdPresentacionView ?");
            preparedStatement.setInt(1, produccion.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Presentacion presentacion = new Presentacion();
                presentacion.setId(resultSet.getInt("ProduccionId"));
                presentacion.setPresentId(resultSet.getInt("PresentacionId"));
                presentacion.setFecha(resultSet.getDate("Fecha"));
                presentacion.setHora(resultSet.getString("Hora"));
                presentacions.add(presentacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return presentacions;


    }

    /**
     * @param IdBloque El id de un bloque
     * @param IdProd   El id de una producicón
     * @return bloque con el precio
     */
    @Override
    public Bloque getBloquePreciosByIDS(int IdBloque, int IdProd) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByIdBloque ?,?");
            preparedStatement.setInt(1, IdBloque);
            preparedStatement.setInt(2, IdProd);
            ResultSet resultSet = preparedStatement.executeQuery();
            Boolean found = resultSet.next();
            if (!found) {
                return null;
            }
            Bloque bloque = new Bloque();
            bloque.setPrecio(resultSet.getBigDecimal("Monto").floatValue());
            return bloque;
        } catch (SQLException e) {

        }

        return null;
    }

    @Override
    public ArrayList<Bloque> getBloquePreciosByProdId(int ProdId) {
        try {

            ArrayList<Bloque> bloques = new ArrayList<Bloque>();
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByProdIdBloquePrecios ?");
            preparedStatement.setInt(1, ProdId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bloque bloque = new Bloque();
                bloque.setNombre(resultSet.getString("BloqueNombre"));
                bloque.setPrecio(resultSet.getBigDecimal("Monto").floatValue());
                bloque.setId(resultSet.getInt("BloqueId"));
                bloque.setIdProd(resultSet.getInt("IdProduccion"));
                bloques.add(bloque);
            }
            return bloques;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
