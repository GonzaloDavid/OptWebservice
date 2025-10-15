package rpg.core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ogarcia
 * @version $Revision: 1.2 $
 */
public class BeanList {

    /**
     * Field row
     */
    protected int row;
    /**
     * Field currentRow
     */
    protected int currentRow;

    /**
     * Field record
     */
    protected ArrayList record;

    /**
     * Field firstRec
     */
    protected int firstRec;
    /**
     * Field lastRec
     */
    protected int lastRec;
    /**
     * Field showPrev
     */
    protected boolean showPrev;
    /**
     * Field showNext
     */
    protected boolean showNext;
    /**
     * Field noResult
     */
    protected boolean noResult;

    /**
     * Constructor for BeanList
     */
    public BeanList() {

        init();

    }

    /**
     * Method addRow
     * @param newRecord Object
     */
    public void addRow(Object newRecord) {

        record.add(newRecord);

        lastRec = firstRec + row;
        row++;

        noResult = false;
    }

    /**
     * Method getList
     * @return Object
     */
    public Object getList() {
        return record;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/28/2000 11:38:33 AM)
     * @return int
     */
    public int getCurrentRow() {
        return currentRow;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:29:46 PM)
     * @return int
     */
    public int getFirstRec() {
        return firstRec;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:30:07 PM)
     * @return int
     */
    public int getLastRec() {
        return lastRec;
    }
    /**
     * Method getNextRow
     * @return boolean
     */
    public boolean getNextRow() {

        currentRow++;
        return (currentRow < row);

    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:32:34 PM)
     * @return boolean
     * @deprecated subtituted by isEmpty()
     */
    public boolean getNoResult() {
        return isEmpty();
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:30:53 PM)
     * @return String
     */
    public Object getRecord() {
        return record.get(currentRow);
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:31:11 PM)
     * @return boolean
     */
    public boolean getShowNext() {
        return showNext;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:30:53 PM)
     * @return boolean
     */
    public boolean getShowPrev() {
        return showPrev;
    }
    /**
     * Method init
     */
    public void init() {

        record = new ArrayList();

        row = 0; // Initialize internal counter

        initRow(); // Initialize cuurent row variable

        firstRec = 0;
        lastRec = 0;
        showPrev = false;
        showNext = false;
        noResult = true;
    }
    /**
     * Method initRow
     */
    public void initRow() {

        currentRow = -1;

    }
    /**
     * Insert the method's description here.
     * Creation date: (7/28/2000 11:38:33 AM)
     * @param newCurrentRow int
     */
    public void setCurrentRow(int newCurrentRow) {
        currentRow = newCurrentRow;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:29:46 PM)
     * @param newFirstRec int
     */
    public void setFirstRec(int newFirstRec) {

        firstRec = newFirstRec;
        showPrev = (newFirstRec > 1);

    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:30:07 PM)
     * @param newLastRec int
     */
    public void setLastRec(int newLastRec) {
        lastRec = newLastRec;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:32:34 PM)
     * @param newNoResult boolean
     */
    public void setNoResult(boolean newNoResult) {
        noResult = newNoResult;
    }
    /**
     * Method setRecord
     * @param value Object
     * @param srow int
     */
    public void setRecord(Object value, int srow) {

        record.add(srow, value);

    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:31:11 PM)
     * @param newShowNext boolean
     */
    public void setShowNext(boolean newShowNext) {
        showNext = newShowNext;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:30:53 PM)
     * @param newShowPrev boolean
     */
    public void setShowPrev(boolean newShowPrev) {
        showPrev = newShowPrev;
    }

    /**
     * Method isLastRecord.
     * @return boolean
     */
    public boolean isLastRecord() {
        if (row > (currentRow + 1)) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Method isEmpty
     * @return boolean
     */
    public boolean isEmpty() {
        return noResult;
    }

    /**
     * Method isUnique
     * @return boolean
     */
    public boolean isUnique(){
        return (row == 1)? true : false;
    }

    /**
     * @return
     */
    public int size() {
        return row;
    }

    public boolean addAll(Collection list){
        return record.addAll(list);
    }
}