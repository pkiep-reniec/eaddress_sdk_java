package pe.gob.reniec.eaddress.sdk.dto;

import java.util.ArrayList;
import java.util.List;

public class ApiPaginatorLotNotifications {
    private Integer recordsTotal;
    private List<NotificationsResponse> notifications;

    public ApiPaginatorLotNotifications() {
        this.recordsTotal = 0;
        this.notifications = new ArrayList<>();
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<NotificationsResponse> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationsResponse> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "PaginatorLotNotifications{" +
                "recordsTotal=" + recordsTotal +
                ", notifications=" + notifications +
                '}';
    }
}