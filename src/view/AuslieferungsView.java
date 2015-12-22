package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionListener;

import controller.Controller;
import model.Buchung;
import model.Lager;

import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class AuslieferungsView extends JFrame {
	private Controller controller;
	
	
	
	public AuslieferungsView(Controller controller, int gesamtMenge ) 
	{
		this.controller = controller;
		getContentPane().setLayout(null);
		
		JLabel auslieferungUeberschrift = new JLabel("Auslieferung");
		auslieferungUeberschrift.setFont(new Font("Tahoma", Font.PLAIN, 15));
		auslieferungUeberschrift.setBounds(10, 11, 102, 20);
		getContentPane().add(auslieferungUeberschrift);
		
		Vector<Lager> auswaehlbareLager = new Vector<Lager>();
		auswaehlbareLager = controller.findeAuswaehlbarLagerAuslieferung(auswaehlbareLager);

		JComboBox<Lager> lagerAuswahl = new JComboBox<Lager>(auswaehlbareLager);
		lagerAuswahl.setBounds(10, 77, 130, 20);
		getContentPane().add(lagerAuswahl);
		
		JLabel lagerUeberschrift = new JLabel("Lager");
		lagerUeberschrift.setBounds(10, 47, 46, 20);
		getContentPane().add(lagerUeberschrift);
		
		JLabel auszulieferndeUeberschrift = new JLabel("auszuliefernde Einheiten");
		auszulieferndeUeberschrift.setBounds(160, 47, 150, 20);
		getContentPane().add(auszulieferndeUeberschrift);
		
		JTextField anzahlAuszulieferndeEinheiten = new JTextField("0");
		anzahlAuszulieferndeEinheiten.setBounds(160, 77, 50, 20);
		getContentPane().add(anzahlAuszulieferndeEinheiten);
		
		JLabel verfgbareEinheitenUeberschrift = new JLabel("Verf�gare Einheiten");
		verfgbareEinheitenUeberschrift.setBounds(330, 47, 120, 20);
		getContentPane().add(verfgbareEinheitenUeberschrift);
		
		JLabel anzahlverfgbareEinheiten = new JLabel();
		anzahlverfgbareEinheiten.setText(controller.findePassendesLager(lagerAuswahl.getSelectedItem().toString(), controller.getLagerModel().getRoot()).getBestand()+ "");
		anzahlverfgbareEinheiten.setBounds(330, 77, 50, 20);
		getContentPane().add(anzahlverfgbareEinheiten);
		
		JLabel auszulieferndeEinheitenUeberschrift = new JLabel("Lieferumfang");
		auszulieferndeEinheitenUeberschrift.setBounds(470, 47, 145, 20);
		getContentPane().add(auszulieferndeEinheitenUeberschrift);
		
		JLabel anzahlauszulieferndeEinheiten = new JLabel(gesamtMenge + "");
		anzahlauszulieferndeEinheiten.setBounds(470, 77, 86, 20);
		getContentPane().add(anzahlauszulieferndeEinheiten);
		
		JList<Buchung> buchungen = new JList<Buchung>();
		DefaultListModel<Buchung> lieferungsBuchungen = new DefaultListModel<Buchung>();
		buchungen.setModel(lieferungsBuchungen);
		JScrollPane buchungenScrollBar = new JScrollPane(buchungen);
		buchungenScrollBar.setBounds(23, 124, 648, 198);
		getContentPane().add(buchungenScrollBar);
		
		JButton neuesLagerButton = new JButton("Neues Lager");
		neuesLagerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (anzahlauszulieferndeEinheiten.getText().length() != 0) {
					try{
					int einheit = Integer.parseInt(anzahlauszulieferndeEinheiten.getText());
					Lager ausgewaehlt = controller.findePassendesLager(lagerAuswahl.getSelectedItem().toString(), controller.getLagerModel().getRoot());
					if (einheit > ausgewaehlt.getBestand()) 
					{
						JOptionPane.showMessageDialog(null,
								"So viele Einheiten sind in dem ausgew�hlten Lager nicht vorhanden. Bitte w�hlen sie eine andere Einheitenzahl.");
					} else if (einheit <= 0) 
					{	
							JOptionPane.showMessageDialog(null, "Bitte verwenden Sie nur positive Zahlen und nicht 0.");
					} 
					else if(einheit <= (gesamtMenge - controller.getVerteilteEinheiten()))
					{
								Buchung neueBuchung = controller.erstelleBuchung(einheit, lagerAuswahl.getSelectedItem().toString());
								lagerAuswahl.removeItem(ausgewaehlt);
								if(!neueBuchung.equals(null))
								{
									lieferungsBuchungen.addElement(neueBuchung);
									buchungen.setModel(lieferungsBuchungen);
							
								}

					}
					else
					{
						JOptionPane.showMessageDialog(null, "Sie wollen mehr Einheiten verteilen als noch zu verteilen sind. Sie m�ssen noch " + (gesamtMenge - controller.getVerteilteEinheiten()) + " Einheiten verteilen.");
					}

					}catch(NumberFormatException f){
						JOptionPane.showMessageDialog(null, "Bitte nur Zahlen verwenden.");
					}	
				} else {
					JOptionPane.showMessageDialog(null, "Es ist eine Angabe von ganzzahligen Einheiten f�r die Verteilung notwendig");
				}

				
			}
		});
		neuesLagerButton.setBounds(20, 359, 140, 23);
		getContentPane().add(neuesLagerButton);
		
		JButton bestaetigenButton = new JButton("Best�tigen");
		bestaetigenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(controller.getVerteilteEinheiten() == gesamtMenge)
				{
					controller.erstelleLieferung(gesamtMenge);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Es m�ssen zuerst alle Einheiten verteilt sein bevor das Best�tigen m�glich ist.");
				}
			}
		});
		bestaetigenButton.setBounds(180, 359, 140, 23);
		getContentPane().add(bestaetigenButton);
		
		JButton abbrechenButton = new JButton("Abbrechen");
		abbrechenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loescheUndoListe();
				dispose();
			}
		});
		abbrechenButton.setBounds(340, 359, 140, 23);
		getContentPane().add(abbrechenButton);
		
		setBounds(400, 200, 695, 450);
		setVisible(true);
		setResizable(false);
	}
}
