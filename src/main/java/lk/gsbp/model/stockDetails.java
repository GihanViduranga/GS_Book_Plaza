package lk.gsbp.model;

public class stockDetails {
    private String SupplierId;
    private int QTY;
    private int ItemPrice;
    private String stockId;

    @Override
    public String toString() {
        return "stockDetails{" +
                "SupplierId='" + SupplierId + '\'' +
                ", QTY=" + QTY +
                ", ItemPrice=" + ItemPrice +
                '}';
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

    public int getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(int itemPrice) {
        ItemPrice = itemPrice;
    }

    public stockDetails(String supplierId, int QTY, int itemPrice) {
        SupplierId = supplierId;
        this.QTY = QTY;
        ItemPrice = itemPrice;
    }

    public stockDetails() {
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
