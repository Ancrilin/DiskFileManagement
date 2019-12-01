package client;

//import java.io.File;
import java.util.List;
import cn.scau.edu.base.*;

import cn.scau.edu.util.Management;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sun.security.action.OpenFileInputStreamAction;
import javafx.util.Callback;
import java.util.Optional;


public class FileView {
	
	private static TreeView<TreeNode> treeView;
	private TreeItem<TreeNode> rootNode, recentNode;
	public static Management DM = Management.getInstance();
	private static MainUIController mainUIController;
	private ChangeListener<TreeItem<TreeNode>> e;
    private static MainUIController MC;
    
	
	public FileView(MainUIController mainUIController,TreeView tree) {
		this.mainUIController=mainUIController;
		this.treeView=tree;
		TreeNode node = new TreeNode(DM.getNow_root());
		rootNode = new TreeItem<TreeNode>(node);//初始化目录树节点
		rootNode.setExpanded(true);
		treeView.setRoot(rootNode);
		initTreeView();
		
		
	}
	
	public void initTreeView() {				//初始化目录树
		
		/*treeView.getSelectionModel().selectedItemProperty().addListener(e = new ChangeListener<TreeItem<TreeNode>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<TreeNode>> observable, TreeItem<TreeNode> oldValue,
					TreeItem<TreeNode> newValue) {
				// TODO Auto-generated method stub
				
				
				
				if(!DM.getPwd().getDir_list().isEmpty()&&!newValue.isLeaf())
				{
					
					initTreeNode(DM.getPwd(), rootNode);
					
				}
				
				
				
			}	
		});
*/
		if(!DM.getPwd().getDir_list().isEmpty())
		{
			
			initTreeNode(DM.getPwd(), rootNode);
			
		}
		
		treeView.setEditable(true);
        treeView.setCellFactory(new Callback<TreeView<TreeNode>,TreeCell<TreeNode>>(){
            @Override
            public TreeCell<TreeNode> call(TreeView<TreeNode> p) {
                return new TreeNodeCell(MC);
            }
        });
        
        
        

		
	}
		
    	private void initTreeNode(Dir dir, TreeItem<TreeNode> parentNode) //初始化目录节点 parentNode为Dir所在的节点
    	{            
    		                                                                          
    		if (!dir.getDir_list().isEmpty()) 
    		{
    			for (Dir child : dir.getDir_list()) 
    			{	
    				TreeItem<TreeNode> newNode = new TreeItem<TreeNode>(new TreeNode(child));
    				parentNode.getChildren().add(newNode);
    				initTreeNode(child, newNode);
    				
    			}
    		}
    		/*else
    		{
    			parentNode.getChildren().add(new TreeItem<TreeNode> (dir.getName()));
    		}*/
    		if(!dir.getFile_list().isEmpty())
    			{
    				for(File file: dir.getFile_list())
    				{
    					TreeItem<TreeNode> newNode = new TreeItem<TreeNode>(new TreeNode(file));
    					parentNode.getChildren().add(new TreeItem<TreeNode>(new TreeNode(file)));
    					
    					
    					
    				}
    			}
    	}
        
	  
    	
    	
    	public TreeView<TreeNode> getTreeView() {
    		return treeView;
    	}
    	public void setTreeView(TreeView<TreeNode> treeView) {
    		this.treeView = treeView;
    	}
    	public TreeItem<TreeNode> getRootNode() {
    		return rootNode;
    	}
    	public void setRootNode(TreeItem<TreeNode> rootNode) {
    		this.rootNode = rootNode;
    	}
    	public TreeItem<TreeNode> getRecentNode() {
    		return recentNode;
    	}
    	public void setRecentNode(TreeItem<TreeNode> recentNode) {
    		this.recentNode = recentNode;
    	}
}
	






