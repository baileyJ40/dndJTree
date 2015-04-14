/* FilterTreeTransferHandler.java
 * Extending DndTreeTransferHandler to enable "muted" nodes
 * to be "drawn" properly when muted. If you do not need
 * the tree to filter out specific nodes by selectively 
 * muting or hiding them, use the superclass 
 * DndTreeTransferHandler instead.
 */

package dndJTree;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.datatransfer.*;
import java.io.*;

public class FilterTreeTransferHandler extends DndTreeTransferHandler{

    public FilterTreeTransferHandler(ArrayList <DndTreeNode> nodesToFilter,
        ArrayList <DndTreeObject> objectsToFilter){
        super();
        filteredNodes = nodesToFilter;
        filteredObjects = objectsToFilter;
    }

    public boolean importData(TransferSupport support){
        
        if(!canImport(support)) return false;

        DefaultDndJTree tree = (DefaultDndJTree)support.getComponent();
        DefaultFilterTreeModel model = (DefaultFilterTreeModel)tree.getModel();

        Transferable transferable = support.getTransferable();
        DefaultDndJTree.DropLocation dropLocation = (DefaultDndJTree.DropLocation)support.getDropLocation();
        TreePath path = dropLocation.getPath();
        DndTreeObject transferData;
        try {
            transferData = (DndTreeObject)transferable.getTransferData(DndTreeObject.flavors[0]);
        } 
        catch (IOException e) {
            System.out.println("DefaultDndJTreeTransferHandler IOException thrown");
            return false;
        } 
        catch (UnsupportedFlavorException e) {
            System.out.println("DefaultDndJTreeTransferHandler UnsupportedFlavorException thrown");
            return false;
        }
        int childIndex = dropLocation.getChildIndex();
        if (childIndex == -1) {
            childIndex = model.getChildCount(path.getLastPathComponent());
        }

        DndTreeNode newNode = new DndTreeNode(transferData);
        DndTreeNode parentNode = (DndTreeNode)path.getLastPathComponent();
        model.insertNodeInto(newNode, parentNode, childIndex);

        TreePath newPath = path.pathByAddingChild(newNode);
        tree.makeVisible(newPath);
        tree.scrollRectToVisible(tree.getPathBounds(newPath));

        if(filteredObjects.contains(transferData)){
            filteredNodes.add(newNode);
        }

        return true;


    }

    protected void exportDone(JComponent source, Transferable data, int action){
        //if the object is being moved, remove it from its original location
        //(it leaves a copy behind without this code)
        if (action == MOVE){
            DefaultDndJTree tree = (DefaultDndJTree)source;
            DndTreeNode selectedNode = (DndTreeNode)tree.getLastSelectedPathComponent();
            DefaultFilterTreeModel model = (DefaultFilterTreeModel)tree.getModel();
            if(filteredNodes.contains(selectedNode)){
                filteredNodes.remove(selectedNode);
            }
            model.removeNodeFromParent(selectedNode);
            
        }
    }

    private ArrayList <DndTreeNode> filteredNodes;
    private ArrayList <DndTreeObject> filteredObjects;
}
