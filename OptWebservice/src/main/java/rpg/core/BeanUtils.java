package rpg.core;


import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;

public class BeanUtils {

    public static String getClassName(Class c){
        if (c.isArray()) {
            return c.getComponentType().getName() + "[]";
        } else {
            return c.getName();
        }
    }

    public static String convertToString(Object obj, String type) {
        String value = null;
        if (type.equals("java.sql.Timestamp")) {
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

    public static Object convertToObject(Object obj, String type) {
        Object robj = null;
        if (obj != null) {
            String oType = getClassName(obj.getClass());
            if (!oType.equals(type)) {
                if (type.equals("byte[]")) {
                    if (obj instanceof String) {
                        String str = (String) obj;
                        robj = str.getBytes();
                    }
                } else if (oType.equals("byte[]")) {
                    if (type.equals("java.lang.String")) {
                        robj = new String((byte[])obj);
                    }
                } else {
                    String str = convertToString(obj, oType);
                    if (type.equals("java.lang.String")) {
                        robj = str;
                    } else if (type.equals("java.math.BigInteger")) {
                        robj = new BigInteger(str);
                    } else if (type.equals("java.math.BigDecimal")) {
                        robj = new BigDecimal(str);
                    } else if (type.equals("boolean")) {
                        robj = new Boolean(str);
                    } else if (type.equals("byte")) {
                        robj = new Byte(str);
                    } else if (type.equals("short")) {
                        robj = new Short(str);
                    } else if (type.equals("int")
                            || type.equals("java.lang.Integer")) {
                        robj = new Integer(str);
                    } else if (type.equals("long")
                            || type.equals("java.lang.Long")) {
                        robj = new Long(str);
                    } else if (type.equals("float")) {
                        robj = new Float(str);
                    } else if (type.equals("double")) {
                        robj = new Double(str);
                    } else if (type.equals("java.sql.Date")) {
                        // robj = Date.valueOf(str);
                        robj = Timestamp.valueOf(str);
                    } else if (type.equals("java.sql.Time")) {
                        robj = Time.valueOf(str);
                    } else if (type.equals("java.sql.Timestamp")) {
                        robj = Timestamp.valueOf(str);
                    }
                }
            } else {
                return obj;
            }
        }
        return robj;
    }

    private static boolean isInstance(Object o, String oName) {
        Class superclass = o.getClass().getSuperclass();
        while (superclass != null) {
            if (superclass.getName().endsWith(oName))
                return true;
            superclass = superclass.getSuperclass();
        }
        return false;
    }

    private static boolean areTypesCompatible(Class[] types, Class[] params){
        if( types.length == params.length ){
            for (int i = 0; i < params.length; i++) {
                if(types[i].isAssignableFrom(params[i])){
                    continue;
                } else if(types[i].isPrimitive()){
                    String type = types[i].getName();
                    if (type.equals("char")
                            && params[i].getName().equals("java.lang.String")){
                        continue;
                    } else if (type.equals("boolean")
                            && params[i].getName().equals("java.lang.Boolean")){
                        continue;
                    } else if (type.equals("byte")
                            && params[i].getName().equals("java.lang.Byte")){
                        continue;
                    } else if (type.equals("short")
                            && params[i].getName().equals("java.lang.Short")){
                        continue;
                    } else if (type.equals("int")
                            && params[i].getName().equals("java.lang.Integer")){
                        continue;
                    } else if (type.equals("long")
                            && params[i].getName().equals("java.lang.Long")){
                        continue;
                    } else if (type.equals("float")
                            && params[i].getName().equals("java.lang.Float")){
                        continue;
                    } else if (type.equals("double")
                            && params[i].getName().equals("java.lang.Double")){
                        continue;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static Object invokeMethod(Object bean, String name, Object[] params){
        try {
            Method method = null;
            if (params != null) {
                Class[] paramsClass = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    paramsClass[i] = params[i].getClass();
                }
                Method[] methods = bean.getClass().getMethods();
                for( int i = 0; i < methods.length; i++ ){
                    if( !methods[i].getName().equals(name))
                        continue;
                    Class[] types = methods[i].getParameterTypes();
                    if( areTypesCompatible(types, paramsClass)){
                        method = methods[i];
                        break;
                    }
                }
            } else {
                params = new Object[]{};
                method = bean.getClass().getMethod(name,  new Class[]{});
            }
            return method.invoke(bean, params);
        } catch (Exception e) {
        }
        return null;
    }

    public static Object getProperty(Object bean, String property){
        try {
            if (isInstance(bean, "MessageRecord")) {
                Object field = invokeMethod(bean, "getField", new String[]{property});
                Integer type = (Integer) getProperty(field, "type");
                Object value = null;
                if (type.intValue() == 2){
                    value = getProperty(field, "bigDecimal");
                } else {
                    value = getProperty(field, "string");
                }
                return value;
            } else {
                return invokeMethod(bean, "get" + property.substring(0, 1).toUpperCase()
                        + property.substring(1), null);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void setProperty(Object bean, String property, Object value){
        try {
            if (isInstance(bean, "MessageRecord")) {
                Object field = invokeMethod(bean, "getField", new String[]{property});
                setProperty(field, "string", convertToString(value, value.getClass().getName()));
            } else {
                invokeMethod(bean, "set" + property.substring(0, 1).toUpperCase()
                        + property.substring(1), new Object[]{value});
            }
        } catch (Exception e) {
        }
    }

    public static void set(Object bean, PropertyDescriptor pd, Object value)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (pd == null)
            return;
        Method setter = pd.getWriteMethod();
        if (setter == null)
            return;
        Object obj = convertToObject(value, getClassName(pd.getPropertyType()));
        setter.invoke(bean, new Object[] { obj });
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Object bean) throws IntrospectionException {
        PropertyDescriptor[] pds = Introspector.getBeanInfo(bean.getClass())
                .getPropertyDescriptors();
        return pds;
    }
}
