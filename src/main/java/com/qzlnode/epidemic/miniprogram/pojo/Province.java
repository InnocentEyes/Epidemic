package com.qzlnode.epidemic.miniprogram.pojo;

import java.net.URL;
import java.util.Arrays;

/**
 * @author qzlzzz
 */
public class Province {

    private String provinceName;

    private String provinceShortName;

    private Integer currentConfirmedCount;

    private Integer confirmedCount;

    private Integer suspectedCount;

    private Integer curedCount;

    private Integer deadCount;

    private String comment;

    private String locationId;

    private URL statisticsData;

    private Integer highDangerCount;

    private Integer midDangerCount;

    private Integer detectOrgCount;

    private Integer vaccinationOrgCount;

    private City[] cities;

    private Area[] dangerAreas;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
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

    public City[] getCities() {
        return cities;
    }

    public void setCities(City[] cities) {
        this.cities = cities;
    }

    public String getProvinceShortName() {
        return provinceShortName;
    }

    public void setProvinceShortName(String provinceShortName) {
        this.provinceShortName = provinceShortName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public URL getStatisticsData() {
        return statisticsData;
    }

    public void setStatisticsData(URL statisticsData) {
        this.statisticsData = statisticsData;
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

    public Integer getDetectOrgCount() {
        return detectOrgCount;
    }

    public void setDetectOrgCount(Integer detectOrgCount) {
        this.detectOrgCount = detectOrgCount;
    }

    public Integer getVaccinationOrgCount() {
        return vaccinationOrgCount;
    }

    public void setVaccinationOrgCount(Integer vaccinationOrgCount) {
        this.vaccinationOrgCount = vaccinationOrgCount;
    }

    public Area[] getDangerAreas() {
        return dangerAreas;
    }

    public void setDangerAreas(Area[] dangerAreas) {
        this.dangerAreas = dangerAreas;
    }

    @Override
    public String toString() {
        return "Province{" +
                "provinceName='" + provinceName + '\'' +
                ", provinceShortName='" + provinceShortName + '\'' +
                ", currentConfirmedCount=" + currentConfirmedCount +
                ", confirmedCount=" + confirmedCount +
                ", suspectedCount=" + suspectedCount +
                ", curedCount=" + curedCount +
                ", deadCount=" + deadCount +
                ", comment='" + comment + '\'' +
                ", locationId='" + locationId + '\'' +
                ", statisticsData=" + statisticsData +
                ", highDangerCount=" + highDangerCount +
                ", midDangerCount=" + midDangerCount +
                ", detectOrgCount=" + detectOrgCount +
                ", vaccinationOrgCount=" + vaccinationOrgCount +
                ", cities=" + Arrays.toString(cities) +
                ", dangerAreas=" + Arrays.toString(dangerAreas) +
                '}';
    }
}
