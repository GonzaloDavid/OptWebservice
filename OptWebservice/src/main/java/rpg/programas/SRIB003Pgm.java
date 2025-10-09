package rpg.programas;

import dto.RespuestaClienteBco  ;
import ec.com.bancointernacional.SRIB003.CLI_DATCLIEN;

import ec.com.bancointernacional.SRIB003.DS_INPDATCLI;
import ec.com.bancointernacional.SRIB003.INP_CABECERA;
import rpg.conexion.Util;

import java.math.BigDecimal;

public class SRIB003Pgm {
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

    public RespuestaClienteBco ejecutarPrograma(BigDecimal codcli, BigDecimal numpro, String user)
    {
        RespuestaClienteBco response=new RespuestaClienteBco();
        try{
            //Creamos el objeto cabecera

            ec.com.bancointernacional.SRIB003.INP_CABECERA header=setupHeader(user);

            //Creamos objeto input
            DS_INPDATCLI d=new DS_INPDATCLI();
            d.setCUSTCODCLI(codcli);
            d.setCUSTNROPRO(numpro);

            CLI_DATCLIEN programAS400 = new CLI_DATCLIEN();
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
                response.setDatos(programAS400.getOU_RESDATCLI());

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }



}
