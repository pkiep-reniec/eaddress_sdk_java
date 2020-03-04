package pe.gob.reniec.eaddress.sdk.dto;

import java.util.ArrayList;
import java.util.List;

public class ApiPaginatorLotNotifications {
    private Integer recordsTotal;
    private List<NotificationsResponse> data;

    public ApiPaginatorLotNotifications() {
        this.recordsTotal = 0;
        this.data = new ArrayList<>();
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<NotificationsResponse> getData() {
        return data;
    }

    public void setData(List<NotificationsResponse> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PaginatorLotNotifications{" +
                "recordsTotal=" + recordsTotal +
                ", notifications=" + data +
                '}';
    }
}