package ventatiquetes.jdbc;

import ventatiquetes.daos.ProduccionesDAO;
import ventatiquetes.models.Bloque;
import ventatiquetes.models.Produccion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProduccionesJDBC implements ProduccionesDAO {
    private Connection connection;
    @Override
    public void setConnection(Connection connection) {this.connection=connection; }

    @Override
    public ArrayList<String> getTipos() {
        try {
            ArrayList<String> tipos = new ArrayList<String>();
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetProduccionesTipos");
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean userFound = resultSet.next();

            while(userFound)
            {
                Integer codigo = resultSet.getInt("Id");
                String nombre = resultSet.getString("Nombre");
                tipos.add(codigo.toString() +"-"+nombre);
                userFound=resultSet.next();
            }
            return tipos;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void AddProd(Produccion produccion) {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC  CreateProduccion ? ,?, ?, ?, ?, ?, ?");
            preparedStatement.setString(1,produccion.getNombre());
            preparedStatement.setString(2,produccion.getDescripcion());
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
            java.sql.Date dateI = new java.sql.Date(produccion.getFechaI().getTime());
            preparedStatement.setDate(3,dateI);
            java.sql.Date dateF = new java.sql.Date(produccion.getFechaF().getTime());
            preparedStatement.setDate(4,dateF);
            preparedStatement.setInt(5,produccion.getIdEstado());
            preparedStatement.setInt(6,produccion.getIdTeatro());
            preparedStatement.setInt(7,produccion.getIdTipo());
            preparedStatement.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Produccion> getProducciones() {

        try
        {
            ArrayList<Produccion> producciones = new ArrayList<Produccion>() ;
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC getProducciones");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Produccion produccion = new Produccion();
                produccion.setNombre(resultSet.getString("Nombre"));
                produccion.setId(resultSet.getInt("Id"));
                producciones.add(produccion);
            }
            return producciones;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean validarNombre(String nombre) {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC ValidateNameProducciones ?");
            preparedStatement.setString(1,nombre);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean prodFound = resultSet.next();
            if (!prodFound) {

                return false;
            }
            return true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Produccion getProdByName(String nombre) {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByNombreProduccion ? ");
            preparedStatement.setString(1,nombre);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean prodFound = resultSet.next();
            if(!prodFound)
            {
                return null;
            }
            Produccion produccion = new Produccion();
            produccion.setNombre(resultSet.getString("Nombre"));
            produccion.setId(resultSet.getInt("Id"));
            produccion.setIdTeatro(resultSet.getInt("IdTeatro"));
            produccion.setFechaI(resultSet.getDate("FechaInicial"));
            produccion.setFechaF(resultSet.getDate("FechaFinal"));
            produccion.setDescripcion(resultSet.getString("Descripcion"));
            produccion.setIdEstado(resultSet.getInt("IdEstado"));
            produccion.setIdTipo(resultSet.getInt("IdTipo"));
            return produccion;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> getEstados() {

        ArrayList<String> estados = new ArrayList<String>();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetProduccionEstados");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                estados.add(resultSet.getString("Nombre"));
            }

        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return estados;
    }

    @Override
    public String getEstadoById(int Id) {

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByIdProduccionEstados ?");
            preparedStatement.setInt(1,Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean found= resultSet.next();
            if(!found)
            {
                return null;
            }
            return resultSet.getString("Nombre");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateEstado(Produccion produccion) {
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC UpdateIdEstadoProduccion ? , ?");
            preparedStatement.setInt(1,produccion.getId());
            preparedStatement.setInt(2,produccion.getIdEstado());
            preparedStatement.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void AddBloquePrecio(ArrayList<Bloque> bloques,int idProd) {
        try {


            for (Bloque bloque : bloques) {

                PreparedStatement preparedStatement = connection.prepareStatement("EXEC CreateBloquePrecios ?,?,?");
                BigDecimal bd = new BigDecimal(bloque.getPrecio()).setScale(2, RoundingMode.HALF_UP);
                preparedStatement.setBigDecimal(1,bd);
                preparedStatement.setInt(2,bloque.getId());
                preparedStatement.setInt(3,idProd);
                preparedStatement.execute();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Produccion> getProdTIdView(int TeatroId) {
        try
        {
            ArrayList<Produccion> producciones = new ArrayList<Produccion>() ;
            PreparedStatement preparedStatement = connection.prepareStatement("EXEC GetByTeatroIdProduccionView ?");
            preparedStatement.setInt(1,TeatroId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                Produccion produccion = new Produccion();
                produccion.setNombre(resultSet.getString("Nombre"));
                produccion.setId(resultSet.getInt("ProduccionId"));
                produccion.setDescripcion(resultSet.getString("Descripcion"));
                produccion.setFechaI(resultSet.getDate("FechaInicial"));
                produccion.setFechaF(resultSet.getDate("FechaFinal"));
                produccion.setTipo(resultSet.getString("tipoProd"));
                producciones.add(produccion);
            }
            return producciones;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
