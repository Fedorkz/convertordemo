package net.pmellaaho.rxapp.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import lombok.Getter;

@Root(name = "Cube", strict=false)
@Getter
public class NCurrencyCode {
    @Attribute(required = true)
    private String currency;

    public NCurrencyCode() {}

    public NCurrencyCode(String currecyCode) {
        this.currency = currecyCode;
    }
    public String getCurrency(){
        return currency;
    }
}
