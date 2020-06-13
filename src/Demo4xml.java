

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class Demo4xml {
	private String filePath;
	private String content;
	private ArrayList<Employee> arrayList;
	
	public Demo4xml(){
		
	}
	public Demo4xml(String filePath) throws Exception {
		this.filePath = filePath;
		this.content = readXML( filePath);
	}
	
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String readXML(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)),"utf-8"));

		String string;
		String xml = "";
		while ((string = br.readLine()) != null) {
			xml += string + "\r\n";
		}
		br.close();
		return	xml;
	}
	
	public void saveXml(TextArea ta2) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
		File file = fileChooser.showSaveDialog(Demo2.stage);
		String path = file.getPath();// 选择的文件夹路径
		File tempFile = new File(path);
		byte bytes[] = new byte[512];
		bytes = ta2.getText().getBytes();
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
}
