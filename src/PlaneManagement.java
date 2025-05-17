import java.util.Scanner;
public class PlaneManagement {

    public static void main(String[] args) {
        System.out.println("Welcome to the Plane Management application");

        // Creating Plane seating Variables
        int[][] seats = new int[4][];
        seats[0] = new int[14];//Row A
        seats[1] = new int[12];//Row B
        seats[2] = new int[12];//Row C
        seats[3] = new int[14];//Row D

        //Set up All seats for available
        for (int row = 0; row < seats.length; row++) {
            for (int seat = 0; seat < seats[row].length; seat++) {
                seats[row][seat] = 0;
            }
        }

        //Read User Input
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("**************************");
            System.out.println("*\t MENU OPTIONS\t\t *");
            System.out.println("**************************");
            System.out.println("\t1) Buy a seat");
            System.out.println("\t2) Cancel a seat");
            System.out.println("\t3) Find first available seat");
            System.out.println("\t4) Show seating plan");
            System.out.println("\t5) Print tickets information and total sales");
            System.out.println("\t6) Search ticket");
            System.out.println("\t0) Quit");
            System.out.println("**************************");

            //Create Cases
            System.out.print("Please select an option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    buy_seat(seats);
                    break;
                case 2:
                    cancel_seat(seats);
                    break;
                case 3:
                    find_first_available(seats);
                    break;
                case 4:
                    show_seating_plan(seats);
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                case 0:
                    System.out.println("Thank you for using plane Management");
                    System.exit(0);
                default:
                    System.out.println("Invalid Option. Please select a valid option.");
            }
        } while (option != 0);
        ;
    }

    //Creating Methods

    // Buying Seats
    public static void buy_seat(int[][]seats){
        Scanner scanner = new Scanner(System.in);

        //Prompt user for row and seat
        System.out.print("Please enter the row letter(A,B,C or D): ");
        char rowletter = scanner.next().toUpperCase().charAt(0);//Convert to Upper case

        //Selecting Seat number
        System.out.print("Please enter the seat number(1 to 14): ");
        int s_num = scanner.nextInt();

        //Validate row and seat
        int row = rowletter -'A';//Convert row letter to  array index
        if (row < 0||row>=seats.length|| s_num<1|| s_num> seats[row].length)
        {
            System.out.println("Invalid row or seat number.Please try again.");
            return;
        }
        Scanner  scanner1 = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner1.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner1.nextLine();
        System.out.print("Enter email: ");
        String email = scanner1.nextLine();

        //Create a new person object
        Person p1 = new Person(name,surname,email);

        //Create a new Ticket object with the seat information and price
        double price = calculatePrice(rowletter,s_num); // Define the price or Calculate it
        Ticket t1 = new Ticket(rowletter,s_num,price,p1);

        //Add the ticket to the array of tickets sold
        ticketsSold[ticketsCount++] = t1;

        //Call save method to write ticket info to file
        ticketsSold[ticketsCount - 1].save();

        //Checking seat availability
        if (seats[row][s_num-1]==1) {
            System.out.println("This seat already sold. Please select another seat");
        } else {
            //Mark as sold
            seats[row][s_num - 1] = 1;
            System.out.println("seat " + rowletter + s_num + " successfully reserved!");
        }

    }

    //Cancelling Seat
    public static void cancel_seat(int[][]seats) {
        Scanner scanner = new Scanner(System.in);

        //Prompt user for row and seat
        System.out.print("Enter the row letter (A,B,C or D) to cancel: ");
        char rowletter = scanner.next().toUpperCase().charAt(0);//Convert to Uppercase

        System.out.print("Enter the seat number (1 to 14) to cancel: ");
        int s_num = scanner.nextInt();

        //Validate row and seat
        int row = rowletter - 'A'; //Convert row letter to array
        if (row < 0 || row >= seats.length || s_num > seats[row].length) {
            System.out.println("Invalid row or seat number. Please try again.");
            return;
        }

        //Check seat already available
        if (seats[row][s_num-1]==0){
            System.out.println("This seat is already available. ");
        } else {

            //Mark the seat as available
            seats[row][s_num - 1] = 0;
            System.out.println("Seat " + rowletter + s_num + " reservation has been successfully canceld!");
        }
        for(int i=0; i < ticketsCount; i ++) {
            if (ticketsSold[i].getRow()== row && ticketsSold[i].getSeat()==s_num) {

                //Shift all subsequent tickets one position up in the array
                for (int j = i; j < ticketsCount - 1; j++) {
                    ticketsSold[j] = ticketsSold[j + 1];
                }
                ticketsCount --; //Decrease the count of sold tickets
                break;

            }
        }
    }

    //Find first available seat
    public static void  find_first_available(int[][]seats) {

        //Iterate over each row and seat
        for (int row = 0; row < seats.length; row++) {
            for (int seat = 0; seat < seats[row].length; seat++) {

                //Check seat availability
                if (seats[row][seat] == 0) {
                    char rowletter = (char) ('A' + row);//Convert array index to row letter
                    System.out.println("The first available seat is " + rowletter + (seat + 1));
                    return;


                }
            }
        }
        System.out.println("No available seats found.");
    }
    public static void  show_seating_plan(int[][]seats) {
        for (int row = 0; row < seats.length; row++) {

            //Add a space at the start of row B and C
            if (row == 1 || row == 2) {
                System.out.print("");
            }

            //Iterate over each seat in the row
            for (int seat = 0; seat < seats[row].length; seat++) {
                //Display 'O' for available and 'X' for sold
                System.out.print(seats[row][seat] == 0 ? "O" : "X");
            }
            System.out.println();//Move to the next line after each row
        }
    }

    //Print ticket info
    public static void print_tickets_info() {
        double totalprice = 0;
        System.out.println("Tickets sold during this session: ");
        for (int i =0; i < ticketsCount; i++) {
            ticketsSold[i].printTicketInformation();
            totalprice += ticketsSold[i].getPrice();
        }
        System.out.println("Total price of tickets sold: €" + totalprice);
    }

    // Search Tickets
    public static void search_ticket() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please Enter Row Letter: ");
        char row = scanner.next().toUpperCase().charAt(0);
        System.out.print("Enter seat number: ");
        int s_num = scanner.nextInt();

        boolean found = false;
        for (int i =0; i < ticketsCount; i++) {
            if (ticketsSold[i].getRow()==row && ticketsSold[i].getSeat()==s_num) {
                System.out.println("Ticket Information: ");
                ticketsSold[i].printTicketInformation();
                found = true;
                break;
            }
        }
        if (! found) {
            System.out.println("This seat is available.");
        }
    }

    //Calculate Price
    public static double calculatePrice(char row, int seatNumber) {
        double price = 0.0;
        if ((row == 'A' && seatNumber >= 1 && seatNumber <= 5) ||
                (row == 'B' && seatNumber >= 1 && seatNumber <= 5) ||
                (row == 'C' && seatNumber >= 1 && seatNumber <= 5) ||
                (row == 'D' && seatNumber >= 1 && seatNumber <= 5)) {1
            price = 200.0; // Price for seats A1 to A5, B1 to B5, C1 to C5, D1 to D5
        } else if ((row == 'A' && seatNumber >= 6 && seatNumber <= 9) ||
                (row == 'B' && seatNumber >= 6 && seatNumber <= 9) ||
                (row == 'C' && seatNumber >= 6 && seatNumber <= 9) ||
                (row == 'D' && seatNumber >= 6 && seatNumber <= 9)) {
            price = 150.0; // Price for seats A6 to A9, B6 to B9, C6 to C9, D6 to D9
        } else {
            price = 180.0; // Price for remaining seats (A10 to D14)
        }
        return price;
    }

    // Assuming a maximum number of tickets that can be sold
    private static final int Max_Tickets = 52;
    private static Ticket[] ticketsSold = new Ticket[Max_Tickets];
    private static int ticketsCount = 0; // To keep track of the number of tickets sold

}




