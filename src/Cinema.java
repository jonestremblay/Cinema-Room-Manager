import java.util.Scanner;
public class Cinema {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CinemaRoom room = initRoom(scan);
        room.fillRoom();

        boolean exit = false;
        int choiceMenu;
        while (!exit){
            printMenu();
            choiceMenu = scan.nextInt();
            switch (choiceMenu){
                case 0:
                    exit = true;
                    break;
                case 1:
                    room.printRoom();
                    continue;
                case 2:
                    int[] coordinates = choose_and_return_seat_coordinates(scan, room);
                    int seatRowNumber = coordinates[0];
                    int seatNumber = coordinates[1];
                    while (!room.checkSeatAvailability(seatRowNumber, seatNumber)){
                        System.out.println("That ticket has already been purchased");
                        coordinates = choose_and_return_seat_coordinates(scan, room);
                        seatRowNumber = coordinates[0];
                        seatNumber = coordinates[1];
                    }
                    int price = room.findTicketPriceBySeat(seatRowNumber);
                    System.out.println("Ticket price: $" + price);
                    room.changeSeatStatusToBusy(seatRowNumber, seatNumber);
                    room.purchaseTickets(price);
                    continue;
                case 3:
                    if (room != null){
                        room.setPurchasedTicketsPercentage();
                        room.printStats();
                    } else {
                        System.out.println("The room is empty.");
                    }
                    break;
                default:
                    System.out.println("Your choice isn't in the menu");
                    break;
            }
        }
    }

    public static void printMenu(){
        String menu  = "1. Show the seats\n"
                + "2. Buy a ticket\n"
                + "3. Statistics\n"
                + "0. Exit\n";
        System.out.println(menu);
    }

    public static CinemaRoom initRoom(Scanner scan){
        System.out.println("Enter the number of rows:");
        int rows = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scan.nextInt();
        scan.nextLine();
        CinemaRoom room = new CinemaRoom(rows, seatsPerRow);
        return room;
    }

    public static int[] choose_and_return_seat_coordinates(Scanner scan, CinemaRoom room){
        int[] coordinates = new int[2];
        int clientSeatRowNumber = -1;
        int clientSeatNumber = -1;
        boolean valide = false;
        while(!valide){
            System.out.println("Enter a row number:");
            clientSeatRowNumber = scan.nextInt();
            System.out.println("Enter a seat number in that row:");
            clientSeatNumber = scan.nextInt();
            if (clientSeatRowNumber <= 0 || clientSeatRowNumber > room.getRows()){
                System.out.println("Wrong input!");
            } else if (clientSeatNumber <= 0 || clientSeatNumber > room.getSeatsPerRow()){
                System.out.println("Wrong input!");
            } else {
                valide = true;
            }
        }
        coordinates[0] = clientSeatRowNumber;
        coordinates[1] = clientSeatNumber;
        return coordinates;
    }


}