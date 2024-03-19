package main;

import controller.HotelController;
import database.Customer;
import database.NormalRoom;
import database.RoomStatEnum;
import global.GlobalData;

public class HotelMain {
	public static void main(String[] args) {
		HotelController controller = HotelController.getInstance();	
		controller.start();
	}
	
	
}