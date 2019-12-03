package client;


import cn.scau.edu.base.*;



import cn.scau.edu.util.Management;
import cn.scau.edu.util.OpenedTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public final class TreeNodeCell extends TreeCell<TreeNode> {
	
	
	private static MainUIController MC;
	private TextField textField;
    private ContextMenu dirRightClickFile = new ContextMenu();
    private ContextMenu  dirRightClickDir = new ContextMenu();
    private ContextMenu diskClick = new ContextMenu();
    private static TreeView<TreeNode> treeView;
	MenuItem paste = new MenuItem("粘贴");
	private boolean flag = true;
    public static Management DM;

    public TreeNodeCell(MainUIController mainUIController) {
    	this.MC= mainUIController;
    	DM = Management.getInstance();
    	fileAction();
    	dirAction();
    	diskAction();
    	//this.MC = mainUIController;
    	//this.treeView = treeView;
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		//重写EventHandler接口实现方法
		@Override
		public void handle(MouseEvent event) {
			//执行事件发生后的动作
			if(getTreeItem() == null) return;
			if(event.getButton().name().equals(MouseButton.PRIMARY.name())){ 
				if(getTreeItem().getValue().getSuper().isDir()) {
					Dir dir = (Dir)getTreeItem().getValue().getSuper();
					Management.getInstance().changeDir(dir);
				}
				else {
					File file = (File)getTreeItem().getValue().getSuper();
					Management.getInstance().selectedFile(file);
					MC.showProperty(file);
				}
				
			}
//			System.out.println(getTreeItem().getValue().getSuper().isDir());
			if(event.getButton().name().equals(MouseButton.SECONDARY.name())&&!getTreeItem().getValue().getSuper().isDir()){ 
				setContextMenu(dirRightClickFile);
				File file = (File)getTreeItem().getValue().getSuper();
				Management.getInstance().selectedFile(file);
			}
			else {
				Dir dir = null;
				if(event.getButton().name().equals(MouseButton.SECONDARY.name())) {
					dir = (Dir)getTreeItem().getValue().getSuper();
					
				}
				if(dir!=null&&dir.getName().equals(FileView.com.getName())) {
//					System.out.println("com: "+dir.getName());
					setContextMenu(diskClick);
					return;
				}
				if(dir!=null)
					Management.getInstance().changeDir(dir);
				setContextMenu(dirRightClickDir);
			}
		}
	});	
    }
    
  
    public void fileAction() {//文件右键点击
    	MenuItem open = new MenuItem("打开文件");
    	MenuItem read = new MenuItem("只读文件");
    	MenuItem write = new MenuItem("只写文件");
    	MenuItem delete = new MenuItem("删除文件");
    	MenuItem close  = new MenuItem("关闭文件");
    	MenuItem change = new MenuItem("改变文件属性");
    	dirRightClickFile.getItems().addAll(open,read,write,delete,close,change);
    	
    	open.setOnAction(e->{
    		Image image = new Image("file:res/open.png",20,20,false,false);
//    		System.out.println("打开文件");
    		File file = Management.getInstance().getNowFile();
//    		System.out.println("file path: "+file.getDisk_path());
    		if(!OpenedTable.getInstance().isExist(Management.getInstance().getNowFile())) {
    			boolean result = OpenedTable.getInstance().add(Management.getInstance().getNowFile(), 1);//写打开文件
    			if(result == false)
    				return;
    		}
    		try {
    			Stage stage=new Stage();
    	       	Parent r = FXMLLoader.load(getClass().getResource("OpenFile.fxml"));
    	       	Scene s = new Scene(r);
    	       	stage.setScene(s);
    	       	stage.setResizable(false);//设置不能窗口改变大小
    	       	stage.setTitle("Open File");
    	       	stage.getIcons().add(image);
    	       	stage.show();
    	       	
    	           
    	   	 } catch(Exception e1) {
    	            e1.printStackTrace();
    	        }
    		
    	});
    	
    	delete.setOnAction(e->{				//删除有问题！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 
    		File file = Management.getInstance().getNowFile();
    		boolean result = Management.getInstance().deleteFile(Management.getInstance().getNowFile());
    		if(result)
    		{
    			file.getParent().getTreeItem().getChildren().remove(file.getTreeItem());
    			MainUIController.getInstance().updateProgress();
    		}
    	
    	});
    	
    	write.setOnAction(e->{
    		File file = Management.getInstance().getNowFile();
    		if(!OpenedTable.getInstance().isExist(Management.getInstance().getNowFile())) {
    			boolean result = OpenedTable.getInstance().add(Management.getInstance().getNowFile(), 1);//读打开文件
    			if(result == false)
    				return;
    		}
    		if(getTreeItem().getValue().getSuper().isOnlyReadFile()) {
    			return;
    		}
    		if(!OpenedTable.getInstance().isExist(Management.getInstance().getNowFile())) {
    			OpenedTable.getInstance().add(Management.getInstance().getNowFile(), 1);//写打开文件
    		}
    		try {
    			Stage stage=new Stage();
    	       	Parent r = FXMLLoader.load(getClass().getResource("writeOnly.fxml"));
    	       	Scene s = new Scene(r);
    	       	stage.setScene(s);
    	       	stage.setResizable(false);//设置不能窗口改变大小
    	       	stage.setTitle("Write File");
    	       	stage.show();
    	       	
    	   
    	       	
    	   	 } catch(Exception e1) {
    	            e1.printStackTrace();
    	        }
    	});
    	
    	read.setOnAction(e->{
    		File file = Management.getInstance().getNowFile();
//    		System.out.println("file path: "+file.getDisk_path());
    		if(!OpenedTable.getInstance().isExist(Management.getInstance().getNowFile())) {
    			boolean result = OpenedTable.getInstance().add(Management.getInstance().getNowFile(), 0);//读打开文件
    			if(result == false)
    				return;
    		}
    		try {
    			Stage stage=new Stage();
    	       	Parent r = FXMLLoader.load(getClass().getResource("ReadOnly.fxml"));
    	       	Scene s = new Scene(r);
    	       	stage.setScene(s);
    	       	stage.setResizable(false);//设置不能窗口改变大小
    	       	stage.setTitle("Read File");
    	       	stage.show();
    	       	
    	   
    	       	
    	   	 } catch(Exception e1) {
    	            e1.printStackTrace();
    	        }
    	});
    	
    	change.setOnAction(e->{
    		
    		try {
    			Stage stage=new Stage();
    			Parent r = FXMLLoader.load(getClass().getResource("Change.fxml"));
//    	       	Parent q = FXMLLoader.load(getClass().getResource("ChangeFileProperty.fxml"));
    	       	Scene s = new Scene(r);
    	       	stage.setScene(s);
    	       	stage.setResizable(false);//设置不能窗口改变大小
    	       	stage.setTitle("Change File Property");
    	       	stage.show();
    	       	
    	   
    	       	
    	   	 } catch(Exception e1) {
    	            e1.printStackTrace();
    	        
    	   	 }
    		
    	});
    	
    	
    	close.setOnAction(e->{
    		File file = Management.getInstance().getNowFile();
    		boolean result = Management.getInstance().closeFile(file);
//    		System.out.println("close result: "+result);
    	});
    }

     public void dirAction() {//目录右键
    	MenuItem newDir = new MenuItem("新建目录");
    	MenuItem delete = new MenuItem("删除目录");
    	MenuItem newFile = new MenuItem("新建文件");
    	dirRightClickDir.getItems().addAll(newDir,newFile,delete);

    	newDir.setOnAction(e->{
    		Image image = new Image("file:res/add.png",20,20,false,false);
    		try {
    			
    			Stage stage=new Stage();
    	       	Parent r = FXMLLoader.load(getClass().getResource("newDir.fxml"));
    	       	Scene s = new Scene(r);
    	       	stage.setScene(s);
    	       	stage.setResizable(false);//设置不能窗口改变大小
    	       	stage.setTitle("New Dir");
    	       	stage.getIcons().add(image);
    	       	stage.show();

    	   	 } catch(Exception e1) {
    	            e1.printStackTrace();
    	        }

    	});
    	
    	delete.setOnAction(e -> {
//    		System.out.println("删除目录");
    		
    		Dir dir = Management.getInstance().getPwd();
    		if(!dir.getName().equals(dir.getDisk().getRoot().getName())) {
    	
        		
        			
        		boolean result = Management.getInstance().removeDir(dir);
        		
        		if(result)
        		{
        			
//        			System.out.println("删除成功！");
        			System.out.println(dir.getName());
        			dir.getParent().getTreeItem().getChildren().remove(dir.getTreeItem());
        			MainUIController.getInstance().updateProgress();
        		}
//        		else System.out.println("删除失败");
    		}
    		
    	});
    	
    	newFile.setOnAction(e->{
    		Image image = new Image("file:res/add.png",20,20,false,false);
//    		System.out.println("新建文件");
    		try {
    			
    			Stage stage=new Stage();
    	       	Parent r = FXMLLoader.load(getClass().getResource("newFile.fxml"));
    	       	Scene s = new Scene(r);
    	       	stage.setScene(s);
    	       	stage.setResizable(false);//设置不能窗口改变大小
    	       	stage.setTitle("New File");
    	       	stage.getIcons().add(image);
    	       	stage.show();
    	       	
    	       	
    	   	 } catch(Exception e1) {
    	            e1.printStackTrace();
    	        }

    	});	
    }
   
     
     public void diskAction(){//磁盘目录
    	 
     	MenuItem newDisk = new MenuItem("新建磁盘");

     	diskClick.getItems().addAll(newDisk);

     	newDisk.setOnAction(e->{
     		Image image = new Image("file:res/add.png",20,20,false,false);
     		try {
     			
     			Stage stage=new Stage();
     	       	Parent r = FXMLLoader.load(getClass().getResource("newDisk.fxml"));
     	       	Scene s = new Scene(r);
     	       	stage.setScene(s);
     	       	stage.setResizable(false);//设置不能窗口改变大小
     	       stage.setTitle("New Disk");
     	       stage.getIcons().add(image);
     	       	stage.show();

     	   	 } catch(Exception e1) {
     	            e1.printStackTrace();
     	        }

     	});
     	
     }
     

    @Override
    public void startEdit() {
        super.startEdit();

//        if (textField == null) {
//            createTextField();
//        }
//        setText(null);
//        setGraphic(textField);
//        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        //setText((String) getItem());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(TreeNode item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
//                if(!getTreeItem().isLeaf()&&getTreeItem().getParent()!= null) {
//                	setContextMenu(dirRightClickFile);
//                }
            }
        }
    }
    
    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyPressed((KeyEvent t) -> {
            if (t.getCode() == KeyCode.ENTER) {
              //  commitEdit(textField.getText());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });  
        
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}



