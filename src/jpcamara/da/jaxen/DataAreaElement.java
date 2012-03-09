package jpcamara.da.jaxen;

import jpcamara.da.DataAreaNode;

/**
 * Object handed around in Jaxen processing
 */
public class DataAreaElement {
    private DataAreaElement parent;
    private String name;
    private byte[] payload;
    private int offset;
    private int length;
    private DataAreaNode definition;
    
    public DataAreaElement(DataAreaElement parent, String name, byte[] payload,
                           int offset, int length, DataAreaNode definition) {
        this.parent = parent;
        this.name = name;
        this.payload = payload;
        this.offset = offset;
        this.length = length;
        this.definition = definition;
    }
    
    public DataAreaElement getParent() {
        return parent;
    }

    public void setParent(DataAreaElement parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public DataAreaNode getDefinition() {
        return definition;
    }

    public void setDefinition(DataAreaNode definition) {
        this.definition = definition;
    }
}
