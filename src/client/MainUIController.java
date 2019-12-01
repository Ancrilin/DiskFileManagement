package client;

import java.net.URL;
import java.util.ResourceBundle;

import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
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

	





	private static MainUIController mainUIController;
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
    private TreeView<?> TreeView1;

    @FXML
    private AnchorPane AnchorPane;
    
    private TreeView<String> nodeTreeView;

    
    
   
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Management DM = Management.getInstance();
		DM.initManage();
		FileView fv = new FileView(this.mainUIController,this.TreeView1);
		ProgressIndicator1.setProgress((double)(128-DM.getNowDiskFreeSpace())/128);
//		TreeView1.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//    		//重写EventHandler接口实现方法
//    		@Override
//    		public void handle(MouseEvent event) {
//    			//执行事件发生后的动作
//    			//System.out.println(event.getButton().name().equals(MouseButton.PRIMARY.name()));
//    			if(event.getButton().name().equals(MouseButton.SECONDARY.name())){ 
//    				//System.out.println("11111111111");
//    				setContextMenu(dirRightClick);
//    					
//    					
//    			}
//    		}
//    	});	
		
	  
		
	}
	
	
	/* public void rootAction() {//磁盘右键
	MenuItem addDisk = new MenuItem("新建磁盘");
	rootMenu.getItems().add(addDisk);
	addDisk.setOnAction(e->{
		DM.newDisk("新建磁盘", 128);
		
	});
}*/



	

	
	public void showProperty(File f)
	{
		this.TextFiel1.setText(f.getName());
		if(f.isOrdinaryFile())
		{
			this.TextFiel2.setText("普通文件");
		}
		else this.TextFiel2.setText("系统文件");
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






	public void setTreeView1(TreeView<?> treeView1) {
		TreeView1 = treeView1;
	}

    

}

