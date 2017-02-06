package net.pmellaaho.rxapp.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Currency extends CurrencyCode{
    private double rate;

    public Currency() {}

    public Currency(String currecyCode, double rate) {
        super(currecyCode);
        this.rate = rate;
    }

    public Double getRate(){
        return rate;
    }
}
