package util;

import javax.ejb.Singleton;

@Singleton
public class MessageService {

    private static final String IDENTIFICACION_VALIDA="Identificación válida";
    private static final String IDENTIFICACION_NO_VALIDA="Identificación NO válida";
    private static final String TIPO_IDENTIFICACION_CEDULA="Tipo Identificación Cedula";
    private static final String TIPO_IDENTIFICACION_RUC ="Tipo Identificación Ruc";
    private static final String TIPO_IDENTIFICACION_PASAPORTE ="Tipo Identificación Passaporte";
    private static final String TIPO_IDENTIFICACION_NO_VALIDO ="Tipo de identificación no válido";
    private static final String EDAD_VALIDA="Edad válida";
    private static final String EDAD_NO_VALIDA="Edad NO válida";
    private static final String EDAD_MENOR="Es menor de edad";
    private static final String EDAD_MAYOR="Es mayor de edad";
    private static final String NOMBRE_MAYUSCULA="Nombre en mayúscula";
    private static final String NOMBRE_MINUSCULA="Nombre en minúscula";
    private static final String NOMBRE_NO_VALIDO="Nombre no válido";
    private static final String NOMBRE_VACIO="Nombre vacío";
    private static final String NOMBRE_VALIDO="Nombre válido";

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
            case "07":
                return EDAD_VALIDA;
            case "08":
                return EDAD_NO_VALIDA;
            case "09":
                return EDAD_MENOR;
            case "10":
                return  NOMBRE_VACIO;
            case "11":
                return NOMBRE_NO_VALIDO;
            case "12":
                return NOMBRE_VALIDO;
            default:
                return "Código de mensaje no reconocido";
        }

    }
}
