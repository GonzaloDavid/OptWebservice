package rpg.core;




import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;



/**
 * This type was created in VisualAge.
 */
public class Util {
    /**
     * Util constructor comment.
     */
    public Util() {
        super();
    }

    /**
     * This method was created in VisualAge.
     */
    public static String addDot(String number) {
        String result = "";

        if (number.length() == 1) {
            number = number + "00";
        }
        result = number.substring(0, number.length() - 2) + "."
                + number.substring(number.length() - 2);
        return (result);
    }
    /**
     * This method was created in VisualAge.
     */
    public static String addComma(String number) {
        String result = "";

        if(number.startsWith("-")) {
            if(number.length()>7){
                result = number.substring(0, number.length()-6) + "," + number.substring(number.length()-6, number.length()-3) + "," + number.substring(number.length()-3);
            }else{
                if(number.length()>4){
                    result = number.substring(0, number.length()-3) + "," + number.substring(number.length()-3);
                }else{
                    result = number;
                }
            }
        }else{
            if(number.length()>6){
                result = number.substring(0, number.length()-6) + "," + number.substring(number.length()-6, number.length()-3) + "," + number.substring(number.length()-3);
            }else{
                if(number.length()>3){
                    result = number.substring(0, number.length()-3) + "," + number.substring(number.length()-3);
                }else{
                    result = number;
                }
            }
        }
        return(result);
    }
    /**
     * This method was created in VisualAge.
     */
    public static String addLeftChar(char ch, int len, String str) {

        for (int i = str.length(); i < len; i++) {
            str = ch + str;
        }

        return (str);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String fcolorCCY(String num) {

        num = formatCCY(num);
        if (num != null) {
            if (num.startsWith("-"))
                num = "<FONT COLOR=RED>" + num + "</FONT>";
        }
        return (num);
    }
    /**
     * This method was created in VisualAge.
     */
    public static String fcolorInt(String num) {

        if(num.startsWith("-"))
            num = "<FONT COLOR=RED>" + num + "</FONT>";

        return (num);

    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String formatAdd(String s1, String s2, String s3) {
        String r = "";

        if (!s1.trim().equals("")) {
            r += s1.trim() + "<br>";
        }
        if (!s2.trim().equals("")) {
            r += s2.trim() + "<br>";
        }
        if (!s3.trim().equals("")) {
            r += s3.trim() + "<br>";
        }
        if (r.trim().equals("")) {
            r = "&nbsp;";
        }
        return r;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String formatBlank(String s) {
        String r = null;

        if (s.trim().equals("&nbsp;")) {
            r = "";
        } else {
            r = s.trim();
        }
        return r;
    }

    public static String formatCCY(BigDecimal value) {
        return DecimalField.formatCCY(value);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String formatCCY(double value) {
        return DecimalField.formatCCY(value);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String formatCCY(String value) {
        double numDouble = 0.0;

        try {
            numDouble = Double.parseDouble(value);
        } catch (NumberFormatException e1) {
            try {
                numDouble = DecimalField.parseDouble(value);
            } catch (NumberFormatException e2) {
                return value;
            }
        }
        return DecimalField.formatCCY(numDouble);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String formatCCY(double value, String CCYS) {
        return CCYS + formatCCY(value);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String formatCCY(String value, String CCYS) {
        return CCYS + formatCCY(value);
    }

    /**
     * This method was created in VisualAge.
     */
    public static double parseCCYtoDouble(String value) {
        double numDouble = 0.0;
        char spt = DecimalField.getDecimalSeparator();

        value = takeCharacter(value, DecimalField.getGroupingSeparator());
        if (spt != '.')
            value = value.replace(spt, '.');
        numDouble = Double.parseDouble(value);
        return (numDouble);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String takeCharacter(String s, char ch) {
        String result = null;
        int posi = s.indexOf(ch);
        if (posi > -1) {
            while (posi > -1) {
                result = s.substring(0, posi);
                result = result + s.substring(posi + 1);
                posi = result.indexOf(ch);
                s = result;
            }
        } else {
            result = s;
        }
        return (result);
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String formatCell(String s) {
        String r = null;
        String rs = null;
        String ls = null;
        int pos = 0;
        if (s.trim().equals("")) {
            r = "&nbsp;";
        } else {
            s = s.trim();
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
            r = s;
        }
        return r;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     * @return java.lang.String
     * @param d1
     *            java.lang.String
     * @param d2
     *            java.lang.String
     * @param d3
     *            java.lang.String
     */
    public static String formatDate(String d1, String d2, String d3) {

        d1 = addLeftChar('0', 2, d1);
        d2 = addLeftChar('0', 2, d2);
        d3 = addLeftChar('0', 2, d3);

        if (d1.equals("00") && d2.equals("00") && d3.equals("00"))
            return "&nbsp;";
        else
            return (d1 + "/" + d2 + "/" + d3);
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String formatID(String c, String l) {
        String fc = "";
        for (int i = 0; i < (9 - c.length()); i++) {
            fc += "0";
        }
        String fl = "";
        for (int i = 0; i < (4 - l.length()); i++) {
            fl += "0";
        }
        return (fc + c + fl + l);
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     */
    public static String formatTime(String t) {

        int len = t.length();
        String time = (len < 6) ? addLeftChar('0', 6, t) : t;
        String hh = addLeftChar('0', 2, time.substring(0, 2));
        String mm = addLeftChar('0', 2, time.substring(2, 4));
        String ss = addLeftChar('0', 2, time.substring(4, time.length()));

        return hh + ":" + mm + ":" + ss;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     */
    public static String formatYear(int y) {

        return formatYear(Integer.toString(y));
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     * @return java.lang.String
     */
    public static String formatYear(String y) {

        if (y.length() < 4) {
            try {
                int aux = Integer.parseInt(y);
                if (aux == 0) {
                    y = "2000";
                } else if (aux > 52) {
                    y = "19" + y;
                } else {
                    y = "20" + justifyRight(y, 2);
                }
            } catch (Exception e) {
                y = "";
            }
        }

        return y;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String justifyLeft(String c, int l) {
        String fc = "";
        for (int i = 0; i < (l - c.length()); i++) {
            fc += "0";
        }
        return (c + fc);
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String justifyRight(String c, int l) {
        String fc = "";
        for (int i = 0; i < (l - c.length()); i++) {
            fc += "0";
        }
        return (fc + c);
    }

    /**
     * Insert the method's description here. Creation date: (7/13/2000 5:18:52
     * PM)
     *
     * @return java.lang.String
     * @param s
     *            java.lang.String
     */
    public static String leftValue(String s) {

        int pos = 0;
        for (pos = 0; pos < s.length(); pos++) {
            if (s.charAt(pos) == '_')
                break;
        }
        return (s.substring(0, pos));
    }

    /**
     * Insert the method's description here. Creation date: (7/13/2000 5:18:52
     * PM)
     *
     * @return java.lang.String
     * @param s
     *            java.lang.String
     */
    public static String rightValue(String s) {

        int pos = 0;
        for (pos = 0; pos < s.length(); pos++) {
            if (s.charAt(pos) == '_')
                break;
        }
        if (pos == s.length())
            return ("0");
        else
            return (s.substring(pos + 1));
    }

    /**
     * This method was created in VisualAge.
     */
    public static String[] splitField(String field, int row, int col) {
        String[] result = new String[row];
        for (int i = 0; i < row; i++) {
            result[i] = "";
        }
        if (field.length() > 0) {
            if (field.length() <= col) {
                result[0] = field;
            } else {
                int enter = field.length() / col;
                double rest = field.length() % col;
                if (rest > 0) {
                    enter++;
                }
                for (int i = 0; i < enter; i++) {
                    if (i == enter - 1) {
                        result[i] = field.substring(i * col, field.length());
                    } else {
                        result[i] = field.substring(i * col, i * col + col);
                    }
                }
            }
        }

        return (result);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String takeComa(String number) {
        String result = "";
        int posi = number.indexOf(",");
        if (posi > -1) {
            while (posi > -1) {
                result = number.substring(0, posi);
                result = result + number.substring(posi + 1);
                posi = result.indexOf(",");
                number = result;
            }
        } else {
            result = number;
        }
        return (result);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String takeDot(String number) {
        String result = "";
        int posi = number.indexOf(".");
        if (posi > -1) {
            result = number.substring(0, posi);
            result = result + number.substring(posi + 1);
        } else {
            result = number + "00";
        }
        return (result);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String addRightChar(char ch, int len, String str) {

        for (int i = str.length(); i < len; i++) {
            str = str + ch;
        }

        return (str);
    }

    public static String addRightString(String ch, int len, String str) {
        int l = str.length();
        while (l < len) {
            str += ch;
            l = str.length();
        }
        return (str);
    }

    /**
     * This method was created in VisualAge.
     */
    public static String addLeftZeros(int len, String str) {

        for (int i = str.length(); i < len; i++) {
            str = "0" + str;
        }

        return (str);
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String concat(String s[], String ch) {
        String r = "";

        int count = 0;
        while (true) {
            try {
                if (!r.equals("") && !s[count].trim().equals(""))
                    r += ch + s[count].trim();
                else
                    r += s[count].trim();
                count++;
            } catch (Exception e) {
                break;
            }
        }
        return r;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String concatBR(String s[]) {
        String r = "";

        int count = 0;
        while (true) {
            try {
                if (!r.equals("") && !s[count].trim().equals(""))
                    r += "<BR>" + s[count].trim();
                else
                    r += s[count].trim();
                count++;
            } catch (Exception e) {
                break;
            }
        }
        return r;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     * @return java.lang.String
     * @param d1
     *            java.lang.String
     * @param d2
     *            java.lang.String
     * @param d3
     *            java.lang.String
     */
    public static String fcolorDate(String d1, String d2, String d3,
                                    String mature) {

        d1 = addLeftChar('0', 2, d1);
        d2 = addLeftChar('0', 2, d2);
        d3 = addLeftChar('0', 2, d3);

        String date = "";
        String fdate = "";

        if (d1.equals("00") && d2.equals("00") && d3.equals("00")) {
            return "&nbsp;";
        } else {
            date = d1 + "/" + d2 + "/" + d3;
            if ("R".equals(mature)) {
                fdate = "<FONT COLOR=RED>" + date + "</FONT>";
            } else if ("O".equals(mature)) {
                fdate = "<FONT COLOR=ORANGE>" + date + "</FONT>";
            } else {
                fdate = date;
            }
            return (fdate);
        }
    }

    public static int parserInteger(String number) {
        return parseInt(number);
    }

    public static int parseInt(String value) {
        int iv;
        try {
            iv = Integer.parseInt(value);
        } catch (Exception e) {
            iv = 0;
        }
        return iv;
    }

    public static long parseLong(String value) {
        long iv;
        try {
            iv = Long.parseLong(value);
        } catch (Exception e) {
            iv = 0;
        }
        return iv;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     * @return java.lang.String
     * @param d1
     *            java.lang.String
     * @param d2
     *            java.lang.String
     * @param d3
     *            java.lang.String
     */
    public static String fcolorDateFull(String fmt, String lg, String d1,
                                        String d2, String d3, String mature) {

        String fdate = "";
        String Dat = formatDateFull(fmt, lg, d1, d2, d3);

        if ("R".equals(mature)) {
            fdate = "<FONT COLOR=RED>" + Dat + "</FONT>";
        } else if ("O".equals(mature)) {
            fdate = "<FONT COLOR=ORANGE>" + Dat + "</FONT>";
        } else {
            fdate = Dat;
        }

        return (fdate);
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     * @return java.lang.String
     */
    public static String formatDate(String format, String d) {
        if(format == null) {
            return formatDate(d);
        }

        d = Util.addLeftChar('0', 8, d);

        DateFormat df = newDateFormat(format);
        Calendar calendar = df.getCalendar();
        calendar.set(Calendar.MONTH, Integer.valueOf(d.substring(0, 2)) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(d.substring(2, 4)));
        calendar.set(Calendar.YEAR, Integer.valueOf(d.substring(4, 8)));
        Date date= calendar.getTime();

        return df.format(date);
    }

    public static String formatDate(String d) {

        d = Util.addLeftChar('0', 8, d);

        if (d.startsWith("00")) {
            return "";
        } else {
            String d1 = d.substring(0, 2);
            String d2 = d.substring(2, 4);
            String d3 = d.substring(4, 8);
            return (d1 + "/" + d2 + "/" + d3);
        }
    }

    public static DateFormat newDateFormat(String format, String lang){
        String pattern = "";

        if ("DMY".equals(format)) {
            pattern = "dd/MM/yyyy";
        } else if ("YMD".equals(format)) {
            pattern = "yyyy/MM/dd";
        } else {
            pattern = "MM/dd/yyyy";
        }
        if (lang == null) {
            return new SimpleDateFormat(pattern);
        } else {
            return new SimpleDateFormat(pattern, new Locale(lang,
                    "", ""));
        }
    }

    public static DateFormat newDateFormat(String format){
        return newDateFormat(format, null);
    }

    public static String formatCustomDate(int month, int day, int year) {
        return formatCustomDate(null, month, day, year);
    }

    public static String formatCustomDate(String format, String month, String day, String year) {
        return formatCustomDate(format, parseInt(month), parseInt(day), parseInt(year));
    }

    public static String formatCustomDate(String format, int month, int day, int year) {
        if (month == 0 && day == 0 && year == 0) {
            return "";
        }
        DateFormat df = newDateFormat(format);

        Calendar calendar = df.getCalendar();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);

        return df.format(calendar.getTime());
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     * @return java.lang.String
     * @param d1
     *            java.lang.String
     * @param d2
     *            java.lang.String
     * @param d3
     *            java.lang.String
     */
    public static String formatDateFull(String fmt, String lg, String d1,
                                        String d2, String d3) {
        d1 = addLeftChar('0', 2, d1);
        d2 = addLeftChar('0', 2, d2);
        d3 = addLeftChar('0', 2, d3);

        String Dat = "";

        int month = 0;
        int day = 0;
        int year = 0;

        if (d1.equals("00") && d2.equals("00") && d3.equals("00")) {
            return "&nbsp;";
        }

        if ("MDY".equals(fmt)) {
            month = parserInteger(d1);
            day = parserInteger(d2);
            year = parserInteger(d3);
        } else if ("DMY".equals(fmt)) {
            month = parserInteger(d2);
            day = parserInteger(d1);
            year = parserInteger(d3);
        } else if ("YMD".equals(fmt)) {
            month = parserInteger(d2);
            day = parserInteger(d3);
            year = parserInteger(d1);
        } else if ("YDM".equals(fmt)) {
            month = parserInteger(d3);
            day = parserInteger(d2);
            year = parserInteger(d1);
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);

        lg = lg.equalsIgnoreCase("s") ? "es" : "en";
        Locale locale = new Locale(lg, "", "");

        if ("MDY".equals(fmt)) {
            Dat = calendar
                    .getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
                    + " - " + day + " - " + year;
        } else if ("DMY".equals(fmt)) {
            Dat = day
                    + " - "
                    + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
                    locale) + " - " + year;
        } else if ("YMD".equals(fmt)) {
            Dat = year
                    + " - "
                    + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
                    locale) + " - " + day;
        } else if ("YDM".equals(fmt)) {
            Dat = year
                    + " - "
                    + day
                    + " - "
                    + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG,
                    locale);
        }

        return (Dat);
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:38:43
     * PM)
     *
     * @return java.lang.String
     * @param d1
     *            java.lang.String
     * @param d2
     *            java.lang.String
     * @param d3
     *            java.lang.String
     */
    public static String formatCustomDate(String fmt_eibs, String mask,
                                          String lang, String d1, String d2, String d3) {
        String Month = "";
        String Day = "";
        String Year = "";

        lang = (lang.equalsIgnoreCase("s") || lang.equalsIgnoreCase("es")) ? "es" : "en";

        DateFormat myDateFormat = new SimpleDateFormat(mask, new Locale(lang,
                "", ""));

        d1 = addLeftChar('0', 2, d1);
        d2 = addLeftChar('0', 2, d2);
        d3 = addLeftChar('0', 2, d3);

        if (d1.equals("00") && d2.equals("00") && d3.equals("00")) {
            return "&nbsp;";
        }

        if (fmt_eibs.equals("MDY")) {
            Month = d1;
            Day = d2;
            Year = d3;
        } else if (fmt_eibs.equals("DMY")) {
            Month = d2;
            Day = d1;
            Year = d3;
        } else if (fmt_eibs.equals("YMD")) {
            Month = d2;
            Day = d3;
            Year = d1;
        } else if (fmt_eibs.equals("YDM")) {
            Month = d3;
            Day = d2;
            Year = d1;
        }

        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Integer.parseInt(formatYear(Year)), Integer
                .parseInt(Month) - 1, Integer.parseInt(Day));
        Date myDate = myCalendar.getTime();

        String myReturn = myDateFormat.format(myDate);
        return myReturn;
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }


    public static String getMonthLetters(final int n,String lang) {
        String mes[]   = {"Enero",   "Febrero",  "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        String month[] = {"January", "February", "March", "April", "May",  "June",  "July",  "August", "September",  "October", "November",   "December"};
        String mlet = "";
        if ((lang.equalsIgnoreCase("s") || lang.equalsIgnoreCase("es"))){//spanish
            mlet = mes[n-1];
        }else{
            mlet = month[n-1];
        }
        return mlet;
    }

    /**
     * Insert the method's description here. Creation date: (6/19/2000 3:13:46
     * PM)
     *
     * @return java.lang.String
     */
    public static String trim(String s) {
        return s.trim();
    }

    /**
     * metodo para verificar si un archivo existe
     *
     * */
    public static boolean existFile(String filename) {
        boolean exists = false;
        try {
            java.io.File f = new java.io.File(filename);
            exists = f.exists();
        } catch (Exception e) {
            exists = false;
        }
        return exists;
    }

    public static String unformatHTML(String s) {

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

    public static String replace(String Text, String Old, String New) {
        if (Old.length() == 0)
            return Text;
        StringBuffer buf = new StringBuffer();
        int i = 0, j = 0;
        while ((i = Text.indexOf(Old, j)) > -1) {
            buf.append(Text.substring(j, i) + New);
            j = i + Old.length();
        }
        if (j < Text.length())
            buf.append(Text.substring(j));
        return buf.toString();
    }

    public static HashMap getPropertiesKeysValues(String file)
            throws MissingResourceException {
        HashMap keys_values = new HashMap();

        try {
            PropertyResourceBundle props = (PropertyResourceBundle) PropertyResourceBundle
                    .getBundle(file);

            Enumeration enumera = props.getKeys();
            while (enumera.hasMoreElements()) {
                String strKey = (String) enumera.nextElement();
                keys_values.put(strKey, props.getString(strKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys_values;
    }

    public static JBObjList getYearList(int begin, int ending) {
        JBObjList result = new JBObjList();

        while (begin <= ending) {
            result.addRow(String.valueOf(begin));
            begin++;
        }

        return result;
    }

    /**
     * Format Date and Time XX/XX/XX HH:MM:SS Creation date: (7/3/2008 3:38:43
     * PM) Format and convert from MDY to fmt
     *
     * @return java.lang.String
     */
    public static String formatDateTime(String f, String t) {

        String d = " ";
        t = t.length() == 11 ? "0" + t : t;

        if (t.length() == 12) {
            String d1 = t.substring(0, 2);
            String d2 = t.substring(2, 4);
            String d3 = t.substring(4, 6);
            String t1 = t.substring(6, 8);
            String t2 = t.substring(8, 10);
            String t3 = t.substring(10, 12);

            if (f.equals("MDY")) {
                d = (d1 + "/" + d2 + "/" + d3 + " " + t1 + ":" + t2 + ":" + t3);
            } else if (f.equals("DMY")) {
                d = (d2 + "/" + d1 + "/" + d3 + " " + t1 + ":" + t2 + ":" + t3);
            } else if (f.equals("YMD")) {
                d = (d3 + "/" + d1 + "/" + d2 + " " + t1 + ":" + t2 + ":" + t3);
            } else if (f.equals("YDM")) {
                d = (d3 + "/" + d2 + "/" + d1 + " " + t1 + ":" + t2 + ":" + t3);
            }
        }

        return (d);

    }

    public final static String getMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        String result = Integer.toString(month);
        result = result.length() == 1 ? "0" + result : result;
        return (result);
    }

    public final static String getDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String result = Integer.toString(day);
        result = result.length() == 1 ? "0" + result : result;
        return (result);
    }

    public final static String getYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String result = Integer.toString(year);
        return (result.substring(2));
    }

    public static double round(double val, int places) {
        long factor = (long) Math.pow(10, places);

        // Shift the decimal the correct number of places
        // to the right.
        val = val * factor;

        // Round to the nearest integer.
        long tmp = Math.round(val);

        // Shift the decimal the correct number of places
        // back to the left.
        return (double) tmp / factor;
    }

    public static float parseFloat(String number) {
        try {
            return Float.parseFloat(number);
        } catch (Exception e) {
            return Float.parseFloat("0");
        }
    }

    public static BigDecimal parseBigDecimal(String number){
        try {
            return new BigDecimal(number);
        } catch (Exception e) {
            return new BigDecimal("0");
        }
    }


    public static String formatJavascript(String s) {
        s = s.trim();
        s = replace(s, "'", "\\'");
        s = replace(s, "\"" + "", "&quot;");//replace(s, "\"" + "", "'+dq+'"); 11800 xd se modifica para que utilice las comillas html y pueda utilziar javascript
        s = replace(s, "&#39;" + "", "\\'");
        return s;
    }

    public static String formatJavascript(MessageField field) {
        return formatJavascript(field.toString()).trim();
    }

    public static final char[] XML_SPECIAL_CHARS = new char[]{'"','&','\'', '<', '>'};
    public static final String[] XML_UNICODE_CODES = new String[]{"&#34;","&#38;","&#39;", "&#60;", "&#62;"};

	/*
	public static String escapeXml(String inputString) {
		if (inputString == null)
			return null;
		StringBuffer sbOutput = new StringBuffer();

		char nx;
		for (int i = 0; i < inputString.length(); i++) {
			nx = inputString.charAt(i);
			String aux = "" + nx;
			for (int j = 0; j < XML_SPECIAL_CHARS.length; j++) {
				if (nx == XML_SPECIAL_CHARS[j]) {
					aux = XML_UNICODE_CODES[j];
					break;
				}
			}
			sbOutput.append(aux);
		}
		return sbOutput.toString();
	}
	*/

    public static final char[] JSON_TO_TAKE = new char[]{'"','\\','\b', '\f', '\n', '\r', '\t'};
    public static final String[] JSON_TO_REPLACE =  new String[]{
            "Á", "A", "á", "a", "É", "E", "é", "e", "Í", "I", "í", "i", "Ó", "O", "ó", "o", "Ú", "U", "ú", "u", "ñ", "n"};

    public static String escapeJson(String string){
        String newString = string;
        for (int i = 0; i < JSON_TO_TAKE.length; i++) {
            char mChar = JSON_TO_TAKE[i];
            while (newString.indexOf(mChar) > -1) {
                newString = takeCharacter(newString, mChar);
            }
        }
        for (int i = 0; i < JSON_TO_REPLACE.length; i = i + 2) {
            String mChar = JSON_TO_REPLACE[i];
            while (newString.indexOf(mChar) > -1) {
                newString = replace(newString, mChar, JSON_TO_REPLACE[i + 1]);
            }
        }
        return newString;
    }



    public static String getTimeFromTimestamp(String timestamp) {
        try {
            return timestamp.substring(11).replace('.', ':');
        } catch (Exception e) {
            return "00:00:00:000";
        }
    }

    public static String getTimeFromTimestamp(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        return formatter.format(date);
    }

    public static boolean isWeekend(int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        day = cal.get(Calendar.DAY_OF_WEEK);
        return (day == Calendar.SATURDAY || day == Calendar.SUNDAY);
    }

    public static String parserDate(String date, int key) {
        if (date == null || date.equals("")) return "";
        switch (key) {
            case Calendar.YEAR:
                if (date.length() == 6) {
                    return date.substring(0, 2);
                } else if (date.length() == 8) {
                    return date.substring(0, 4);
                } else if (date.length() == 10) {
                    return date.substring(0, 4);
                } else {
                    return "";
                }
            case Calendar.MONTH:
                if (date.length() == 6) {
                    return date.substring(2, 4);
                } else if (date.length() == 8) {
                    return date.substring(4, 6);
                } else if (date.length() == 10) {
                    return date.substring(5, 7);
                } else {
                    return "";
                }
            case Calendar.DAY_OF_MONTH:
                if (date.length() == 6) {
                    return date.substring(5);
                } else if (date.length() == 8) {
                    return date.substring(7);
                } else if (date.length() == 10) {
                    return date.substring(8);
                } else {
                    return "";
                }
            default:
                return "";
        }
    }


    public static String getReqParameter(String paramName, HttpServletRequest req, String fallbackValue) {
        String varReq = req.getParameter(paramName);
        String fallback = fallbackValue == null ? "" : fallbackValue;
        boolean isNotValidParameter = (varReq == null || varReq.equals("undefined"));

        return (isNotValidParameter) ?  fallback : varReq.trim() ;
    }

    public static String getReqParameter(String paramName, HttpServletRequest req) {
        return getReqParameter(paramName,req,"");
    }

    @SuppressWarnings("unchecked")
    public static JBObjList sortByDate(List<Object> oList, String format, final String sMonth, final String sDay, final String sYear, final String otherCompare){
        List<Object> list = new LinkedList<Object>(oList);

        final DateFormat df = newDateFormat(format);

        Collections.sort( list, new Comparator<Object> (){
            public int compare( Object o1, Object o2 ){
                int compare;

                String month = "0";
                String day = "0";
                String year = "0";
                Calendar calendar;
                //Date Object 1
                month = String.valueOf(BeanUtils.getProperty(o1, sMonth));
                day = String.valueOf(BeanUtils.getProperty(o1, sDay));
                year = String.valueOf(BeanUtils.getProperty(o1, sYear));

                calendar = df.getCalendar();
                calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
                calendar.set(Calendar.YEAR, Integer.valueOf(year));

                Date dateO1 = calendar.getTime();

                //Date Object 2
                month = String.valueOf(BeanUtils.getProperty(o2, sMonth));
                day = String.valueOf(BeanUtils.getProperty(o2, sDay));
                year = String.valueOf(BeanUtils.getProperty(o2, sYear));

                calendar = df.getCalendar();
                calendar.set(Calendar.MONTH, Integer.valueOf(month) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
                calendar.set(Calendar.YEAR, Integer.valueOf(year));

                Date dateO2 = calendar.getTime();

                compare = dateO1.before(dateO2) ? -1 : dateO1.after(dateO2) ? 1 : 0;

                if(compare == 0 && otherCompare != null){
                    Comparable compareFieldO1 = (Comparable) BeanUtils.getProperty(o1, otherCompare);
                    Comparable compareFieldO2 = (Comparable) BeanUtils.getProperty(o2, otherCompare);

                    compare = compareFieldO1.compareTo(compareFieldO2);
                }

                return compare;
            }
        });

        return new JBObjList(list);
    }

    public static String getStringField(MessageRecord msg, String field) {
        try {
            return msg.getField(field).getString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String toStringField(MessageRecord msg, String field) {
        try {
            return msg.getField(field).toString();
        } catch (Exception e) {
            return "";
        }
    }
    /*
    public static JSONObject getJSON(MessageRecord mr, String [] ExcludeFields) {
        JSONObject retJSON = new JSONObject();
        int fieldCount = mr.getFieldCount();
        String pairValue = "";

        for(int i = 0; i < fieldCount; i++) {
            try {
                if(!mr.getField(i).getTag().startsWith("H0")){//Header Information not send to Client
                    if(null != ExcludeFields){
                        if(contains(ExcludeFields, mr.getField(i).getTag())){
                            continue;
                        }
                    }

                    pairValue = mr.getField(i).toString();
                    if (pairValue != null) {
                        retJSON.put(mr.getField(i).getTag(), pairValue.trim());
                    }
                }
            } catch (Exception e) {

            }
        }
        return retJSON;
    }

     */

    /*
    public static JSONObject getJSON(MessageRecord mr) {
        return getJSON(mr, null);
    }

     */

    public static <T> boolean contains(final T[] array, final T var) {
        Boolean contain = false;
        for (final T element : array){
            if (element == var || var != null && var.equals(element)){
                contain = true;
            }
        }
        return contain;
    }

    public static Object cloneObject(Object obj)
            throws IOException{
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        Object object = null;
        try{
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);

            byte[] byteData = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            object = (Object) new ObjectInputStream(bais).readObject();
        }catch(Exception e){
        } finally {
            if(null != oos){
                oos.flush();
                oos.close();
            }
            if(null != baos){
                baos.close();
            }
        }
        return object;
    }

    public static java.sql.Date getSqlDate(BigDecimal month, BigDecimal day, BigDecimal year) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month.intValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day.intValue());
        calendar.set(Calendar.YEAR, year.intValue());

        return new java.sql.Date(calendar.getTime().getTime());
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getMACAddress(String ip) {

        Pattern macpt = null;

        // Find OS and set command according to OS
        String OS = System.getProperty("os.name").toLowerCase();

        String[] cmd;
        if (OS.contains("win")) {
            // Windows
            macpt = Pattern
                    .compile("[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+");
            String[] a = { "arp", "-a", ip };
            cmd = a;
        } else {
            // Mac OS X, Linux
            macpt = Pattern
                    .compile("[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+");
            String[] a = { "arp", ip };
            cmd = a;
        }

        try {
            // Run command
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            // read output with BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line = reader.readLine();

            // Loop trough lines
            while (line != null) {
                Matcher m = macpt.matcher(line);

                // when Matcher finds a Line then return it as result
                if (m.find()) {
                    System.out.println("Found");
                    System.out.println("MAC: " + m.group(0));
                    return m.group(0);
                }

                line = reader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return empty string if no MAC is found
        return "";
    }


    /**
     * Quita los espacios en blanco adicionales en un texto.
     * @param texto
     * @return
     */
    public static String removeExtraBlanks(String texto) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
        StringBuilder buff = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            buff.append(" ").append(tokens.nextToken());
        }
        return buff.toString();
    }



}