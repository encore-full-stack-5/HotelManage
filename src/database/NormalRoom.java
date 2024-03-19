package database;

public class NormalRoom extends HotelRoom {

	public NormalRoom(int roomNum, RoomStatEnum stat) {
		super(roomNum, 2, false, stat);
		
	}

	@Override
	public void printRoomService() {
		System.out.println("¿œπ›∑Î ∑Îº≠∫ÒΩ∫ ¿‘¥œ¥Ÿ.");
		
	}
}
