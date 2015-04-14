/* DndTreeTest.java -
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

public class DndTreeTest{
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
        DndTreeObject root = new DndTreeObject("Root");
        DndTreeObject parent1 = new DndTreeObject("Parent1");
        DndTreeObject parent2 = new DndTreeObject("Parent2");
        DndTreeObject parent3 = new DndTreeObject("Parent3");
        DndTreeObject child1 = new DndTreeObject("Child1");
        DndTreeObject child2 = new DndTreeObject("Child2");
        DndTreeObject child3 = new DndTreeObject("Child3");
        DndTreeObject child4 = new DndTreeObject("Child4");
        DndTreeObject child5 = new DndTreeObject("Child5");
        DndTreeObject child6 = new DndTreeObject("Child6");

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

        //seperate nodes into types for filter testing
       
        testObjects = new ArrayList(); //{child1, child3, child5};
        testObjects.add(child1);
        testObjects.add(child3);
        testObjects.add(child5);
        testNodes = new ArrayList(); //{child1Node, child3Node, child5Node};
        testNodes.add(child1Node);
        testNodes.add(child3Node);
        testNodes.add(child5Node);
        
        model = new DefaultFilterTreeModel(rootNode);
        dndJTree.DefaultDndJTree tree = new dndJTree.DefaultDndJTree(model, DropMode.ON_OR_INSERT);

        //set up the GUI for the tree and test button
        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();
        centerPanel.add(new JScrollPane(tree));
        add(centerPanel, BorderLayout.CENTER);
        
        visibleButton = new JRadioButton("Test objects visible", true);
        southPanel.add(visibleButton);
        add(southPanel, BorderLayout.SOUTH);
        
        //install ActionListener for button
        visibleButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                setVisibility();
            }
        });

        tree.setDragEnabled(true);
        tree.setTransferHandler(new FilterTreeTransferHandler(testNodes, testObjects));

        

    }

    private void setVisibility(){
        if(visibleButton.isSelected()){
            for(int i = 0; i < testObjects.size(); i++){
                testObjects.get(i).setVisible(true);
            }
        }
        else{
            for(int i = 0; i < testObjects.size(); i++){
                testObjects.get(i).setVisible(false);
            }
        }
        
        //Properly updates the display to hide or show the specified nodes.
        //This replaces the plain model.reload() method, which causes the 
        //entire tree to collapse.
        for(int i = 0; i < testNodes.size(); i++){
            model.reload(testNodes.get(i).getParent());
        }
        
        
    }

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 500;
    JRadioButton visibleButton;
    ArrayList <DndTreeObject> testObjects;
    ArrayList <DndTreeNode> testNodes;
    DefaultFilterTreeModel model;
    
}
