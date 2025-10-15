package rpg.core;

import java.io.IOException;
import java.io.Serializable;

/**
 * Abstract base class for all fields in Message objects.
 * Contains constant values for field types.
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 2:43:09 PM)
 *
 * @author ?
 *
 * @tag    Date          Flag        Description
 */

public abstract class MessageField implements Serializable {
    final static String COPYRIGHT =
            "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    byte[] bytes;
    int start;
    int length;
    String stringvalue = null;
    String tag = null;
    /*
     * 0 = Upper
     * 1 = Lower
     * 2 = sensitive
     */
    protected int casesensitive = 0;

    public static final int CHARACTERFIELD = 1;
    public static final int DECIMALFIELD = 2;

    public static final int ALIGNLEFT = 0;
    public static final int ALIGNRIGHT = 1;

    /**
     * Base constructor for field objects.  Specific field types may add
     * other parameters.
     *
     * @param messagebytes  byte array containing bytes for message.
     * @param fieldstart    starting position of this field in byte array.
     * @param fieldlength   length of this field in byte array.
     */
    public MessageField(byte[] messagebytes, int fieldstart, int fieldlength) {
        bytes = messagebytes;
        start = fieldstart;
        length = fieldlength;
    }
    /**
     * Base constructor for field objects with tag.  Specific field types may add
     * other parameters.
     *
     * @param messagebytes  byte array containing bytes for message.
     * @param fieldstart    starting position of this field in byte array.
     * @param fieldlength   length of this field in byte array.
     * @param fieldtag      tag name for this field.
     */
    public MessageField(
            byte[] messagebytes,
            int fieldstart,
            int fieldlength,
            String fieldtag) {
        this(messagebytes, fieldstart, fieldlength);
        tag = fieldtag;
    }
    /**
     * Get the number of digits to the right of the decimal point for numbers.
     * This method provides a default implementation for non numeric fields.
     * Numeric fields should always override this method.
     *
     * @return the number of digits to right of decimal (or -1 if not a number).
     */
    public int getDecimals() {
        return -1;
    }
    /**
     * Get the length of this field.  The meaning of length may vary depending
     * on the field type.
     *
     * @return length of this field.
     */
    public int getLength() {
        return length;
    }
    /**
     * Return the value of the field as a String.
     *
     * @return String containing displayable field value.
     */
    public String getString() {
        if (stringvalue == null)
            stringvalue = makeString();

        return stringvalue;
    }
    /**
     * Get the tag name for this field.
     *
     * @return tag name for this field.
     */
    public String getTag() {
        return tag;
    }
    /**
     * Get the field type.
     * Acceptable return values are provided as constants in this class.
     * @return The field type.
     */
    public abstract int getType();
    /**
     * Initialize the field to a default value.
     */
    public abstract void initialize();
    /**
     * Fill the field with specified byte value.
     *
     * @param value byte value for filling field.
     */
    public void initialize(byte value) {
        for (int i = 0; i < length; i++)
            bytes[i + start] = value;

        reset();
    }
    /**
     * Internal function to convert field value to String.
     */
    abstract String makeString();
    /**
     * Reset return value for field.
     */
    public void reset() {
        stringvalue = null;
    }
    /**
     * Set the message byte array to a new array and reset return values.
     *
     * @param messagebytes new message byte array.
     */
    public void reset(byte[] messagebytes) {
        bytes = messagebytes;
        reset();
    }
    /**
     * Set the bytes of the field using with a specified pad character and
     * alignment.
     *
     * @param newvalue  String containing new value for field.
     * @param pad       byte to be used to pad field.
     * @param alignment ALIGNLEFT or ALIGNRIGHT.
     */
    public void setBytes(String newvalue, byte pad, int alignment)
            throws IllegalArgumentException {
        byte[] rawbytes = newvalue.getBytes();

        if (alignment == ALIGNRIGHT) {
            int sourcepos = rawbytes.length - 1;
            int destpos = start + length - 1;

            for (int i = 0; i < length; i++)
                bytes[destpos - i] =
                        (i <= sourcepos) ? rawbytes[sourcepos - i] : pad;
        } else {
            int rawlength = rawbytes.length;

            for (int i = 0; i < length; i++)
                bytes[start + i] = (i < rawlength) ? rawbytes[i] : pad;
        }

        stringvalue = null;

        if (rawbytes.length > length)
            throw (new IllegalArgumentException("Value too long for field"));
    }
    /**
     * Set the message bytes to a new array.
     *
     * @param messagebytes  byte array containing bytes for message.
     */
    public void setMessageBytes(byte[] messagebytes) {
        bytes = messagebytes;
        reset();
    }
    /**
     * Assign new value to field with a string.
     *
     * @param     newvalue the new value for the field.
     * @exception IllegalArgumentException
     *              if the field cannot contain the new value.
     */
    public abstract void setString(String newvalue)
            throws IllegalArgumentException;
    /**
     * Set the tag value for this field
     *
     * @param fieldtag tag name for this field.
     */
    public void setTag(String fieldtag) {
        tag = fieldtag;
    }

    public int getCasesensitive() {
        return casesensitive;
    }

    public void setCasesensitive(int casesensitive) {
        this.casesensitive = casesensitive;
    }

}
