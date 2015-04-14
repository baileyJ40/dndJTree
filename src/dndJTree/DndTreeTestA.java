/* DndTreeTestA.java -
 * Lets see if this stuff works
 */

package dndJTree;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.UIManager.*;

public class DndTreeTestA{
    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try{
                    for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                        if("Nimbus".equals(info.getName())){
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                JFrame frame = new DndTree();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class DndTree extends JFrame{
    public DndTree(){
        setTitle("DndTreeTest");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //Objects to populate the tree with - I have choosen
        //Strings for simplicity. These will be passed to
        //the DnDObject constructor, which in turn will be
        //passed to the DefaultDndTreeNode constructor. Then
        //they can be strung together and passed to the 
        //DefaultDndJTree constructor to populate the tree
        //as a new DefaultFilterTreeModel.
        TestObject root = new TestObject("Root", TestObject.ZERO);
        TestObject parent1 = new TestObject("Parent1", TestObject.ZERO);
        TestObject parent2 = new TestObject("Parent2", TestObject.ZERO);
        TestObject parent3 = new TestObject("Parent3", TestObject.ZERO);
        TestObject child1 = new TestObject("Child1", TestObject.ODD);
        TestObject child2 = new TestObject("Child2", TestObject.EVEN);
        TestObject child3 = new TestObject("Child3", TestObject.ODD);
        TestObject child4 = new TestObject("Child4", TestObject.EVEN);
        TestObject child5 = new TestObject("Child5", TestObject.ODD);
        TestObject child6 = new TestObject("Child6", TestObject.EVEN);

        //install Objects into Treenodes
        DndTreeNode rootNode = new DndTreeNode(root);
        DndTreeNode parent1Node = new DndTreeNode(parent1);
        DndTreeNode parent2Node = new DndTreeNode(parent2);
        DndTreeNode parent3Node = new DndTreeNode(parent3);
        DndTreeNode child1Node = new DndTreeNode(child1);
        DndTreeNode child2Node = new DndTreeNode(child2);
        DndTreeNode child3Node = new DndTreeNode(child3);
        DndTreeNode child4Node = new DndTreeNode(child4);
        DndTreeNode child5Node = new DndTreeNode(child5);
        DndTreeNode child6Node = new DndTreeNode(child6);
        rootNode.add(parent1Node);
        rootNode.add(parent2Node);
        rootNode.add(parent3Node);
        parent1Node.add(child1Node);
        parent1Node.add(child2Node);
        parent2Node.add(child3Node);
        parent2Node.add(child4Node);
        parent3Node.add(child5Node);
        parent3Node.add(child6Node);
        
        model = new DefaultFilterTreeModel(rootNode);
        dndJTree.DefaultDndJTree tree = new dndJTree.DefaultDndJTree(model, DropMode.ON_OR_INSERT);

        //set up the GUI for the tree and test button
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();
        centerPanel.add(new JScrollPane(tree));
        add(centerPanel, BorderLayout.CENTER);
        
        oddButton = new JRadioButton("Odd objects visible", true);
        southPanel.add(oddButton);
        evenButton = new JRadioButton("Even objects visible", true);
        southPanel.add(evenButton);
        add(southPanel, BorderLayout.SOUTH);
        
        //install ActionListener for buttons
        oddButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                oddVisibility();
            }
        });
        evenButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                evenVisibility();
            }
        });

        tree.setDragEnabled(true);
        tree.setTransferHandler(new DndTreeTransferHandler());

        oddList = new ArrayList();
        evenList = new ArrayList();

    }

    /**
     * This method and the evenVisibility() method both work in the
     * following manner: 
     * 1. Test to see if the radio button is selected (objects are
     *        to be visible or not).
     * 2. If so, there will be a list of the objects already stored, 
     *        so iterate through the list and set all of the objects
     *        to visible.
     * 3. If not, there is a possibility the list does not exist yet
     *        or has changed. Rebuild the list by iterating through
     *        ALL of the tree nodes (the traverseTree() method) and
     *        selecting the ones that are of the correct type. Then
     *        set them all to invisible.
     * 4. Go through the list and tell each changed node's parent to
     *        reload() so the view will be properly updated.
     */
    private void oddVisibility(){
        
        if(oddButton.isSelected()){
            for(int i = 0; i < oddList.size(); i++){
                ((TestObject)((DndTreeNode)oddList.get(i)).getUserObject()).setVisible(true);
            }
        }
        else{
            ArrayList <Object> newList = new TestClass().traverseTree(model);
            oddList = new ArrayList();
            for(int i = 0; i < newList.size(); i++){
                if(((TestObject)((DndTreeNode)newList.get(i)).getUserObject()).getThisType() == TestObject.ODD){
                    oddList.add(newList.get(i));
                }
            }
            for(int i = 0; i < oddList.size(); i++){
                ((TestObject)((DndTreeNode)oddList.get(i)).getUserObject()).setVisible(false);
            }
        }
        
        //Properly updates the display to hide or show the specified nodes.
        //This replaces the plain model.reload() method, which causes the 
        //entire tree to collapse.
        for(int i = 0; i < oddList.size(); i++){
            model.reload(((DndTreeNode)oddList.get(i)).getParent());
        }
        
        
    }
    private void evenVisibility(){

        if(evenButton.isSelected()){
            for(int i = 0; i < evenList.size(); i++){
                ((TestObject)((DndTreeNode)evenList.get(i)).getUserObject()).setVisible(true);
            }
        }
        else{
            ArrayList <Object> newList = new TestClass().traverseTree(model);
            evenList = new ArrayList();
            for(int i = 0; i < newList.size(); i++){
                if(((TestObject)((DndTreeNode)newList.get(i)).getUserObject()).getThisType() == TestObject.EVEN){
                    evenList.add(newList.get(i));
                }
            }
            for(int i = 0; i < evenList.size(); i++){
                ((TestObject)((DndTreeNode)evenList.get(i)).getUserObject()).setVisible(false);
            }
        }
        
        //Properly updates the display to hide or show the specified nodes.
        //This replaces the plain model.reload() method, which causes the 
        //entire tree to collapse.
        for(int i = 0; i < oddList.size(); i++){
            model.reload(((DndTreeNode)evenList.get(i)).getParent());
        }
    }

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 500;
    JRadioButton oddButton;
    JRadioButton evenButton;
    ArrayList <Object> oddList;
    ArrayList <Object> evenList;
    
    DefaultFilterTreeModel model;
    
}
