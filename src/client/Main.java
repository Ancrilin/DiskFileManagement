package client;

import cn.scau.edu.util.Management;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			Stage primarystage = new Stage();
	       	Parent r = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
	       	//AnchorPane anchorpane = new AnchorPane();
	       	Scene s = new Scene(r);
	       //	MainUIController mc = new MainUIController();
	       	//TreeView<String> tv = new TreeView<> (Management.getInstance());
	       	
	           primaryStage.setScene(s);
	           primaryStage.setResizable(false);//设置不能窗口改变大小
	           primaryStage.show();
	          
	           
	           
	   	 } catch(Exception e) {
	            e.printStackTrace();
	        }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			launch(args);
	}
	


}
