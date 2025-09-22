package WebService;

import dao.EstablecimientoDAO;
import dto.Cliente;
import dto.RespuestaMensaje;
import entidad.Establecimiento;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import service.ClientService;

import java.util.ArrayList;
import java.util.List;


@Stateless
@Path("primerosPasos")
public class PrimerosPasosWs {
    @Inject
    private ClientService client;
    @Inject
    private EstablecimientoDAO establecimientoDAO;
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
            listaRetorno =client.procesoGuardaCliente(inputListaClientes);
            respuesta.setCodigo("00");
            respuesta.setMensaje("Transacción exitosa");
            respuesta.setDatos(listaRetorno);
            
        }catch (Exception error){

            Throwable cause = error.getCause();
            if (cause instanceof NumberFormatException) {
                respuesta.setCodigo("02");
                System.out.println("Se detectó un NumberFormatException: " + cause.getMessage());
                for (Cliente c:inputListaClientes){
                    c.setFechaNacimiento("1969/08/12");
                }
                listaRetorno =client.procesoGuardaCliente(inputListaClientes);
                respuesta.setDatos(listaRetorno);
            } else {
                respuesta.setCodigo("01");
                System.out.println("Se detectó otra excepción: " + error.getMessage());
            }
            System.out.println("Ocurrio un error: " + error.getMessage());

            respuesta.setMensaje("Ocurrio un error: " + error.getMessage());
        }
        return respuesta;
    }

    @GET
    @Path("obtenerCliente")
    @Produces({ MediaType.APPLICATION_JSON})
    public Establecimiento obtenerCliente(
            @QueryParam("codigo") String codigo)
    {
        return establecimientoDAO.obtenerPorCodigo(codigo);
    }

    @DELETE
    // @GET
    @Path("borrarEstablecimiento")
    @Produces({ MediaType.APPLICATION_JSON})
    public Integer borrarEstablecimiento(
            @QueryParam("codigo") String codigo)
    {
        return establecimientoDAO.borraEstablecimiento(codigo);
    }

    @POST
    @Path("insertEstablecimiento")
    @Produces({ MediaType.APPLICATION_JSON})
    public Establecimiento inserEstablecimiento(Establecimiento estab)
    {
        establecimientoDAO.insertEstablecimiento(estab);
        return estab;
    }
    @PUT
    @Path("updateEstablecimiento")
    @Produces({ MediaType.APPLICATION_JSON})
    public Establecimiento updateEstablecimiento(Establecimiento estab)
    {
        establecimientoDAO.updateEstablecimiento(estab);
        return estab;
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
