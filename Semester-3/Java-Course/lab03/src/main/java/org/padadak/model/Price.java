package org.padadak.model;

public class Price {
    private int id;
    private String serviceName;
    private int priceValue;

    public Price(int nr, String serviceName, int priceValue) {
        this.id = nr;
        this.serviceName = serviceName;
        this.priceValue = priceValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(int priceValue) {
        this.priceValue = priceValue;
    }

    @Override
    public String toString() {
        return "- ID["+ id +"] - "+ serviceName +", Price: "+ priceValue;
    }
}
