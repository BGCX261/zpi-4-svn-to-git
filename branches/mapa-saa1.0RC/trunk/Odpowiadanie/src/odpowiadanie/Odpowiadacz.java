/**
 * 
 */
package odpowiadanie;

/**
 * Prosta klasa generująca odpowiedź po wywołaniu.
 * Dla wywołania z arugmentem 0 - wypisuje 0 na konsole.
 * Dla wywołania z argumentem 1 - wypisuje 1 na konsole z 5 sekundowym opoznieniem
 * Dla wywolania z argumentem 2 - konczy prace ze statusem 2
 * Dla wywolania z argumentem 3 - konczy prace ze statusem 3 z 5 sekundowym opoznieniem
 * Dla wywolania bez argumentu rzucany jest runtime exception
 * 
 * @author elistan
 *
 */
public class Odpowiadacz {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		if(args.length < 1){
			throw new RuntimeException("Brak argumentow");
		}
		switch(args[0]){
		case "0":
			System.out.println(0);
			break;
		case "1":
			Thread.sleep(1000);
			System.out.println(1);
			break;
		case "2":
			System.exit(2);
		case "3":
			Thread.sleep(1000);
			System.exit(3);
		default:
			throw new RuntimeException("Zle argumenty");
		}
	}

}
