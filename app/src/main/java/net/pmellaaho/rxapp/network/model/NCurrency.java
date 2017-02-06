package net.pmellaaho.rxapp.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import lombok.Getter;

@Getter
@Root(name = "Cube", strict=false)
public class NCurrency extends NCurrencyCode {
    @Attribute(required = true)
    private double rate;

    public NCurrency() {}

    public NCurrency(String currecyCode, double rate) {
        super(currecyCode);
        this.rate = rate;
    }
}
