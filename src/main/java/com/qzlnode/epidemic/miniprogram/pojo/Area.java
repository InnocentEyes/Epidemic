package com.qzlnode.epidemic.miniprogram.pojo;

/**
 * @author qzlzzz
 */
public class Area {

    private String cityName;

    private String areaName;

    private Integer dangerLevel;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(Integer dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    @Override
    public String toString() {
        return "Area{" +
                "cityName='" + cityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", dangerLevel=" + dangerLevel +
                '}';
    }
}
