import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddressBookMain {
	private static HashMap<String, ArrayList<Info>> deathNote;
	private static ArrayList<Info> friends;
	static Scanner stdlin = new Scanner(System.in);
	private static final String FILE_NAME = "AddressBookRecord.txt";
	private static final String FILE_NAME_CSV = "AddressBookRecord.csv";
	private static final String FILE_NAME_JSON = "AddressBookRecord.JSON";
	private static final String JSON_FILE_WRITE = "AddressBookRecord.JSON";

	public AddressBookMain() {
		friends = new ArrayList<Info>();
		deathNote = new HashMap<String, ArrayList<Info>>();
	}

	public void addNewContact(Info entry) {
		friends.add(new Info(entry.fName, entry.lName, entry.address, entry.city, entry.state, entry.zip, entry.phone,
				entry.email));
	}

	public void addAddressBook(String bookName) {
		deathNote.put(bookName, new ArrayList<Info>());
	}

	public static Info add() {

		// method for adding new entries.
		Scanner stdlin = new Scanner(System.in);
		// Attributes to be added

		// asking user input
		System.out.println("enter details ");
		System.out.print("First Name: ");
		String fName = stdlin.next();
		System.out.print("Last Name: ");
		String lName = stdlin.next();
		System.out.print("Address: ");
		String address = stdlin.next();
		System.out.print("City: ");
		String city = stdlin.next();
		System.out.print("State: ");
		String state = stdlin.next();
		System.out.print("ZipCode: ");
		String zip = stdlin.next();
		System.out.print("Phone No.: ");
		String phone = stdlin.next();
		System.out.print("Email: ");
		String email = stdlin.next();

		// saving as new entry
		Info contact = new Info(fName, lName, address, city, state, zip, phone, email);
		return contact;
	}

	public static ArrayList<Info> edit(ArrayList<Info> list, String name) {

		// method for edit
		Scanner stdlin = new Scanner(System.in);
		boolean flag = false;

		for (Info entry : list) {

			if (entry.fName.equals(name)) {
				flag = true;
				System.out.println("Please enter new details.");
				System.out.print("Address: ");
				entry.address = stdlin.next();
				System.out.print("City: ");
				entry.city = stdlin.next();
				System.out.print("State: ");
				entry.state = stdlin.next();
				System.out.print("ZipCode: ");
				entry.zip = stdlin.next();
				System.out.print("Phone No.: ");
				entry.phone = stdlin.next();
				System.out.print("Email: ");
				entry.email = stdlin.next();

				System.out.println("Contact updated.");
				break;
			}
		}
		if (flag == false) {
			System.out.println("Contact not found");
		}
		return list;
	}

	public static ArrayList<Info> delete(ArrayList<Info> list, String namedel) {

		// method for delete

		boolean flag = false;

		try {
			for (Info entry : list) {

				if (entry.fName.equals(namedel)) {
					flag = true;

					System.out.println("Contact deleted for " + entry.fName);
					list.remove(entry); // delete entry from record

				}
			}
		} catch (Exception e) {
		}
		if (flag == false) {
			System.out.println("No entry found for " + namedel);
		}
		return list;
	}

	// Sort By State
	private static void SortState() {
		System.out.println("Enter the name of the state");
		String getStatename = stdlin.next();
		Stream<Entry<String, ArrayList<Info>>> result = deathNote.entrySet().stream()
				.filter(map -> getStatename.equals(map.getValue()));
		System.out.println("Following persons belong to provided states:" + result);
	}

	// Sort by City
	private static void SortCity() {

		System.out.println("Enter the name of the city");
		String getCityname = stdlin.next();
		Stream<Entry<String, ArrayList<Info>>> result = deathNote.entrySet().stream()
				.filter(map -> getCityname.equals(map.getValue()));
		System.out.println("Following persons belong to provided city:" + result);
	}

	// Count by State
	private static void CountbyState() {
		System.out.println("Enter the name of the state");
		String getStatename = stdlin.next();
		int count = Collections.frequency(deathNote.values(), getStatename);
		System.out.println(count);

	}

	// Count by city
	private static void CountbyCity() {
		System.out.println("Enter the name of the city");
		String getCityname = stdlin.next();
		int count = Collections.frequency(deathNote.values(), getCityname);
		System.out.println(count);

	}

	// Sort by first name
	/*
	 * private static void SortByFirstName(ArrayList<Info> friends) { Stream<Info>
	 * firstNameBasis = friends.stream().sorted();
	 * firstNameBasis.sorted(Comparator.comparing(Info::getfName));
	 * System.out.println(firstNameBasis); }
	 */

	public static void writeToFile() {

		StringBuffer strBuffer = new StringBuffer();
		friends.forEach(contacts -> {
			String contactStr = contacts.pushInfoToFile().concat("\n");
			strBuffer.append(contactStr);
		});

		try {
			Files.write(Paths.get(FILE_NAME), strBuffer.toString().getBytes());
			System.out.println("Data written to File");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public static ArrayList<Info> readFile() {

		ArrayList<Info> fileInfo = new ArrayList<Info>();

		try {
			Files.lines(new File(FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> {
				String data = line.toString();
				String[] record = data.split(":");

				String fName = record[0];
				String lName = record[1];
				String address = record[2];
				String city = record[3];
				String state = record[4];
				String zip = record[5];
				String phone = record[6];
				String email = record[7];

				fileInfo.add(new Info(fName, lName, address, city, state, zip, phone, email));
			});
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return fileInfo;
	}

	public static void writeToCSV() {
		new FileIO().writeData(FILE_NAME_CSV, friends);
		System.out.println("Write Successful.");
	}

	public static void readFromCSV() {
		new FileIO().readData(FILE_NAME_CSV);
	}

	public static void readFromJSON() {
		new FileIO().readJSONFile(FILE_NAME_JSON);
	}

	public static void jsonWriter() {
		new FileIO().writeDatatoJson(friends, JSON_FILE_WRITE);
	}

	public static void main(String[] args) {
		Scanner stdlin = new Scanner(System.in);
		AddressBookMain makeentry = new AddressBookMain();
		makeentry.addAddressBook("AddressBook1");

		// Creating first entry
		Info entry1 = new Info("Arjun", "Gupta", "Ayodhya Bypass", "Bhopal", "MP", "462041", "8824347236",
				"arjun17697@gmail.com");
		makeentry.addNewContact(entry1); // Adding entry to record
		System.out.println(entry1);

		// initiating user functions of entries

		String user_input = "1";
		while ((user_input.equals("1") || user_input.equals("2") || user_input.equals("3") || user_input.equals("4")
				|| user_input.equals("5") || user_input.equals("6") || user_input.equals("7") || user_input.equals("8")
				|| user_input.equals("9") || user_input.equals("10") || user_input.equals("11")
				|| user_input.equals("12"))) {

			// Checking if address list is present in hashmap
			System.out.print("Enter the Name of the Address Book: ");
			String bookName = stdlin.next();
			if (makeentry.deathNote.containsKey(bookName)) {
				makeentry.friends = makeentry.deathNote.get(bookName);
			}

			else {
				System.out.println("Address book with name " + bookName + " not found. Creating a new entry");
				makeentry.addAddressBook(bookName);
			}

			System.out.println("Record " + bookName + " loaded.");

			System.out.println("1. Add a new contact.");
			System.out.println("2. Edit an existing contact.");
			System.out.println("3. Delete an existing contact.");
			System.out.println("4.Sort by City");
			System.out.println("5.Sort by State");
			System.out.println("6.Count by State");
			System.out.println("7.Sort by City");
			System.out.println("9.Write data to a CSV File");
			System.out.println("10.Read data from a CSV File");
			System.out.println("11.Read data from a JSON File");
			System.out.println("12.Write data to a JSON File");
			System.out.println("13.Write data to a txt File");
			System.out.println("14.Read data from a txt File");
			user_input = stdlin.next();

			switch (user_input) {

			case "1":
				Info contact = makeentry.add(); // calling function to make new entry
				makeentry.addNewContact(contact); // Adding entry to record
				System.out.println(contact);
				break;

			case "2":
				System.out.println("Please enter First name of entry to be edited.");
				String name = stdlin.next();
				ArrayList<Info> list = makeentry.edit(friends, name);
				break;

			case "3":
				System.out.println("Please enter First name of entry to be deleted.");
				String namedel = stdlin.next();
				friends = delete(friends, namedel);
				break;

			case "4":
				SortState();
				break;

			case "5":
				SortCity();
				break;

			case "6":
				CountbyState();
				break;

			case "7":
				CountbyCity();
				break;

			/*
			 * case "8": SortByFirstName(friends); break;
			 */
			case "9":
				
				writeToCSV();

				break;

			case "10":
				
				readFromCSV();
				break;

			case "11":
				readFromJSON();
				break;
			case "12":
				jsonWriter();
				break;
				
			case "13":
				writeToFile();
				break;
				
			case "14":
				readFile();
				break;

			default:
				break;

			}

			// Removing duplicates (Only first entry is preserved,rest removed)
			List<Info> noDuplicates = friends.stream().distinct().collect(Collectors.toList());
			System.out.println("List with duplicates removed:" + noDuplicates);

		}
	}
}
