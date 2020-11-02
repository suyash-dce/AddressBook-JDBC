import com.opencsv.bean.CsvBindByName;

public class Contact {
	private int id;
	@CsvBindByName(column = "First Name")
	private String firstName;
	@CsvBindByName(column = "Last Name")
	private String lastName;
	@CsvBindByName(column = "Name")
	private String name;
	@CsvBindByName(column = "Address")
	private String address;
	@CsvBindByName(column = "City")
	private String city;
	@CsvBindByName(column = "State")
	private String state;
	@CsvBindByName(column = "ZIP")
	private String zip;
	@CsvBindByName(column = "Ph Number")
	private String phoneNo;
	@CsvBindByName(column = "Email")
	private String email;

	public Contact() {
	}

	public Contact(String firstName, String lastName, String address, String city, String state, String zip,
			String phoneNo, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNo = phoneNo;
		this.email = email;
		this.name = (firstName + " " + lastName).toLowerCase();

	}

	public Contact(int id, String firstName, String lastName, String address, String city, String state, String zip,
			String phoneNo, String email) {
		this(firstName, lastName, address, city, state, zip, phoneNo, email);
		this.id = id;
	}

	// getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (email == null && phoneNo == null) {
			if (other.email != email && other.phoneNo != phoneNo)
				return false;
		} else if (!phoneNo.equals(other.phoneNo) && !email.equals(other.email)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Name: " + firstName + " " + lastName + "\tAddress: " + address + "\tCity: " + city + "\tState: " + state
				+ "\tZip: " + zip + "\tPhone No: " + phoneNo + "\tEmail: " + email + "\n";
	}

}
