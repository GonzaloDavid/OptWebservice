package rpg.core;

import java.util.*;

/**
 * The ClassCache class is used by the MessageHandler class to hold the classes
 * that have already been loaded and any available instances of those classes.
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 2:23:01 PM)
 *
 * @author ?
 *
 * @tag    Date          Flag        Description
 *
 */

public class ClassCache
{
    final static String COPYRIGHT =  "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    Class myclass = null;
    Vector instances = new Vector();

    /**
     * Construct a new ClassCache object and load the class from the specified package.
     *
     * @param packagename the package containing the class (null for no package)
     * @param classname   the name of the class (without the package name)
     *
     * @exception ClassNotFoundException the class could not be found in the package specified.
     */
    public ClassCache(String packagename, String classname) throws ClassNotFoundException
    {
        if (packagename == null)
            myclass = Class.forName(classname);
        else
            myclass = Class.forName(packagename + '.' + classname);
    }
    /**
     * Get an instance of the cached object.  This could be a new instance or an
     * instance that was returned earlier.
     *
     * @return an instance of the class loaded when this ClassCache was created.
     * @exception IllegalAccessException if the class or initializer is not accessible.
     * @exception InstantiationException if an application tries to instantiate an
     *             abstract class or an interface, or if the instantiation fails for some other reason.
     *
     */
    public Object getInstance() throws IllegalAccessException, InstantiationException
    {
        Object instance = null;
        int lastelement = instances.size() - 1;

        if (lastelement >= 0)
        {
            instance = instances.elementAt(lastelement);
            instances.removeElementAt(lastelement);
        }
        else
        {
            instance = myclass.newInstance();
        }

        return instance;
    }
    /**
     * Return an instance of the class to the cache.
     *
     * @param returned An instance of this class that is no longer in use.
     */
    public void returnInstance(Object returned)
    {
        if (myclass.isInstance(returned))
            instances.addElement(returned);
    }
}
