

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Demo2 extends Application{

	public static final Window stage = null;
	public void start(Stage stage) throws Exception{
		 Parent root = FXMLLoader.load(getClass().getResource("treeView.fxml"));
		 	
	        stage.setTitle("demo");
	        stage.setScene(new Scene(root, 1170, 700));
	        stage.setResizable(false);
	        stage.show();
	}
	 public static void main(String[] args) {
	        launch(args);
	    }
}
