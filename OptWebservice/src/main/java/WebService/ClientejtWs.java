package WebService;


import dao.ClientejtDAO;
import dto.Cliente;
import entidad.Clientejt;
import service.ClientServicejt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static util.MessageService.SALDO_VALIDO;

@Stateless
@Path("ClientejtWs")

public class ClientejtWs {
    @Inject
    private ClientejtDAO clientejtDAO;
    @Inject
    private ClientServicejt client;

        public ClientejtWs() {
    }

   // @GET
   // @Path("obtenerCliente")
   // @Produces({ MediaType.APPLICATION_JSON})
   // public Clientejt obtenerCliente(
   //         @QueryParam("codigo") String codigo)
  //  {
  //      return clientejtDAO.obtenerPorCodigo(codigo);
//
 //    }

    @GET
    @Path("listaClientes")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Clientejt> listaclientes()
    {
        List<Clientejt> listaRetorno=clientejtDAO.obtenerClientes();
        List<Clientejt> listaRetornofinal=new ArrayList<>();

        for(Clientejt clientejtIteracion: listaRetorno)
        {
           if ( client.validaSaldo(clientejtIteracion.getSaldo()).equals(SALDO_VALIDO) ){

               listaRetornofinal.add(clientejtIteracion);
           } else {
                System.out.println("Cliente no agregado por saldo insuficiente: " + clientejtIteracion.getNombre());
           }

        }
        return listaRetornofinal;

    }
}
