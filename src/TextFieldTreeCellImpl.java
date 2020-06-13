
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class TextFieldTreeCellImpl extends TreeCell<Text>{
	private TextField textField;
	
	private ContextMenu addMenu = new ContextMenu();
	
	public TextFieldTreeCellImpl(){
		MenuItem addMenuItem = new MenuItem("Add Employee");
		addMenu.getItems().add(addMenuItem);
		addMenuItem.setOnAction((ActionEvent t)->{
			TreeItem<Text> newEmployee = new TreeItem<>(new Text("New Employee"));
			getTreeItem().getChildren().add(newEmployee);
		});
	}
	
	@Override
	public void startEdit(){
		super.startEdit();
		if(textField == null){
			createTextField();
		}
		setText(null);
		textField.selectAll();
	}
	
	@Override
	public void cancelEdit(){
		super.cancelEdit();
		setText(getItem().getText());
	}
	
	@Override
	public void updateItem(Text item, boolean empty){
		super.updateItem(item, empty);
		
		if (empty) {
			setText(null);
		}else {
			if (isEditable()) {
				if(textField != null){
					textField.setText(getString());
				}
				setText(null);
			}else {
				setText(getString());
				if(!getTreeItem().isLeaf() && getTreeItem().getParent() != null)
					setContextMenu(addMenu);
				else {
					setContextMenu(null);
				}
			}
		}
	}
	
	
	private void createTextField() {
        textField = new TextField(getString());

        // �����¼������ɿ�����ʱ
        textField.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.ENTER) {
                // ����س�ʱ����Ԫ�༭Ϊ�ı�������
                commitEdit(new Text(textField.getText()));
            } else if (t.getCode() == KeyCode.ESCAPE) {
                // �����˳���ʱ,��ձ༭
                cancelEdit();
            }
        });
    }
	

	// ��ȡ��Item������
    private String getString() {
        return getItem() == null ? "" : getItem().getText();
    }

}
