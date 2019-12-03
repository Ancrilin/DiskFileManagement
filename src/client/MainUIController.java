package client;

import java.net.URL;
import java.util.ResourceBundle;

import cn.scau.edu.base.Disk;
import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
import cn.scau.edu.util.OpenedTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainUIController implements Initializable{

	//private static MainUIController mainUIController;
    @FXML
    private VBox VBox1;

    @FXML
    private TextField TextFiel3;

    @FXML
    private Label Label1;

    @FXML
    private MenuBar MenuBar1;

    @FXML
    private TextField TextFiel4;

    @FXML
    private Label Label3;

    @FXML
    private Label Label2;

    @FXML
    private TextField TextFiel1;

    @FXML
    private TextField TextFiel2;

    @FXML
    private ProgressIndicator ProgressIndicator1;

    @FXML
    private Label Label5;

    @FXML
    private Label Label4;
    
    @FXML
    private Label openedFileNum;

    @FXML
    private TreeView<?> TreeView1;

    @FXML
    private AnchorPane AnchorPane;
    
    @FXML
    private Button showBtn;
    
    
    private static MainUIController mainController = null;
    private TreeView<String> nodeTreeView;
	private ContextMenu directoryMenu = new ContextMenu();
	private ContextMenu textMenu = new ContextMenu();
	private ContextMenu excuteMenu = new ContextMenu();
	private ContextMenu rootMenu = new ContextMenu();
    
//    public MainUIController() {
//    	ControllerContainer.controllerContainer.put(this.getClass().getSimpleName(), this);
//    }
    
    public static MainUIController getInstance() {
    	return mainController;
    }
   
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		mainController = this;
		Management DM = Management.getInstance();
		DM.init();
//		this.updateProgress();
		FileView fv = new FileView(this,this.TreeView1);
		fv.newDisk(Management.getInstance().getNow_disk());//C
		this.newDisk("D", 256);
//		System.out.println("pwd: "+Management.getInstance().getPwd().getName());
		Management.getInstance().changeDisk("D");
//		System.out.println("pwd: "+Management.getInstance().getPwd().getName());
		File f1 = Management.getInstance().makeFile("f1");
		File f2 = Management.getInstance().makeFile("f2");
		File f3 = Management.getInstance().makeFile("f3");
		File f4 = Management.getInstance().makeFile("f4");
		File f5 = Management.getInstance().makeFile("f5");
		Management.getInstance().writeFile(f1, "D盘下的文件1#");
		Management.getInstance().writeFile(f2, "D盘下的文件2 D盘下的文件2#");
		Management.getInstance().writeFile(f3, "D盘下的文件3 D盘下的文件3 D盘下的文件3#");
		Management.getInstance().writeFile(f4, "D盘下的文件4 D盘下的文件4 D盘下的文件4 D盘下的文件4#");
		Management.getInstance().writeFile(f5, "D盘下的文件5 D盘下的文件5 D盘下的文件5 D盘下的文件5 D盘下的文件5#");
		FileView.addDirTreeNode(f1, f1.getParent().getTreeItem());
//		Management.getInstance().writeFile(f1, "这是文件1#");
		Management.getInstance().updateBuffer();
		FileView.addDirTreeNode(f2, f2.getParent().getTreeItem());
		FileView.addDirTreeNode(f3, f3.getParent().getTreeItem());
		FileView.addDirTreeNode(f4, f4.getParent().getTreeItem());
		FileView.addDirTreeNode(f5, f5.getParent().getTreeItem());
		
		this.updateProgress();
	}
	
	public boolean newDisk(String diskName, int blocks_num) {
		
		Disk new_disk = Management.getInstance().newDisk(diskName, blocks_num);
		if(new_disk==null) {
			return false;
		}
		FileView.newDisk(new_disk);
		return true;
	}
	

	
	public void showProperty(File f)
	{
		this.TextFiel1.setText(f.getName());
		if(f.isOrdinaryFile())
		{
			this.TextFiel2.setText("普通文件");
		}
		else this.TextFiel2.setText("系统文件");
		this.TextFiel3.setText(f.getByte_num()+8+"字节");
		if(f.isOnlyReadFile())
		{
			this.TextFiel4.setText("是");
		}
		else this.TextFiel4.setText("否");
		
	}
    
    
	
	
	
	
	
	public ProgressIndicator getProgressIndicator1() {
		return ProgressIndicator1;
	}






	public void setProgressIndicator1(ProgressIndicator progressIndicator1) {
		ProgressIndicator1 = progressIndicator1;
	}






	public TreeView<?> getTreeView1() {
		return TreeView1;
	}


	public void updateOpenedFileNum() {
		int num = OpenedTable.getInstance().getOpenedSize();
		this.openedFileNum.setText(String.valueOf(num)+"/5");
	}



	public void setTreeView1(TreeView<?> treeView1) {
		TreeView1 = treeView1;
	}

    public MainUIController getMain() {
    	return this;
    }

    
    public void updateProgress() {
    	MainUIController.getInstance().getProgressIndicator1().setProgress((double)(Management.getInstance().getNow_disk().getFat().getBlocks_num()-Management.getInstance().getNow_disk().getFat().getFreeSpace())/(double)Management.getInstance().getNow_disk().getFat().getBlocks_num());
    }
    
    

    @FXML
    void showDiskFAT(ActionEvent event) {
    	System.out.println(Management.getInstance().getNow_disk().getDisk_id());
    	for(int i=0;i<Management.getInstance().getNow_disk().getFat().getBlocks_num();i++) {
    		System.out.print(Management.getInstance().getNow_disk().getFat().getAllocation()[i]+" ");
    	}
    	System.out.println();
    }
    
    public Node getFileIcon() {
    	Node node = new ImageView(
                new Image("file:res/File1.png",15,15,false,false)
        	    );
    	return node ;
    }
    
    public Node getDirIcon() {
    	Node node = new ImageView(
                new Image("file:res/Dir.png",22,22,false,false)
        	    );
    	return node ;
    }
    
    public Node getDiskIcon() {
    	Node node = new ImageView(
                new Image("file:res/disk1.png",15,15,false,false)
        	    );
    	return node ;
    }
    
    public Node getComputerIcon() {
    	Node node = new ImageView(
                new Image("file:res/computer.png",20,20,false,false)
        	    );
    	return node ;
    }
    
    public Node getOpenedFile() {
    	Node node = new ImageView(
                new Image("file:res/OpenedFile.png",20,20,false,false)
        	    );
    	return node ;
    }
}

