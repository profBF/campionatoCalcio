import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Test {
	public static void stampaMenu(String[] opts) {
		System.out.println("---MENU---");
		for (String opt : opts) {
			System.out.println(opt);
		}
		System.out.print("Scegli che cosa fare : ");
	}

	public static void stampaGiornate(List<Partita> l) {
		for (Partita p : l) {
			System.out.println(
					p.giornata + ", " + p.data + ", " + p.squadra_casa + ", " + p.punteggio + ", " + p.squadra_ospite);
		}
	}

	public static void main(String[] args) throws IOException {
		Campionato c1 = new Campionato("Premier League", "2019-2020");
		c1.carica_dati("league2020.csv");
		Input input = new Input();
		
		
		String[] opts = { "1- Cerca le partite per giornata.", "2- Cerca le partite di ogni squadra.",
				"3- Mostra la classifica.", "4- Esci" };

		//Scanner scanner = new Scanner(System.in);

		int opt = 1;
		while (opt != 4) {
			stampaMenu(opts);

			//opt = scanner.nextInt();
			opt = input.inputInt("");
			//input.inputInt("");
			//scanner.nextLine();
			switch (opt) {
			case 1:
				//System.out.println("Numero della giornata(1-38): ");
				//int nG = scanner.nextInt();
				int nG = input.inputInt("Numero della giornata(1-38): ");
				List<Partita> partiteG = c1.ricercaPerGiornata(nG);
				if (partiteG != null) {
					System.out.println("---PARTITE DELLA GIORNATA " + nG + "---");
					stampaGiornate(partiteG);
				} else
					System.out.println("Nessuna partita in questa giornata!");
				System.out.println();
				break;
			case 2:
				//System.out.println("Nome della squadra: ");
				//String nome = scanner.nextLine();
				String nome = input.inputString("Nome della squadra: ");
				List<Partita> partiteS = c1.ricercaPerSquadra(nome);
				if (partiteS != null) {
					System.out.println("---PARTITE DELLA SQ. " + nome + "---");
					stampaGiornate(partiteS);
				} else
					System.out.println("Nessuna squadra con questo nome!");
				break;
			case 3:
				c1.StampaClassifica();
				break;
			case 4:
				break;
			}

		}
		//scanner.close();

	}
}