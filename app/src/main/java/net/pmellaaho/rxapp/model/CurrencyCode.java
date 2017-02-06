package net.pmellaaho.rxapp.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyCode {
    private String currency;

    public CurrencyCode() {}

    public CurrencyCode(String currecyCode) {
        this.currency = currecyCode;
    }
    public String getCurrency(){
        return currency;
    }
}
