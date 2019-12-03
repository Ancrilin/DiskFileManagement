package client;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileView;

import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
import javafx.fxml.Initializable;

public class NewFileController implements Initializable {

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

	
public void onButtonClickedNewFile(ActionEvent e) {
		this.label2.setText("");

		if(!this.textField1.getText().equals(""))
		{
			File file = Management.getInstance().makeFile(this.textField1.getText());
			if(file!=null) {
				this.label2.setText("新建文件成功!");
				client.FileView.addDirTreeNode(file, file.getParent().getTreeItem());
				MainUIController.getInstance().updateProgress();
				file.getParent().getTreeItem().setExpanded(true);
			}else {
				this.label2.setText("新建文件失败，有重复文件名！!");
			}
			
			
			
		}
		
	}
}
