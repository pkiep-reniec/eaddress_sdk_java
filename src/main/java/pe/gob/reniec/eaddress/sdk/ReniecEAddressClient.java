package pe.gob.reniec.eaddress.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import pe.gob.reniec.eaddress.sdk.common.Acuse;
import pe.gob.reniec.eaddress.sdk.dto.*;
import pe.gob.reniec.eaddress.sdk.service.LotService;
import pe.gob.reniec.eaddress.sdk.service.NotificationService;
import pe.gob.reniec.eaddress.sdk.service.SendService;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author Alexander Llacho
 */
public class ReniecEAddressClient {

    private Config config;
    private ConfigAga configAga = null;
    private SendService sendService;
    private LotService lotService;
    private NotificationService notificationService;

    public ReniecEAddressClient(String configFile, ConfigAga oConfigAga) {
        this.setConfig(configFile, oConfigAga);
    }

    public ReniecEAddressClient(String configFile) {
        this.setConfig(configFile, null);
    }

    public ApiResponse sendSingleNotification(Message oMessage) {
        return this.sendSingleNotification(oMessage, null);
    }

    public ApiResponse sendSingleNotification(Message oMessage, List<Attachment> attachments) {
        try {
            if (attachments != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonAttachments = objectMapper.writeValueAsString(attachments);

                oMessage.setAttachments(jsonAttachments);
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
        }

        return this.sendService.procSingleNotification(oMessage);
    }

    public ApiResponse sendMassiveNotification(Message oMessage, File file) {
        return this.sendService.procMassiveNotification(oMessage, file);
    }

    public PaginatorLot fetchAllLots(SearchRequest searchRequest) {
        return this.lotService.fetchAll(searchRequest);
    }

    public PaginatorLotNotifications fetchLotNotifications(String lotId, SearchRequest searchRequest) {
        return this.lotService.fetchNotifications(lotId, searchRequest);
    }

    public PaginatorLotNotifications fetchAllNotifications(SearchRequest searchRequest) {
        return this.notificationService.fetchAll(searchRequest);
    }

    public NotificationResponse getNotification(String notificationId, String lotId) {
        return this.notificationService.getOne(notificationId, lotId);
    }

    public byte[] downloadMetadata(String lotId) {
        return this.lotService.downloadMetadata(lotId);
    }

    public byte[] downloadAcuse(String id, String lotId, Acuse acuse) {
        return this.notificationService.downloadAcuse(id, lotId, acuse);
    }

    private void setConfig(String configFile, ConfigAga oConfigAga) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.config = mapper.readValue(new File(configFile), Config.class);
            this.configAga = oConfigAga;
            this.sendService = SendService.getInstance(this.config, this.configAga);
            this.lotService = LotService.getInstance(this.config);
            this.notificationService = NotificationService.getInstance(this.config);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }
    }

    public Config getConfig() {
        return config;
    }

    public ConfigAga getConfigAga() {
        return configAga;
    }
}
