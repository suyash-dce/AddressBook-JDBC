import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import com.bridgelabz.main.AddressBookJDBCServices;
import com.bridgelabz.main.Contact;

public class AddressBookJDBCServiceTest {
	AddressBookJDBCServices addressBookJDBCServices;

	@Before
	public void initialize() {
		addressBookJDBCServices = new AddressBookJDBCServices();
	}

	@Test
	public void givenAddressBookData_WhenRetrieved_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookJDBCServices.readData();
		assertEquals(7, contactList.size());
	}
}
