package ventatiquetes.models;

import java.util.Date;

public class Produccion {
    private int Id;
    private String descripcion;
    private Date fechaI;
    private Date fechaF;
    private String Nombre;
    private int IdEstado;
    private int IdTeatro;
    private int IdTipo;
    private String Tipo;



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int idEstado) {
        IdEstado = idEstado;
    }

    public int getIdTeatro() {
        return IdTeatro;
    }

    public void setIdTeatro(int idTeatro) {
        IdTeatro = idTeatro;
    }

    public int getIdTipo() {
        return IdTipo;
    }

    public void setIdTipo(int idTipo) {
        IdTipo = idTipo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
    @Override
    public String toString() {
        return this.getNombre();
    }
}
