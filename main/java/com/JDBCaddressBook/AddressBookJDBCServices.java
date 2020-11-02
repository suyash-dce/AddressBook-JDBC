import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressBookJDBCServices{

	public List<Contact> readData() {
		String sql = String.format("select * from addressBook");
		return getContactList(sql);
	}

	private List<Contact> getContactList(String sql) {
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				contactList.add(new Contact(resultSet.getString("first_name"), resultSet.getString("last_name"),
						resultSet.getString("address"), resultSet.getString("city"), resultSet.getString("state"),
						resultSet.getString("zip"), resultSet.getString("phone_no"), resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}

	private Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/addressBook?useSSL=false";
		String userName = "root";
		String password = "jain1234";
		return DriverManager.getConnection(jdbcURL, userName, password);
	}
}