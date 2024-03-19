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
	
	/** 해당 roomType의 비어있는 방 수를 반환합니다.
	 * <p>
	 * @param roomType - 0=NormalRoom, 1=SuiteRoom, 2=LuxuyRoom
	 * <p>
	 * GlobalData.roomList는 3개의 방 종류와, 4개의 방 갯수를 가진 3*4형태의 2차원 배열입니다.<p>
	 * 즉, roomType은 GlobalData.roomList의 첫번째 차원 인덱스입니다.
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
	
	/** GlobalData.roomList에서 roomType에 맞는 빈방에 예약자 정보를 넣습니다.
	 * <p>
	 * 예약자 정보는 비어있는 방 중 인덱스가 가장 작은 방에 들어가게 됩니다.
	 * 예약자 정보와 더불어 방의 stat이 BOOKING으로 변경됩니다.
	 * <p>
	 * @param roomType - 0=NormalRoom, 1=SuiteRoom, 2=LuxuyRoom
	 * @param customer - 예약하는 고객 정보
	 * <p>
	 * GlobalData.roomList는 3개의 방 종류와, 4개의 방 갯수를 가진 3*4형태의 2차원 배열입니다.<p>
	 * 즉, roomType은 GlobalData.roomList의 첫번째 차원 인덱스입니다.
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
	
	/** 입력한 전화번호로 예약된 모든 방의 번호를 반환합니다.
	 * <p>
	 * @param phone - 폰 번호
	 * @return result - 방 번호가 저장된 Integer List
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
	
	/** 입력받은 방의 고객을 null로 바꾸고, stat을 AVAILABLE로 초기화합니다.
	 * <p>
	 * @param roomType - 0=NormalRoom, 1=SuiteRoom, 2=LuxuyRoom
	 * @param roomNum - 해당 타입의 몇번째 방인지
	 * <p>
	 * GlobalData.roomList는 3개의 방 종류와, 4개의 방 갯수를 가진 3*4형태의 2차원 배열입니다.<p>
	 * 즉, roomType은 GlobalData.roomList의 첫번째 차원 인덱스이고,<p>
	 * roomNum은 GlobalData.roomList의 두번째 차원 인덱스입니다.
	 */
	public void unbookingRoom(int roomType, int roomNum) {
		mainDao.delBooking(roomType*100 + roomNum);
//		GlobalData.roomList[roomType][roomNum].setCustomer(null);
//		GlobalData.roomList[roomType][roomNum].setStat(RoomStatEnum.AVAILABLE);
	}
	
	/** 입력받은 방의 고객을 null로 바꾸고, stat을 AVAILABLE로 초기화합니다.
	 * <p>
	 * @param roomNumber - Hotelroom.getRoomNum() 하면 나오는 3글자 String입니다.
	 * 자동으로 roomType과, roomNum으로 변환합니다.
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