package rpg.core;

import java.math.BigDecimal;
import java.text.*;
import java.util.Locale;

/**
 * Decimal number field for use in Message objects.  This class is used by
 * the generated classes, it is not designed for use outside of those objects.
 *
 * @tag Copyright (C) 2000 IBM Corp. All Rights Reserved.
 *
 * @tag Start date: (04/25/00 2:26:54 PM)
 *
 * @author ?
 *
 * @tag    Date          Flag        Description
 */

public class DecimalField extends MessageField {
    public 	final static String DECIMAL_FORMAT_MASK = "#,###,###,###,##0.00";
    private	final static String INTEGER_DECIMAL_FORMAT_MASK = "#,###,###,###,##0.";
    final static String COPYRIGHT =
            "Copyright (C) 2000 IBM Corp. All Rights Reserved";

    static DecimalFormat decimalFormatNumber = null;
    BigDecimal decimalvalue = null;
    int decimals;

    /**
     * Constructs a decimal field over the specified byte array at the specified position.
     *
     * @param messagebytes  the byte array containing the actual message sent and received.
     * @param fieldstart    the starting position of this field in the byte array.
     * @param fieldlength   the length of the this field in the byte array.
     * @param fielddecimals the number of digits to the right of the decimal point.
     */
    public DecimalField(
            byte[] messagebytes,
            int fieldstart,
            int fieldlength,
            int fielddecimals) {
        super(messagebytes, fieldstart, fieldlength);
        decimals = fielddecimals;
    }
    /**
     * Constructs a decimal field over the specified byte array at the specified position with a tag value.
     *
     * @param messagebytes  the byte array containing the actual message sent and received.
     * @param fieldstart    the starting position of this field in the byte array.
     * @param fieldlength   the length of the this field in the byte array.
     * @param fielddecimals the number of digits to the right of the decimal point.
     * @param fieldtag      the tag for this field.
     */
    public DecimalField(
            byte[] messagebytes,
            int fieldstart,
            int fieldlength,
            int fielddecimals,
            String fieldtag) {
        super(messagebytes, fieldstart, fieldlength, fieldtag);
        decimals = fielddecimals;
    }
    /**
     * Get the value of this field as a BigDecimal.
     *
     * @return BigDecimal containing field value.
     */
    public BigDecimal getBigDecimal() {
        if (decimalvalue == null)
            decimalvalue =
                    new BigDecimal(new String(bytes, start, length).trim());

        return decimalvalue;
    }
    /**
     * Return the number of digits to the right of the decimal point for this number.
     *
     * @return number of digits to right of decimal.
     */
    public int getDecimals() {
        return decimals;
    }
    /**
     * Get the total number of digits in this number.  The actual message
     * buffer length will be 1 (or 2 if there are digits to the right of the decimal)
     * bytes longer than this value.
     *
     * @return The total number of digits for this number.
     */
    public int getLength() {
        return (length - 1) - (decimals > 0 ? 1 : 0);
    }
    /**
     * Return the field type.
     * Implementation of abstract method required by MessageField superclass.
     *
     * @return MessageField.DECIMALFIELD
     */
    public int getType() {
        return DECIMALFIELD;
    }
    /**
     * Initialize field to zero.
     */
    public void initialize() {
        initialize((byte) '0');
        bytes[start] = (byte) ' ';
        if (decimals != 0)
            bytes[start + length - decimals - 1] = (byte) '.';
    }
    /**
     * Return a String representation of the field.
     * Implementation of abstract method required by MessageField superclass.
     *
     * @return String containing displayable version of field value.
     */
    public String makeString() {
        if (decimals == 2) {
            return formatCCY(getBigDecimal());
        } else if (decimals > 0) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            DecimalFormat decimalFormatNumber;
            String mask = INTEGER_DECIMAL_FORMAT_MASK;
            for (int i = 0; i < decimals; i++) {
                mask += "0";
            }
            if (nf instanceof DecimalFormat) {
                decimalFormatNumber = (DecimalFormat) nf;
                decimalFormatNumber.applyPattern(mask);
            } else {
                decimalFormatNumber = new DecimalFormat(mask);
            }
            double numDouble = Double.parseDouble(getBigDecimal().toString());
            return decimalFormatNumber.format(numDouble);
        } else {
            return getBigDecimal().toString();
        }
    }
    /**
     * Reset return value for field.
     */
    public void reset() {
        super.reset();
        decimalvalue = null;
    }
    /**
     * Set the value of the field using a BigDecimal.
     *
     * @param     value the new value for the field.
     * @exception NumberFormatException
     *              if the field cannot contain the value.
     */
    public void setBigDecimal(BigDecimal value) throws NumberFormatException {
        String svalue;

        if (value.scale() == decimals)
            decimalvalue = value;
        else
            decimalvalue = value.setScale(decimals, BigDecimal.ROUND_HALF_EVEN);

        svalue = decimalvalue.toString();
        if (svalue.length() > length) {
            initialize();
            throw (new NumberFormatException("Number too large for field"));
        }

        setBytes(svalue, (byte) ' ', ALIGNRIGHT);
    }
    /**
     * Set the value of this field from a String.
     *
     * @param     newvalue the new value for the field.
     * @exception IllegalArgumentException
     *              if the field cannot contain the new value.
     */
    public void setString(String newvalue) throws IllegalArgumentException {
        newvalue = (newvalue.trim().equals("")?"0":newvalue);
        setBigDecimal(new BigDecimal(parse(newvalue).toString()));
    }

    public static String formatCCY(String value) {
        double numDouble = 0.0;

        try {
            numDouble = parseDouble(value);
        } catch (Exception e) {
            return value;
        }
        return formatCCY(numDouble);
    }

    public static String formatCCY(BigDecimal value) {
        double numDouble = 0.0;
        if (value != null) numDouble = value.doubleValue();
        return formatCCY(numDouble);
    }

    public static String formatCCY(double value) {
        return getDecimalFormatNumber().format(value);
    }

    /**
     * This method was created in VisualAge.
     */
    public static double parseDouble(String num) throws NumberFormatException{
        return parse(num).doubleValue();
    }

    /**
     * This method was created in VisualAge.
     */
    public static Number parse(String num) throws NumberFormatException {
        try {
			/*char spt = getDecimalSeparator();
			num = takeCharacter(num, getGroupingSeparator());
			if (spt != '.') num = num.replace(spt, '.');
			return(new BigDecimal(num));*/

            return getDecimalFormatNumber().parse(num);
        } catch(Exception e) {
            throw new NumberFormatException();
        }
    }

    /**
     * Parsing the Integer part by the current JVM locale Number format
     * @param
     */
    public static int parseIntegerPart(String num) throws NumberFormatException {
        int result = 0;
        double numDouble = parseDouble(num);
        result = (int) numDouble;
        return result;
    }

    /**
     * DecimalFormat Object of the current JVM locale Number format
     * @param
     */
    public static synchronized void setDecimalFormatNumber() {
        //NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es","",""));
        NumberFormat nf = NumberFormat.getNumberInstance();

        if (nf instanceof DecimalFormat) {
            decimalFormatNumber = (DecimalFormat) nf;
            decimalFormatNumber.applyPattern(DECIMAL_FORMAT_MASK);
        } else {
            decimalFormatNumber = new DecimalFormat(DECIMAL_FORMAT_MASK);
        }
    }

    /**
     * DecimalFormat Object of the current JVM locale Number format
     * @param
     */
    public static DecimalFormat getDecimalFormatNumber() {
        if (decimalFormatNumber == null) setDecimalFormatNumber();
        return decimalFormatNumber;
    }

    /**
     * This method was created in VisualAge.
     */
    public static DecimalFormatSymbols getDecimalSymbols() {
        return getDecimalFormatNumber().getDecimalFormatSymbols();
    }

    /**
     * @return
     */
    public static char getDecimalSeparator() {
        return getDecimalSymbols().getDecimalSeparator();
    }

    /**
     * @return
     */
    public static char getGroupingSeparator() {
        return getDecimalSymbols().getGroupingSeparator();
    }

    /**
     * This method was created in VisualAge.
     */
    public static String takeCharacter(String s, char ch) {
        String result = null;
        int posi = s.indexOf(ch);
        if(posi > -1){
            while(posi > -1){
                result = s.substring(0,posi);
                result = result + s.substring(posi+1);
                posi = result.indexOf(ch);
                s = result;
            }
        } else {
            result = s;
        }
        return(result);
    }

    public String toString(){
        return makeString();
    }
    public double doubleValue() {
        return getBigDecimal().doubleValue();
    }
    public float floatValue() {
        return getBigDecimal().floatValue();
    }
    public int intValue() {
        return getBigDecimal().intValue();
    }
    public long longValue() {
        return getBigDecimal().longValue();
    }

}