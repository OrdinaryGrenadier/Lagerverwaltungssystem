package model;

import java.util.Date;
import java.util.LinkedList;

public class Lieferung {
	LinkedList<Buchung> zugehoerigeBuchungen = new LinkedList<Buchung>();
	Date datum;
	int gesamtEinheiten;
	
	public Lieferung(Date datum, int gesamtEinheiten){
		this.datum = datum;
		this.gesamtEinheiten = gesamtEinheiten;
	}
	
	public int getGesamtEinheiten(){
		return this.gesamtEinheiten;
	}
	
	public Date getDatum(){
		return this.datum;
	}
	
	public LinkedList<Buchung> getZugehoerigeBuchungen(){
		return this.zugehoerigeBuchungen;
	}
	
	public void hinzufuegenBuchung(Buchung buchung){
		this.zugehoerigeBuchungen.add(buchung);
	}
}