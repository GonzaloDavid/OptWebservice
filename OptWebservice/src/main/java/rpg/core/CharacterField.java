package rpg.core;

/**
 * Character field for use in Message objects.  This class is designed for
 * use in the generated Message objects.  It is not designed for use outside
 * of those objects.
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 2:14:03 PM)
 *
 * @author ?
 *
 * @tag    Date          Flag        Description
 */

public class CharacterField extends MessageField
{
    final static String COPYRIGHT =  "Copyright (C) 2000 IBM Corp. All Rights Reserved";
    /**
     * Constructs a character field over the specified message byte array at the specified position.
     *
     * @param messagebytes the byte array containing the actual message sent and received.
     * @param fieldstart   the starting position of this field in the byte array.
     * @param fieldlength  the length of the this field in the byte array.
     */
    public CharacterField(byte[] messagebytes,
                          int fieldstart,
                          int fieldlength)
    {
        super(messagebytes, fieldstart, fieldlength);
    }
    /**
     * Constructs a character field over the specified message byte array at the
     * specified position with a tag value.
     *
     * @param messagebytes the byte array containing the actual message sent and
     *                     received.
     * @param fieldstart   the starting position of this field in the byte array.
     * @param fieldlength  the length of the this field in the byte array.
     * @param fieldtag     the tag for this field.
     */
    public CharacterField(byte[] messagebytes,
                          int fieldstart,
                          int fieldlength,
                          String fieldtag)
    {
        super(messagebytes, fieldstart, fieldlength, fieldtag);
    }
    /**
     * Returns the type of message field.
     * Implementation of abstract method required by MessageField superclass.
     *
     * @return MessageField.CHARACTERFIELD
     */
    public int getType()
    {
        return CHARACTERFIELD;
    }
    /**
     * Initialize field to blanks.
     */
    public void initialize()
    {
        initialize((byte)' ');
    }
    /**
     * Returns a String representation of the field.
     * Implementation of abstract method required by MessageField superclass.
     *
     * @return String containing displayable version of field value.
     */
    String makeString()
    {
        String rt = new String(bytes, start, length);
        return formatHTML(rt);
    }
    /**
     * Sets the field to a new value.
     * Implementation of abstract method required by MessageField superclass.
     *
     * new value to upper case if tag is not "E01LAN" (datapro's change)
     *
     * @exception IllegalArgumentException
     *              if the field cannot contain the new value.
     */
    public void setString(String newvalue) throws IllegalArgumentException
    {
        if (!this.tag.equals("E01LAN" ) && this.casesensitive == 0) {
            newvalue = newvalue.toUpperCase();
        } else if (this.casesensitive == 1) {
            newvalue = newvalue.toLowerCase();
        }
        setBytes(unformatHTML(newvalue), (byte)' ', ALIGNLEFT);
    }

    /**
     * Format String for HTML.
     * Creation date: (8/28/2001 11:21:23 AM)
     * @return java.lang.String
     * @param s java.lang.String
     */
    private String formatHTML(String s) {

        String rs = null;
        String ls = null;
        int pos = 0;
        s = s.trim();
        if (!s.equals("")) {
            while (s.indexOf("'") != -1) {
                pos = s.indexOf("'");
                ls = s.substring(0, pos);
                rs = s.substring(pos + 1, s.length());
                s = ls + "&#39;" + rs;
            }

            while (s.indexOf("\"") != -1) {
                pos = s.indexOf("\"");
                ls = s.substring(0, pos);
                rs = s.substring(pos + 1, s.length());
                s = ls + "&#34;" + rs;
            }
        }
        return s;

    }

    /**
     * Unformat String for HTML.
     * Creation date: (8/28/2001 11:21:23 AM)
     * @return java.lang.String
     * @param s java.lang.String
     */
    private String unformatHTML(String s) {

        String rs = null;
        String ls = null;
        int pos = 0;
        s = s.trim();
        if (!s.equals("")) {
            while (s.indexOf("&#39;") != -1) {
                pos = s.indexOf("&#39;");
                ls = s.substring(0, pos);
                rs = s.substring(pos + 5, s.length());
                s = ls + "'" + rs;
            }

            while (s.indexOf("&#34;") != -1) {
                pos = s.indexOf("&#34;");
                ls = s.substring(0, pos);
                rs = s.substring(pos + 5, s.length());
                s = ls + "\"" + rs;
            }
        }
        return s;

    }

    public void setStringUD(String newvalue) throws IllegalArgumentException {

        setBytes(newvalue, ( byte ) ' ' , ALIGNLEFT);

    }

    public String getStringUD()
    {
        return new String(bytes, start, length);
    }

    public String toString() {
        return new String(bytes, start, length);
    }

}
