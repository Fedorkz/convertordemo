package com.fedorkzsoft.demo.network.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

import lombok.Getter;

@Root(name = "Cube", strict=false)
@Getter
public class TimeCubeList {
    @Attribute(required = false)
    private String time;
    @ElementList(entry="Cube", inline=true)
    private List<NCurrency> currency;

    public TimeCubeList() {}
}
