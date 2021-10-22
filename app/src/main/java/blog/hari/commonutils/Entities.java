package blog.hari.commonutils;

import java.util.LinkedHashMap;

public class Entities {
    String SrNo="";
    String Item = "";
    String Specification ="";
    String Size1BR="0";
    String Size2BRR="";
    String GRADE="";
    String Unit="";
    String Quantity="";
    String Contingencies="";
    String TotalQuantity="";
    LinkedHashMap<String,String> vendors;

    public Entities() {

    }

    public Entities(String srNo, String item, String specification, String size1BR, String size2BRR, String GRADE, String unit, String quantity, String contingencies, String totalQuantity, LinkedHashMap<String, String> vendors) {
        SrNo = srNo;
        Item = item;
        Specification = specification;
        Size1BR = size1BR;
        Size2BRR = size2BRR;
        this.GRADE = GRADE;
        Unit = unit;
        Quantity = quantity;
        Contingencies = contingencies;
        TotalQuantity = totalQuantity;
        this.vendors = vendors;
    }

    public String getSrNo() {
        return SrNo;
    }

    public void setSrNo(String srNo) {
        SrNo = srNo;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getSize1BR() {
        return Size1BR;
    }

    public void setSize1BR(String size1BR) {
        Size1BR = size1BR;
    }

    public String getSize2BRR() {
        return Size2BRR;
    }

    public void setSize2BRR(String size2BRR) {
        Size2BRR = size2BRR;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getContingencies() {
        return Contingencies;
    }

    public void setContingencies(String contingencies) {
        Contingencies = contingencies;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public LinkedHashMap<String, String> getVendors() {
        return vendors;
    }

    public void setVendors(LinkedHashMap<String, String> vendors) {
        this.vendors = vendors;
    }
}
