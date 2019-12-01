package client;


import cn.scau.edu.base.*;



import cn.scau.edu.util.Management;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public final class TreeNodeCell extends TreeCell<TreeNode> {
	
	
	private static MainUIController MC;
	private TextField textField;
    private final ContextMenu addMenu = new ContextMenu();
    private ContextMenu dirRightClick = new ContextMenu();
    private ContextMenu rootMenu = new ContextMenu();
    private static TreeView<TreeNode> treeView;
	MenuItem paste = new MenuItem("粘贴");
	private boolean flag = true;
    public static Management DM;

    public TreeNodeCell(MainUIController mainUIController) {
    	this.MC= mainUIController;
    	DM = Management.getInstance();
    	dirAction();
    	//this.MC = mainUIController;
    	//this.treeView = treeView;
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		//重写EventHandler接口实现方法
		@Override
		public void handle(MouseEvent event) {
			//执行事件发生后的动作
			//System.out.println(event.getButton().name().equals(MouseButton.PRIMARY.name()));
			if(event.getButton().name().equals(MouseButton.PRIMARY.name())){ 
				//MC.showProperty((File)DM.search(getTreeItem().getValue()));
				
				
			}
		    
			if(event.getButton().name().equals(MouseButton.SECONDARY.name())){ 
				System.out.println("11111111111");
				setContextMenu(dirRightClick);
			}
		}
	});	
    }
    
  
    public void dirAction() {//目录右键点击
    	MenuItem add = new MenuItem("新建文件");
    	MenuItem delete = new MenuItem("删除文件夹");
    	MenuItem rename = new MenuItem("重命名");
    	MenuItem copy = new MenuItem("复制");
    	dirRightClick.getItems().addAll(add, delete, rename, copy);
    	
    	
    	add.setOnAction(e->{
    		System.out.println("添加文件");
    		File file = DM.makeFile("新建文件夹");
    		this.getTreeView().getSelectionModel().getSelectedItem().getChildren().add(new TreeItem<TreeNode> (new TreeNode(file)));
    		
    	});
    }
    /*	delete.setOnAction(e->{
    		System.out.println("删除");
    		
    		
    		Services.selectTreeNode=getTreeItem();
    		Services.indexOfSelectTreeNode = FileTree.getTreeView().getRow(getTreeItem());
    		filecontroller.delete = new DeleteAction();
    	
    	});
    	
    	copy.setOnAction(e->{
    		System.out.println("复制");
    		Services.fileBuffer = getTreeItem().getValue().getFile();

    	});
    	
    	rename.setOnAction(e->{
    		Services.selectTreeNode=getTreeItem();
    		filecontroller.rename = new RenameAction();
    	});
    	
    	paste.setOnAction(e->{
    		pasteFile();
    		Services.fileBuffer=null;

    	});
    }*/

    /* public void txtAction() {//文件右键
    	MenuItem open = new MenuItem("打开");
    	MenuItem delete = new MenuItem("删除文件");
    	MenuItem rename = new MenuItem("重命名");
    	MenuItem copy = new MenuItem("复制");
    	textMenu.getItems().addAll(open, delete, rename, copy);

    	open.setOnAction(e->{
    		Services.selectTreeNode = getTreeItem();
    		filecontroller.open = new OpenTextFileAction();

    	});
    	
    	delete.setOnAction(e -> {
    		System.out.println("删除");
    		Services.selectTreeNode = getTreeItem();
    		Services.indexOfSelectTreeNode = FileTree.getTreeView().getRow(getTreeItem());
    		filecontroller.delete = new DeleteAction();

    	});
    	
    	copy.setOnAction(e->{
    		System.out.println("复制");
    		Services.fileBuffer = getTreeItem().getValue().getFile();

    	});
    	
    	rename.setOnAction(e->{
    		Services.selectTreeNode=getTreeItem();
    		filecontroller.rename = new RenameAction();
    	});
    	
    	paste.setOnAction(e->{
    		pasteFile();
    		Services.fileBuffer=null;

    	});
    	
    	
    }*/
   
    
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
                if(!getTreeItem().isLeaf()&&getTreeItem().getParent()!= null) {
                	setContextMenu(dirRightClick);
                }
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



