package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dao.MainDao;
import database.Customer;
import database.HotelRoom;
import database.RoomStatEnum;
import global.GlobalData;

public class HotelService {
	private static HotelService hotelService;
	private final MainDao dao;
	
	public static HotelService getInstance() {
		if(hotelService == null)
			hotelService = new HotelService();
		return hotelService;
	}
	
	private HotelService() {
		dao = MainDao.getInstance();
	}
	
	public void bookingState() {
		List<HotelRoom> list = new ArrayList<HotelRoom>();
		List<HotelRoom> rooms = dao.getAllRoomInfo();
		for(int i = 0; i < rooms.size(); i ++) {
			if(rooms.get(i).getStat().getCode() == 2) {
				list.add(rooms.get(i));
			}
		}
		
		
//		for(int i = 0; i < GlobalData.roomList.length; i++) {
//			for(int j = 0; j < GlobalData.roomList[0].length; j++) {
//				if(GlobalData.roomList[i][j].getCustomer() != null)
//					if(GlobalData.roomList[i][j].getStat() == RoomStatEnum.BOOKING)
//						list.add(GlobalData.roomList[i][j]);
//			}
//		}

		for(int i = 0; i < list.size(); i++) {
			System.out.println("�� ��ȣ: " + list.get(i).getRoomNum() + "ȣ");
			
			list.get(i).getCustomer().print();

			System.out.println("--------------------");
		}
	}
	
	public void roomState() {
		HotelRoom[][] list = new HotelRoom[3][4];
		List<HotelRoom> rooms = dao.getAllRoomInfo();
		
		
		for(int i = 0; i < list.length; i++) {
			for(int j = 0; j < rooms.size(); j ++) {
				list[i][j%4] = rooms.get(j+(i*4));
				if(j % 4 == 3) break;
			}
		}
		
		System.out.println("���డ�� : ��  ���� �� : ��   ���� �� : ��  û�� �� : ��  ");
		System.out.println("���ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�-�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѦ�");
		System.out.println("| \t\t1ȣ\t2ȣ\t3ȣ\t4ȣ\t|");
		
		for(int i = list.length-1; i >= 0; i--) {
//			
			if(i == 0) System.out.print("|�Ϲݷ� 1��\t");
			else if(i == 1) System.out.print("|����Ʈ�� 2��\t");
			else System.out.print("|���Ÿ��� 3��\t");
			
			for(int j = 0; j < list[i].length; j++) {
				if(list[i][j].getStat() == RoomStatEnum.AVAILABLE)
					System.out.print("��\t");
				else if(list[i][j].getStat() == RoomStatEnum.BOOKING)
					System.out.print("��\t");
				else if(list[i][j].getStat() == RoomStatEnum.STAYING)
					System.out.print("��\t");
				else
					System.out.print("��\t");
			}
			System.out.println("|");
		}
		System.out.println("���ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�-�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѦ�");
		
//		for(int i = rooms.size()-1; i >= 0; i--) {
//			
//			if(i == 0) System.out.print("|�Ϲݷ� 1��\t");
//			else if(i == 4) System.out.print("|����Ʈ�� 2��\t");
//			else if(i == 8) System.out.print("|���Ÿ��� 3��\t");
//			
//			if(rooms.get(i).getStat() == RoomStatEnum.AVAILABLE)
//				System.out.print("��\t");
//			else if(rooms.get(i).getStat() == RoomStatEnum.BOOKING)
//				System.out.print("��\t");
//			else if(rooms.get(i).getStat() == RoomStatEnum.STAYING)
//				System.out.print("��\t");
//			else
//				System.out.print("��\t");
//			
//			if(i % 4 == 3) System.out.println("|");
//		}
//		System.out.println("���ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�-�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѦ�");
		
//		System.out.println("���డ�� : ��  ���� �� : ��   ���� �� : ��  û�� �� : ��  ");
//		System.out.println("���ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�-�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѦ�");
//		System.out.println("| \t\t1ȣ\t2ȣ\t3ȣ\t4ȣ\t|");
//		for(int i = GlobalData.roomList.length-1; i >=0; i--) {
//			
//			if(i == 0) System.out.print("|�Ϲݷ� 1��\t");
//			else if(i == 1) System.out.print("|����Ʈ�� 2��\t");
//			else System.out.print("|���Ÿ��� 3��\t");
//			
//			for(int j = 0; j < GlobalData.roomList[0].length; j++) {
//				if(GlobalData.roomList[i][j].getStat() == RoomStatEnum.AVAILABLE)
//					System.out.print("��\t");
//				else if(GlobalData.roomList[i][j].getStat() == RoomStatEnum.BOOKING)
//					System.out.print("��\t");
//				else if(GlobalData.roomList[i][j].getStat() == RoomStatEnum.STAYING)
//					System.out.print("��\t");
//				else
//					System.out.print("��\t");
//			}
//			System.out.println("|");
//		}
//		System.out.println("���ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�-�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѦ�");
	}
	
	public void checkOutRoom(String roomNum) {
		RoomStatEnum room = dao.getRoom(roomNum);
		
		if(room == RoomStatEnum.STAYING) {
			dao.setRoomStat(Integer.parseInt(roomNum), 4);
			dao.addCustomerLog(Integer.parseInt(roomNum));
			dao.updateRoom(roomNum);
			System.out.println("üũ �ƿ� �ƽ��ϴ�.");
			roomState();
		} else if(room == RoomStatEnum.AVAILABLE)
			System.out.println("û�Ҹ� ��ģ ���Դϴ�.");
		else
			System.out.println("û�� �� �Դϴ�.");
	}
	
	public void checkInRoom(String name, String phone) {
		String p ="";
		try {
			p = phone.split("-")[2];	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("����ó�� �߸��� �����Դϴ�.");
			return;
		}
		
		List<HotelRoom> rooms = dao.getAllRoomInfo();
		
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).getCustomer() != null) {
				if(name.equals(rooms.get(i).getCustomer().getName())) {
					if(p.equals(rooms.get(i).getCustomer().getPhone().split("-")[2])) {
						System.out.println("��ſ� ���� �ǽʽÿ�~*^^*\n");
						dao.setRoomStat(rooms.get(i).getRoomNum(),RoomStatEnum.STAYING.getCode());
						return;
					}
				} 
			}else {
				if(i == rooms.size()-1)
					System.out.println(name + "������ ����� ���� �����ϴ�.");
			}
		}
	}
}
