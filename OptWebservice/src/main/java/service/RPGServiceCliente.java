package service;

//import com.sun.org.apache.xml.internal.serializer.utils.MsgKey;

import rpg.beans.ELEERRMessage;
import rpg.beans.ESD008001Message;
import rpg.core.ESS0030DSMessage;
import rpg.core.JSEIBSServlet;
import rpg.core.MessageProcessor;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Stateless
public class RPGServiceCliente extends JSEIBSServlet {
    public String listadocliente(String user, HttpServletRequest req, String numidn )
    {
        boolean existRecord=false;
        String resultado ="";
        try{
            MessageProcessor mp = null;
            try {
                mp = getMessageProcessor("ESD0080", req);
               ESD008001Message msg = (ESD008001Message) mp.getMessageRecord("ESD008001");
                msg.setH01USR(user);
                msg.setH01PGM("ESD0080");
                msg.setH01TIM(getTimeStamp());
                msg.setH01OPE("0004");
                msg.setH01SCR("01");
                msg.setH01WK1("I");
                msg.setE01IDN(numidn);
               // msg.setH01TIMSYS(getTimeStamp());
               // msg.setH01OPECOD("0002");
               // msg.setE01DEAACC(new BigDecimal(numeroPrestamo));

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

                    msg = (ESD008001Message) mp.receiveMessageRecord();
                    existRecord=true;
                }
                resultado = msg.toString();
            } finally {
                if (mp != null)
                    mp.close();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    protected void processRequest(ESS0030DSMessage user, HttpServletRequest req, HttpServletResponse res, HttpSession session, int screen) throws ServletException, IOException {

    }
}
