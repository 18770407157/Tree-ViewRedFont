

import java.io.File;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class Demo3 {


    @FXML
    private Button bt3;

    @FXML
    private Button bt2;

    @FXML
    private Button bt5;

    @FXML
    private Button bt4;

    @FXML
    private TextArea ta2;

    @FXML
    private TextArea ta1;

    @FXML
    private TreeView<Text> tv1;

    @FXML
    private Button bt1;

    public void selectButton1(ActionEvent event) throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开txt文件");
		File selectedFile = fileChooser.showOpenDialog(Demo2.stage);
		Demo4txt demo4txt = new Demo4txt(selectedFile.getPath());
		ta1.setText(demo4txt.getContent());
	}
    
    public void selectButton2(ActionEvent event) throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开xml文件");
		File selectedFile = fileChooser.showOpenDialog(Demo2.stage);
		Demo4xml demo4xml = new Demo4xml(selectedFile.getPath());
		ta2.setText(demo4xml.getContent());
		Demo5XmlToTxt demo5XmlToTxt = new Demo5XmlToTxt(demo4xml.getContent());
		demo5XmlToTxt.ParseXml(ta1);
		demo5XmlToTxt.TreeViewShow(tv1);
	}
    
    public void saveTxt(ActionEvent event) throws Exception {
    	Demo4txt demo4txt = new Demo4txt();
    	demo4txt.saveTxt(ta1);
    }
    
    public void saveXml(ActionEvent event) throws Exception {
    	Demo4xml demo4xml = new Demo4xml();
    	demo4xml.saveXml(ta2);
    }

}

