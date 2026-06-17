package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagementDAO extends BaseDAO {
	public static void main(String[] args) {
		selectUser();
	}

	public static void selectUser() {
		String sql = "SELECT id,login_id,password,name,auth FROM users";

		//DBに接続、SQL文の準備
		try (Connection conn = getConnection2();
				PreparedStatement ps = conn.prepareStatement(sql);) {

			////SQLの実行
			try (ResultSet rs = ps.executeQuery()) {

				// 検索にヒットしたデータがあれば DTO に格納
				while (rs.next()) {
					System.out.println(rs.getInt("id"));
					System.out.println(rs.getString("login_id"));
					System.out.println(rs.getString("password"));
					System.out.println(rs.getString("name"));
					System.out.println(rs.getInt("auth"));
					System.out.println();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
