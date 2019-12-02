package client;

import java.net.URL;
import java.util.ResourceBundle;

import cn.scau.edu.base.Dir;
import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.fxml.Initializable;

public class NewDirController implements Initializable{
	private Dir dir = null;
	
    @FXML
    private TextField textField1;

    @FXML
    private Label label1;

    @FXML
    private Button button1;
    
    @FXML
	 private Label label2;

    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void onButtonClickedNewDir(ActionEvent e) {
		
		if(!this.textField1.getText().equals(""))
		{
			Dir dir = Management.getInstance().makeDir(this.textField1.getText());
			dir.setTreeItem(new TreeItem<TreeNode> (new TreeNode(dir)));
			if(dir!=null) {
				System.out.println("make dir success!");
				this.label2.setText("新建目录成功!");
				client.FileView.addDirTreeNode(dir, dir.getParent().getTreeItem());
				MainUIController.getInstance().updateProgress();
			}else {
				this.label2.setText("新建目录失败，有重复文件名！!");
			}
			
			
			
		}
		
	}
	
	public void setDir(Dir dir) {
		this.dir = dir;
	}

}
