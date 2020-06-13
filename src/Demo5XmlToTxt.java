

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

	//解析xml
		public ArrayList<Employee> ParseXml(TextArea ta1 ){
			String str = "";
			ArrayList<String> arr = new ArrayList<String>();
			arrayList = new ArrayList<>();
			int num1 = 0;//记录<跟>是否成对
			int num2 = 0;//记录<>跟</>是否成对
			
			for (int i = 0; i < this.content.length(); i++, str = "") {//检验格式
				if (this.content.charAt(i) == '<') {
					num1++;//有一个<计数加一，遇到/a减一
					//判断是否为注释<!-- This is a comment --> 
					if(this.content.charAt(i+1) == '!'){
						if(i<this.content.length()){//先判断i的值，防止数组越界
							if(this.content.charAt(i+2) == '-'&&this.content.charAt(i+3) == '-'){
								do{
									i++;
									if(i==this.content.length())//防止越界
										break;
								}while(this.content.charAt(i) != '>');//字符为>时跳出
								if (this.content.charAt(i) == '>') {//此时匹配到了一对<>,a减一
									num1--;
									//包括注释num2可能为单，所以这里num2不计数
								}
								continue;
							}
						}
					}
					//把单个字符连成需要的内容
					do {
						str += this.content.charAt(i);
						i++;
						if(i==this.content.length())//防止越界
							break;
					} while (this.content.charAt(i) != '>');//字符为>时跳出
					if (this.content.charAt(i) == '>') {//此时匹配到了一对<>,a减一
						num1--;
						num2++;
					}
					str = str.replaceAll("&lt;", "<");
					str = str.replaceAll("&gt;", ">");
					str = str.replaceAll("&amp;", "&");
					str = str.replaceAll("&apos;", "'");
					str = str.replaceAll("&quot;", "\"");
					arr.add(str);//将获得的字符串加入到数组中
				} else if (this.content.charAt(i) != '\n' && this.content.charAt(i) != '\r' && this.content.charAt(i) != '\t'
						&& this.content.charAt(i) != ' ') {//非<>内的\n,\r,\t,或空格,直接跳在大的for循环跳过,获取><中间的内容部分
					do {
						str += this.content.charAt(i);
						i++;
						if(i==this.content.length())//防止越界,虽然这里没有必要
							break;
					} while (this.content.charAt(i) != '<');//此时的i指向<，为了用到上一个if里面的方法，让i -1
					i--;
					str = str.replaceAll("&lt;", "<");
					str = str.replaceAll("&gt;", ">");
					str = str.replaceAll("&amp;", "&");
					str = str.replaceAll("&apos;", "'");
					str = str.replaceAll("&quot;", "\"");
					arr.add(str);//将>content<中的content放入数组中
				}
			}
			if(num1 != 0 || num2 % 2 != 0){
				ta1.appendText("格式错误！");
			}else{
				for (int i = 0, j = 0; i < arr.size(); i++) {
					if (i == 0) {
						ta1.appendText(arr.get(i).replaceAll("<", ""));
						j++;
						String name = "";
						String attribute = "";
						//将一整串的字符串分离出name 和属性
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
						arrayList.add(employee);//加入数组
					}else{
						if (arr.get(i).charAt(0) == '<' && arr.get(i).charAt(1) != '/') {//开始标签
							ta1.appendText("\n");
							for (int j2 = 0; j2 < j; j2++) {
								ta1.appendText("\t");
							}
							j++;//用来记录是第几层<
							ta1.appendText(arr.get(i).replaceAll("<", ""));
							String name = "";
							String attribute = "";
							//将一整串的字符串分离出name 和属性
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
							arrayList.add(employee);//加入数组
						}else if(arr.get(i).charAt(0) == '<' && arr.get(i).charAt(1) == '/'){//关闭标签
							j--;
						}else{//属性部分
							ta1.appendText(":");
							ta1.appendText(arr.get(i));
							String name = arr.get(i);
							String attribute = "";
							Employee employee = new Employee(-1, name, attribute);
							arrayList.add(employee);//加入数组
						}
					}
					
				}
				
			}
			return arrayList;
		}
	//树序显示xml
		public void TreeViewShow(TreeView<Text> tv1){
			tv1.setEditable(true);
			ArrayList<TreeItem<Text>> aTreeItems = new ArrayList<>();//存放节点的模拟栈数组
			ArrayList<Employee> aEmployees = new ArrayList<>();//存放节点元素的模拟栈数组，用来跟上一个数组同步，获得对应的节点的编号属性
			TreeItem<Text> rootNode = new TreeItem<>(new Text(arrayList.get(0).getName()) );//根节点
			if (arrayList.get(0).getAttribute() != "") {//如果有属性，就先附加到这个节点上去
				Text aText = new Text(arrayList.get(0).getAttribute());
				aText.setFill(Color.RED);
				rootNode.getChildren().add(new TreeItem<Text>(aText));
			}
			for (int i = 1; i < arrayList.size(); i++) {//跳过根节点开始遍历
				TreeItem<Text> empLeaf = new TreeItem<>(new Text(arrayList.get(i).getName()) );//设置节点变量
				TreeItem<Text> empLeaf1 = null;//下一个节点的变量，先声明，便于后面的串接
				if (arrayList.get(i).getAttribute() != "") {
					Text aText = new Text(arrayList.get(i).getAttribute());
					aText.setFill(Color.RED);
					empLeaf.getChildren().add(new TreeItem<Text>(aText));
				}
				aTreeItems.add(empLeaf);//入模拟栈
				aEmployees.add(arrayList.get(i));//同步
				do {
					i++;//先加
					if(i == arrayList.size() || arrayList.get(i).getNO()==2)//跳出条件，这样写可以防止数组越界，
						break;
					if((arrayList.get(i-1).getNO()<arrayList.get(i).getNO())&&arrayList.get(i-1).getNO()!=-1){//后一个节点的编号大于前一个时表明是它的子节点
						//注意内容部分的编号为-1，进行串接
						empLeaf1 = new TreeItem<>(new Text(arrayList.get(i).getName()));//空节点变量赋值
						if (arrayList.get(i).getAttribute() != "") {
							Text aText = new Text(arrayList.get(i).getAttribute());
							aText.setFill(Color.RED);
							empLeaf1.getChildren().add(new TreeItem<Text>(aText));
						}
						aTreeItems.get(aTreeItems.size()-1).getChildren().add(empLeaf1);//模拟栈顶元素为当前元素的父节点
						aTreeItems.add(empLeaf1);//入模拟栈
						aEmployees.add(arrayList.get(i));
					}
					if(arrayList.get(i).getNO()==-1){//内容部分，非节点
						TreeItem<Text> attri = new TreeItem<>(new Text(arrayList.get(i).getName()));
						aTreeItems.get(aTreeItems.size()-1).getChildren().add(attri);//直接模拟栈顶串接
					}
					if(arrayList.get(i-1).getNO()==-1){//模拟栈退栈部分，编号重复，要找到小于它编号的父节点再串接
						while (arrayList.get(i).getNO()!=aEmployees.get(aEmployees.size()-1).getNO()) {//同步模拟栈的功能体现所在，获得当前对应栈中的位置的元素编号，
							aTreeItems.remove(aTreeItems.size()-1);//退模拟栈
							aEmployees.remove(aEmployees.size()-1);//同步
						}
						aTreeItems.remove(aTreeItems.size()-1);//编号相等，再退一步就ok了
						aEmployees.remove(aEmployees.size()-1);
						empLeaf1 = new TreeItem<>(new Text(arrayList.get(i).getName()));
						aTreeItems.get(aTreeItems.size()-1).getChildren().add(empLeaf1);
						aTreeItems.add(empLeaf1);//再将新的节点入栈
						aEmployees.add(arrayList.get(i));
					}
					
				} while (arrayList.get(i).getNO()!=2);//do-while是完成一个小部分的串，第二标签的所有孩子串
				//它的跳出条件为第二标签编号冲突或者数组全部遍历完
				i--;//将i重新定位到下一个冲突的第二标签再循环串
				rootNode.getChildren().add(empLeaf);//每串成一个第二标签串就串到根节点上去
			}
			tv1.setRoot(rootNode);
//			tv1.setCellFactory((TreeView<Text> p) -> 
//            new TextFieldTreeCellImpl());
		}		
		
}
