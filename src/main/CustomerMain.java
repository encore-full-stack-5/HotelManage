package main;

import controller.CustomerController;
import database.NormalRoom;
import database.RoomStatEnum;
import global.GlobalData;

public class CustomerMain {

	public static void main(String[] args) {
		CustomerController customerController = CustomerController.getInstance();
		customerController.start();
	}

}
