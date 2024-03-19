package database;

public class SuiteRoom extends HotelRoom {

	public SuiteRoom(int roomNum, RoomStatEnum stat) {
		super(roomNum, 3, false, stat);	
	}

	@Override
	public void printRoomService() {
		System.out.println("스위트룸 룸서비스 입니다.");
	}
	
}