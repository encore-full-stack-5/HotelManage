package service;

import java.util.ArrayList;
import java.util.List;

import dao.MainDao;
import database.Customer;
import database.HotelRoom;
import database.NormalRoom;
import database.RoomStatEnum;

public class CustomerService {
	private static MainDao mainDao;
	private static CustomerService customerService;
	public static CustomerService getInstance() {
		if (customerService == null) {
			customerService = new CustomerService();
		}
		return customerService;
	}
	
	public CustomerService() {
		mainDao = MainDao.getInstance();
	}
	
	/** �ش� roomType�� ����ִ� �� ���� ��ȯ�մϴ�.
	 * <p>
	 * @param roomType - 0=NormalRoom, 1=SuiteRoom, 2=LuxuyRoom
	 * <p>
	 * GlobalData.roomList�� 3���� �� ������, 4���� �� ������ ���� 3*4������ 2���� �迭�Դϴ�.<p>
	 * ��, roomType�� GlobalData.roomList�� ù��° ���� �ε����Դϴ�.
	 */
	public int roomTypeIsAvailable(int roomType) {
		List<HotelRoom> rooms = mainDao.getAllRoomInfo();
		int num=0;
		for(HotelRoom room : rooms) {
			if (room.getRoomNum()/100 == roomType+1)
				if(room.getStat().getCode() == 1)
					num++;
		}
		return num;
	}
	
	/** GlobalData.roomList���� roomType�� �´� ��濡 ������ ������ �ֽ��ϴ�.
	 * <p>
	 * ������ ������ ����ִ� �� �� �ε����� ���� ���� �濡 ���� �˴ϴ�.
	 * ������ ������ ���Ҿ� ���� stat�� BOOKING���� ����˴ϴ�.
	 * <p>
	 * @param roomType - 0=NormalRoom, 1=SuiteRoom, 2=LuxuyRoom
	 * @param customer - �����ϴ� �� ����
	 * <p>
	 * GlobalData.roomList�� 3���� �� ������, 4���� �� ������ ���� 3*4������ 2���� �迭�Դϴ�.<p>
	 * ��, roomType�� GlobalData.roomList�� ù��° ���� �ε����Դϴ�.
	 */
	public void bookingRoom(int roomType, Customer customer) {
		List<HotelRoom> rooms = mainDao.getAllRoomInfo();
		for(HotelRoom room : rooms) {
			if (room.getRoomNum()/100 == roomType+1)
				if(room.getStat().getCode() == 1){
					room.setCustomer(customer);
					mainDao.setBooking(room);
					mainDao.setRoomStat(room.getRoomNum(), RoomStatEnum.BOOKING.getCode());
					return;
				}
		}
	}
	
	/** �Է��� ��ȭ��ȣ�� ����� ��� ���� ��ȣ�� ��ȯ�մϴ�.
	 * <p>
	 * @param phone - �� ��ȣ
	 * @return result - �� ��ȣ�� ����� Integer List
	 */
	public List<HotelRoom> getBokingRoomByPhone(String phone) {
		List<HotelRoom> result = new ArrayList<>();
		List<HotelRoom> rooms = mainDao.getAllRoomInfo();
		for(HotelRoom room : rooms) {
			if(room.getCustomer() != null && room.getCustomer().getPhone().equals(phone))
				result.add(room);
		}
		return result;
	}
	
	/** �Է¹��� ���� ���� null�� �ٲٰ�, stat�� AVAILABLE�� �ʱ�ȭ�մϴ�.
	 * <p>
	 * @param roomType - 0=NormalRoom, 1=SuiteRoom, 2=LuxuyRoom
	 * @param roomNum - �ش� Ÿ���� ���° ������
	 * <p>
	 * GlobalData.roomList�� 3���� �� ������, 4���� �� ������ ���� 3*4������ 2���� �迭�Դϴ�.<p>
	 * ��, roomType�� GlobalData.roomList�� ù��° ���� �ε����̰�,<p>
	 * roomNum�� GlobalData.roomList�� �ι�° ���� �ε����Դϴ�.
	 */
	public void unbookingRoom(int roomType, int roomNum) {
		mainDao.delBooking(roomType*100 + roomNum);
//		GlobalData.roomList[roomType][roomNum].setCustomer(null);
//		GlobalData.roomList[roomType][roomNum].setStat(RoomStatEnum.AVAILABLE);
	}
	
	/** �Է¹��� ���� ���� null�� �ٲٰ�, stat�� AVAILABLE�� �ʱ�ȭ�մϴ�.
	 * <p>
	 * @param roomNumber - Hotelroom.getRoomNum() �ϸ� ������ 3���� String�Դϴ�.
	 * �ڵ����� roomType��, roomNum���� ��ȯ�մϴ�.
	 */
	public void unbookingRoom(String roomNumber) {
		mainDao.delBooking(Integer.parseInt(roomNumber));
		mainDao.setRoomStat(Integer.parseInt(roomNumber), RoomStatEnum.AVAILABLE.getCode());
//		int roomType = (Integer.parseInt(roomNumber.split("0")[0]))-1;
//		int roomNum = (Integer.parseInt(roomNumber.split("0")[1]))-1;
//		GlobalData.roomList[roomType][roomNum].setCustomer(null);
//		GlobalData.roomList[roomType][roomNum].setStat(RoomStatEnum.AVAILABLE);
	}

	public boolean checkingRoomNum(String roomNum) {
		if (mainDao.getRoom(roomNum) == null) {
			return false;
		} else {
			return true;
		}
	}
}