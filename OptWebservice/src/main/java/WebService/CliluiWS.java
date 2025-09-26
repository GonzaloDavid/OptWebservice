package WebService;


import entidad.Clilui;
import service.CliluiService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Stateless
@Path("CliluiWS")

public class CliluiWS {
@Inject private dao.CliluiDAO clienteDAO;
@Inject private CliluiService cliluiService;

    @GET
    @Path("obtenerClilui")
    @Produces({ MediaType.APPLICATION_JSON})
    public Clilui obtenerClilui(
            @QueryParam("codigo") Integer codigo)
    {
        return clienteDAO.obtenerClilui(codigo);
    }

    @DELETE
    // @GET
    @Path("borrarClilui")
    @Produces({ MediaType.APPLICATION_JSON})
    public Integer borrarClilui(
            @QueryParam("codigo") String codigo)
    {
        return clienteDAO.borraClilui(Integer.valueOf(codigo));
    }

    @POST
    @Path("insertClilui")
    @Produces({ MediaType.APPLICATION_JSON})
    public Clilui inserClilui  (Clilui clilui)
    {
        clienteDAO.insertClilui(clilui);
        return clilui;
    }

    @PUT
    @Path("updateClilui")
    @Produces({ MediaType.APPLICATION_JSON})
    public Clilui updateClilui(Clilui clilui)
    {
        clienteDAO.updateClilui(clilui);
        return clilui;
    }

    @GET
    @Path("validaSaldosClilui")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<String> validaSaldosClilui() {
        List<Clilui> listaClilui = clienteDAO.obtenerTodos();
        List<String> resultados = new ArrayList<>();
        for (Clilui clilui : listaClilui) {
            String resultado = cliluiService.validaSaldo(clilui.getSaldo());
            resultados.add("Cliente: " + clilui.getNombre() + " " +clilui.getApellido() + ", Resultado: " + resultado);
        }
        return resultados;
    }



}
