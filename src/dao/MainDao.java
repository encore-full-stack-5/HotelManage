package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;

import database.Customer;
import database.HotelRoom;
import database.LuxuryRoom;
import database.NormalRoom;
import database.RoomStatEnum;
import database.SuiteRoom;
import global.DBInit;
import global.GlobalData;

public class MainDao {
	Connection c;
	private static MainDao mainDao;

	public static MainDao getInstance() {
		if (mainDao == null)
			mainDao = new MainDao();
		return mainDao;
	}

	public MainDao() {
		try {
			c = DBInit.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<HotelRoom> getAllRoomInfo() {
		List<HotelRoom> rooms = new ArrayList<HotelRoom>();
		String sql = "select * from room_info";
		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				String roomNum = rs.getString("room_num");
				int stat = rs.getInt("room_state");
				int bookingInfo = rs.getInt("booking_info");
				HotelRoom room;
				switch (roomNum.charAt(0)) {
				case '1':
					room = new NormalRoom(Integer.parseInt(roomNum), RoomStatEnum.intToStatu(stat));
					if(stat == 2 && bookingInfo > 0)
						room.setCustomer(getCustomer(bookingInfo));
					rooms.add(room);
					break;
				case '2':
					room = new SuiteRoom(Integer.parseInt(roomNum), RoomStatEnum.intToStatu(stat));
					if(stat == 2 && bookingInfo > 0)
						room.setCustomer(getCustomer(bookingInfo));
					rooms.add(room);
					break;
				case '3':
					room = new LuxuryRoom(Integer.parseInt(roomNum), RoomStatEnum.intToStatu(stat));
					if(stat == 2 && bookingInfo > 0)
						room.setCustomer(getCustomer(bookingInfo));
					rooms.add(room);
					break;
				default:
					break;
				}
			}
//			for (int i = 0; i < rooms.size(); i++) {
//				// roomList�� ũ�Ⱑ 3*4�� 2���� �迭, rooms�� ���̰� 12�� 1���� ����Ʈ
//				GlobalData.roomList[i / 4][i % 4] = rooms.get(i);
//			}
			return rooms;

		} catch (SQLException e) {
			e.printStackTrace();
//			try {
//				c.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//				System.out.println("Rollback Error");
//			}
		}
		return null;
	}

	/**
	 * DB���� Customer �����͸� �����ɴϴ�.
	 * 
	 * @param id - HotelRoom�� ����� Customer Id.
	 * @return Customer ���� ��ȯ, ��ġ�ϴ� id�� ������ Null ��ȯ
	 */
	public Customer getCustomer(int id) {
		String sql = "select * from customer where id = " + id;

		try {
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql);
//			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery(sql);
//			System.out.println(rs.getRow());
			Customer customer = null;
			while (rs.next()) {
				String name = rs.getString("customer_name");
				int member = rs.getInt("member");
				String phone = rs.getString("phone_num");
				customer = new Customer(name, member, phone);
			}
			return customer;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Rollback Error");
			}
		}
		return null;

	}

	/**
	 * DB�� ������ �߰��ϰ�, �������� ������ id ����
	 */
	public void setBooking(HotelRoom room) {
		String sql = "insert into customer(customer_name, member, phone_num) values (?, ?, ?);";
		Integer c_id = null;
		Customer customer = room.getCustomer();
		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, customer.getName());
			ps.setInt(2, customer.getMember());
			ps.setString(3, customer.getPhone());
			
			int updateCount = ps.executeUpdate(); // int ����
			if(updateCount != 1) c.rollback();
			else c.commit();
			
			sql = "select id from customer order by id desc limit 1;";
			
			ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				c_id = rs.getInt("id");
			}
			
			sql = "update room_info set booking_info = ? where room_num = ?;";
			
			ps = c.prepareStatement(sql);
			ps.setInt(1, c_id);
			ps.setString(2, room.getRoomNum()+"");
			
			updateCount = ps.executeUpdate(); // int ����
			if(updateCount != 1) c.rollback();
			else c.commit();
			
		}catch (Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Rollback ����");
			}
		}
	}
	
	/**
	 * �������� ������ �����ϰ�, �ش� �������� ����
	 * @param roomNum - ���� ȣ�ڷ� ���ȣ
	 */
	public void delBooking(int roomNum) {
		String sql = "delete c.* from customer as c join (select * from room_info where room_num = ?) as r where r.booking_info = c.id;";

			PreparedStatement ps;
			try {
				ps = c.prepareStatement(sql);
				ps.setInt(1, roomNum);
				int rs = ps.executeUpdate();
				if (rs != 1)
					c.rollback();
				else
					c.commit();
				
				sql = "update room_info set booking_info = null where room_num = ?;";
				
				ps = c.prepareStatement(sql);
				ps.setInt(1, roomNum);
				rs = ps.executeUpdate();
				if (rs != 1)
					c.rollback();
				else
					c.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					c.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("Rollback Error");
				}
			}
	}
	
	/**
	 * ȣ�ڷ��� ���� ����
	 * @param roomNum - ������ ȣ�ڷ� ���ȣ
	 * @param stat - ������ ���� 
	 */
	public void setRoomStat(int roomNum,int stat) {
		String sql = "update room_info set room_state = "+stat+" where room_num=" + roomNum;
		
		try {
			c.setAutoCommit(false);
			// prepareStatement > ������ ���� �� ����
			PreparedStatement ps = c.prepareStatement(sql);
//			ps.setInt(1,roomNum );
//			ps.setInt(2,stat );
			int rs = ps.executeUpdate();
			if (rs == 1) c.commit();
			else c.rollback();
		}catch(Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				
			}
		}
	}
	
	/**
	 * �������� �α�DB�� �߰��մϴ�.
	 * @param roomNum - ���� ��ϵ� ȣ�� roomNum
	 */
	public void addCustomerLog(int roomNum) {
		String sql = "select booking_info from room_info where room_num = ?";
		Customer customer = null;
		try {
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, roomNum);
			ResultSet rs = ps.executeQuery();
			int customer_id = 0;
			while (rs.next()) {
				customer_id = rs.getInt("booking_info");
			}
			sql = "select * from customer where id = ?";
			ps = c.prepareStatement(sql);
			ps.setInt(1, customer_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString("customer_name");
				int member = rs.getInt("member");
				String phone = rs.getString("phone_num");
				customer = new Customer(name, member, phone);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Rollback Error");
			}
			return;
		}
		
		sql = "insert into customer_log values(null, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, roomNum);
			ps.setString(2, customer.getName());
			ps.setString(3, customer.getPhone());
			ps.setString(4, LocalDateTime.now().toString());
			int rs = ps.executeUpdate();
			if (rs != 1) {
				System.out.println("insert Error");
				c.rollback();
				return;
			}
			c.commit();
			
			sql = "update room_info set cleaning_time = now() where room_num = ?";
			ps = c.prepareStatement(sql);
			ps.setInt(1, roomNum);
			
			rs = ps.executeUpdate();
			if (rs == 1) c.commit();
			else c.rollback();
			
			delBooking(roomNum);
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Rollback Error");
			}
			return;
		}
	}
	
	/**
	 * 
	 * @return [0]-room_num, [1]room_state, [2]cleaning_time
	 */
	public List<String[]> getCleaningRoom() {
		List<String[]> rooms = new ArrayList<>();
		String sql = "select * from room_info where room_state = 4";
		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				String[] str = new String[3];
				str[0] = rs.getString("room_num");
				str[1] = rs.getString("room_state");
				str[2] = rs.getString("cleaning_time");
				rooms.add(str);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Rollback Error");
			}
		}
		return rooms;
	}
	
	public RoomStatEnum getRoom(String roomNum) {
		String sql = "select room_state from room_info where room_num = " + roomNum;
//		RoomStatEnum room = RoomStatEnum.STAYING;
		try {
			PreparedStatement ps = c.prepareStatement(sql);
//			ps.setString(1, roomNum);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				return RoomStatEnum.intToStatu(rs.getInt("room_state"));
			}

		} catch (SQLException e) {
//			e.printStackTrace();
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Rollback Error");
			}
		}
		return null;
	}
	
	public void cleaningTimeNull(String roomNum) {
		String sql = "update room_info set cleaning_time = null where room_num = " + roomNum;
		
		try {
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql);
			int rs = ps.executeUpdate();
			if (rs == 1) c.commit();
			else c.rollback();
		}catch(Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				
			}
		}
	}
	
	public void updateRoom(String roomNum) {
		String sql = "update room_info set booking_info = null where room_num = " + roomNum;
		
		try {
			c.setAutoCommit(false);
			PreparedStatement ps = c.prepareStatement(sql);
			int rs = ps.executeUpdate();
			if (rs == 1) c.commit();
			else c.rollback();
		}catch(Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				
			}
		}
	}
	
}