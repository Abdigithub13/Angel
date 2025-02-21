import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

abstract class PhoneBook {
    protected Map<String, String> phoneBook = new HashMap<>();
    protected static final String CONTACTS_FILE = "contacts.txt"; // Default filename for saving/loading contacts

    public abstract void addContact(String name, int phoneNumber) throws IllegalArgumentException, IOException;
    public abstract void deleteContact(String name) throws IOException;
    public abstract void editContact(String oldName, String newName, int newPhoneNumber) throws IllegalArgumentException, IOException;
    public abstract void viewAllContacts();
    public abstract boolean searchforedit(String name);
    public abstract void searchContact(String name);

    protected void saveContactsToFile() throws IOException {
        FileWriter writer = new FileWriter(CONTACTS_FILE);
        for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
            writer.write(entry.getKey() + "," + entry.getValue() + "\n");
        }
        writer.close();

    }

    protected void loadContactsFromFile() {
        try {
            FileReader reader = new FileReader(CONTACTS_FILE);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            phoneBook.clear(); // Clear the existing phone book
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    String phone = parts[1];
                    phoneBook.put(name, phone);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading contacts from file: " + e.getMessage());
        }
    }
}

class Contact extends PhoneBook {
    static String nameWithSpace() {
        Scanner data = new Scanner(System.in);
        String Full_name = data.nextLine();
        return Full_name;
    }

    private int checker(int num) {
        int count = 0;
        while (num > 0) {
            num = num / 10;
            count++;
        }

        return count;
    }

    @Override
    public void addContact(String name, int phoneNumber) throws IllegalArgumentException, IOException {
        int check = checker(phoneNumber);
        if (check != 8) {
            throw new IllegalArgumentException("Invalid phone number. Please try again.");
        }
        phoneBook.put(name, String.valueOf(phoneNumber));
        saveContactsToFile(); // Save contacts to file after adding a new contact
        System.out.println("Contact added successfully.");
    }

    @Override
    public void deleteContact(String name) throws IOException {
        if (phoneBook.containsKey(name)) {
            phoneBook.remove(name);
            saveContactsToFile(); // Save contacts to file after deleting a contact
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    @Override
    public void editContact(String oldName, String newName, int newPhoneNumber) throws IllegalArgumentException, IOException {
        int check = checker(newPhoneNumber);
        if (check != 8) {
            throw new IllegalArgumentException("Invalid phone number. Please try again.");
        }
        phoneBook.put(newName, String.valueOf(newPhoneNumber));
        saveContactsToFile(); // Save contacts to file after editing a contact
        System.out.println("Contact edited successfully.");
        deleteContact(oldName); // Delete the old contact after editing
    }

    @Override
    public void viewAllContacts() {
        if (phoneBook.isEmpty()) {
            System.out.println("Phonebook is empty.");
        } else {
            System.out.println("Contacts:");
            int i = 1;
            for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
                System.out.print(i + " ");
                i++;
                System.out.println(entry.getKey() + " - " + "+2519" + entry.getValue());
            }
        }
    }

    @Override
    public boolean searchforedit(String name) {
        boolean found = false;
        if (phoneBook.containsKey(name)) {
            found = true;
            return found;
        } else
            System.out.println("Contact not found.");
        return found;
    }

    @Override
    public void searchContact(String name) {
        if (phoneBook.containsKey(name)) {
            System.out.println(name + " - " + phoneBook.get(name));
        } else {
            System.out.println("Contact not found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Contact phonebook = new Contact();
        phonebook.loadContactsFromFile(); // Load contacts from file on startup
        Scanner scanner = new Scanner(System.in);
       while(true) {
           try {
               final int password = 12;
               System.out.println(" PERSONAL CONTACT");
               System.out.print(" Enter Password :");
               int lock = scanner.nextInt();
               if (lock == password) {
                   while (true) {
                       try {
                           System.out.println("\nPhonebook Management System");
                           System.out.println("1. Add Contact");
                           System.out.println("2. Delete Contact");
                           System.out.println("3. Edit Contact");
                           System.out.println("4. View All Contacts");
                           System.out.println("5. Search Contact");
                           System.out.println("6. Exit");
                           System.out.print("Enter your choice: ");
                           int choice = scanner.nextInt();

                           switch (choice) {
                               case 1:
                                   System.out.print("Enter name: ");
                                   String name = Contact.nameWithSpace();
                                   System.out.print("Enter phone number: +2519");
                                   int phoneNumber = scanner.nextInt();
                                   phonebook.addContact(name, phoneNumber);
                                   break;
                               case 2:
                                   System.out.print("Enter name to delete: ");
                                   name = Contact.nameWithSpace();
                                   phonebook.deleteContact(name);
                                   break;
                               case 3:
                                   System.out.print("Enter name of the contact want to edit: ");
                                   String nameToEdit = Contact.nameWithSpace();
                                   boolean search = phonebook.searchforedit(nameToEdit);
                                   if (search) {
                                       System.out.print("Enter new name: ");
                                       String nameNew = Contact.nameWithSpace();
                                       System.out.print("Enter new phone number: +2519");
                                       int newPhoneNumber = scanner.nextInt();
                                       phonebook.editContact(nameToEdit, nameNew, newPhoneNumber);
                                   } else
                                       System.out.print("your search is not found  ");
                                   break;
                               case 4:
                                   phonebook.viewAllContacts();
                                   break;
                               case 5:
                                   System.out.print("Enter name to search: ");
                                   name = Contact.nameWithSpace();
                                   phonebook.searchContact(name);
                                   break;
                               case 6:
                                   System.out.println("Exiting program.");
                                   System.exit(0);
                               default:
                                   System.out.println("Invalid choice. Please try again.");
                           }

                       } catch (InputMismatchException e) {
                           System.out.println("Invalid input. Please try again.");
                           scanner.nextLine(); // Clear the input buffer
                       } catch (IllegalArgumentException e) {
                           System.out.println("Error: " + e.getMessage());
                       } catch (IOException e) {
                           System.out.println("Error: " + e.getMessage());
                       }
                   }
               } else {
                   System.out.println("\n Sorry you are not authorised to access!");
               }
           } catch (InputMismatchException e) {
               System.out.println("Invalid input. Please try again.");
               scanner.nextLine();
           }
       }
    }
}
