import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    // Attributes
    private char row;
    private int seat;
    private double price;
    private Person person; // Person object from Task 7



    // Constructor
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters
    public char getRow() {
        return row;
    }

    public int getSeat() {

        return seat;
    }

    public double getPrice() {

        return price;
    }

    public Person getPerson() {
        return person;
    }


    // Setters
    public void setRow(char row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    // Method to print ticket information
    public void printTicketInformation() {
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price );
        // Print the information of the Person associated with this ticket
        person.printInformation();
    }

    // save
    public void save() {
        String filename;
        filename= row + seat + ".txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Ticket Information: \n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat Number: " + seat + "\n");
            writer.write("Price: €" + price + "\n");
            writer.write("Person Information: \n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket.");
            e.printStackTrace();
        }
    }

}
