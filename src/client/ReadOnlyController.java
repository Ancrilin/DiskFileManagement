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

public class ReadOnlyController implements Initializable {

	
    @FXML
    private TextArea textArea1;

    @FXML
    private TextField textField1;

    @FXML
    private Label label1;

    @FXML
    private Button button1;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
public void onButtonClickedReadOnly(ActionEvent e) {
		if(!this.textField1.getText().equals("")&&this.textField1.getText()!=null)
		{
			
			File file = Management.getInstance().getNowFile();
			int length = Integer.valueOf(this.textField1.getText()).intValue();
			String data =  Management.getInstance().readFile(file, length);
			if(data == null) {
				System.out.println("read fail");
			}
			this.textArea1.appendText(data);
			this.textArea1.appendText("\n");
			this.textField1.clear();
		}
		
	}

}
