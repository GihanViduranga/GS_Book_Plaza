package lk.gsbp.repository;

import lk.gsbp.model.Stock;
import lk.gsbp.model.stockDetails;

import java.util.List;

public class PlaceStock {
    private Stock stock;
    private List<stockDetails> stList;

    public PlaceStock() {
    }

    public PlaceStock(Stock stock, List<stockDetails> stList) {
        this.stock = stock;
        this.stList = stList;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<stockDetails> getStList() {
        return stList;
    }

    public void setStList(List<stockDetails> stList) {
        this.stList = stList;
    }

    @Override
    public String toString() {
        return "PlaceStock{" +
                "stock=" + stock +
                ", stList=" + stList +
                '}';
    }
}
