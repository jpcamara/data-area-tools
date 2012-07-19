package jpcamara.da;

import java.util.ArrayList;
import java.util.List;

public class DataAreaNode {
    private List<DataAreaNode> children = new ArrayList<DataAreaNode>();
    private DataAreaValueType type = DataAreaValueType.NONE;
    private int occurs = 1;
    private DataAreaNode parent;
    private DataAreaNode sibling;
    private String name;
    private int length;
    private boolean redefine;
    private DataAreaNode redefinedNode;
    private int level; //FIXME is this too cobol specific?

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public DataAreaNode getRedefinedNode() {
        return redefinedNode;
    }

    public void setRedefinedNode(DataAreaNode redefinedNode) {
        this.redefinedNode = redefinedNode;
    }
    
    public int getOccurs() {
        return occurs;
    }

    public void setOccurs(int occurs) {
        this.occurs = occurs;
    }

    public DataAreaNode getParent() {
        return parent;
    }

    public void setParent(DataAreaNode parent) {
        this.parent = parent;
    }

    public DataAreaValueType getType() {
        return type;
    }

    public void setType(DataAreaValueType type) {
        this.type = type;
    }

    public List<DataAreaNode> getChildren() {
        return children;
    }

    public void setChildren(List<DataAreaNode> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataAreaNode getSibling() {
        return sibling;
    }

    public void setSibling(DataAreaNode sibling) {
        this.sibling = sibling;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isRedefine() {
        return redefine;
    }

    public void setRedefine(boolean redefine) {
        this.redefine = redefine;
    }
    
    @Override
    public String toString() {
        return String.format("Level = [%d], Name = [%s], Length = [%d], Occurs = [%d], Redefines = [%s]",
                level, name, length, occurs, redefine);
    }
}
