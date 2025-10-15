package WebService;

import dto.RespuestaClienteBco;
import dto.RespuestaProspecto;
import rpg.programas.SRIB003Pgm;
import rpg.programas.SRIB004Pgm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Stateless
@Path("ClienteBcoWs")

public class ClienteBcoWS {
    @Inject
    private SRIB003Pgm programasrib003Pgm;

    public ClienteBcoWS() {
    }

    @GET
    @Path("obtenerClienteBco")
    @Produces({ MediaType.APPLICATION_JSON})

    public RespuestaClienteBco obtenerclienteBco(
            @QueryParam("codpro") BigDecimal codpro,
            @QueryParam("codcli") BigDecimal codcli)
    {
        return programasrib003Pgm.ejecutarPrograma(codcli, codpro,  "BIQALLTR");
    }
}
