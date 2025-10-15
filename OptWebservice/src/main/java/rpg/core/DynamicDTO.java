package rpg.core;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @author ogarcia
 *
 * DynamicDTO makes data transfer easy.
 * Use it when you want to transfer data
 * form source bean to target bean
 * using a data transfer object.
 * .
 * There are two requirements:
 * property name and type need to be equals
 * from source to target.
 *
 * @version $Revision: 1.3 $
 */
public class DynamicDTO {

    /**
     * Field LOG
     */

    /**
     * Field values
     */
    private HashMap values;
    private HashMap types;
    private Map namesMap;

    /**
     * Constructor for DynamicDTO.
     */
    public DynamicDTO() {
        super();
        init();
    }


    /**
     * Method init
     */
    public void init() {
        values = new HashMap();
        types = new HashMap();
    }


    public boolean load(Object sourceBean) {
        boolean ok = true;
        BeanParser bp = null;
        try {
            bp = new BeanParser(sourceBean);
            types.putAll(bp.getTypes());
            values.putAll(bp.getValues());
        }
        catch(Exception e) {
            ok = false;
        }
        return ok;
    }

    public boolean extract(Object targetBean) {
        boolean ok = true;
        Class targetClass = null;
        BeanInfo beanInfo = null;
        PropertyDescriptor properties[] = (PropertyDescriptor[])null;
        PropertyDescriptor propInfo = null;
        BeanProxy targetProxy = null;
        try {
            targetClass = targetBean.getClass();
            targetProxy = new BeanProxy(targetBean);
            beanInfo = Introspector.getBeanInfo(targetClass);
            properties = beanInfo.getPropertyDescriptors();
        }
        catch(Exception exception) { }
        if(properties != null) {
            for(int i = 0; i < properties.length; i++) {
                propInfo = properties[i];
                String name = propInfo.getDisplayName();
                String type = propInfo.getPropertyType().getName();
                try {
                    Object value = values.get(name);
                    targetProxy.set(name, value);
                }
                catch(Exception exception1) { }
            }

        } else {
            ok = false;
        }
        return ok;
    }

    public Object get(String name) {
        return values.get(name);
    }

    public boolean unload(Object targetBean) {
        boolean ok = true;
        BeanParser bp = null;
        try {
            bp = new BeanParser(targetBean);
            Set ks = bp.getTypes().keySet();
            for(Iterator iter = ks.iterator(); iter.hasNext();) {
                String name = (String)iter.next();
                String tgType = (String)bp.getTypes().get(name);
                String srType = (String)types.get(name);
                Object value = values.get(name);
                try {
                    if(value != null)
                        if(value instanceof String) {
                            String v = (String)value;
                            bp.setString(name, tgType, v);
                        } else
                        if(!tgType.equalsIgnoreCase(srType)) {
                            String v = value.toString();
                            bp.setString(name, tgType, v);
                        } else {
                            bp.set(name, value);
                        }
                }
                catch(Exception exception) { }
            }

        }
        catch(Exception e) {
            ok = false;
        }
        return ok;
    }

    public void add(String name, Object value) {
        types.put(name, value.getClass().getName());
        values.put(name, value);
    }

    /**
     * @return the namesMap
     */
    public Map getNamesMap() {
        return namesMap;
    }

}
