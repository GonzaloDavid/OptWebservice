package rpg.core;

import java.util.*;

public class JBObjList extends ArrayList {

    private int currentRow;

    private int firstRec;
    private int lastRec;
    private boolean showPrev;
    private boolean showNext;

    public JBObjList() {

        init();

    }

    @SuppressWarnings("unchecked")
    public JBObjList(List list) {
        super(list);
    }
    public void addRow(Object newRecord) {
        add(newRecord);
    }

    public void removeRow(int srow) {
        super.remove(srow);
    }

    public Object getList() {
        return this;
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
        if (lastRec != 0)
            return lastRec;
        else
            return firstRec + size() - 1;
    }

    public boolean getNextRow() {

        currentRow++;
        return (currentRow < size());

    }
    /**
     * Insert the method's description here.
     * Creation date: (5/23/2000 4:32:34 PM)
     * @return boolean
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
        return get(currentRow);
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
    public void init() {
        initRow(); // Initialize cuurent row variable

        firstRec = 0;
        lastRec = 0;
        showPrev = false;
        showNext = false;
        clear();
    }
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
     * @deprecated you don't need setNoResult,
     * getNoResult should be returning List.isEmpty(),
     */
    public void setNoResult(boolean newNoResult) {
        //Do Nothing
    }
    public void setRecord(Object value, int srow) {
        set(srow, value);
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
}