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
	
	@Before
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
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
	
	@Test
	public void givenAddressBookData_whenRetreivedByCity_ShouldMatchContactCount() {
		List<Contact>contactList=addressBookService.getContactsByCity("New Delhi");
		assertEquals(1,contactList.size());
	}
        
	@Test
	public void givenAddressBookData_whenRetreivedByState_ShouldMatchContactCount() {
		List<Contact>contactList=addressBookService.getContactsByCity("Delhi");
		assertEquals(1,contactList.size());
	}
	
	@Test
	public void givenContactData_WhenAddedToDB_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookService.addNewContact("2018-08-08", "Raman", "Agarwal", "Kavi Nagar", "Ghaziabad",
				"Uttar Pradesh", "220502", "9911668657", "raman.agarwal@gmail.com");
		boolean isSynced = addressBookService.isAddressBookSyncedWithDB("Trisha");
		assertTrue(isSynced);
	}
	
	@Test
	public void givenMultipeContacts_WhenAddedToDBWithMultiThreads_ShouldSyncWithDB() throws AddressBookDBException {
		List<Contact> contacts = new ArrayList<>() {
			{
				add(new Contact("Akshit", "Jain", "Uttam Nagar", "New Delhi", "Delhi", "110059",
						"7968700591", "akshit.jain@gmail.com"));
				add(new Contact("Sarvagya", "Bhargava", "Vijaynagar", "Bengaluru", "Karnataka", "560091", "8800793730",
						"emailsarvagya@gmail.com"));
			}
		};
		addressBookService.addNewMultipleContacts(contacts);
		assertEquals(7, addressBookService.readContactData(IOService.DB_IO).size());
	}
	
	public Contact[] getContactList() {
		Response response = RestAssured.get("/contacts");
		log.info("Contact entries in JSON Server :\n" + response.asString());
		Contact[] arrayOfContacts = new Gson().fromJson(response.asString(), Contact[].class);
		return arrayOfContacts;
	}
	
	@Test
	public void givenEmployeeDataInJsonServer_WhenRetrived_ShouldMatchCount() {
		Contact[] arrayOfEmps = getContactList();
		AddressBookService employeePayrollService;
		employeePayrollService = new AddressBookService(Arrays.asList(arrayOfEmps));
		long entries = employeePayrollService.countEntries(IOService.REST_IO);
		Assert.assertEquals(3, entries);
	}
	
	@Test
	public void givenNewContact_WhenUpdated_ShouldMatch() {
		Contact[] arrayOfContacts = getContactList();
		addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
		addressBookService.updateContactJsonServer("Suyash", "N.Delhi", IOService.REST_IO);
		Contact contactData = addressBookService.getContactData("Suyash");
		String contactJson = new Gson().toJson(contactData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		Response response = request.put("/contacts/" + contactData.id);
	}
}
