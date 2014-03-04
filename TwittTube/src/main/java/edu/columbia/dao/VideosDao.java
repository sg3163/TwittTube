package edu.columbia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import edu.columbia.vo.User;
import edu.columbia.vo.Video;

public class VideosDao {
	
	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		System.out.println("------------- Setting datasource to the dao");
		this.dataSource = dataSource;
	}
 
	public List<Video> getVideosforUser(String userId){
 
	/*	String sql = "select * from usr_videos uv where user_id in (" +
					"	select tu.user_id from twit_users tu " +
					"	join USR_GRP_MAP maps on maps.user_id = tu.user_id" +
					"	where maps.group_id in (" +
					"	select maps.group_id from USR_GRP_MAP maps" +
					"	join twit_groups tg on tg.group_id = maps.group_id" +
					"	where maps.user_id = ?))";*/
		
		String sql = "select uv.*, t.first_nm || ' ' || t.last_nm as name " +
					"	from usr_videos uv " +
					"	join twit_users t on t.user_id = uv.user_id" +
					"	where reply_to is null and uv.user_id in (" +
				"	select tu.user_id " +
				"	from twit_users tu" +
				"	join USR_GRP_MAP maps on maps.user_id = tu.user_id" +
				"	where maps.group_id in (" +
				"	select maps.group_id from USR_GRP_MAP maps" +
				"	join twit_groups tg on tg.group_id = maps.group_id" +
				"	where maps.user_id = ?))";
 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			List<Video> videosList = new ArrayList<>();
			Video video = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				video = new Video(
					rs.getString("VIDEO_ID"),
					rs.getString("USER_ID"), 
					rs.getString("VIDEO_LOC"),
					rs.getString("REPLY_TO"),
					rs.getString("NAME")
				);
				videosList.add(video);
			}
			rs.close();
			ps.close();
			return videosList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public List<Video> getVideoAndReplies(String videoId){
		 
			String sql = "select uv.*, t.first_nm || ' ' || t.last_nm as name  " +
				        " from usr_videos uv  " +
				       " join twit_users t on t.user_id = uv.user_id " +
				        "where reply_to is null and  uv.video_id= ? " +
				       " union " +
				      " select uv.*, t.first_nm || ' ' || t.last_nm as name  " +
				       " from usr_videos uv  " +
				      "  join twit_users t on t.user_id = uv.user_id " +
				       " where reply_to=?";
	 
			Connection conn = null;
	 
			try {
				conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, videoId);
				ps.setString(2, videoId);
				List<Video> videosList = new ArrayList<>();
				Video video = null;
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					video = new Video(
						rs.getString("VIDEO_ID"),
						rs.getString("USER_ID"), 
						rs.getString("VIDEO_LOC"),
						rs.getString("REPLY_TO"),
						rs.getString("NAME")
					);
					videosList.add(video);
				}
				rs.close();
				ps.close();
				return videosList;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} finally {
				if (conn != null) {
					try {
					conn.close();
					} catch (SQLException e) {}
				}
			}
		}
	
	public List<User> findByCustomerId(int custId){
		 
		String sql = "SELECT * FROM TWIT_USERS";
 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			List<User> usersList = new ArrayList<>();
			User user = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User(
					rs.getString("USER_ID"),
					rs.getString("FIRST_NM"), 
					rs.getString("LAST_NM")
				);
				usersList.add(user);
			}
			rs.close();
			ps.close();
			return usersList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public String getNextVideoSequence() {
		String sql = "select usr_videos_seq.nextval from dual";
		Connection conn = null;
		
		try 
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("NEXTVAL");
			}
			rs.close();
			ps.close();
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException(e);
		} 
		finally 
		{
			if (conn != null) 
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) {}
			}
		}
		return "0";
	}
	
	public void saveVideoMetadata(String videoId, String userId, String videoLoc, String replyTo)
	{
		String sql = "INSERT INTO USR_VIDEOS VALUES (" +  "\'" + videoId +  "\'" + "," +  "\'" + userId +  "\'" + "," +  "\'" + videoLoc+  "\'" + ","+  "\'" + replyTo +  "\'" + ") ";
		Connection conn = null;
		 
		try 
		{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate(sql); 
			ps.close();
		} 
		catch (SQLException e) 
		{
			throw new RuntimeException(e);
		} 
		finally 
		{
			if (conn != null) 
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) {}
			}
		}
	}
}
