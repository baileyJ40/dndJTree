/* DefaultFilterTreeModel.java
 * An extension of the DefaultTreeModel that will
 * enable the user to selectively "hide" nodes
 * in the tree, while retaining the data in the
 * model so that the nodes can be made visible again.
 * Two of the DefaultTreeModel methods will be 
 * overriden: getChild() and getChildCount().
 */

package dndJTree;

import javax.swing.tree.*;
import java.util.*;

public class DefaultFilterTreeModel extends DefaultTreeModel{
    public DefaultFilterTreeModel(DndTreeNode root){
        super(root);
        
    }
    public DefaultFilterTreeModel(DndTreeNode root, boolean askAllowsChildren){
        super(root, askAllowsChildren);
        
    }
    public Object getChild(Object parent, int index){
        DndTreeNode node = (DndTreeNode) parent;
        int pos= 0;
        for (int i= 0, cnt= 0; i< node.getChildCount(); i++) {
            if (((DndTreeNode)node.getChildAt(i)).getUserObject().isVisible()){
                if (cnt++ == index) {
                    pos= i;
                    break;
                }
            }
        }
 
        return node.getChildAt(pos);

    }
    public int getChildCount(Object parent){
        DndTreeNode node = (DndTreeNode) parent;
        int childCount= 0;
        Enumeration children= node.children();
        while (children.hasMoreElements()) {
            if (((DndTreeNode)children.nextElement()).getUserObject().isVisible()) 
                childCount++;
        }
 
        return childCount;

    }
    //added to try and fix bug - didn't work
    public int getIndexOfChild(Object parent, Object child){
        DndTreeNode node = (DndTreeNode) parent;
        DndTreeNode sub = (DndTreeNode) child;
        if (parent == null || child == null) 
            return -1;
        if (!sub.getUserObject().isVisible())
            return -1;
        return node.getIndex(sub);
    } 
}
