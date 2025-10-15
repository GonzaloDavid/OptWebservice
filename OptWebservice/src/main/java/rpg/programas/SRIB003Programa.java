package rpg.programas;

import dto.RespuestaMensajejt;
import ec.com.bancointernacional.SRIB003.CLI_DATCLIEN;
import ec.com.bancointernacional.SRIB003.DS_INPDATCLI;
import ec.com.bancointernacional.SRIB003.INP_CABECERA;
import rpg.conexion.Util;

import java.math.BigDecimal;

public class SRIB003Programa {

    public ec.com.bancointernacional.SRIB003.INP_CABECERA setupHeader(String user) {
        ec.com.bancointernacional.SRIB003.INP_CABECERA header = new ec.com.bancointernacional.SRIB003.INP_CABECERA();
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
    public RespuestaMensajejt datoClienteProgramas(String user, BigDecimal bgNumpro, BigDecimal bgCodcli)
    {
        RespuestaMensajejt response=new RespuestaMensajejt();
        try{
            //Creamos el objeto cabecera
            INP_CABECERA header=setupHeader(user);

            //Creamos objeto input
            DS_INPDATCLI d=new DS_INPDATCLI();
            d.setCUSTNROPRO(bgNumpro);
            d.setCUSTCODCLI (bgCodcli);

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
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }


}
