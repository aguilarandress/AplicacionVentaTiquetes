package ventatiquetes.models;

import java.util.ArrayList;

public class Teatro {
    private int id;
    private int capacidad;
    private String nombre;
    private String correo;
    private String sitioWeb;
    private String telefonoAdministracion;
    private String telefonoBoleteria;
    private ArrayList<Bloque> bloques;

    public Teatro() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setTelefonoAdministracion(String telefonoAdministracion) {
        this.telefonoAdministracion = telefonoAdministracion;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setTelefonoBoleteria(String telefonoBoleteria) {
        this.telefonoBoleteria = telefonoBoleteria;
    }

    public int getId() {
        return id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public String getTelefonoAdministracion() {
        return telefonoAdministracion;
    }

    public String getTelefonoBoleteria() {
        return telefonoBoleteria;
    }

    @Override
    public String toString() {
        return this.getNombre();
    }
}
