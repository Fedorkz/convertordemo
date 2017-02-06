package net.pmellaaho.rxapp.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import lombok.Getter;

@Root(name = "Cube", strict=false)
@Getter
public class RootCube {
    @Element(required = true, name = "Cube")
    private TimeCubeList cube;

    public RootCube() {}
}
