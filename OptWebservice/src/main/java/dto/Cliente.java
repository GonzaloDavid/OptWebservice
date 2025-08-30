package dto;

public class Cliente {
    private String nombre;
    private String identificacion;
    private String tipoIdentificacion;
    private String fechaNacimiento;

    private String codRetornoIdentificacion;
    private String codtipoIdentificacion;
private String codEdad;
private String codNombre;

    public Cliente() {
    }

    public String getCodtipoIdentificacion() {
        return codtipoIdentificacion;
    }

    public void setCodtipoIdentificacion(String codtipoIdentificacion) {
        this.codtipoIdentificacion = codtipoIdentificacion;
    }

    public String getCodRetornoIdentificacion() {
        return codRetornoIdentificacion;
    }

    public void setCodRetornoIdentificacion(String codRetornoIdentificacion) {
        this.codRetornoIdentificacion = codRetornoIdentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCodEdad() {
        return codEdad;
    }

    public String getCodNombre() {
        return codNombre;
    }

    public void setCodNombre(String codNombre) {
        this.codNombre = codNombre;
    }

    public void setCodEdad(String codEdad) {
        this.codEdad = codEdad;
    }
}
