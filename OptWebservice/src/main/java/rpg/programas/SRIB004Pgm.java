package rpg.programas;

import dto.RespuestaMensajejt;
import ec.com.bancointernacional.BSIM001.DS_INPSTRBUS;
import ec.com.bancointernacional.BSIM001.PRO_STRINGBUSQ;
import ec.com.bancointernacional.SRIB004.CLI_DATPROS;
import ec.com.bancointernacional.SRIB004.DS_INPDATCLI;
import ec.com.bancointernacional.SRIB004.INP_CABECERA;
import rpg.conexion.Util;

import javax.ejb.Stateless;
import java.math.BigDecimal;

@Stateless
public class SRIB004Pgm {
    public INP_CABECERA setupHeader(String user) {
        INP_CABECERA header = new INP_CABECERA();
        header.setIPADDRESS("");
        header.setCHANNEL("OPT");
        header.setCUSTOMERID("1");
        header.setADITIONALCUSID("");
        header.setSEQUENTIAL(new BigDecimal(1));
        header.setDATEANDTIME("");
        header.setUSERID(user);
        header.setPRODUCTCODE("1");
        header.setSERVICECODE("20004");
        header.setTRANSERVICECODE("2066");
        header.setGROUPID("");
        header.setBANKID("01");
        return header;
    }
    public RespuestaMensajejt ejecutarPrograma(BigDecimal numpro, BigDecimal codcli, String user)
    {
        RespuestaMensajejt response=new RespuestaMensajejt();
        try{
            //Creamos el objeto cabecera

            ec.com.bancointernacional.SRIB004.INP_CABECERA header=setupHeader(user);

            //Creamos objeto input
            DS_INPDATCLI d=new DS_INPDATCLI();
            d.setCUSTNROPRO(numpro);
            d.setCUSTCODCLI(codcli);

            CLI_DATPROS programAS400 = new CLI_DATPROS();
            programAS400.setIN_CABEC(header);
            programAS400.setIN_DATCLI(d);
            Util.invokeTrx(programAS400);

            if (programAS400.getReturnValue() != 0 || "".equals(programAS400.getOU_CABEC().getMESSAGEDESCR().trim())) {

                System.out.println("Resultado programa as400"+ programAS400.getOU_CABEC().getMESSAGEDESCR());
                response.setCodigo("00");
                response.setMensaje(programAS400.getOU_CABEC().getMESSAGEDESCR());

            } else {

                response.setCodigo("500");
                System.out.println("Resultado con error "+ programAS400.getOU_CABEC().getMESSAGEDESCR());
                response.setMensaje(programAS400.getOU_CABEC().getMESSAGEDESCR());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }



}
