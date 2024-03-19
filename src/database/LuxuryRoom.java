package database;

public class LuxuryRoom extends HotelRoom {

	public LuxuryRoom(int roomNum, RoomStatEnum stat) {
		super(roomNum, 3, true, stat);
		
	}

	@Override
	public void printRoomService() {
		System.out.println("∑∞º≈∏Æ∑Î ∑Îº≠∫ÒΩ∫ ¿‘¥œ¥Ÿ.");
		
	}
}