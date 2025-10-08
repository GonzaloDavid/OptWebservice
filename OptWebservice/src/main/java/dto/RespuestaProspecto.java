package dto;

import ec.com.bancointernacional.SRIB004.DS_RESDATCLI;
import entidad.Clientejt;

import java.util.ArrayList;
import java.util.List;

public class RespuestaProspecto {
    private String codigo;
    private String mensaje;
    private DS_RESDATCLI datos=new DS_RESDATCLI();

    public RespuestaProspecto() {

    }

    public RespuestaProspecto(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje= mensaje;

    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }



    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
