package database;

import java.time.LocalDateTime;

public abstract class HotelRoom {
	private final int roomNum;
	private final int bad;
	private final boolean pool;
	private RoomStatEnum stat;
	private Customer customer;
	private LocalDateTime cleanTime;

	public HotelRoom(int roomNum, int bad, boolean pool, RoomStatEnum stat) {
		this.roomNum = roomNum;
		this.bad = bad;
		this.pool = pool;
		this.stat = stat;
		this.customer = null;
		this.cleanTime = null;
	}

	public abstract void printRoomService();

	public int getRoomNum() {
		return roomNum;
	}

	public int getBad() {
		return bad;
	}

	public boolean isPool() {
		return pool;
	}

	public RoomStatEnum getStat() {
		return stat;
	}

	public void setStat(RoomStatEnum stat) {
		this.stat = stat;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDateTime getCleanTime() {
		return cleanTime;
	}

	public void setCleanTime(LocalDateTime cleanTime) {
		this.cleanTime = cleanTime;
	}

}