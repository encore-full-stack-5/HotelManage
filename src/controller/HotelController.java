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
			System.out.println("작업내용을 선택하세요\n" + "1.예약내역확인   2.방상태확인 3.체크 인 4.체크 아웃");
			int input;
			
			try {
				input = Integer.parseInt(sc.nextLine());
				if(input==1) {
					System.out.println("예약자정보확인\n--------------------");
					hotelService.bookingState();
				}else if(input==2) {
					System.out.println("방상태확인");
					hotelService.roomState();
				}else if(input == 3) {
					System.out.println("예약자 정보를 입력해주세요.");
					System.out.print("이름: ");
					name = sc.nextLine();
					System.out.print("연락처: "); 
					phone = sc.nextLine();
					hotelService.checkInRoom(name, phone);
				}else if(input == 4) {
					System.out.println("체크아웃 할 방 번호를 입력해주세요");
					String roomNum = sc.nextLine();
					hotelService.checkOutRoom(roomNum);
				}else if(input == 9) {
					break;
				}
			} catch (NumberFormatException e) {
				// TODO: handle exception
				System.out.println("잘못된 번호입니다. 다시 입력하세요 \n");
			}
			
//			}else {
//				System.out.println("다시 입력하세요.");
//			}
		}
	}
}