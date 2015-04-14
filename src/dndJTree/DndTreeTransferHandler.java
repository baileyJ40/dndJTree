/* DndTreeTransferHandler.java -
 * This class will allow drop support for the 
 * DefaultDndJTree
 */

package dndJTree;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.datatransfer.*;
import java.io.*;


/**
 * The <code>DndTreeTransferHandler</code> subclasses <code>TransferHandler</code>
 * to enable drag-and-drop specifically for JTrees. This class will not work
 * with any other component.
 * @author John Bailey
 */
public class DndTreeTransferHandler extends TransferHandler{

    /**
     * This method returns a <code>Transferable</code> object
     * from the source of a drag operation. The <code>Transferable</code>
     * in this case is a <code>DndObject</code>, which implements
     * the <code>Transferable</code> interface. This method gets the
     * node from the treepath of the drag source (a <code>JTree</code>),
     * and gets a reference to the object contained in the node. 
     * @param the source component of the drag operation. In this implementation
     *     the source will always be a <code>DefaultDndJTree</code>.
     * @return a reference to the object contained in the source node.
     */
    protected Transferable createTransferable(JComponent source){       
        DefaultDndJTree tree = (DefaultDndJTree)source;
        DndTreeNode node = (DndTreeNode)tree.getLastSelectedPathComponent();
        return node.getUserObject();
    }

    /**
     * This method informs the caller as to what types of
     * transfer actions are permitted. this implementation
     * always returns <code>COPY_OR_MOVE</code>.
     * @param the source component of the drag operation.
     * @return an int constant representing copy or move operations.
     */
    public int getSourceActions(JComponent c){
        return COPY_OR_MOVE;
    }

    public boolean canImport(TransferSupport support){
        
        if(!support.isDataFlavorSupported(DndTreeObject.flavors[0])){
            return false;
        }
        
        JTree.DropLocation dropLocation = (JTree.DropLocation)support.getDropLocation();
        return dropLocation.getPath() != null;

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

        return true;


    }

    protected void exportDone(JComponent source, Transferable data, int action){
        //if the object is being moved, remove it from its original location
        //(it leaves a copy behind without this code)
        if (action == MOVE){
            DefaultDndJTree tree = (DefaultDndJTree)source;
            DndTreeNode selectedNode = (DndTreeNode)tree.getLastSelectedPathComponent();
            DefaultFilterTreeModel model = (DefaultFilterTreeModel)tree.getModel();
            model.removeNodeFromParent(selectedNode);
        }
    }
}
