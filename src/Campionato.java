import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Campionato {
	private String serie;
	private String stagione;
	private BufferedReader br;

	private Map<Integer, List<Partita>> partite_giornate;

	private Map<String, List<Partita>> partite_squadre;

	private List<Squadra> classifica;

	public Campionato() {
	}

	public Campionato(String serie, String stagione) {
		this.serie = serie;
		this.stagione = stagione;
	}

	private void caricaPartiteSq(String nome, String[] data) {
		List<Partita> list2 = new ArrayList<>();

		if (partite_squadre.containsKey(nome)) {
			list2 = partite_squadre.get(nome);
			list2.add(new Partita(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]));
		} else {
			list2 = new ArrayList<Partita>();
			list2.add(new Partita(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]));
			partite_squadre.put(nome, list2);
		}
	}

	public void carica_dati(String filename) {
		this.partite_giornate = new HashMap<>();
		this.partite_squadre = new TreeMap<>();
		this.classifica = new ArrayList<>();
		List<Partita> list = new ArrayList<>();
		String data[] = new String[5];
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String header = br.readLine();

			while ((line = br.readLine()) != null) {

				data = line.split(",");

				if (partite_giornate.containsKey(Integer.parseInt(data[0]))) {
					list = partite_giornate.get(Integer.parseInt(data[0]));
					list.add(new Partita(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]));
				} else {
					list = new ArrayList<Partita>();
					list.add(new Partita(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]));
					partite_giornate.put(Integer.parseInt(data[0]), list);
				}

				caricaPartiteSq(data[2], data);
				caricaPartiteSq(data[4], data);
			}

		} catch (IOException e) {
			System.out.println("File non trovato");
		}
		for (String key : partite_squadre.keySet()) {

			List<Partita> lista = partite_squadre.get(key);

			Squadra sq = new Squadra(key);

			for (Partita p : lista) {

				String pt[] = new String[2];

				pt = p.punteggio.split("-");

				if (key.equals(p.squadra_ospite)) {

					if (Integer.parseInt(pt[0]) < Integer.parseInt(pt[1])) {
						sq.setVittorie(sq.getVittorie() + 1);
					} else if (Integer.parseInt(pt[0]) > Integer.parseInt(pt[1])) {
						sq.setSconfitte(sq.getSconfitte() + 1);
					} else {
						sq.setPareggi(sq.getPareggi() + 1);
					}
				} else if (key.equals(p.squadra_casa)) {

					if (Integer.parseInt(pt[0]) > Integer.parseInt(pt[1])) {
						sq.setVittorie(sq.getVittorie() + 1);
					} else if (Integer.parseInt(pt[0]) < Integer.parseInt(pt[1])) {
						sq.setSconfitte(sq.getSconfitte() + 1);
					} else {
						sq.setPareggi(sq.getPareggi() + 1);
					}
				}
			}
			sq.setPunti(sq.getVittorie() * 3 + sq.getPareggi());
			classifica.add(sq);
			aggiornaClassifica();
		}

	}

	private void aggiornaClassifica() {
		Collections.sort(classifica);
	}

	public void StampaClassifica() {
		Formatter fmt = new Formatter();
		fmt.format("%26s %5s %5s %5s %5s\n", "Squadra", "Punti", "Vittorie", "Pareggi", "Sconfitte");
		for (Squadra sq : this.classifica) {
			fmt.format("%26s %4d %6d %7d %8d\n", sq.getNome(), sq.getPunti(), sq.getVittorie(), sq.getPareggi(),
					sq.getSconfitte());
		}
		System.out.println(fmt);
	}

	public List<Partita> ricercaPerGiornata(int nGiornata) {
		for (int key : partite_giornate.keySet()) {
			if (key == nGiornata)
				return partite_giornate.get(key);
		}
		return null;

	}

	public List<Partita> ricercaPerSquadra(String nomeSq) {
		for (String key : this.partite_squadre.keySet()) {
			if (key.equalsIgnoreCase(nomeSq))
				return this.partite_squadre.get(key);
		}
		return null;
	}
}
