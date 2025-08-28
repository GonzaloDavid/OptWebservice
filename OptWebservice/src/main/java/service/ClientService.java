package service;

import jakarta.inject.Inject;

public class ClientService {
    public String convertirNombresMayuscula(String nombre)
    {
        String nombreMayuscula = nombre.toUpperCase();
        return nombreMayuscula;
    }
    public String convertirNombresMinuscula(String nombre)
    {
        String nombreMinuscula = nombre.toLowerCase();
        return nombreMinuscula;
    }
    public String validaIdentificacion(String identificacion)
    {  int lenIdentificacion=identificacion.length();
        if (lenIdentificacion < 10 ) {
            return "Identificación inválida: " + identificacion + " debe tener 10 caracteres";
        }
        return "Identificación válida";
    }

    public String VerifiIden(String tipoIdentificacion)
    {
        switch (
            tipoIdentificacion) {
            case "C":
                return "Tipo Identificación Cedula" ;
            case "R":
                return "Tipo Identificación Ruc" ;
            case "P":
                return "Tipo Identificación Passaporte " ;
            default:
                return "Tipo de identificación no válido";
        }
        }

}
