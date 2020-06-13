

public class Employee {
	private int NO;
	private String name;
	private String attribute;
	
	public Employee() {
		this.NO = 0;
		this.name = "";
		this.attribute = "";
		
	}

	public Employee(int number, String name, String attribute) {
		this.name = name;
		this.NO = number;
		this.attribute = attribute;
	}
	public int getNO() {
		return NO;
	}
	public void setNO(int nO) {
		NO = nO;
	}
	public String getName() {
		return name;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public void setName(String name) {
		this.name = name;
	}
}
