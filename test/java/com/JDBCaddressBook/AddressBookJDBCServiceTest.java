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
	
	@Test
	public void givenName_WhenUpdatedContactInfo_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookService.updateCity("Suyash", "Delhi");
		boolean isSynced = addressBookService.isAddressBookSyncedWithDB("Suyash");
		assertTrue(isSynced);
	}
	
	@Test
	public void givenDateRange_WhenRetrievedContactInfo_ShouldMatchCount() throws AddressBookDBException{
		LocalDate startDate = LocalDate.of(2019, 01, 01);
		LocalDate endDate= LocalDate.now();
		List<Contact> contactList=addressBookService.getContactsForDateRange(startDate,endDate);
	}
}
