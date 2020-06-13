

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class Demo4txt {
	private String filePath;
	private String content;
	

	public Demo4txt(){
		
	}
	
	public Demo4txt(String filePath) throws Exception {
		this.filePath = filePath;
		this.content = readTXT( filePath);
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
	
	public String readTXT(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)),"utf-8"));

		String string;
		String txt = "";
		while ((string = br.readLine()) != null) {
			txt += string + "\r\n";
		}
		br.close();
		return	txt;
	}
	
	public void saveTxt(TextArea ta1) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
		File file = fileChooser.showSaveDialog(Demo2.stage);
		String path = file.getPath();// 选择的文件夹路径
		File tempFile = new File(path);
		byte bytes[] = new byte[512];
		bytes = ta1.getText().getBytes();
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
}
