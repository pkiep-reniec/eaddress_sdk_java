package pe.gob.reniec.eaddress.sdk.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginatorLotNotifications {
    private Integer recordsTotal;
    private List<NotificationResponse> notifications;

    public PaginatorLotNotifications() {
        this.recordsTotal = 0;
        this.notifications = new ArrayList<>();
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<NotificationResponse> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationResponse> notifications) {
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