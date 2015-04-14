/* DnDTreeNode.java
 * A class that will encapsulate the data
 * for nodes on the DefaultDnDJTree
 */

package dndJTree;

import javax.swing.tree.*;

/**
 * A <code>DndTreeNode</code> is basically the same as a
 * <code>DefaultMutableTreeNode</code> except that it
 * <em>requires</em> the object supplied to the constructors
 * to be a <code>DndTreeObject</code>.
 * @author John Bailey
 */
public class DndTreeNode extends DefaultMutableTreeNode{
    //Constructors
    public DndTreeNode(){
        super();
    }
    public DndTreeNode(DndTreeObject uObject){      
        super(uObject);
        setUserObject(uObject);
    }
    public DndTreeNode(DndTreeObject uObject, boolean allowsChildren){
        super(uObject, allowsChildren);
        setUserObject(uObject);
    }

    /**
     * This method sets the object the node contains to
     * some instance of a <code>DndTreeObject</code>.
     * @param an object that is a <code>DndTreeObject</code>
     */
    public void setUserObject(DndTreeObject uObject){
        userObject = uObject;
    }

    /**
     * This method returns a reference to the <code>DndTreeObject</code>
     * contained in the node.
     * @return a reference to the <code>DndTreeObject</code> contained in the node.
     */
    public DndTreeObject getUserObject(){
        return userObject;
    }

    //instance variables
    private DndTreeObject userObject;
    
}
