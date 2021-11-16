package database;

import java.util.Date;

public class Tungsten {
    
    private String lot;
    private Date update_date;
    private Date product_date;
    private int quantity;
    private char quality;
    private String method;

    public Tungsten(String lot, Date update_date, Date product_date, int quantity, char quality, String method) {
        this.lot = lot;
        this.update_date = update_date;
        this.product_date = product_date;
        this.quantity = quantity;
        this.quality = quality;
        this.method = method;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public Date getProduct_date() {
        return product_date;
    }

    public void setProduct_date(Date product_date) {
        this.product_date = product_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public char getQuality() {
        return quality;
    }

    public void setQuality(char quality) {
        this.quality = quality;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
