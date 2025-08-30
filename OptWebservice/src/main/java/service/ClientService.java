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
public String validarEdad(String fechaNacimiento)
{   if (fechaNacimiento.length()<10)
    {
        return messageService.obtenerMensaje("08");
    }
    int yearOfBirth=Integer.parseInt(fechaNacimiento.substring(0,4));
    int currentYear=2025;
    int age=currentYear-yearOfBirth;
    if (age<18 )
    {
        return messageService.obtenerMensaje("09");
    }
    return messageService.obtenerMensaje("07");

}
public String validarNombre(String nombre)
{
    if (nombre==null || nombre.isEmpty())
    {
        return messageService.obtenerMensaje("10");
    }
    if (nombre.length()<3)
    {
        return messageService.obtenerMensaje("11");
    }
    return messageService.obtenerMensaje("12");
}
}
