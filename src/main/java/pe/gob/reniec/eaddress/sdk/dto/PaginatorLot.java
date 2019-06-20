package pe.gob.reniec.eaddress.sdk.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginatorLot {
    private Integer recordsTotal;
    private List<LotData> data;

    public PaginatorLot() {
        this.recordsTotal = 0;
        this.data = new ArrayList<>();
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<LotData> getData() {
        return data;
    }

    public void setData(List<LotData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PaginatorLot{" +
                "recordsTotal=" + recordsTotal +
                ", data=" + data +
                '}';
    }
}
