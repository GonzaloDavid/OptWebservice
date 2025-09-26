package dto;

import java.util.ArrayList;
import java.util.List;

public class RespuestaMensaje {
    private String codigo;
    private String mensaje;
    private List<Cliente> datos=new ArrayList<>();


    public RespuestaMensaje() {
    }

    public RespuestaMensaje(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Cliente> getDatos() {
        return datos;
    }

    public void setDatos(List<Cliente> datos) {
        this.datos = datos;
    }
}
