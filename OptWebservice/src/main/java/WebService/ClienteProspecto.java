package WebService;

import dto.RespuestaMensajejt;
import dto.RespuestaProspecto;
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
@Path("ClienteProspecto")

public class ClienteProspecto {

    @Inject
    private  SRIB004Pgm programasrib004Pgm;
    public ClienteProspecto() {
    }

    @GET
    @Path("obtenerProspecto")
    @Produces({ MediaType.APPLICATION_JSON})
    public RespuestaProspecto obtenerProspecto(
            @QueryParam("codpro") BigDecimal codpro,
            @QueryParam("codcli") BigDecimal codcli)
    {
        return programasrib004Pgm.ejecutarPrograma(codpro, codcli,  "BIQALLTR");
    }
}
