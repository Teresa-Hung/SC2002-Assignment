package sc2002Proj;
import java.util.Scanner;

public class mainApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Hotel reservation system!");
		int choice1;
		do {
			System.out.println("1) Guest Information.");
			System.out.println("2) Reservation Information.");
			System.out.println("3) Room Information.");
			System.out.println("4) Room Service Information.");
			System.out.println("5) Payment Information.");
			System.out.println("6) Quit");
			choice1 = sc.nextInt();
			switch(choice1) {
				case 1:
					GuestManager manager = new GuestManager();
					System.out.println("1) Update Guest Details.");
					System.out.println("2) Search Guest.");
					int a = sc.nextInt();
					switch(a) {
						case 1:
							Guest g = manager.updateDetails();
							if(g != null)
								manager.displayGuestDetails(g);
							break;
						case 2:
							Guest guest1 = manager.searchGuest();
							if(guest1 != null) {
								System.out.println("Guest found.");
								manager.displayGuestDetails(guest1);
							}
							break;
					}
					
				case 2:
					//reservation functions
					System.out.println("1) Create Reservation.");
					System.out.println("2) Update Reservation.");
					System.out.println("3) Remove Reservation.");
					System.out.println("4) Print Reservation Status.");
					int b = sc.nextInt();
					switch(b) {
					
					
					
					}

					
				case 3:
					//room functions
					
				case 4:
					//room service functions
				
					
				case 5:
					
					

			}
		}while(choice1 != 6);

	}

}
