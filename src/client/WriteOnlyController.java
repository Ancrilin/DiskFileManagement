package client;

import java.net.URL;

import java.util.ResourceBundle;

import cn.scau.edu.base.Dir;
import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class WriteOnlyController implements Initializable {

	
    @FXML
    private TextField textField1;

    @FXML
    private Label label1;

    @FXML
    private Button button1;
    

    @FXML
    private Label Label2;
    

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
public void onButtonClickedWriteOnly(ActionEvent e) {
		if(this.textField1.getText() != "")
		{
			File file = Management.getInstance().getNowFile();
			if(!file.isOnlyReadFile()) {
				boolean result = Management.getInstance().writeFile(file, this.textField1.getText());
				this.textField1.clear();
				if(result) {
					this.Label2.setText("写入成功！");
					Management.getInstance().updateBuffer();
					MainUIController.getInstance().updateProgress();
				}else {
					this.Label2.setText("写入失败");
				}
			}
		}
		
	}

}
