package dto;

import ec.com.bancointernacional.SRIB003.DS_RESDATCLI;

import java.util.ArrayList;
import java.util.List;

public class RespuestaCliente {

    private String codigo;
    private String mensaje;
    private DS_RESDATCLI datos=new DS_RESDATCLI();


    public RespuestaCliente() {

    }

    public RespuestaCliente(String codigo, String mensaje) {
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

    public DS_RESDATCLI getDatos() {
        return datos;
    }

    public void setDatos(DS_RESDATCLI datos) {
        this.datos = datos;
    }
}
