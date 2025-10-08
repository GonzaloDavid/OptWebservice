package dto;

import ec.com.bancointernacional.SRIB004.DS_RESDATCLI;
import entidad.Clientejt;

import java.util.ArrayList;
import java.util.List;

public class RespuestaMensajejt {
    private String codigo;
    private String mensaje;
    private List<Clientejt> datos=new ArrayList<>();


    public RespuestaMensajejt() {
    }

    public RespuestaMensajejt(String codigo, String mensaje) {
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

    public List<Clientejt> getDatos() {
        return datos;
    }

    public void setDatos(List<Clientejt> datos) {
        this.datos = datos;
    }
}
