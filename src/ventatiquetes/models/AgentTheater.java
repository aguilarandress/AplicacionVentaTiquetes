package ventatiquetes.models;

import java.util.Date;

public class AgentTheater {
    private int cedula;
    private String nombre;
    private Date fechaNacimiento;
    private char sexo;
    private int idTeatro;
    private String direccion;
    private String telefonoCasa;
    private String telefonoCelular;
    private String telefonoOtro;
    private String email;
    private String username;
    private String password;

    public AgentTheater() { }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public char getSexo() {
        return sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefonoCasa() {
        return telefonoCasa;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public String getTelefonoOtro() {
        return telefonoOtro;
    }

    public String getEmail() {
        return email;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public void setTelefonoOtro(String telefonoOtro) {
        this.telefonoOtro = telefonoOtro;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdTeatro() { return idTeatro; }

    public void setIdTeatro(int idTeatro) { this.idTeatro = idTeatro; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
