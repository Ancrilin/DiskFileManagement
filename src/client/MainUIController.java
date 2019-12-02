package client;

import java.net.URL;
import java.util.ResourceBundle;

import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
import cn.scau.edu.util.OpenedTable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
    
    private static MainUIController mainController = null;
    private TreeView<String> nodeTreeView;
	private ContextMenu directoryMenu = new ContextMenu();
	private ContextMenu textMenu = new ContextMenu();
	private ContextMenu excuteMenu = new ContextMenu();
	private ContextMenu rootMenu = new ContextMenu();
    
    public MainUIController() {
    	ControllerContainer.controllerContainer.put(this.getClass().getSimpleName(), this);
    }
    
    public static MainUIController getInstance() {
    	return mainController;
    }
   
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Management DM = Management.getInstance();
		DM.init();
		FileView fv = new FileView(this,this.TreeView1);
		ProgressIndicator1.setProgress((double)(128-DM.getNowDiskFreeSpace())/128);
		mainController = this;
	}
	
	
	

	
	public void showProperty(File f)
	{
		this.TextFiel1.setText(f.getName());
		if(f.isOrdinaryFile())
		{
			this.TextFiel2.setText("普通文件");
		}
		else this.TextFiel2.setText("系统文件");
		System.out.println(f.isOrdinaryFile());
		System.out.println(f.isSystemFile());
		this.TextFiel3.setText(f.getByte_num()+"KB");
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
    	MainUIController.getInstance().getProgressIndicator1().setProgress((double)(128-Management.getInstance().getNowDiskFreeSpace())/128);
    }
}

