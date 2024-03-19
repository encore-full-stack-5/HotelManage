package database;

public enum RoomStatEnum {
	AVAILABLE(1), BOOKING(2), STAYING(3), CLEANING(4);
	
	private int code;

	private RoomStatEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


	public static RoomStatEnum intToStatu(int code) {
		if(code == 1) return RoomStatEnum.AVAILABLE;
		else if(code == 2) return RoomStatEnum.BOOKING;
		else if(code == 3) return RoomStatEnum.STAYING;
		else return RoomStatEnum.CLEANING;
	}
	
}
