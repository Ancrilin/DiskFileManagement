package client;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;

import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
import javafx.fxml.Initializable;

public class OpenFileController implements Initializable {

	
	@FXML
    private TextField textField1;

    @FXML
    private TextArea textArea1;

    @FXML
    private Button btn1;

    @FXML
    private Label label2;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		File file = Management.getInstance().getNowFile();
		String data =  Management.getInstance().typeFile(file);
		if(data == null) {
			System.out.println("read fail");
		}
		this.textArea1.appendText(data);
		this.textArea1.setEditable(false);
		
//		this.textField1.focusTraversableProperty();
//		textField1.requestFocus();
	}
	
	
	
	public void onButtonClickedWrite(ActionEvent e) {
		
		if(this.textField1.getText() != "")
		{
			File file = Management.getInstance().getNowFile();
			if(!file.isOnlyReadFile()) {
				boolean result = Management.getInstance().writeFile(file, this.textField1.getText());
				
				if(result) {
					this.label2.setText("写入成功！");
					Management.getInstance().updateBuffer();
					String input = textField1.getText();
					char [] input2 = new char[input.length()-1];
					for(int i=0;i<input.length()-1;i++) {
						input2[i]=input.charAt(i);
					}
					this.textArea1.appendText(new String(input2));
					this.textField1.clear();
					MainUIController.getInstance().updateProgress();
				}else {
					this.label2.setText("写入失败");
				}
			}
		}
		
	}
	

}
