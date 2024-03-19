package controller;

import java.util.Scanner;
import database.Customer;
import service.HotelService;
import thread.CleaningThread;


public class HotelController {
	private static HotelController hotelController;
	private HotelService hotelService;
	private final Scanner sc;
	
	public static HotelController getInstance() {
		if(hotelController == null)
			hotelController = new HotelController();
		return hotelController;
	}
	
	public HotelController() {
		this.sc = new Scanner(System.in);
		this.hotelService = HotelService.getInstance();
	}

	public void start() {
		String name;
		String phone;
		
		CleaningThread cleaningThread = new CleaningThread();
		Thread thread = new Thread(cleaningThread);
		thread.start();
		
		while(true) {
			System.out.println("�۾������� �����ϼ���\n" + "1.���೻��Ȯ��   2.�����Ȯ�� 3.üũ �� 4.üũ �ƿ�");
			int input;
			
			try {
				input = Integer.parseInt(sc.nextLine());
				if(input==1) {
					System.out.println("����������Ȯ��\n--------------------");
					hotelService.bookingState();
				}else if(input==2) {
					System.out.println("�����Ȯ��");
					hotelService.roomState();
				}else if(input == 3) {
					System.out.println("������ ������ �Է����ּ���.");
					System.out.print("�̸�: ");
					name = sc.nextLine();
					System.out.print("����ó: "); 
					phone = sc.nextLine();
					hotelService.checkInRoom(name, phone);
				}else if(input == 4) {
					System.out.println("üũ�ƿ� �� �� ��ȣ�� �Է����ּ���");
					String roomNum = sc.nextLine();
					hotelService.checkOutRoom(roomNum);
				}else if(input == 9) {
					break;
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("�߸��� ��ȣ�Դϴ�. �ٽ� �Է��ϼ��� \n");
			}
			
//			}else {
//				System.out.println("�ٽ� �Է��ϼ���.");
//			}
		}
	}
}