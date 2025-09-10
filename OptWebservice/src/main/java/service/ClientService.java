package service;

import dto.Cliente;
import dto.ValidaredadDto;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import util.MessageService;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
public ValidaredadDto validarEdad(String fechaNacimiento)
{   ValidaredadDto valedad = new ValidaredadDto();

    try{
        if (fechaNacimiento.length()<10)
        {
            valedad.setErroformat(messageService.obtenerMensaje("08"));
            valedad.setExisteerror(false);
            return valedad;
        }
        int yearOfBirth=Integer.parseInt(fechaNacimiento.substring(0,4));
        int currentYear=2025;
        int age=currentYear-yearOfBirth;
        if (age<18 )
        {
            valedad.setErroformat(messageService.obtenerMensaje("09"));
            valedad.setExisteerror(false);
            return valedad;
        }
        valedad.setErroformat(messageService.obtenerMensaje("07"));
        valedad.setExisteerror(false);
        return valedad;

    }catch (NumberFormatException e)
    {
        valedad.setErroformat("Error de formato edad");
        valedad.setExisteerror(true);
        System.out.println("Error de Numeros: " + e.getMessage());

    }catch (Exception e1)
    {
        System.out.println("Error generico: " + e1.getMessage());
        throw e1;
    }
    return valedad;
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
public List<Cliente> procesoGuardaCliente(List<Cliente> inputListaClientes){

    List<Cliente> listaRetorno=new ArrayList<>();
    for(Cliente clienteIteracion: inputListaClientes)
    {

        String nombreMayusculas=convertirNombresMayuscula(clienteIteracion.getNombre());
        String codvalidaIdentificacion=validaIdentificacion(clienteIteracion.getIdentificacion());
        String tipoiden=VerifiIden(clienteIteracion.getTipoIdentificacion());
        ValidaredadDto codvalidacion =validarEdad(clienteIteracion.getFechaNacimiento());
        if (codvalidacion.isExisteerror()){

            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            clienteIteracion.setFechaNacimiento(fechaActual.format(formatter));
        }
        String nombre=validarNombre(clienteIteracion.getNombre());
        clienteIteracion.setCodEdad(codvalidacion.getErroformat());
        clienteIteracion.setCodNombre(nombre);
        clienteIteracion.setNombre(nombreMayusculas);
        clienteIteracion.setCodRetornoIdentificacion(codvalidaIdentificacion);
        clienteIteracion.setCodtipoIdentificacion(tipoiden);
        listaRetorno.add(clienteIteracion);
    }
 return listaRetorno;
}
}
