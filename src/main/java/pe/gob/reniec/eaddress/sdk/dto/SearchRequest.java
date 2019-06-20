package pe.gob.reniec.eaddress.sdk.dto;

/**
 * @author Miguel Pazo (https://miguelpazo.com)
 */
public class SearchRequest {

    private Integer page;
    private Integer count;
    private String sort;
    private String tag;
    private String name;
    private String doc;
    private String subject;
    private Integer status;
    private Long dateBegin;
    private Long dateEnd;
    private Boolean failed;

    public SearchRequest() {
        this.page = 1;
        this.count = 20;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Long dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Boolean getFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "page=" + page +
                ", count=" + count +
                ", sort='" + sort + '\'' +
                ", tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", doc='" + doc + '\'' +
                ", subject='" + subject + '\'' +
                ", status=" + status +
                ", dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                ", failed=" + failed +
                '}';
    }
}