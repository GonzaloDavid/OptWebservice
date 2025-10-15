package rpg.core;




import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;




/**
 * @author ogarcia
 *
 * @version $Revision: 1.4 $
 */
public class BeanParser {

    /**
     * Field LOG
     */

    /**
     * Field bean
     */
    private Object bean;
    /**
     * Field types
     */
    private HashMap types;

    /**
     * Field TYPE_BOOLEAN
     */
    private final static String TYPE_BOOLEAN = "boolean";
    /**
     * Field TYPE_BYTE
     */
    private final static String TYPE_BYTE = "byte";
    /**
     * Field TYPE_SHORT
     */
    private final static String TYPE_SHORT = "short";
    /**
     * Field TYPE_INT
     */
    private final static String TYPE_INT = "int";
    /**
     * Field TYPE_LONG
     */
    private final static String TYPE_LONG = "long";
    /**
     * Field TYPE_FLOAT
     */
    private final static String TYPE_FLOAT = "float";
    /**
     * Field TYPE_DOUBLE
     */
    private final static String TYPE_DOUBLE = "double";
    /**
     * Field TYPE_BIG_DECIMAL
     */
    private final static String TYPE_BIG_DECIMAL = "java.math.BigDecimal";
    /**
     * Field TYPE_STRING
     */
    private final static String TYPE_STRING = "java.lang.String";
    /**
     * Field TYPE_DATE
     */
    private final static String TYPE_DATE = "java.sql.Date";
    /**
     * Field TYPE_TIME
     */
    private final static String TYPE_TIME = "java.sql.Time";
    /**
     * Field TYPE_TIMESTAMP
     */
    private final static String TYPE_TIMESTAMP = "java.sql.Timestamp";
    /**
     * Field TYPE_BYTE_ARRAY
     */
    private final static String TYPE_BYTE_ARRAY = "byte[]";

    /**
     * Constructor for BeanParser.
     */
    public BeanParser() {
        super();
    }

    /**
     * Constructor for BeanParser
     * @param obj Object
     */
    public BeanParser(Object obj) {
        super();
        setBean(obj);
    }

    /**
     * Method parseResultSet
     * @param md ResultSetMetaData
     * @param rs ResultSet
     * @return boolean
     */
    public boolean parseResultSet(ResultSetMetaData md, ResultSet rs) {
        boolean ok = false;
        try {
            BeanProxy targetProxy = new BeanProxy(bean);
            for (int i = 1; i <= md.getColumnCount(); i++) {
                try {
                    String cname = md.getColumnName(i);
                    Object rsobj = null;
                    switch (md.getColumnType(i)) {
                        case Types.BLOB:
                            rsobj = new SerialBlob(rs.getBlob(i));
                            break;
                        case Types.BINARY:
                        case Types.VARBINARY:
                        case Types.LONGVARBINARY :
                            rsobj = new SerialBlob(rs.getBytes(i));
                            break;
                        case Types.TIMESTAMP :
                            rsobj = rs.getTimestamp(i);
                            break;
                        default:
                            rsobj = rs.getObject(i);
                            break;
                    }
                    String type = (String) types.get(cname);
                    if (type.equalsIgnoreCase(rsobj.getClass().getName())){
                        targetProxy.set(cname, rsobj);
                    } else {
                        targetProxy.set(cname, convertToObject(rsobj, type));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            ok = true;
        } catch (SQLException e) {
            System.out.println(e);

        } catch (IntrospectionException e) {
            System.out.println(e);
        }
        return ok;
    }

    /**
     * Method get
     * @param name String
     * @return Object
     */
    public Object get(String name) {
        Object rs = null;
        try {
            BeanProxy targetProxy = new BeanProxy(bean);
            rs = targetProxy.get(name);
        } catch (IntrospectionException e) {
            System.out.println(e);
        } catch (Exception e) {
            //if (LOG.isDebugEnabled()) LOG.debug(e);
        }
        return rs;
    }

    /**
     * Method getString
     * @param name String
     * @return String
     */
    public String getString(String name) {
        String rs = "";
        try {
            String type = (String) types.get(name);
            rs = convertToStr(get(name), type);
        } catch (Exception e) {
            //if (LOG.isDebugEnabled()) LOG.debug(e);
        }
        return rs;
    }

    /**
     * Method set
     * @param name String
     * @param value Object
     * @return boolean
     */
    public boolean set(String name, Object value) {
        boolean ok = false;

        try {
            BeanProxy targetProxy = new BeanProxy(bean);
            targetProxy.set(name, value);
            ok = true;
        } catch (IntrospectionException e) {
            System.out.println(e);
        } catch (Exception e) {
            //if (LOG.isDebugEnabled()) LOG.debug(e);
        }

        return ok;
    }

    /**
     * Method setString
     * @param name String
     * @param str String
     * @return boolean
     */
    public boolean setString(String name, String str) {
        boolean ok = false;
        if (str != null) {
            try {
                String type = (String) types.get(name);
                ok = setString(name, type, str);
            } catch (Exception e) {
                //if (LOG.isDebugEnabled()) LOG.debug(e);
            }
        }
        return ok;
    }

    /**
     * Method setString
     * @param name String
     * @param type String
     * @param str String
     * @return boolean
     */
    public boolean setString(String name, String type, String str) {
        boolean ok = false;
        if (str != null) {
            try {
                Object value = convertToObj(str, type);
                ok = set(name, value);
            } catch (Exception e) {
                //if (LOG.isDebugEnabled()) LOG.debug(e);
            }
        }
        return ok;
    }

    /**
     * Method convertToObj
     * @param str String
     * @param type String
     * @return Object
     */
    public Object convertToObj(String str, String type) {
        Object value = null;
        if (type.equals(TYPE_STRING)) {
            value = str;
        } else if (type.equals(TYPE_BIG_DECIMAL)) {
            value = new BigDecimal(str);
        } else if (type.equals(TYPE_BOOLEAN)) {
            value = new Boolean(str);
        } else if (type.equals(TYPE_BYTE)) {
            value = new Byte(str);
        } else if (type.equals(TYPE_SHORT)) {
            value = new Short(str);
        } else if (type.equals(TYPE_INT)) {
            value = new Integer(str);
        } else if (type.equals(TYPE_LONG)) {
            value = new Long(str);
        } else if (type.equals(TYPE_FLOAT)) {
            value = new Float(str);
        } else if (type.equals(TYPE_DOUBLE)) {
            value = new Double(str);
        } else if (type.equals(TYPE_DATE)) {
            // value = Date.valueOf(str);
            value = Timestamp.valueOf(str);
        } else if (type.equals(TYPE_TIME)) {
            value = Time.valueOf(str);
        } else if (type.equals(TYPE_TIMESTAMP)) {
            value = Timestamp.valueOf(str);
        } else if (type.equals(TYPE_BYTE_ARRAY)) {
        }
        return value;
    }

    private Object convertToObject(Object obj, String type) throws SQLException {
        if (type.equals(TYPE_BYTE_ARRAY)) {
            if (obj instanceof SerialBlob) {
                SerialBlob blob = (SerialBlob) obj;
                return blob.getBytes(0, (int)blob.length());
            } else {
                return null;
            }
        } else {
            return convertToObj(obj.toString(), type);
        }
    }

    /**
     * Method convertToStr
     * @param obj Object
     * @param type String
     * @return String
     */
    public String convertToStr(Object obj, String type) {
        String value = null;
        if (type.equals(TYPE_TIMESTAMP)) {
            String str = obj.toString();
            int ls = str.length() - 1;
            int pt = str.indexOf(".");
            int fl = 3 - (ls - pt);
            for (int i = 0; i < fl; i++) {
                str += "0";
            }
            value = str;
        } else {
            value = obj.toString();
        }
        return value;
    }

    /**
     * Method getBeanInfo
     */
    private void getBeanInfo() {

        BeanInfo beanInfo = null;
        PropertyDescriptor[] properties = null;
        PropertyDescriptor propInfo = null;

        BeanProxy sourceProxy = null;

        try {
            sourceProxy = new BeanProxy(bean);
            beanInfo = Introspector.getBeanInfo(bean.getClass());
            properties = beanInfo.getPropertyDescriptors();

        } catch (Exception e) {
            System.out.println(e);
        }

        types = new HashMap();

        if (properties != null) {
            // For each property descriptor ...
            for (int i = 0; i < properties.length; i++) {

                propInfo = properties[i];
                String name = propInfo.getDisplayName();
                if (name.equals("class"))
                    continue;

                String type = "";
                if (propInfo.getPropertyType().isArray()) {
                    type = propInfo.getPropertyType().getComponentType().getName() + "[]";
                } else {
                    type = propInfo.getPropertyType().getName();
                }

                types.put(name, type);

            }
        }
    }

    /**
     * Returns the bean.
     * @return Object
     */
    public Object getBean() {
        return bean;
    }

    /**
     * Sets the bean.
     * @param bean The bean to set
     */
    public void setBean(Object bean) {
        this.bean = bean;
        getBeanInfo();
    }

    /**
     * Method getSQLType.
     * @param type
     * @return int
     */
    public int getSQLType(String type) {
        int ty = -1;
        if (type.equals(TYPE_STRING)) {
            ty = Types.VARCHAR;
        } else if (type.equals(TYPE_BIG_DECIMAL)) {
            ty = Types.NUMERIC;
        } else if (type.equals(TYPE_BOOLEAN)) {
            ty = Types.BIT;
        } else if (type.equals(TYPE_BYTE)) {
            ty = Types.TINYINT;
        } else if (type.equals(TYPE_SHORT)) {
            ty = Types.SMALLINT;
        } else if (type.equals(TYPE_INT)) {
            ty = Types.INTEGER;
        } else if (type.equals(TYPE_LONG)) {
            ty = Types.BIGINT;
        } else if (type.equals(TYPE_FLOAT)) {
            ty = Types.FLOAT;
        } else if (type.equals(TYPE_DOUBLE)) {
            ty = Types.DOUBLE;
        } else if (type.equals(TYPE_DATE)) {
            ty = Types.DATE;
        } else if (type.equals(TYPE_TIME)) {
            ty = Types.TIME;
        } else if (type.equals(TYPE_TIMESTAMP)) {
            ty = Types.TIMESTAMP;
        } else if (type.equals(TYPE_BYTE_ARRAY)) {
            ty = Types.ARRAY;
        }
        return ty;
    }

    /**
     * Returns the types.
     * @return HashMap
     */
    public HashMap getTypes() {
        return types;
    }

    /**
     * Method getValues
     * @return HashMap
     */
    public HashMap getValues() {
        HashMap values = new HashMap();
        Set ks = types.keySet();
        for (Iterator iter = ks.iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            values.put(name, get(name));
        }
        return values;
    }
}
