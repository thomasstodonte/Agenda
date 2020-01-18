package repertoire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.JFrame;
import exceptions.ImportContactException;
import exceptions.ModificationContactException;
import exceptions.SuppressionContactException;

/**
 * Classe qui g�re l'insertion, la modification et la suppression de contacts.
 * Elle g�re �galement l'enregistrement des propri�t�s "contacts" dans un fichier externe "phone.properties"
 * @author Thomas Charmes
 *
 */
public class CarnetAdresses extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propri�t� pour stocker les informations
	static Properties contacts = new Properties();
	
	// Cr�ation d'une HashMap pour les contacts
	static HashMap<String,String> listeDExemplesEnDur = new HashMap<String,String>();
	
	/**
	 * m�thode utilis�e pour ins�rer un contact � une liste puis ajouter cette liste � la property
	 * insertion possible si le nom n'existe pas d�j� dans les contacts,
	 * sinon on modifie les propri�t�s du nom existant
	 * @param nom
	 * @param numero, doit �tre de longueur 10 exactement
	 * @return true si l'insertion a bien eu lieu
	 * @throws ImportContactException
	 * @throws ModificationContactException
	 * @see modifierContact(String nom, String numero)
	 */
	public static boolean insererContact(String nom, String numero) throws ImportContactException, ModificationContactException {
		
		if (numero.trim().length() == 10) {	
			if (!contacts.containsKey(nom)) {
				// Cr�ation du contact � partir des donn�es nom et numero
				HashMap<String, String> contact = new HashMap<String, String>();
				contact.put(nom, numero);
				// Insertion du contact dans la liste en dur
				listeDExemplesEnDur.putAll(contact);
				
				// Ajout de cette liste au r�pertoire
				contacts.putAll(listeDExemplesEnDur);
			}
			else modifierContact(nom, numero);
			return true;
		}
		else {
			throw new ImportContactException("Erreur lors de l'importation des contacts : le num�ro de t�l�phone ne contient pas exactement 10 chiffres");
		}
	}
	
	/**
	 * m�thode qui modifie le numero de t�l�phone du nom pass� en param�tre.
	 * Le nouveau numero est �galement pass� en param�tre.
	 * @param nom, doit exister dans le r�pertoire avant la modification
	 * @param numero, doit avoir une longueur d'exactement 10 chiffres
	 * @return true si la modification a bien eu lieu
	 * @throws ModificationContactException
	 */
	public static boolean modifierContact(String nom, String numero) throws ModificationContactException {
		
		if (!contacts.containsKey(nom)) {
			throw new ModificationContactException("le nom saisi ne figure pas dans la liste des contacts");
		}
		else {
			if (numero.trim().length() != 10) {
				throw new ModificationContactException("Le num�ro de t�l�phone saisi ne comporte pas exactement 10 chiffres");
			}
			else {
				contacts.setProperty(nom, numero);
				return true;
			}
		}
	}
	
	/**
	 * m�thode qui supprime un contact du r�pertoire
	 * @param nom, doit exister dans le r�pertoire avant la suppression
	 * @return true si la suppression a bien eu lieu
	 * @throws SuppressionContactException
	 */
	public static boolean supprimerContact(String nom) throws SuppressionContactException {
		if (!contacts.containsKey(nom)) {
			throw new SuppressionContactException("le nom saisi ne figure pas dans la liste des contacts");
		}
		else contacts.remove(nom);
		return true;
	}
	
	/**
	 * m�thode qui enregistre les contacts dans un fichier externe "phone.properties"
	 */
	public static void enregistrer() {
				
		String emplacementDuFichierDeSauvegarde = "phone.properties";
		try (OutputStream out = new FileOutputStream(emplacementDuFichierDeSauvegarde)){
			contacts.store(out, "fichier d'enregistrement");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * m�thode principale pour ex�cuter le programme
	 * @throws ImportContactException
	 * @throws ModificationContactException
	 * @throws SuppressionContactException
	 */
	public static void main(String[] args) throws ImportContactException, ModificationContactException, SuppressionContactException {
		
		insererContact("Thomas","0667207113");
		insererContact("Valentine","0788113611");
		insererContact("Maman","0673502934");
		insererContact("Papa","0663090977");
		
		// Affichage des contacts
		for (Object info : contacts.entrySet()) {
			System.out.println(info);
		}
		
		supprimerContact("Valentine");
		
		System.out.println("Apr�s supprression du contact Valentine : ");
		for (Object info : contacts.entrySet()) {
			System.out.println(info);
		}
		
		modifierContact("Thomas","0677889934");

		System.out.println("Apr�s modification du num�ro de Thomas :");
		for (Object info : contacts.entrySet()) {
			System.out.println(info);
		}
		
		enregistrer();	
	}
}
