package lk.gsbp.model;

import lombok.*;


public class Supplier {
    private String SupplierId;
    private String SuppName;
    private String Contact;
    private String Product;
    private String ItemName;
    private String Qty;
    private String TotalPrice;

    public Supplier() {
    }

    public Supplier(String supplierId, String suppName, String contact, String product, String itemName, String qty, String totalPrice) {
        SupplierId = supplierId;
        SuppName = suppName;
        Contact = contact;
        Product = product;
        ItemName = itemName;
        Qty = qty;
        TotalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "SupplierId='" + SupplierId + '\'' +
                ", SuppName='" + SuppName + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Product='" + Product + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", Qty='" + Qty + '\'' +
                ", TotalPrice='" + TotalPrice + '\'' +
                '}';
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getSuppName() {
        return SuppName;
    }

    public void setSuppName(String suppName) {
        SuppName = suppName;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
