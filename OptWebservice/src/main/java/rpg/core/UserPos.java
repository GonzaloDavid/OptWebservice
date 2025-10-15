package rpg.core;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This type was created in VisualAge.
 */
public class UserPos {
    private java.lang.String purpose; // "NEW", "MAINTENANCE", "INQUIRY"
    private java.lang.String option;  // "CLIENT", "CLIENT_P", "CLIENT_C"
    private java.lang.String identifier;  // Key Number
    private java.lang.String header1;
    private java.lang.String header2;
    private java.lang.String header3;
    private java.lang.String header4;
    private java.lang.String header5;
    private java.lang.String header6;
    private java.lang.String header7;
    private java.lang.String header8;
    private java.lang.String header9;
    private java.lang.String header10;
    private java.lang.String header11;
    private java.lang.String header12;
    private java.lang.String header13;
    private java.lang.String header14;
    private java.lang.String header15;
    private java.lang.String header16;
    private java.lang.String header17;
    private java.lang.String header18;
    private java.lang.String header19;
    private java.lang.String header20;
    private java.lang.String header21;
    private java.lang.String header22;
    private java.lang.String header23;
    private java.lang.String proposalNum;
    private java.lang.String accNum;
    private java.lang.String cusNum;
    private java.lang.String cusNumCxf;
    private java.lang.String porfNum;
    private java.lang.String seqNum;
    private java.lang.String Bank;
    private java.lang.String Branch;
    private java.lang.String GenLedger;
    private java.lang.String creditLineNum;
    private java.lang.String currency;
    private java.lang.String prodCode;
    private java.lang.String cusName;
    private java.lang.String creditLineType;
    private java.lang.String type;
    private java.lang.String menu;
    private java.lang.String prevPage;
    private java.lang.String nextPage;
    private java.lang.String source = "";
    private boolean thereIsMsg = false;
    private java.lang.String accOpt;
    private java.lang.String applicationCode;

    /**
     * UserPos constructor comment.
     */
    public UserPos() {
        super();
        purpose="";
        option="";
        identifier="";
        header1="";
        header2="";
        header3="";
        header4="";
        header5="";
        header6="";
        header7="";
        header8="";
        header9="";
        header10="";
        header11="";
        header12="";
        header13="";
        header14="";
        header15="";
        header16="";
        header17="";
        header18="";
        header19="";
        header20="";
        header21="";
        header22="";
        header23="";
        proposalNum="";
        accNum="";
        cusNum="";
        cusNumCxf="";
        Bank="";
        Branch="";
        creditLineNum="";
        currency="";
        prodCode="";
        cusName="";
        creditLineType="";
        type="";
        thereIsMsg = false;
        redirect="";
        ID="";
        officer="";
        cusType="";
        cusTypeCxf="";
        GenLedger="";
        accOpt="";
        applicationCode="";
    }
    /**
     * @return java.lang.String
     */
    public java.lang.String getProposalNum() {
        return proposalNum;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/28/2000 1:44:10 PM)
     * @return java.lang.String
     */
    public java.lang.String getAccNum() {
        return accNum;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/11/2000 10:16:18 AM)
     * @return java.lang.String
     */
    public java.lang.String getBank() {
        return Bank;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/11/2000 10:16:36 AM)
     * @return java.lang.String
     */
    public java.lang.String getBranch() {
        return Branch;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:25:04 PM)
     * @return java.lang.String
     */
    public java.lang.String getCreditLineNum() {
        return creditLineNum;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/13/2000 3:55:18 PM)
     * @return java.lang.String
     */
    public java.lang.String getCreditLineType() {
        return creditLineType;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:39:31 PM)
     * @return java.lang.String
     */
    public java.lang.String getCurrency() {
        return currency;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:41:26 PM)
     * @return java.lang.String
     */
    public java.lang.String getCusName() {
        return cusName;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/28/2000 1:45:23 PM)
     * @return java.lang.String
     */
    public java.lang.String getCusNum() {
        return cusNum;
    }

    public java.lang.String getCusNumCxf() {
        return cusNumCxf;
    }

    public java.lang.String getPorfNum() {
        return porfNum;
    }
    public void setPorfNum(java.lang.String porfNum) {
        this.porfNum = porfNum;
    }
    public java.lang.String getSeqNum() {
        return seqNum;
    }
    public void setSeqNum(java.lang.String seqNum) {
        this.seqNum = seqNum;
    }
    /**
     * This method was created in VisualAge.
     * @return java.lang.String
     */
    public String getHeader1() {
        return header1;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader10() {
        return header10;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:30:08 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader11() {
        return header11;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:30:39 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader12() {
        return header12;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:01 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader13() {
        return header13;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:14 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader14() {
        return header14;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:28 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader15() {
        return header15;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:41 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader16() {
        return header16;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:54 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader17() {
        return header17;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:32:07 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader18() {
        return header18;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:32:21 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader19() {
        return header19;
    }
    /**
     * This method was created in VisualAge.
     * @return java.lang.String
     */
    public String getHeader2() {
        return header2;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:32:34 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader20() {
        return header20;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/22/2000 12:35:38 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader21() {
        return header21;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/22/2000 6:38:02 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader22() {
        return header22;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/22/2000 6:38:19 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader23() {
        return header23;
    }
    /**
     * This method was created in VisualAge.
     * @return java.lang.String
     */
    public String getHeader3() {
        return header3;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader4() {
        return header4;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader5() {
        return header5;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader6() {
        return header6;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader7() {
        return header7;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader8() {
        return header8;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @return java.lang.String
     */
    public java.lang.String getHeader9() {
        return header9;
    }
    /**
     * This method was created in VisualAge.
     * @return java.lang.String
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * This method was created in VisualAge.
     * @return java.lang.String
     */
    public String getOption() {
        return option;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:41:07 PM)
     * @return java.lang.String
     */
    public java.lang.String getProdCode() {
        return prodCode;
    }
    /**
     * This method was created in VisualAge.
     * @return java.lang.String
     */
    public String getPurpose() {
        return purpose;
    }
    /**
     * Insert the method's description here.
     * Creation date: (8/29/2000 1:01:35 PM)
     * @return boolean
     */
    public boolean getThereIsMsg() {
        return thereIsMsg;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/20/2000 10:34:25 AM)
     * @return java.lang.String
     */
    public java.lang.String getType() {
        return type;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/28/2000 1:44:10 PM)
     * @return java.lang.String
     */
    public java.lang.String getGenLedger() {
        return GenLedger;
    }
    /**
     * Insert the method's description here.
     * Creation date: (8/29/2000 1:01:35 PM)
     * @return boolean
     */
    public boolean isThereIsMsg() {
        return thereIsMsg;
    }
    /**
     * @param newProposalNum java.lang.String
     */
    public void setProposalNum(java.lang.String newProposalNum) {
        proposalNum = newProposalNum;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/28/2000 1:44:10 PM)
     * @param newAccNum java.lang.String
     */
    public void setAccNum(java.lang.String newAccNum) {
        accNum = newAccNum;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/11/2000 10:16:18 AM)
     * @param newBank java.lang.String
     */
    public void setBank(java.lang.String newBank) {
        Bank = newBank;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/11/2000 10:16:36 AM)
     * @param newBranch java.lang.String
     */
    public void setBranch(java.lang.String newBranch) {
        Branch = newBranch;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:25:04 PM)
     * @param newCreditLineNum java.lang.String
     */
    public void setCreditLineNum(java.lang.String newCreditLineNum) {
        creditLineNum = newCreditLineNum;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/13/2000 3:55:18 PM)
     * @param newCreditLineType java.lang.String
     */
    public void setCreditLineType(java.lang.String newCreditLineType) {
        creditLineType = newCreditLineType;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:39:31 PM)
     * @param newCurrency java.lang.String
     */
    public void setCurrency(java.lang.String newCurrency) {
        currency = newCurrency;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:41:26 PM)
     * @param newCusName java.lang.String
     */
    public void setCusName(java.lang.String newCusName) {
        cusName = newCusName;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/28/2000 1:45:23 PM)
     * @param newCusNum java.lang.String
     */
    public void setCusNum(java.lang.String newCusNum) {
        cusNum = newCusNum;
    }

    public void setCusNumCxf(java.lang.String newCusNumCxf) {
        cusNumCxf = newCusNumCxf;
    }

    /**
     * This method was created in VisualAge.
     * @param value java.lang.String
     */
    public void setHeader1(java.lang.String value) {
        header1 = value;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     */
    public void setHeader10(java.lang.String newHeader) {
        header10 = newHeader;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:30:08 PM)
     * @param newHeader11 java.lang.String
     */
    public void setHeader11(java.lang.String newHeader11) {
        header11 = newHeader11;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:30:39 PM)
     * @param newHeader12 java.lang.String
     */
    public void setHeader12(java.lang.String newHeader12) {
        header12 = newHeader12;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:01 PM)
     * @param newHeader13 java.lang.String
     */
    public void setHeader13(java.lang.String newHeader13) {
        header13 = newHeader13;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:14 PM)
     * @param newHeader14 java.lang.String
     */
    public void setHeader14(java.lang.String newHeader14) {
        header14 = newHeader14;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:28 PM)
     * @param newHeader15 java.lang.String
     */
    public void setHeader15(java.lang.String newHeader15) {
        header15 = newHeader15;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:41 PM)
     * @param newHeader16 java.lang.String
     */
    public void setHeader16(java.lang.String newHeader16) {
        header16 = newHeader16;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:31:54 PM)
     * @param newHeader17 java.lang.String
     */
    public void setHeader17(java.lang.String newHeader17) {
        header17 = newHeader17;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:32:07 PM)
     * @param newHeader18 java.lang.String
     */
    public void setHeader18(java.lang.String newHeader18) {
        header18 = newHeader18;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:32:21 PM)
     * @param newHeader19 java.lang.String
     */
    public void setHeader19(java.lang.String newHeader19) {
        header19 = newHeader19;
    }
    /**
     * This method was created in VisualAge.
     * @param value java.lang.String
     */
    public void setHeader2(java.lang.String value) {
        header2 = value;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/15/2000 4:32:34 PM)
     * @param newHeader20 java.lang.String
     */
    public void setHeader20(java.lang.String newHeader20) {
        header20 = newHeader20;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/22/2000 12:35:38 PM)
     * @param newHeader21 java.lang.String
     */
    public void setHeader21(java.lang.String newHeader21) {
        header21 = newHeader21;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/22/2000 6:38:02 PM)
     * @param newHeader22 java.lang.String
     */
    public void setHeader22(java.lang.String newHeader22) {
        header22 = newHeader22;
    }
    /**
     * Insert the method's description here.
     * Creation date: (6/22/2000 6:38:19 PM)
     * @param newHeader23 java.lang.String
     */
    public void setHeader23(java.lang.String newHeader23) {
        header23 = newHeader23;
    }
    /**
     * This method was created in VisualAge.
     * @param value java.lang.String
     */
    public void setHeader3(java.lang.String value) {
        header3 = value;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     * @param newHeader4 java.lang.String
     */
    public void setHeader4(java.lang.String newHeader4) {
        header4 = newHeader4;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     */
    public void setHeader5(java.lang.String newHeader) {
        header5 = newHeader;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     */
    public void setHeader6(java.lang.String newHeader) {
        header6 = newHeader;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     */
    public void setHeader7(java.lang.String newHeader) {
        header7 = newHeader;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     */
    public void setHeader8(java.lang.String newHeader) {
        header8 = newHeader;
    }
    /**
     * Insert the method's description here.
     * Creation date: (5/17/2000 5:05:15 PM)
     */
    public void setHeader9(java.lang.String newHeader) {
        header9 = newHeader;
    }
    /**
     * This method was created in VisualAge.
     * @param value java.lang.String
     */
    public void setIdentifier(String value) {
        identifier = value;
    }
    /**
     * This method was created in VisualAge.
     * @param value java.lang.String
     */
    public void setOption(String value) {
        option = value;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/12/2000 12:41:07 PM)
     * @param newProdCode java.lang.String
     */
    public void setProdCode(java.lang.String newProdCode) {
        prodCode = newProdCode;
    }
    /**
     * This method was created in VisualAge.
     * @param value java.lang.String
     */
    public void setPurpose(String value) {
        purpose = value;
    }
    /**
     * Insert the method's description here.
     * Creation date: (8/29/2000 1:01:35 PM)
     * @param newThereIsMsg boolean
     */
    public void setThereIsMsg(boolean newThereIsMsg) {
        thereIsMsg = newThereIsMsg;
    }
    /**
     * Insert the method's description here.
     * Creation date: (7/20/2000 10:34:25 AM)
     * @param newType java.lang.String
     */
    public void setType(java.lang.String newType) {
        type = newType;
    }

    private java.lang.String cusType;
    private java.lang.String cusTypeCxf;
    private java.lang.String ID;
    private java.lang.String officer;
    private java.lang.String redirect;

    /**
     * Insert the method's description here.
     * Creation date: (8/14/2001 11:27:03 AM)
     * @return java.lang.String
     */
    public java.lang.String getCusType() {
        return cusType;
    }

    public java.lang.String getCusTypeCxf() {
        return cusTypeCxf;
    }

    /**
     * Insert the method's description here.
     * Creation date: (8/8/2001 11:50:24 AM)
     * @return java.lang.String
     */
    public java.lang.String getID() {
        return ID;
    }

    /**
     * Insert the method's description here.
     * Creation date: (8/10/2001 1:16:14 PM)
     * @return java.lang.String
     */
    public java.lang.String getOfficer() {
        return officer;
    }

    /**
     * Insert the method's description here.
     * Creation date: (3/7/2001 4:41:38 PM)
     * @return java.lang.String
     */
    public java.lang.String getRedirect() {
        return redirect;
    }

    /**
     * Insert the method's description here.
     * Creation date: (8/14/2001 11:27:03 AM)
     * @param newCusType java.lang.String
     */
    public void setCusType(java.lang.String newCusType) {
        cusType = newCusType;
    }

    public void setCusTypeCxf(java.lang.String newCusTypeCxf) {
        cusTypeCxf = newCusTypeCxf;
    }

    /**
     * Insert the method's description here.
     * Creation date: (8/14/2001 11:27:03 AM)
     */
    public void setGenLedger(java.lang.String newGenLedger) {
        GenLedger = newGenLedger;
    }

    /**
     * Insert the method's description here.
     * Creation date: (8/8/2001 11:50:24 AM)
     * @param newID java.lang.String
     */
    public void setID(java.lang.String newID) {
        ID = newID;
    }

    /**
     * Insert the method's description here.
     * Creation date: (8/10/2001 1:16:14 PM)
     * @param newOfficer java.lang.String
     */
    public void setOfficer(java.lang.String newOfficer) {
        officer = newOfficer;
    }

    /**
     * Insert the method's description here.
     * Creation date: (3/7/2001 4:41:38 PM)
     * @param newRedirect java.lang.String
     */
    public void setRedirect(java.lang.String newRedirect) {
        redirect = newRedirect;
    }
    /**
     * Returns the accOpt.
     * @return java.lang.String
     */
    public java.lang.String getAccOpt() {
        return accOpt;
    }

    /**
     * Sets the accOpt.
     * @param accOpt The accOpt to set
     */
    public void setAccOpt(java.lang.String accOpt) {
        this.accOpt = accOpt;
    }

    /**
     * @return
     */
    public java.lang.String getNextPage()
    {
        return nextPage;
    }

    /**
     * @return
     */
    public java.lang.String getPrevPage()
    {
        return prevPage;
    }

    /**
     * @param string
     */
    public void setNextPage(java.lang.String string)
    {
        nextPage = string;
    }

    /**
     * @param string
     */
    public void setPrevPage(java.lang.String string)
    {
        prevPage = string;
    }
    public java.lang.String getApplicationCode() {
        return applicationCode;
    }
    public void setApplicationCode(java.lang.String applicationCode) {
        this.applicationCode = applicationCode;
    }
    public java.lang.String getSource() {
        return source;
    }
    public void setSource(java.lang.String source) {
        this.source = source;
    }
    public java.lang.String getMenu() {
        return menu;
    }
    public void setMenu(java.lang.String menu) {
        this.menu = menu;
    }

    public String getSearchType() {
        String searchType = "account";
        if(this.applicationCode.contains("C1")){
            searchType = "customer";
        } else if(this.option.matches(".*(RT|SV|CP|CD|LN|PV|CO|DV|CR|DFT)")){
            searchType = "account";
        }
        return searchType;
    }

    public boolean isAccount() {
        return "account".equals(this.getSearchType());
    }

    public boolean isCustomer() {
        return "customer".equals(this.getSearchType());
    }

    public boolean isEmpty() {
        boolean empty = true;
        try {
            for (Field f : this.getClass().getDeclaredFields()) {
                if(f.getType().getSimpleName().equals("boolean")){
                    if(!f.get(this).equals(false)){
                        empty = true;
                    } else {
                        empty = false;
                        break;
                    }
                } else if(f.getType().getSimpleName().equals("String")){
                    if(null == f.get(this) || f.get(this).equals("")){
                        empty = true;
                    } else {
                        empty = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, e.getMessage());
        }
        return empty;
    }
}