package rpg.core;
/*
 * Created on Feb 28, 2007
 * Created by Saif Mazhar
 */



import rpg.beans.ELEERRMessage;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public abstract class JSEIBSServlet extends SuperServlet {

    private static final long serialVersionUID = 6572814041801466787L;

    public MessageProcessor getMessageProcessor(String targetProgram, HttpServletRequest request)
            throws IOException {
        try {
            return new MessageProcessor(
                    getMessageHandler(targetProgram, request));
        } catch (ServiceLocatorException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Gets the Language Path for pages.
     * @param msgUser
     * @return
     */
    protected String getLangPath(ESS0030DSMessage msgUser) {
        return SuperServlet.rootPath + (msgUser != null ? msgUser.getE01LAN().toLowerCase() : "s") + "/";
    }

    /**
     * Gets the Language Path for pages.
     * @param req
     * @return
     */
    protected String getLangPath(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            ESS0030DSMessage msgUser =
                    (ESS0030DSMessage) session.getAttribute("currUser");
            return getLangPath(msgUser);
        } else {
            return SuperServlet.rootPath + "s/";
        }
    }

    /**
     * Gets the Language Path for pages.
     * @param req
     * @return
     */
    protected String getLangPath(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = getSession(req, res);
        if (session != null) {
            ESS0030DSMessage msgUser =
                    (ESS0030DSMessage) session.getAttribute("currUser");
            return getLangPath(msgUser);
        } else {
            return SuperServlet.rootPath + "s/";
        }
    }

    public void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        ESS0030DSMessage msgUser = null;
        HttpSession session = null;

        session = getSession(req, res);
        if (session != null) {
            msgUser = (ESS0030DSMessage) session.getAttribute("currUser");
            if (msgUser == null) {
                //Session Expiration Control
                try {
                    res.setContentType("text/html");
                    printLogInAgain(res.getWriter());
                } catch (Exception e) {
                    flexLog("Exception ocurred. Exception = " + e);
                }
                return;
            }
            // Here we should get the path from the user profile
            try {
                int screen ;
                try {
                    screen = Integer.parseInt(req.getParameter("SCREEN"));
                } catch (Exception e) {
                    screen = 1;
                }
                //Set port if has been provided
                hasPort(req);
                //Set Previous Year if has been provided
                if (isPrevYear(req)) {
                    flexLog(this.getServletName() + " in Previous Year Mode...");
                }
                processRequest(msgUser, req, res, session, screen);
            } catch (Exception e) {
                e.printStackTrace();
                flexLog("Error: " + e, SYSTEM);
                forward(sckNotOpenPage, req, res);
            }
        }
    }

    /**
     * Forwards to the given page the request.
     * This method adds to the <code>forward</code> method that
     * will be removing the error object from the session so no error it's shown.
     * @param pageName
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    protected void forwardOnSuccess(String pageName,
                                    HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null)
            session.removeAttribute("error");
        if (!res.isCommitted()) {
            callPage(getLangPath(req, res) + pageName, req, res);
        }
    }

    /**
     * Forwards to the given page the request
     * @param pageName
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    protected void forward(String pageName,
                           HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        if (!res.isCommitted()) {
            callPage(getLangPath(req, res) + pageName, req, res);
        }
    }

    /**
     * A servlet must implement this method in order to process a request.
     * The JSEIBSServlet will be reading from the request
     * and providing to this method the current arguments.
     * The default provided screen has the value 1.
     * @param user
     * @param req
     * @param res
     * @param session
     * @param screen
     * @throws ServletException
     * @throws IOException
     */
    abstract protected void processRequest(
            ESS0030DSMessage user, HttpServletRequest req, HttpServletResponse res,
            HttpSession session, int screen)
            throws ServletException, IOException;

    protected void redirect(String servletName, HttpServletResponse res) throws IOException {
        flexLog("Redirect to servlet: " + servletName);
        if (servletName.indexOf("/servlet/") == -1) {
            res.sendRedirect(srctx + "/servlet/" + servletName);
        } else {
            res.sendRedirect(srctx + servletName);
        }
    }

    /**
     * @param srcList
     * @param trgCls
     * @return
     */
    protected List convertList(BeanList srcList, Class trgCls) {

        JBObjList trgList = new JBObjList();
        populate((List) srcList.getList(), trgList, trgCls);
        return trgList;
    }

    protected boolean populate(MessageRecord source, MessageRecord target) {
        Enumeration enu = target.fieldEnumeration();
        MessageField field = null;
        String value = null;
        while (enu.hasMoreElements()) {
            field = (MessageField) enu.nextElement();
            try {
                value = source.getFieldString(field.getTag());
                if (value != null) {
                    field.setString(value);
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

    protected boolean populate(Object srcObj, Object trgObj) {
        boolean ok = true;
        try {
            if (srcObj instanceof DynamicDTO) {
                // From DTO to bean
                DynamicDTO dto = (DynamicDTO) srcObj;
                dto.unload(trgObj);
            } else {
                if (trgObj instanceof DynamicDTO) {
                    // From bean to DTO
                    DynamicDTO dto = (DynamicDTO) trgObj;
                    dto.load(srcObj);
                } else {
                    // From bean to bean with different classes
                    DynamicDTO dto = new DynamicDTO();
                    dto.load(srcObj);
                    dto.unload(trgObj);
                }
            }
        } catch (Exception e) {
            ok = false;
        }
        return ok;
    }

    protected boolean populate(List sourceList, List targetList, Class targetClass) {

        boolean ok = true;
        try {
            Iterator iter = sourceList.listIterator();
            while (iter.hasNext()) {
                Object srcObj = iter.next();
                Object trgObj = targetClass.newInstance();
                if (srcObj.getClass() == targetClass) {
                    // From bean to bean with the same class
                    trgObj = srcObj;
                } else {
                    populate(srcObj, trgObj);
                }
                targetList.add(trgObj);
            }
        } catch (Exception e) {
            ok = false;
        }
        return ok;
    }

    protected long copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[4 * 1024]; // 4K buffer
        int bytesRead;
        long size = 0;
        while ((bytesRead = in.read(buf)) != -1) {
            out.write(buf, 0, bytesRead);
            size = size + bytesRead;
        }
        return size;
    }

    protected void setResponseHeader(HttpServletResponse response, String name) throws IOException {
        StringBuffer sbContentDispValue = new StringBuffer();
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        //response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "max-age=0");
        response.addHeader("Cache-Control", "s-maxage=0");

        int dotPos = name.lastIndexOf('.');
        String ext = name;
        if (dotPos > 0)
            ext = name.substring(dotPos + 1).toLowerCase();

        if (ext.equals("infopathxml")) {
            response.setContentType("application/ms-infopath.xml");
            sbContentDispValue.append("attachment; filename=");
            sbContentDispValue.append(name);
            response.setHeader("Content-disposition", sbContentDispValue.toString());
        } else if (ext.equals("pdf")) {
            response.setContentType("application/pdf");
            sbContentDispValue.append("inline; filename=");
            sbContentDispValue.append(name);
            response.setHeader("Content-disposition", sbContentDispValue.toString());
        } else if (ext.startsWith("xls") || ext.equals("csv")) {
            response.setContentType("application/vnd.ms-excel");
            sbContentDispValue.append("attachment; filename=");
            sbContentDispValue.append(name);
            response.setHeader("Content-disposition", sbContentDispValue.toString());
        } else if(ext.startsWith("xlsx")) { // Modificado David Paucar Banco Internacional
            response.setContentType("application/vnd.ms-excel");
            sbContentDispValue.append("attachment; filename=");
            sbContentDispValue.append(name);
            response.setHeader("Content-disposition", sbContentDispValue.toString());
        } else if (ext.trim().equals("doc")) {
            response.setContentType("application/msword");
            sbContentDispValue.append("attachment; filename=");
            sbContentDispValue.append(name);
            response.setHeader("Content-disposition", sbContentDispValue.toString());
        } else if(ext.equals("txt")){
            response.setContentType("application/x-text");
        } else if(ext.equals("json")){
            response.setContentType("application/json");
            response.setCharacterEncoding("iso-8859-1");//Para aceptar letras acentuadas y caracteres especiales (Ñ,ñ,etc)
        } else if(ext.equals("xml")
                || ext.equals("xsd")){
            response.setContentType("text/xml");
        } else if (ext.equals("jpg")
                || ext.equals("jpeg")) {
            response.setContentType("image/jpeg");
        } else if (ext.equals("tif")
                || ext.equals("tiff")) {
            response.setContentType("image/tiff");
        } else if (ext.indexOf("html") >= 0 ) {
            response.setContentType("text/html" );
        } else if(ext.equals("msg")){ //Modificado David Paucar
            response.setContentType("application/vnd.ms-outlook");
            sbContentDispValue.append("attachment; filename=");
            sbContentDispValue.append(name);
            response.setHeader("Content-disposition", sbContentDispValue.toString());
        } else {
            response.setContentType("image/" + ext);
        }
    }

    protected void sendResponse(HttpServletResponse response, InputStream in, int length, String name) throws IOException {
        OutputStream out = null;
        try {
            response.reset();
            setResponseHeader(response, name);
            if (length != 0)
                response.setContentLength(length);

            out = (ServletOutputStream) response
                    .getOutputStream();
            copy(in, out);
            out.flush();

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void sendResponse(HttpServletResponse response, Object obj, String name) throws IOException {
        OutputStream out = null;
        try {
            setResponseHeader(response, name);
            if (obj instanceof InputStream) {
                out = (ServletOutputStream) response.getOutputStream();
                response.setContentLength(
                        (int)copy((InputStream) obj, out));
            } else {
                String type = null;
                if (obj.getClass().isArray()) {
                    type = obj.getClass().getComponentType().getName() + "[]";
                } else {
                    type = obj.getClass().getName();
                }
                if (type.equals("byte[]")) {
                    response.setContentLength(((byte[]) obj).length);
                    out = new BufferedOutputStream(response.getOutputStream());
                    out.write((byte[]) obj);
                } else {
                    out = (ServletOutputStream) response.getOutputStream();
                    ((ServletOutputStream)out).print(obj.toString());
                }
            }
            out.flush();

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void sendJSONResponse(HttpServletResponse response, Object json) throws IOException {
        PrintWriter out = null;
        try {
            setResponseHeader(response, "json");
            out = response.getWriter();
            out.print(json.toString());
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }

    protected void sendXMLResponse(HttpServletResponse response, Object xmlResponse) throws IOException {
        PrintWriter out = null;
        try {
            setResponseHeader(response, "xml");
            out = response.getWriter();
            out.print(xmlResponse.toString());
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }

    protected void sendPdfResponse(HttpServletResponse response, byte[] bytes, String name) throws IOException {
        BufferedOutputStream out = null;
        try {
            response.reset();
            setResponseHeader(response, name);
            response.setContentLength(bytes.length);
            out = new BufferedOutputStream(response.getOutputStream());
            out.write(bytes);
            out.flush();

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private int getDescriptionLines(int length) {
        int size = length / 70;
        size = size % 70 == 0 ? size : size + 1;
        return size;
    }

    private String[] splitSentences(String message) {
        String[] result = null;
        if (message.length() > 70) {
            result = new String[getDescriptionLines(message.length())];
            String delims = "[ ]+";
            String[] tokens = message.split(delims);
            int offset = 0;
            String str = "";
            for (int i = 0; i < tokens.length; i++) {
                boolean br = tokens[i].indexOf("<br>") != -1;
                if ((str.length() + tokens[i].length() < 70) && (!br)) {
                    str += str.length() == 0 ? tokens[i] : " " + tokens[i];
                } else {
                    if (br) {
                        tokens[i] = tokens[i].substring(tokens[i].indexOf("<br>") + 4);
                    }
                    if (i == tokens.length - 1) {
                        result[offset++] = str;
                        result[offset++] = tokens[i];
                    } else {
                        result[offset++] = str;
                        str = tokens[i];
                    }
                }
                if (str.indexOf(".") > 0) {
                    String[] words = str.split(".", 1);
                    result[offset++] = words[0];
                    str = words[1];
                }
            }
        } else {
            result = new String[1];
            result[0] = message;
        }
        return result;
    }

    protected void addError(ELEERRMessage source, ELEERRMessage target) {
        int idx1 = target.getBigDecimalERRNUM().intValue();
        if (idx1 < 10) {
            if (!source.getERRNUM().equals("0")) {
                int idx2 = source.getBigDecimalERRNUM().intValue();
                for (int i = 1; i <= idx2; i++) {
                    idx1++;
                    String tSufix = Util.addLeftChar('0', 2, ""+idx1);
                    String sSufix = Util.addLeftChar('0', 2, ""+i);
                    if (idx1 < 11) {
                        target.getField("ERNU" + tSufix).setString(source.getFieldString("ERNU" + sSufix));
                        target.getField("ERDS" + tSufix).setString(source.getFieldString("ERDS" + sSufix));
                        target.getField("ERDF" + tSufix).setString(source.getFieldString("ERDF" + sSufix));
                        target.getField("ERDR" + tSufix).setString(source.getFieldString("ERDR" + sSufix));
                        target.getField("ERWF" + tSufix).setString(source.getFieldString("ERWF" + sSufix));
                    } else {
                        idx1 = 10;
                        break;
                    }
                }
            }
            target.setERRNUM(new BigDecimal(String.valueOf(idx1)));
        }
    }

    protected void addError(ELEERRMessage error, String field, String code, String description) {
        int i = error.getBigDecimalERRNUM().intValue()+1;
        String sufix = "";
        if ("".equals(field) && "".equals(code)) {
            String[] message = splitSentences(description);
            int num = 0;
            for (int j = 0; j < message.length; j++) {
                if (message[j] == null) break;
                sufix = j + 1 < 10 ? "0" + (j + 1) : "" + (j + 1);
                num++;
                error.getField("ERNU" + sufix).setString(""+num);
                error.getField("ERDS" + sufix).setString(message[j]);
            }
            error.getField("ERRNUM").setString(""+num);
        } else {
            sufix = i < 10 ? "0" + i : "" + i;
            error.getField("ERDF" + sufix).setString(field);
            error.getField("ERNU" + sufix).setString(code);
            error.getField("ERDS" + sufix).setString(
                    description.length() > 70 ? description.substring(0, 70) : description);
            error.getField("ERRNUM").setString(""+i);
        }
    }

    protected void convertErrors(ELEERRMessage error, Map errors){
        Iterator iter = errors.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry object = (Map.Entry) iter.next();
            String code = object.getKey().toString();
            addError(error, code, code,
                    object.getValue().toString());
        }
    }


    /**
     * Hexadecimal a String  BanInter INC_15133
     * @param hex
     * @return
     */
    /**
     * Hexadecimal a String  BanInter INC_15133
     * @param hex
     * @return
     */
    public String hexDeciamlToString(String hex) {
        String nameSearch = "";
        byte[] data = null;
        try {
            int l = hex.length();
            data = new byte[l / 2];
            for (int i = 0; i < l; i += 2) {
                data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                        + Character.digit(hex.charAt(i + 1), 16));
            }
            nameSearch = new String(data, "ISO-8859-1");
        } catch (Exception e) {
            nameSearch = hex;
        }
        return nameSearch;

    }
}
