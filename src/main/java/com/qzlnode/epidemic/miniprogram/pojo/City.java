package com.qzlnode.epidemic.miniprogram.pojo;

/**
 * @author qzlzzz
 */
public class City {

    private String cityName;

    private Integer currentConfirmedCount;

    private Integer confirmedCount;

    private Integer suspectedCount;

    private Integer curedCount;

    private Integer deadCount;

    private Integer highDangerCount;

    private Integer midDangerCount;

    private String locationId;

    private Boolean notShowCurrentConfirmedCount;

    private String currentConfirmedCountStr;


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(Integer currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public Integer getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(Integer confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public Integer getSuspectedCount() {
        return suspectedCount;
    }

    public void setSuspectedCount(Integer suspectedCount) {
        this.suspectedCount = suspectedCount;
    }

    public Integer getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(Integer curedCount) {
        this.curedCount = curedCount;
    }

    public Integer getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(Integer deadCount) {
        this.deadCount = deadCount;
    }

    public Integer getHighDangerCount() {
        return highDangerCount;
    }

    public void setHighDangerCount(Integer highDangerCount) {
        this.highDangerCount = highDangerCount;
    }

    public Integer getMidDangerCount() {
        return midDangerCount;
    }

    public void setMidDangerCount(Integer midDangerCount) {
        this.midDangerCount = midDangerCount;
    }

    public String getCurrentConfirmedCountStr() {
        return currentConfirmedCountStr;
    }

    public void setCurrentConfirmedCountStr(String currentConfirmedCountStr) {
        this.currentConfirmedCountStr = currentConfirmedCountStr;
    }

    public Boolean getNotShowCurrentConfirmedCount() {
        return notShowCurrentConfirmedCount;
    }

    public void setNotShowCurrentConfirmedCount(Boolean notShowCurrentConfirmedCount) {
        this.notShowCurrentConfirmedCount = notShowCurrentConfirmedCount;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", confirmedCount=" + confirmedCount +
                ", suspectedCount=" + suspectedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", highDangerCount=" + highDangerCount +
                ", midDangerCount=" + midDangerCount +
                ", locationId='" + locationId + '\'' +
                ", notShowCurrentConfirmedCount=" + notShowCurrentConfirmedCount +
                ", currentConfirmedCountStr='" + currentConfirmedCountStr + '\'' +
                '}';
    }
}
