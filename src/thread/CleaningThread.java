package thread;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.MainDao;
import database.RoomStatEnum;

public class CleaningThread extends Thread {
	final int NEED_CLEENING_TIME = 10;	// second
	final int UPDATE_TIME = 1000;		// Millisecond
	
	public void run() {
		MainDao mainDao = MainDao.getInstance();
		while (true) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			List<String[]> rooms = mainDao.getCleaningRoom();
			for(String[] room : rooms) {
				
				if(LocalDateTime.parse(room[2], formatter).plusSeconds(NEED_CLEENING_TIME).isBefore(LocalDateTime.now())){
					mainDao.setRoomStat(Integer.parseInt(room[0]), RoomStatEnum.AVAILABLE.getCode());
					mainDao.cleaningTimeNull(room[0]);
				}
			}
			
			try {
				sleep(UPDATE_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}