package client;

//import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import cn.scau.edu.base.File;

public class FileTreeItem<T> extends TreeItem<T> {
	
	 	/*public TreeItem<File> createNode(final File f) {
	    return new TreeItem<File>(f) {
	    	
	      private boolean isLeaf;
	      private boolean isFirstTimeChildren = true;
	      private boolean isFirstTimeLeaf = true;
	      
	      @Override
	      public ObservableList<TreeItem<File>> getChildren() {
	        if (isFirstTimeChildren) {
	          isFirstTimeChildren = false;
	          super.getChildren().setAll(buildChildren(this));
	        }
	        return super.getChildren();
	      }
	      
	      @Override
	      public boolean isLeaf() {
	        if (isFirstTimeLeaf) {
	          isFirstTimeLeaf = false;
	          File f = (File) getValue();
	          isLeaf = f.isFile();
	        }
	        return isLeaf;
	      }
	      
	      private ObservableList<TreeItem<File>> buildChildren(
	          TreeItem<File> TreeItem) {
	        File f = TreeItem.getValue();
	        if (f == null) {
	          return FXCollections.emptyObservableList();
	        }
	        if (f.isFile()) {
	          return FXCollections.emptyObservableList();
	        }
	        File[] files = f.listFiles();
	        if (files != null) {
	          ObservableList<TreeItem<File>> children = FXCollections
	              .observableArrayList();
	          for (File childFile : files) {
	            children.add(createNode(childFile));
	          }
	          return children;
	        }
	        return FXCollections.emptyObservableList();
	      }
	    };
	  }*/

	protected TreeItem<File> buildChildren(TreeItem<File> treeItem) {
		// TODO Auto-generated method stub
		return null;
	}
}
