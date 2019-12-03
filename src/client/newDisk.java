package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class newDisk {

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField1;

    @FXML
    private Label label1;

    @FXML
    private Button button1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    public void newDisk() {
		this.label3.setText("");
    	if(!textField1.equals("")&&!textField2.equals("")) {
//    		System.out.println("num: "+this.textField2.getText());
    		int num = Integer.valueOf(this.textField2.getText()).intValue();
    		String disk_id = textField1.getText();
    		boolean result = MainUIController.getInstance().newDisk(disk_id, num);
    		if(result == true) {
    			this.label3.setText("磁盘创建成功！");
    		}else {
    			this.label3.setText("磁盘创建失败！");
    		}
        	this.textField1.setText("");
    		this.textField2.setText("");
    	}
    }

}
