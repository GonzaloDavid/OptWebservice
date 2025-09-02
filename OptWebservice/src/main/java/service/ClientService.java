package service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import util.MessageService;

@Stateless
public class ClientService {

    @Inject
    private MessageService messageService;
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
                return messageService.obtenerMensaje("03") ;
            case "R":
                return messageService.obtenerMensaje("04") ;
            case "P":
                return messageService.obtenerMensaje("05") ;
            default:
                return messageService.obtenerMensaje("06") ;
        }
        }
    public String validaEdad(String fechaNacimiento)
    {
        if (fechaNacimiento.length() != 10 ) {
            return messageService.obtenerMensaje("08");
        }
        else {
            String anioNacimientoStr = fechaNacimiento.substring(0, 4);
            int anioNacimiento = Integer.parseInt(anioNacimientoStr);
            int anioActual = java.time.Year.now().getValue();
            int edad = anioActual - anioNacimiento;
            if (edad < 18) {
                return messageService.obtenerMensaje("06");
            } else {
                return messageService.obtenerMensaje("07");
            }
        }
    }
    public String valNombre(String nombre)
    {
        int lenNombre=nombre.length();
        if (lenNombre < 3 ) {
            return messageService.obtenerMensaje("10");
        }
        return messageService.obtenerMensaje("11");
    }

}
