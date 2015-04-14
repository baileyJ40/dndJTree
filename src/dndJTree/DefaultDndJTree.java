/* DefaultDndJTree.java
 * The classes in this package are
 * my attempt at creating a reusable JTree that 
 * supports drag and drop.
 */

package dndJTree;

import javax.swing.*;
import javax.swing.tree.*;

public class DefaultDndJTree extends JTree{

    //Constructors 
    //One primary difference between this and the superclass
    //is the added constructor parameter dropMode. This can be
    //DropMode.ON, DropMode.INSERT, DropMode.ON_OR_INSERT, or
    //DropMode.USE_SELECTION. DropMode is an enumeration found
    //in javax.swing.

    public DefaultDndJTree(DndTreeNode root, DropMode dropMode){
        super(root);
        setDropMode(dropMode);
    }
    public DefaultDndJTree(DndTreeNode root, boolean asksAllowChildren, DropMode dropMode){
        super(root, asksAllowChildren);
        setDropMode(dropMode);
    }
    public DefaultDndJTree(DefaultTreeModel model, DropMode dropMode){
        super(model);
        setDropMode(dropMode);
    }
}

