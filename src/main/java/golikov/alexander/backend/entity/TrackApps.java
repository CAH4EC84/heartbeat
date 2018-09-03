package golikov.alexander.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Table(name = "TrackApps", schema = "dbo", catalog = "Medis_Reports")
public class TrackApps {
    private Integer id;
    private String name;
    private Integer enableTrack;
    private String cheker;
    private String path;
    private Timestamp lastSuccessCheck;
    private String errorLevel;
    private String charSet;
    private String checkInterval;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name",columnDefinition = "NVARCHAR()")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "enableTrack")
    public Integer getEnableTrack() {
        return enableTrack;
    }

    public void setEnableTrack(Integer enableTrack) {
        this.enableTrack = enableTrack;
    }

    @Basic
    @Column(name = "cheker",columnDefinition = "NVARCHAR()")
    public String getCheker() {
        return cheker;
    }

    public void setCheker(String cheker) {
        this.cheker = cheker;
    }

    @Basic
    @Column(name = "path",columnDefinition = "NVARCHAR()")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "lastSuccessCheck")
    public Timestamp getLastSuccessCheck() {
        return lastSuccessCheck;
    }

    public void setLastSuccessCheck(Timestamp lastSuccessCheck) {
        this.lastSuccessCheck = lastSuccessCheck;
    }

    @Basic
    @Column(name = "errorLevel",columnDefinition = "NVARCHAR()")
    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        this.errorLevel = errorLevel;
    }

    @Basic
    @Column(name = "charSet", columnDefinition = "NVARCHAR()")
    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    @Basic
    @Column(name = "checkInterval", columnDefinition = "NVARCHAR()")
    public String getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(String checkInterval) {
        this.checkInterval = checkInterval;
    }

    //    @Transient
//    public Timestamp getLastSuccessCheckShort() {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//        return Timestamp.valueOf(
//                LocalDateTime.parse(
//                        getLastSuccessCheck().toString().split("\\.")[0]
//                        ,dateTimeFormatter));
//    }

}
