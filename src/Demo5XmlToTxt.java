

import java.util.ArrayList;

import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Demo5XmlToTxt {
	private String content;
	private ArrayList<Employee> arrayList;
	
	public Demo5XmlToTxt(String xml){
		this.content = xml;
	}

	//����xml
		public ArrayList<Employee> ParseXml(TextArea ta1 ){
			String str = "";
			ArrayList<String> arr = new ArrayList<String>();
			arrayList = new ArrayList<>();
			int num1 = 0;//��¼<��>�Ƿ�ɶ�
			int num2 = 0;//��¼<>��</>�Ƿ�ɶ�
			
			for (int i = 0; i < this.content.length(); i++, str = "") {//�����ʽ
				if (this.content.charAt(i) == '<') {
					num1++;//��һ��<������һ������/a��һ
					//�ж��Ƿ�Ϊע��<!-- This is a comment --> 
					if(this.content.charAt(i+1) == '!'){
						if(i<this.content.length()){//���ж�i��ֵ����ֹ����Խ��
							if(this.content.charAt(i+2) == '-'&&this.content.charAt(i+3) == '-'){
								do{
									i++;
									if(i==this.content.length())//��ֹԽ��
										break;
								}while(this.content.charAt(i) != '>');//�ַ�Ϊ>ʱ����
								if (this.content.charAt(i) == '>') {//��ʱƥ�䵽��һ��<>,a��һ
									num1--;
									//����ע��num2����Ϊ������������num2������
								}
								continue;
							}
						}
					}
					//�ѵ����ַ�������Ҫ������
					do {
						str += this.content.charAt(i);
						i++;
						if(i==this.content.length())//��ֹԽ��
							break;
					} while (this.content.charAt(i) != '>');//�ַ�Ϊ>ʱ����
					if (this.content.charAt(i) == '>') {//��ʱƥ�䵽��һ��<>,a��һ
						num1--;
						num2++;
					}
					str = str.replaceAll("&lt;", "<");
					str = str.replaceAll("&gt;", ">");
					str = str.replaceAll("&amp;", "&");
					str = str.replaceAll("&apos;", "'");
					str = str.replaceAll("&quot;", "\"");
					arr.add(str);//����õ��ַ������뵽������
				} else if (this.content.charAt(i) != '\n' && this.content.charAt(i) != '\r' && this.content.charAt(i) != '\t'
						&& this.content.charAt(i) != ' ') {//��<>�ڵ�\n,\r,\t,��ո�,ֱ�����ڴ��forѭ������,��ȡ><�м�����ݲ���
					do {
						str += this.content.charAt(i);
						i++;
						if(i==this.content.length())//��ֹԽ��,��Ȼ����û�б�Ҫ
							break;
					} while (this.content.charAt(i) != '<');//��ʱ��iָ��<��Ϊ���õ���һ��if����ķ�������i -1
					i--;
					str = str.replaceAll("&lt;", "<");
					str = str.replaceAll("&gt;", ">");
					str = str.replaceAll("&amp;", "&");
					str = str.replaceAll("&apos;", "'");
					str = str.replaceAll("&quot;", "\"");
					arr.add(str);//��>content<�е�content����������
				}
			}
			if(num1 != 0 || num2 % 2 != 0){
				ta1.appendText("��ʽ����");
			}else{
				for (int i = 0, j = 0; i < arr.size(); i++) {
					if (i == 0) {
						ta1.appendText(arr.get(i).replaceAll("<", ""));
						j++;
						String name = "";
						String attribute = "";
						//��һ�������ַ��������name ������
						for (int k = 1; k < arr.get(i).length(); k++) {
							if(arr.get(i).charAt(k) != ' '&&arr.get(i).charAt(k) != '\t'){
								name+=arr.get(i).charAt(k);
							}else {
								do {
									if(arr.get(i).charAt(k) != ' '&&arr.get(i).charAt(k) != '\t'){
										attribute+=arr.get(i).charAt(k);
									}
									k++;
								} while (k < arr.get(i).length());
							}
						}
						Employee employee = new Employee(j, name, attribute);
						arrayList.add(employee);//��������
					}else{
						if (arr.get(i).charAt(0) == '<' && arr.get(i).charAt(1) != '/') {//��ʼ��ǩ
							ta1.appendText("\n");
							for (int j2 = 0; j2 < j; j2++) {
								ta1.appendText("\t");
							}
							j++;//������¼�ǵڼ���<
							ta1.appendText(arr.get(i).replaceAll("<", ""));
							String name = "";
							String attribute = "";
							//��һ�������ַ��������name ������
							for (int k = 1; k < arr.get(i).length(); k++) {
								if(arr.get(i).charAt(k) != ' '&&arr.get(i).charAt(k) != '\t'){
									name+=arr.get(i).charAt(k);
								}else {
									do {
										if(arr.get(i).charAt(k) != ' '&&arr.get(i).charAt(k) != '\t'){
											attribute+=arr.get(i).charAt(k);
										}
										k++;
									} while (k < arr.get(i).length());
								}
							}
							Employee employee = new Employee(j, name, attribute);
							arrayList.add(employee);//��������
						}else if(arr.get(i).charAt(0) == '<' && arr.get(i).charAt(1) == '/'){//�رձ�ǩ
							j--;
						}else{//���Բ���
							ta1.appendText(":");
							ta1.appendText(arr.get(i));
							String name = arr.get(i);
							String attribute = "";
							Employee employee = new Employee(-1, name, attribute);
							arrayList.add(employee);//��������
						}
					}
					
				}
				
			}
			return arrayList;
		}
	//������ʾxml
		public void TreeViewShow(TreeView<Text> tv1){
			tv1.setEditable(true);
			ArrayList<TreeItem<Text>> aTreeItems = new ArrayList<>();//��Žڵ��ģ��ջ����
			ArrayList<Employee> aEmployees = new ArrayList<>();//��Žڵ�Ԫ�ص�ģ��ջ���飬��������һ������ͬ������ö�Ӧ�Ľڵ�ı������
			TreeItem<Text> rootNode = new TreeItem<>(new Text(arrayList.get(0).getName()) );//���ڵ�
			if (arrayList.get(0).getAttribute() != "") {//��������ԣ����ȸ��ӵ�����ڵ���ȥ
				Text aText = new Text(arrayList.get(0).getAttribute());
				aText.setFill(Color.RED);
				rootNode.getChildren().add(new TreeItem<Text>(aText));
			}
			for (int i = 1; i < arrayList.size(); i++) {//�������ڵ㿪ʼ����
				TreeItem<Text> empLeaf = new TreeItem<>(new Text(arrayList.get(i).getName()) );//���ýڵ����
				TreeItem<Text> empLeaf1 = null;//��һ���ڵ�ı����������������ں���Ĵ���
				if (arrayList.get(i).getAttribute() != "") {
					Text aText = new Text(arrayList.get(i).getAttribute());
					aText.setFill(Color.RED);
					empLeaf.getChildren().add(new TreeItem<Text>(aText));
				}
				aTreeItems.add(empLeaf);//��ģ��ջ
				aEmployees.add(arrayList.get(i));//ͬ��
				do {
					i++;//�ȼ�
					if(i == arrayList.size() || arrayList.get(i).getNO()==2)//��������������д���Է�ֹ����Խ�磬
						break;
					if((arrayList.get(i-1).getNO()<arrayList.get(i).getNO())&&arrayList.get(i-1).getNO()!=-1){//��һ���ڵ�ı�Ŵ���ǰһ��ʱ�����������ӽڵ�
						//ע�����ݲ��ֵı��Ϊ-1�����д���
						empLeaf1 = new TreeItem<>(new Text(arrayList.get(i).getName()));//�սڵ������ֵ
						if (arrayList.get(i).getAttribute() != "") {
							Text aText = new Text(arrayList.get(i).getAttribute());
							aText.setFill(Color.RED);
							empLeaf1.getChildren().add(new TreeItem<Text>(aText));
						}
						aTreeItems.get(aTreeItems.size()-1).getChildren().add(empLeaf1);//ģ��ջ��Ԫ��Ϊ��ǰԪ�صĸ��ڵ�
						aTreeItems.add(empLeaf1);//��ģ��ջ
						aEmployees.add(arrayList.get(i));
					}
					if(arrayList.get(i).getNO()==-1){//���ݲ��֣��ǽڵ�
						TreeItem<Text> attri = new TreeItem<>(new Text(arrayList.get(i).getName()));
						aTreeItems.get(aTreeItems.size()-1).getChildren().add(attri);//ֱ��ģ��ջ������
					}
					if(arrayList.get(i-1).getNO()==-1){//ģ��ջ��ջ���֣�����ظ���Ҫ�ҵ�С������ŵĸ��ڵ��ٴ���
						while (arrayList.get(i).getNO()!=aEmployees.get(aEmployees.size()-1).getNO()) {//ͬ��ģ��ջ�Ĺ����������ڣ���õ�ǰ��Ӧջ�е�λ�õ�Ԫ�ر�ţ�
							aTreeItems.remove(aTreeItems.size()-1);//��ģ��ջ
							aEmployees.remove(aEmployees.size()-1);//ͬ��
						}
						aTreeItems.remove(aTreeItems.size()-1);//�����ȣ�����һ����ok��
						aEmployees.remove(aEmployees.size()-1);
						empLeaf1 = new TreeItem<>(new Text(arrayList.get(i).getName()));
						aTreeItems.get(aTreeItems.size()-1).getChildren().add(empLeaf1);
						aTreeItems.add(empLeaf1);//�ٽ��µĽڵ���ջ
						aEmployees.add(arrayList.get(i));
					}
					
				} while (arrayList.get(i).getNO()!=2);//do-while�����һ��С���ֵĴ����ڶ���ǩ�����к��Ӵ�
				//������������Ϊ�ڶ���ǩ��ų�ͻ��������ȫ��������
				i--;//��i���¶�λ����һ����ͻ�ĵڶ���ǩ��ѭ����
				rootNode.getChildren().add(empLeaf);//ÿ����һ���ڶ���ǩ���ʹ������ڵ���ȥ
			}
			tv1.setRoot(rootNode);
//			tv1.setCellFactory((TreeView<Text> p) -> 
//            new TextFieldTreeCellImpl());
		}		
		
}
