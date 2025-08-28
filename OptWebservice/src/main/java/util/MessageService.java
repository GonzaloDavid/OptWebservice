package util;

import jakarta.ejb.Singleton;

@Singleton
public class MessageService {

    private static final String IDENTIFICACION_VALIDA="Identificación válida";
    private static final String IDENTIFICACION_NO_VALIDA="Identificación NO válida";
    private static final String TIPO_IDENTIFICACION_CEDULA="Tipo Identificación Cedula";
    private static final String TIPO_IDENTIFICACION_RUC ="Tipo Identificación Ruc";
    private static final String TIPO_IDENTIFICACION_PASAPORTE ="Tipo Identificación Passaporte";
    private static final String TIPO_IDENTIFICACION_NO_VALIDO ="Tipo de identificación no válido";

    public MessageService() {
    }
    public String obtenerMensaje(String codMensaje)
    {
        switch (codMensaje) {
            case "01":
                return IDENTIFICACION_VALIDA;
            case "02":
                return IDENTIFICACION_NO_VALIDA;
            case "03":
                return TIPO_IDENTIFICACION_CEDULA;
            case "04":
                return TIPO_IDENTIFICACION_RUC;
            case "05":
                return TIPO_IDENTIFICACION_PASAPORTE;
            case "06":
                return TIPO_IDENTIFICACION_NO_VALIDO;
            default:
                return "Código de mensaje no reconocido";
        }

    }
}
