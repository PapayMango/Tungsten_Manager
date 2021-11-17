package database;

import java.util.Date;

public class Evaluation implements DataObject{

    private String lot;
    private String methylene;
    private int deodorize;
    private String quality;
    private Date update_date;

    @Override
    public DataType getType() {
        return DataType.Evaluation;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getMethylene() {
        return methylene;
    }

    public void setMethylene(String methylene) {
        this.methylene = methylene;
    }

    public int getDeodorize() {
        return deodorize;
    }

    public void setDeodorize(int deodorize) {
        this.deodorize = deodorize;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}
