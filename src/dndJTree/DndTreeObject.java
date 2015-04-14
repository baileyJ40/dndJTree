/* DnDTreeObject.java
 * A class that will actually hold the data contained by the tree nodes
 */

package dndJTree;

import java.awt.datatransfer.*;
import java.io.*;

/**
 * A <code>DndTreeObject</code> encapsulates the data to be contained
 * in a <code>DndTreeNode</code>. Methods and fields have been
 * added to assist the containing <code>DndTreeNode</code> to be
 * muted, or made invisible. These methods are not necessary to
 * merely implement drag-and-drop functionality.
 *  @author John Bailey
 */
public class DndTreeObject implements Transferable, Serializable{

    //Constructor
    public DndTreeObject(Object ob){
        object = ob;
        visible = true;
    }

    // The Transferable interface methods
    /**
     * The <code>getTransferData</code> method returns a reference to
     * this object. This is one of the <code>Transferable</code> interface
     * methods. This implementation is <em>only</em> designed for use
     * within a single JVM. If you wish to make objects of this type
     * transferable outside of the spawning JVM, you will need to
     * subclass this class and override the <code>Transferable</code>
     * methods.
     *  @param flavor the <code>DataFlavor</code> the calling method is expecting
     *  @return a reference to this <code>DndTreeObject</code>
     *  @throws <code>UnsupportedFlavorException</code>
     *  @throws <code>IOException</code>
     */
    public Object getTransferData(DataFlavor flavor)throws UnsupportedFlavorException, IOException{
        if(flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType)){
            return this;
        }
        else{
            throw new UnsupportedFlavorException(flavor);
        }
    }

    /**
     * The <code>getTransferDataFlavors()</code> method returns an array of
     * <code>DataFlavor</code> objects representing the possible mime-types
     * the data may be presented as. This implementation only has one
     * item in the array, <code>DataFlavor.javaJVMLocalObjectMimeType</code>.
     * @return an array containing only one element, <code>DataFlavor.javaJVMLocalObjectMimeType</code>
     */
    public DataFlavor[] getTransferDataFlavors(){
        return flavors;
    }

    /**
     * The <code>isDataFlavorSupported(DataFlavor flavor)</code> method tests
     * to see if this object supports the supplied <code>DataFlavor</code>.
     * @param flavor the <code>DataFlavor</code> to test
     * @return <code>true</code> if the supplied <code>DataFlavor</code> is supported,
     *    <code>false</code> if not
     */
    public boolean isDataFlavorSupported(DataFlavor flavor){
        return flavor.isMimeTypeEqual(DataFlavor.javaJVMLocalObjectMimeType +
            ";class=" + dndJTree.DndTreeObject.class.getName());
    }
    // End Transferable methods

    /**
     * This method returns a string representation of the encapsulated object.
     * This actually uses the contained object's <code>toString()</code> method.
     * @return a <code>String</code> that represents the encapsulated object.
     */
    public String toString(){
        return object.toString();
    }

    /**
     * This method sets whether the object is intended to be visible on its
     * containing JTree or not.
     * @param a <code>boolean</code> that is <code>true</code> if the object is
     *     intended to be visible in the JTree, <code>false</code> if it is not.
     */
    public void setVisible(boolean b){
        visible = b;
    }

    /**
     * This method is used by the <code>DefaultFilterTreeModel</code> to determine
     * whether the object will be shown in the containing JTree or not.
     * @return <code>true</code> if the object will be shown, <code>false</code> if not.
     */
    public boolean isVisible(){
        return visible;
    }


    private Object object;
    static final DataFlavor[] flavors = {null};
    static{
       try{
           DataFlavor DND_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType +
               ";class=" + dndJTree.DndTreeObject.class.getName());
           flavors[0] = DND_FLAVOR;
       }
       catch(Exception ex){
           ex.printStackTrace();
       }
    }
    private boolean visible;
}
