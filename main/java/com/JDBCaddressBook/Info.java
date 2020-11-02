public class Info {
	public String fName; 
	public String lName;
	public String address;
	public String city;
	public String state;
	public String zip;
	public String phone;
	public String email;

	public Info(String firstName, String lastName, String address, String city, String state, String zip,
			String phoneNo, String email) {
		this.fName = firstName; 
		this.lName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phoneNo;
		this.email = email;
	}
	
	public String pushInfoToFile() {
		return fName+":"+lName+":"+address+":"+city+":"+state
				+":"+zip+":"+phone+":"+email;
	}

	public String[] pushDataCSV() {
		String[] dataStr={fName, lName, address, city, state
				, zip, phone, email};
		return dataStr;
	}
	
	@Override
	public String toString() {
		System.out.println();
		return "Created entry for "+fName+" "+lName;
	}
}
