/**
 * This class will allow me to get an ArrayList containing all 
 * of the treeNodes in the tree. I can then check the contents
 * of each node to see if it is a particular type for filtering
 * purposes. I can then build a new collection of that type,
 * or even change the visible property as I go through the 
 * ArrayList. This should eliminate the need for a special
 * TransferHandler for filtering. I should be able to go back
 * to using the DndTreeTransferHandler.
 */
package dndJTree;

import javax.swing.tree.*;
import javax.swing.*;
import java.util.*;

public class TestClass{

    //constructor
    public TestClass(){
        nodesList = new ArrayList();
    }
    public ArrayList <Object> traverseTree(DefaultTreeModel model){
        traverseThis(model.getRoot(), model);
        return nodesList;
    }
    private void traverseThis(Object node, DefaultTreeModel model){
        int childCount = model.getChildCount(node);
        nodesList.add(node);
        while(childCount > 0){
            Object tNode = model.getChild(node, childCount - 1);
            //do stuff
            traverseThis(tNode, model);
            childCount--;
        }
    }
    public void clearList(){
        nodesList.clear();
    }
    //instance vars
    private ArrayList <Object> nodesList;
}