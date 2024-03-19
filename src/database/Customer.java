package database;

public class Customer {
	private final String name;
	private final int member;
	private final String phone;
	
	public Customer(String name, int member, String phone) {
		this.name = name;
		this.member = member;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public int getMember() {
		return member;
	}

	public String getPhone() {
		return phone;
	}

	public void print() {
		System.out.println("�� �̸� : " + name + "\n���� �ο� ��: " + member + "\n����ó: " + phone);
	}
	
	@Override
	public String toString() {
		return "Customer [name=" + name + ", member=" + member + ", phone=" + phone + "]";
	}
	
	
}
