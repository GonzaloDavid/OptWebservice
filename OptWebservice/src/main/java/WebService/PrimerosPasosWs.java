package WebService;

import dto.Cliente;
import dto.RespuestaMensaje;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import service.ClientService;

import java.util.ArrayList;
import java.util.List;


@Stateless
@Path("primerosPasos")
public class PrimerosPasosWs {
    @Inject
    private ClientService client;
    public PrimerosPasosWs() {
    }

    @GET
    @Path("getHellowWold")
    @Produces({ MediaType.APPLICATION_JSON})
    public String getHellowWold()
    {
        return "Hola Mundo";
    }

    @POST
    @Path("guardarCliente")
    @Produces({ MediaType.APPLICATION_JSON})
    public RespuestaMensaje guardaCliente(List<Cliente> inputListaClientes)
    {
        RespuestaMensaje respuesta=new RespuestaMensaje();
        List<Cliente> listaRetorno=new ArrayList<>();
        try{

            for(Cliente clienteIteracion: inputListaClientes)

            {

                String nombreMayusculas=client.convertirNombresMayuscula(clienteIteracion.getNombre());
                String codvalidaIdentificacion=client.validaIdentificacion(clienteIteracion.getIdentificacion());
                String tipoiden=client.VerifiIden(clienteIteracion.getTipoIdentificacion());
                String edad=client.validarEdad(clienteIteracion.getFechaNacimiento());
                String nombre=client.validarNombre(clienteIteracion.getNombre());
                clienteIteracion.setCodEdad(edad);
                clienteIteracion.setCodNombre(nombre);
                clienteIteracion.setNombre(nombreMayusculas);
                clienteIteracion.setCodRetornoIdentificacion(codvalidaIdentificacion);
                clienteIteracion.setCodtipoIdentificacion(tipoiden);
                listaRetorno.add(clienteIteracion);
            }
            respuesta.setCodigo("00");
            respuesta.setMensaje("Transacción exitosa");
            respuesta.setDatos(listaRetorno);
        }
        catch (NumberFormatException error_num)
        {
            System.out.println("Ocurrio un error de formato: " + error_num.getMessage());
            respuesta.setCodigo("02");
            respuesta.setMensaje("Ocurrio un error de formato: " + error_num.getMessage());
        }
        catch (RuntimeException error_run)
        {
            System.out.println("Ocurrio un error de formato: " + error_run.getMessage());
            respuesta.setCodigo("03");
            respuesta.setMensaje("Ocurrio un error de formato: " + error_run.getMessage());
        }
        catch (Exception error){

            Throwable cause = error.getCause();
            if (cause instanceof NumberFormatException) {
                System.out.println("Se detectó un NumberFormatException: " + cause.getMessage());
            } else {
                System.out.println("Se detectó otra excepción: " + error.getMessage());
            }
            System.out.println("Ocurrio un error: " + error.getMessage());
            respuesta.setCodigo("01");
            respuesta.setMensaje("Ocurrio un error: " + error.getMessage());
        }
        return respuesta;
    }
    @POST
    @Path("pathLuis")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> guardaCliente2(List<Cliente> inputListaClientes)
    {
        List<Cliente> listaRetorno=new ArrayList<>();
        for(Cliente clienteIteracion: inputListaClientes)
        {
            String nombreMinusculas=client.convertirNombresMinuscula(clienteIteracion.getNombre());

            clienteIteracion.setNombre(nombreMinusculas);
            listaRetorno.add(clienteIteracion);
        }
        return listaRetorno;

    }


}
