package rpg.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;


/**
 * BeanProxy is a class for accessing a JavaBean dynamically at runtime. It
 * provides methods for getting properties, setting properties, and invoking
 * methods on a target bean. BeanProxy can be used in situations where bean
 * properties and methods are not known at compile time, or when compile time
 * access to a bean would result in brittle code.
 * <br><br>
 * Here is an example of brittle code:
 * <pre>
 * public class MyTableModel extends DefaultTableModel {
 *    ...
 *
 *    public Object getValueAt(int row, int col) {
 *       LogEntry entry;
 *       Object   value;
 *
 *       try {
 *          entry = log.get(row);
 *
 *          switch(col) {
 *             case 0:
 *                value = entry.getWorkDate();
 *                break;
 *
 *             case 1:
 *                value = entry.getStartTime();
 *                break;
 *             ...
 *          }
 *        } catch(Exception ex) {
 *           return "";
 *       }
 *
 *       return value;
 *    }
 *
 *    ...
 * }
 * </pre>
 *
 * Here is an example of non-brittle code using BeanProxy:
 * <pre>
 * public class MyTableModel extends DefaultTableModel {
 *    ...
 *
 *    public MyTableModel() {
 *       columns = new String[] { "workDate",  "startTime", "endTime",
 *          "mealTime", "hours", "notes" };
 *       entryProxy = new BeanProxy(LogEntry.class);
 *       ...
 *    }
 *
 *    ...
 *
 *    public Object getValueAt(int row, int col) {
 *       LogEntry entry;
 *       Object   value;
 *
 *       try {
 *          entry = log.get(row);
 *          entryProxy.setBean(entry);
 *          value = entryProxy.get(columns[col]);
 *        } catch(Exception ex) {
 *           return "";
 *       }
 *
 *       return value;
 *    }
 * </pre>
 *
 * @author Thornton Rose
 * @version 1.2
 */
public class BeanProxy {
    /**
     * Field bean
     */
    private Object    bean;
    /**
     * Field beanClass
     */
    private Class     beanClass;
    /**
     * Field pdsByName
     */
    private Hashtable pdsByName = new Hashtable();

    /**
     * Constructs a proxy for the given class.
     *
     * @param theBeanClass The target bean class.
     * @throws IntrospectionException
     */
    public BeanProxy(Class theBeanClass) throws IntrospectionException {
        BeanInfo             bi;
        PropertyDescriptor[] pds;
        String               name;

        // Save reference to bean class.

        beanClass = theBeanClass;

        // Build hashtable for bean property descriptors.

        bi = Introspector.getBeanInfo(beanClass);
        pds = bi.getPropertyDescriptors();

        for (int i = 0; i < pds.length; i ++) {
            name = pds[i].getName();
            pdsByName.put(name, pds[i]);
        }
    }

    /**
     * Constructs a proxy for the given bean.
     *
     * @param theBean The target bean.
     * @throws IntrospectionException
     */
    public BeanProxy(Object theBean) throws IntrospectionException {
        this(theBean.getClass());
        bean = theBean;
    }

    /**
     * Method getBean
     * @return Object
     * @see com.datapro.generic.beanutil.IBeanProxy#getBean()
     */
    public Object getBean() {
        return bean;
    }

    /**
     * Method setBean
     * @param newBean Object
     * @see com.datapro.generic.beanutil.IBeanProxy#setBean(Object)
     */
    public void setBean(Object newBean) {
        bean = newBean;
    }

    /**
     * Get bean property.
     *
     * @param name Bean property name.
     *
     * @return Bean property value as Object.
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see com.datapro.generic.beanutil.IBeanProxy#get(String)
     */
    public Object get(String name) throws NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor pd;
        Method             getter;

        pd = (PropertyDescriptor) pdsByName.get(name);

        if (pd == null) {
            throw new NoSuchFieldException("Unknown property: " + name);
        }

        getter = pd.getReadMethod();

        if (getter == null) {
            throw new NoSuchMethodException("No read method for: " + name);
        }

        return getter.invoke(bean, new Object[] {});
    }

    /**
     * Set bean property.
     *
     * @param name   Bean property name.
     * @param value  Bean property value.
     * @return Object
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see com.datapro.generic.beanutil.IBeanProxy#set(String, Object)
     */
    public Object set(String name, Object value) throws NoSuchFieldException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        PropertyDescriptor pd;
        Method             setter;

        pd = (PropertyDescriptor) pdsByName.get(name);

        if (pd == null) {
            throw new NoSuchFieldException("Unknown property: " + name);
        }

        setter = pd.getWriteMethod();

        if (setter == null) {
            throw new NoSuchMethodException("No write method for: " + name);
        }

        return setter.invoke(bean, new Object[] { value } );
    }

    /**
     * Invoke named method on target bean.
     *
     * @param name       Method name.
     * @param types      Parameter types.
     * @param parameters List of parameters passed to method.
     *
     * @return Return value from method (may be null).
     *
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws Throwable When any exception occurs.
     * @see com.datapro.generic.beanutil.IBeanProxy#invoke(String, Class[], Object[])
     */
    public Object invoke(String name, Class[] types, Object[] parameters) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return beanClass.getMethod(name, types).invoke(bean, parameters);
    }

    /**
     * added by fhernandez
     * Returns the bean Properties as a Hashtable
     * @return The Hashtable Properties
     */
    public Hashtable getPdsByName() {
        return pdsByName;
    }
}
