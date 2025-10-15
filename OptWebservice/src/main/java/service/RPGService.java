package service;

import rpg.beans.DPR000101Message;
import rpg.beans.EDL094001Message;
import rpg.beans.ELEERRMessage;
import rpg.core.ESS0030DSMessage;
import rpg.core.JSEIBSServlet;
import rpg.core.MessageProcessor;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@Stateless
public class RPGService extends JSEIBSServlet {
    public boolean actualizaFechaPrestamo(String user, HttpServletRequest req, String numeroPrestamo)
    {
        boolean existRecord=false;
        try{
            MessageProcessor mp = null;
            try {
                mp = getMessageProcessor("EDL0940", req);
                EDL094001Message msg = (EDL094001Message) mp.getMessageRecord("DPR000101");
                msg.setH01USERID(user);
                msg.setH01PROGRM("EDL0940");
                msg.setH01TIMSYS(getTimeStamp());
                msg.setH01OPECOD("0002");
                msg.setE01DEAACC(new BigDecimal(numeroPrestamo));

                mp.sendMessage(msg);
                ELEERRMessage msgError = (ELEERRMessage) mp.receiveMessageRecord("ELEERR");

                if (mp.hasError(msgError)) {

                    System.out.println("ERROR RPG : " + msg.toString());
                    if(msgError.getERRNUM().equals("9933"))
                    {
                        //No hay informacion
                        System.out.println("No existe informacion  : " + msg.toString());

                    }
                } else {

                    msg = (EDL094001Message) mp.receiveMessageRecord();
                    existRecord=true;
                }
            } finally {
                if (mp != null)
                    mp.close();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return existRecord;
    }

    @Override
    protected void processRequest(ESS0030DSMessage user, HttpServletRequest req, HttpServletResponse res, HttpSession session, int screen) throws ServletException, IOException {

    }
}
