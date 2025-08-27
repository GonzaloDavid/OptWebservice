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
    @Path("guardarCliente")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Cliente> guardaCliente(List<Cliente> inputListaClientes)
    {
        List<Cliente> listaRetorno=new ArrayList<>();
        for(Cliente clienteIteracion: inputListaClientes)
        {
            String nombreMayusculas=client.convertirNombresMayuscula(clienteIteracion.getNombre());
            String codvalidaIdentificacion=client.validaIdentificacion(clienteIteracion.getIdentificacion());

            clienteIteracion.setNombre(nombreMayusculas);
            clienteIteracion.setCodRetornoIdentificacion(codvalidaIdentificacion);
            listaRetorno.add(clienteIteracion);
        }
        return listaRetorno;

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
