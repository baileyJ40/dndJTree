/**
 * TestObject.java
 * Here I will extend the DndObject and experiment with
 * giving it an integer that distinguishes which "type"
 * of Object it is for selective filtering.
 */

package dndJTree;

public class TestObject extends DndTreeObject{
    //constructors
    public TestObject(Object ob){
        super(ob);
        thisType = ZERO;
    }
    public TestObject(Object ob, int i){
        super(ob);
        thisType = i;
    }

    public void setThisType(int i){
        thisType = i;
    }
    public int getThisType(){
        return thisType;
    }

    // instance vars
    private int thisType;

    // final type constants
    public static final int EVEN = 2;
    public static final int ODD = 1;
    public static final int ZERO = 0;
}