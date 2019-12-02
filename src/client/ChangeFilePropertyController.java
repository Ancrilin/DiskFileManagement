package client;

import cn.scau.edu.base.File;
import cn.scau.edu.util.Management;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ChangeFilePropertyController {

    @FXML
    private Button btn4;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn1;
    
    
    
    public void setOnlyRead()
    {
    	File file = Management.getInstance().getNowFile();
    	Management.getInstance().changeFileProperty(file,0);
    	MainUIController.getInstance().showProperty(file);
    }

    
    public void cancelOnlyRead()
    {
    	File file = Management.getInstance().getNowFile();
    	Management.getInstance().changeFileProperty(file,1);
    	MainUIController.getInstance().showProperty(file);
    }
    
    public void setSystemFile()
    {
    	File file = Management.getInstance().getNowFile();
    	Management.getInstance().changeFileProperty(file,2);
    	MainUIController.getInstance().showProperty(file);
    }
    
    public void setOridinary()
    {
    	File file = Management.getInstance().getNowFile();
    	Management.getInstance().changeFileProperty(file,3);
    	MainUIController.getInstance().showProperty(file);
    }
}