package rpg.core;

import java.util.*;

/**
 * This class implements an enumerator for a MessageRecord's fields.
 * @see com.ibm.as400.ctc.ffm.MessageRecord
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 2:33:38 PM)
 *
 * @author ?
 *
 * @tag    Date          Flag        Description
 */
public class MessageEnumerator implements Enumeration, Iterator
{
    final static String COPYRIGHT =  "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    MessageRecord myrec;
    int fcount;
    int fpos;

    /**
     * Create a MessageEnumerator for the specified MessageRecord.
     *
     * @param MessageRecord the message record to enumerate the fields of
     */
    protected MessageEnumerator(MessageRecord mr)
    {
        myrec = mr;
        fcount = myrec.getFieldCount();
        fpos = 0;
    }
    /**
     * Method to check for more elements.
     *
     * @return true if there are more elements.
     */
    public boolean hasMoreElements()
    {
        return (fpos < fcount);
    }
    /**
     * Returns the next element.
     *
     * @return The next element.
     * @exception NoSuchElementException If there is no next element.
     */
    public Object nextElement() throws NoSuchElementException
    {
        if (fpos >= fcount)
            throw new NoSuchElementException();

        return myrec.getField(fpos++);
    }

    public boolean hasNext() {
        return hasMoreElements();
    }
    public Object next() {
        return nextElement();
    }
    public void remove() {
        throw new UnsupportedOperationException("Can't remove Fields, this is a fixed Object");
    }
}
