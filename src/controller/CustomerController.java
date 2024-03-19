package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import database.Customer;
import database.HotelRoom;
import database.NormalRoom;
import global.GlobalData;
import service.CustomerService;
import thread.CleaningThread;

public class CustomerController {
	// �̱���
	private static CustomerController customer = null;
	private static CustomerService customerService = CustomerService.getInstance();

	private CustomerController() {
		// customerService = CustomerService.getInstance();
	}

	public static CustomerController getInstance() {
		if (customer == null) {
			customer = new CustomerController();
		}
		return customer;
	}
	// �Է�

	Scanner sc = new Scanner(System.in);
	int choose;
	
	public void start() {
		CleaningThread cleaningThread = new CleaningThread();
		Thread thread = new Thread(cleaningThread);
		thread.start();
		while (true) {
			System.out.println("���Ͻô� �޴��� �����ϼ��� \n1.���� �����ϱ� 2. ���� ���� ����ϱ� 3. �� ���� �����ϱ�");
			choose = Integer.parseInt(sc.nextLine());
			try {
				if (choose == 1) {
					bookRoom();
				} else if (choose == 2) {
					bookCancel();
				} else if (choose == 3) {
					printRoomInfo();
				} else {
					System.out.println("�߸��� ��ȣ�Դϴ�. �ٽ� �Է��ϼ��� \n");
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("�߸��� ��ȣ�Դϴ�. �ٽ� �Է��ϼ��� \n");
			}
			

		}
	}

	public void bookRoom() {

		while (true) {
			System.out.println("������ ����Ͻô� ���� ������ �Է��ϼ���\n1. �Ϲݷ� \n2. ����Ʈ�� \n3. ���Ÿ��� \n4. �ڷΰ���");
			choose = Integer.parseInt(sc.nextLine());
			if (choose == 1) {
				System.out.println("�Ϲݷ�");
				// �Ϲݷ� ������Ȳ ���
				System.out.println("���డ�� �� ���� : " + customerService.roomTypeIsAvailable(0));
				System.out.println("1. �����ϱ� 2. �ڷΰ���");
				choose = Integer.parseInt(sc.nextLine());
				// 1�� ����
				if (choose == 1)
					bookPerson(0);
				// 2�� ����
				else if (choose == 2)
					continue;
			} else if (choose == 2) {
				System.out.println("����Ʈ��");
				// ����Ʈ�� ������Ȳ ���
				System.out.println("���డ�� �� ���� : " + customerService.roomTypeIsAvailable(1));
				System.out.println("1. �����ϱ� 2. �ڷΰ���");
				choose = Integer.parseInt(sc.nextLine());
				// 1�� ����
				if (choose == 1)
					bookPerson(1);
				// 2�� ����
				else if (choose == 2)
					continue;
			} else if (choose == 3) {
				System.out.println("���Ÿ���");
				// ���Ÿ��� ������Ȳ ���
				System.out.println("���డ�� �� ���� : " + customerService.roomTypeIsAvailable(2));
				System.out.println("1. �����ϱ� 2. �ڷΰ���");
				choose = Integer.parseInt(sc.nextLine());
				// 1�� ����
				if (choose == 1)
					bookPerson(2);
				// 2�� ����
				else if (choose == 2)
					continue;
			} else if (choose == 4)
				break;
			else {
				System.out.println("�߸��� ��ȣ�Դϴ�. �ٽ� �Է��ϼ���");
			}
		}

	}

	public void bookCancel() {
		while (true) {
			System.out.println("�ڵ��� ��ȣ�� �Է��ϼ��� : ");
			String phone = sc.nextLine();
			List<HotelRoom> customerList = customerService.getBokingRoomByPhone(phone);
			if (customerList.size() == 0) {
				System.out.println("�ش� ��ȣ�� ��ȸ�Ǵ� ���� �����ϴ�.\n");
				break;
			}
			System.out.println("\n" + phone + "�� �����Ͻ� ������ ������ �����ϴ�.");
			for (HotelRoom room : customerList) {
				System.out.println("\n- ���ȣ\t�ο���\t�̸�");
				System.out.println("  " + room.getRoomNum() + "\t" + room.getCustomer().getMember() + "\t" + room.getCustomer().getName());
			}
			while (true) {
				System.out.println("������ ����Ͻ� ���� ��ȣ�� �Է��ϼ��� : ");
				String roomNum = sc.nextLine();
				
				if (customerService.checkingRoomNum(roomNum)) {
					customerService.unbookingRoom(roomNum);
					System.out.println("��Ұ� �Ϸ�Ǿ����ϴ�.\n");
					break;
				} else {
					System.out.println("�� ��ȣ�� �ٽ� �Է��ϼ���\n");
				}
			}
			break;
		}

	}

	public void printRoomInfo() {
		System.out.println(
				"�� ����\n1. �Ϲݽ�\t\t2. ����Ʈ��\t3. ���Ÿ���\nħ�밳�� : 2\tħ�밳�� : 3\tħ�밳�� : 3\nǮ�忩�� : ��\tǮ�忩�� : ��\tǮ�忩�� : ��\n");
//		System.out.println("�� ���� \n\n1. �Ϲݽ� \nħ�밳�� : 2 \nǮ�� ���� : �� \n\n2. ����Ʈ�� \nħ�밳�� : 3 \nǮ�忩�� : �� \n\n3. ���Ÿ��� \nħ�밳�� : 3 \nǮ�忩�� : ��\n");
//		List<HotelRoom> list = new ArrayList<HotelRoom>();
//		System.out.println("�� ����");
//		System.out.println("�Ϲݽ�");
//		System.out.println("ħ�� ���� : " + list.get(0).getBed());
	}

	public void bookPerson(int roomType) {
		Customer customer;
		String name;
		int family;
		String phone;

		System.out.println("�̸��� �Է��ϼ��� :");
		name = sc.nextLine();
		System.out.println("�ο� ���� �Է��ϼ��� :");
		family = Integer.parseInt(sc.nextLine());
		System.out.println("�ڵ��� ��ȣ�� �Է��ϼ���");
		phone = sc.nextLine();
//		System.out.println("�̸� : "+name+"\n�ο� �� : "+family+"/n�ڵ��� ��ȣ : "+phone+"/n�� ������ �½��ϱ�?");
		System.out.println("������ �Ϸ�Ǿ����ϴ�.\n");
		customer = new Customer(name, family, phone);

		customerService.bookingRoom(roomType, customer);

	}

}