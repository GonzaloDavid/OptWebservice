package rpg.programas;

import dto.RespuestaMensajejt;

import ec.com.bancointernacional.SRIB004.INP_CABECERA;
import ec.com.bancointernacional.SRIB004.CLI_DATPROS;
import ec.com.bancointernacional.SRIB004.DS_INPDATCLI;
import rpg.conexion.Util;

import javax.ejb.Stateless;
import java.math.BigDecimal;
@Stateless
public class SRIB004Programa {

    public ec.com.bancointernacional.SRIB004.INP_CABECERA setupHeader(String user) {
        ec.com.bancointernacional.SRIB004.INP_CABECERA header = new ec.com.bancointernacional.SRIB004.INP_CABECERA();
        header.setADITIONALCUSID("");
        header.setBANKID("01");
        header.setCHANNEL("OPT");
        header.setCUSTOMERID("1");
        header.setGROUPID("G9");
        header.setIPADDRESS("");
        header.setPRODUCTCODE("1");
        header.setDATEANDTIME("2025-10-06 10:04:10.581");
        header.setSEQUENTIAL(new BigDecimal(2410415));
        header.setUSERID(user);
        header.setSERVICECODE("20004");
        header.setTRANSERVICECODE("2066");
        return header;
    }
    public RespuestaMensajejt datoProspectoProgramas(String user, BigDecimal bgNumpro, BigDecimal bgCodcli)
    {
        RespuestaMensajejt response=new RespuestaMensajejt();
        try{
            //Creamos el objeto cabecera
            INP_CABECERA header=setupHeader(user);

            //Creamos objeto input
            DS_INPDATCLI d=new DS_INPDATCLI();
            d.setCUSTNROPRO(bgNumpro);
            d.setCUSTCODCLI (bgCodcli);

            CLI_DATPROS programAS400 = new CLI_DATPROS();
            programAS400.setIN_CABEC(header);
            programAS400.setIN_DATCLI(d);
            Util.invokeTrx(programAS400);

            if (programAS400.getReturnValue() != 0 || "".equals(programAS400.getOU_CABEC().getMESSAGEDESCR().trim())) {
                response.setCodigo("500");
                System.out.println("Resultado con error "+ programAS400.getOU_CABEC().getMESSAGEDESCR());
                response.setMensaje(programAS400.getOU_CABEC().getMESSAGEDESCR());

            } else {

                System.out.println("Resultado programa as400"+ programAS400.getOU_CABEC().getMESSAGEDESCR());
                response.setCodigo("00");
                response.setMensaje(programAS400.getOU_CABEC().getMESSAGEDESCR());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }
}


