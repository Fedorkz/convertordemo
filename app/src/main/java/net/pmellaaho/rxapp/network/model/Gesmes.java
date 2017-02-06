package net.pmellaaho.rxapp.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Getter;

@Getter
@Root(name = "gesmes:Envelope", strict=false)
public class Gesmes {
    @Element(required = true, name = "Cube")
    private RootCube cube;

    public Gesmes() {}

}
