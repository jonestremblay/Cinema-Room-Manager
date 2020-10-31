import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Scanner;

public class CinemaRoom {
    final int STANDARD_SEAT_PRICE = 10;
    final int FRONT_SEAT_PRICE = STANDARD_SEAT_PRICE;
    final int REAR_SEAT_PRICE = 8;
    private int rows;
    private int seatsPerRow;
    String[][] cinemaRoom;
    private int nbrSeats;
    private int nbrPurchasedTickets;
    private double purchasedTicketsPercentage = 0;
    private int currentIncome = 0;
    private int possibleTotalIncome = 0;

    public CinemaRoom(){

    }

    public CinemaRoom(int nbrRows, int nbrSeatsPerRow){
        rows = nbrRows;
        seatsPerRow = nbrSeatsPerRow;
        nbrSeats = nbrRows * nbrSeatsPerRow;
        nbrPurchasedTickets = 0;
        possibleTotalIncome = calculatePotentialProfitsPerRoom();
    }

    // Getter and setter
    public void setRows(int nbrRows){
        rows = nbrRows;
    }

    public void setSeatsPerRow(int nbrSeatsPerRow){
        seatsPerRow = nbrSeatsPerRow;
    }

    public int getRows(){
        return rows;
    }

    public int getSeatsPerRow(){
        return seatsPerRow;
    }

    public void setNbrPurchasedTickets(int nbrTickets){
        nbrPurchasedTickets = nbrTickets;
    }

    public void setPurchasedTicketsPercentage(){
        purchasedTicketsPercentage = Double.parseDouble(calculatePercentagePurchasedTickets(getNbrPurchasedTickets()));
    }

    public void setCurrentIncome(int income){
        currentIncome = income;
    }

    public void setPossibleTotalIncome(int totalIncome){
        possibleTotalIncome = totalIncome;
    }

    public int getNbrPurchasedTickets(){
        return nbrPurchasedTickets;
    }

    public double getPurchasedTicketsPercentage(){
        return purchasedTicketsPercentage;
    }

    public int getCurrentIncome(){
        return currentIncome;
    }

    public int getPossibleTotalIncome(){
        return possibleTotalIncome;
    }
    // End of getter and setter

    public void fillRoom() {
        cinemaRoom = new String[rows + 1][seatsPerRow + 1];

        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < seatsPerRow + 1; j++) {
                cinemaRoom[i][j] = "S";
            }
        }
        // Change first row for columns number
        for (int seat = 0; seat < seatsPerRow + 1; seat++){
            cinemaRoom[0][seat] = String.valueOf(seat);
        }
        // Change first column for rows number
        for (int row = 0; row < rows + 1; row++){
            cinemaRoom[row][0] = String.valueOf(row);
        }
        // Change first cell to a void one (aesthetic)
        cinemaRoom[0][0] = " ";
    }

    public void printRoom(){
        System.out.println("\nCinema:");
        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < seatsPerRow + 1; j++) {
                System.out.print(cinemaRoom[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }


    public int calculatePotentialProfitsPerRoom(){
        int totalProfits = 0;
        int nbrSeats = rows * seatsPerRow;
        // calculateNumberOfSeatsPerPrice-----------------------------------------------
        double halfRows = rows / 2.0;
        int frontRowCount;
        int rearRowCount;
        switch (rows % 2){
            case 1:
                frontRowCount = (int)Math.floor(halfRows);
                rearRowCount = (int)Math.round(halfRows);
                break;
            default: // Must be 0
                frontRowCount = (int)halfRows;
                rearRowCount = (int)halfRows;
                break;
        }
        // calculateProfits--------------------------------------------------------
        if (nbrSeats <= 0){
            totalProfits = 0;
        } else if (nbrSeats <= 60) {
            totalProfits = nbrSeats * STANDARD_SEAT_PRICE;
        } else { // More than 60 people.
            totalProfits = ((frontRowCount * seatsPerRow) * FRONT_SEAT_PRICE)
                            + ((rearRowCount * seatsPerRow) * REAR_SEAT_PRICE);
        }
        return  totalProfits;
    }

    public void changeSeatStatusToBusy(int rowNumber, int seatNumber){
        final String BUSY = "B";
        String seat = cinemaRoom[rowNumber][seatNumber];

        if (seat.equals(BUSY)){
            System.out.println("This seat is taken.");
        } else {
            cinemaRoom[rowNumber][seatNumber] = BUSY;
        }
    }

    public void changeSeatStatusToEmpty(int rowNumber, int seatNumber){
        final String BUSY = "B";
        final String EMPTY = "S"; // this is Jetbrains =)
        String seat = cinemaRoom[rowNumber][seatNumber];

        if (seat.equals(EMPTY)){
            System.out.println("This seat is already empty.");
        } else if (seat.equals(BUSY)){
            cinemaRoom[rowNumber][seatNumber] = EMPTY;
        }
    }

    public boolean checkSeatAvailability(int rowNumber, int seatNumber){
        final String BUSY = "B";
        final String EMPTY = "S";
        String seat = cinemaRoom[rowNumber][seatNumber];
        boolean seatAvailable = false;
        if (seat.equals(BUSY)){
            seatAvailable = false;
        } else {
            seatAvailable = true;
        }
        return seatAvailable;
    }

    public int findTicketPriceBySeat(int rowNumber){
        int nbrSeats = rows * seatsPerRow;
        int firstHalf = (int)Math.floor((double)rows / 2);
        int secondHalf = (int)Math.round((double)rows / 2);
        int price = 0;
        if (nbrSeats <= 0) {
            price = 0;
        } else if (nbrSeats <= 60){
            price = STANDARD_SEAT_PRICE;
        } else {
            if (rowNumber <= firstHalf){
                price = STANDARD_SEAT_PRICE;
            } else if (rowNumber >= secondHalf){
                price = REAR_SEAT_PRICE;
            }
        }
        return price;
    }

    public void purchaseTickets(int price){
        nbrPurchasedTickets = nbrPurchasedTickets + 1;
        currentIncome = currentIncome + price;
        setPurchasedTicketsPercentage();
    }

    public String calculatePercentagePurchasedTickets(int purchasedTickets){
        NumberFormat formatter = new DecimalFormat("#0.00");
        String result = "0";
        if (purchasedTickets <= 0 || nbrSeats <= 0) {
            result = formatter.format(0);
        } else {
            result = formatter.format((purchasedTickets / nbrSeats) * 100L);
        }

        return result;
    }

    public void printStats(){
        NumberFormat formatter = new DecimalFormat("#0.00");
        String percentage = formatter.format(getPurchasedTicketsPercentage());
        String menu  = "Number of purchased tickets: " + getNbrPurchasedTickets() + "\n"
                + "Percentage: " + percentage + "%\n"
                + "Current income: $" + getCurrentIncome() + "\n"
                + "Total income: $" + calculatePotentialProfitsPerRoom() + "\n";
        System.out.println(menu);
    }

    @Override
    public String toString(){
        return "Rows : " + rows + "\nSeats per rows : " + seatsPerRow + "\nTotal seats : " + (rows * seatsPerRow);
    }
}
//    public void printFrontRange_and_BackRange(){
//        // This methods does not work as intended.
//        int firstHalf = (int)Math.floor((double)rows / 2);
//        int secondHalf = (int)Math.round((double)rows / 2);
//        int[] frontRange = new int[firstHalf - 1];
//        int count1 = 0;
//        Arrays.fill(frontRange, count1 + 1);
//        int[] backRange = new int[rows - secondHalf];
//        int count2 = secondHalf - 1;
//        Arrays.fill(backRange, count2 + 1);
//        System.out.println(Arrays.toString(frontRange));
//        System.out.println(Arrays.toString(backRange));
//    }

