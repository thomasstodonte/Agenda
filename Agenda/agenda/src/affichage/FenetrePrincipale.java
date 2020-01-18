package affichage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetrePrincipale extends JFrame implements ActionListener,
		WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JButton btEnregistrer;
	private JButton btAjouter;
	private JButton btSupprimer;
	private JButton btQuitter;
	
	public FenetrePrincipale() {
		
		setTitle("Liste des contacts");
		setBounds(500, 500, 320, 250);
		JPanel panAffichage = new JPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btEnregistrer = new JButton("Enregistrer");
		btAjouter = new JButton("Ajouter un contact");
		btSupprimer = new JButton("Supprimer un contact");
		panAffichage.add(btEnregistrer);
		panAffichage.add(btAjouter);
		panAffichage.add(btSupprimer);
		contentPane.add(panAffichage);

		btEnregistrer.addActionListener(this);
		btAjouter.addActionListener(this);
		btSupprimer.addActionListener(this);
		
		addWindowListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btSupprimer)
			new FenetreSuppression();
		if (e.getSource() == btAjouter)
			new FenetreAjout();
		if (e.getSource() == btQuitter){
			new FenetreQuitter();
			System.out.println("Au revoir");
			System.exit(0);
		}	
	}

	public void windowClosing(WindowEvent arg0) {
		System.out.println("Au revoir");
		System.exit(0);
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

	
	
	public static void main(String[] args) {
		new FenetrePrincipale();
	}

}