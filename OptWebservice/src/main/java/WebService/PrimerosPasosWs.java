package WebService;

import dto.Cliente;
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
    @Path("pathAnabel")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> guardaCliente(List<Cliente> inputListaClientes)
    {
        List<Cliente> listaRetorno=new ArrayList<>();
        for(Cliente clienteIteracion: inputListaClientes)
        {
            String nombreMayusculas=client.convertirNombresMayuscula(clienteIteracion.getNombre());

            clienteIteracion.setNombre(nombreMayusculas);
            listaRetorno.add(clienteIteracion);
        }
        return listaRetorno;

    }
    @POST
    @Path("pathLuis")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> guardaCliente1(List<Cliente> inputListaClientes)
    {
        List<Cliente> listaRetorno=new ArrayList<>();
        for(Cliente clienteIteracion: inputListaClientes)
        {
            String nombreMinuscula=client.convertirNombresMinuscula(clienteIteracion.getNombre());

            clienteIteracion.setNombre(nombreMinuscula);
            listaRetorno.add(clienteIteracion);
        }
        return listaRetorno;

    }
}
