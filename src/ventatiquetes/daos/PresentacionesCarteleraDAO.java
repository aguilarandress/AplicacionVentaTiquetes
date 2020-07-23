package ventatiquetes.daos;

import ventatiquetes.models.Bloque;
import ventatiquetes.models.PresentacionCartelera;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

public interface PresentacionesCarteleraDAO {
    public void setConnection(Connection connection);
    public ArrayList<PresentacionCartelera> getPresentacionesCarteleraByFechas(Date fechaInicial, Date fechaFinal);
    public ArrayList<Bloque> getBloquePreciosByProduccionId(int produccionId);
}
