package rpg.programas;

import dto.RespuestaMensajejt;
import ec.com.bancointernacional.BSIM001.DS_INPSTRBUS;
import ec.com.bancointernacional.BSIM001.INP_CABECERA;
import ec.com.bancointernacional.BSIM001.PRO_STRINGBUSQ;
import org.apache.logging.log4j.Level;
import rpg.conexion.Util;

import javax.ejb.Stateless;
import java.math.BigDecimal;

@Stateless
public class BSIM001Programa {

    public INP_CABECERA setupHeader(String user) {
        INP_CABECERA header = new INP_CABECERA();
        header.setADITIONALCUSID("");
        header.setBANKID("01");
        header.setCHANNEL("OPT");
        header.setCUSTOMERID("1");
        header.setGROUPID("");
        header.setIPADDRESS("");
        header.setPRODUCTCODE("1");
        header.setDATEANDTIME("");
        header.setSEQUENTIAL(new BigDecimal(1));
        header.setUSERID(user);
        header.setSERVICECODE("20004");
        header.setTRANSERVICECODE("2066");
        return header;
    }
    public RespuestaMensajejt ejecutarPrograma(String strBusqueda, String user)
    {
        RespuestaMensajejt response=new RespuestaMensajejt();
        try{
            //Creamos el objeto cabecera
            INP_CABECERA header=setupHeader(user);

            //Creamos objeto input
            DS_INPSTRBUS d=new DS_INPSTRBUS();
            d.setSTRIBUSQUE(strBusqueda);

            PRO_STRINGBUSQ programAS400 = new PRO_STRINGBUSQ();
            programAS400.setIN_CABEC(header);
            programAS400.setIN_STRBUS(d);
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
