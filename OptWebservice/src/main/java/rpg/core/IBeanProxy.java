package rpg.core;

/**
 * IBeanProxy is the interface for BeanProxy.
 *
 * @author Thornton Rose
 * @version $Revision: 1.1 $
 */
public interface IBeanProxy {
    /**
     * Get target bean.
     * @return Object
     */
    Object getBean();

    /**
     * Set target bean.
     * @param newBean Object
     */
    void setBean(Object newBean);

    /**
     * Get named property.
     * @param name String
     * @return Object
     * @throws Exception
     */
    Object get(String name) throws Exception;

    /**
     * Set named property to value.
     * @param name String
     * @param value Object
     * @return Object
     * @throws Exception
     */
    Object set(String name, Object value) throws Exception;

    /**
     * Invoke named method that accepts types, passing it parameters.
     * @param name String
     * @param types Class[]
     * @param parameters Object[]
     * @return Object
     * @throws Exception
     */
    Object invoke(String name, Class[] types, Object[] parameters) throws Exception;
}
