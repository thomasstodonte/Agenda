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
 * Classe qui gère l'insertion, la modification et la suppression de contacts.
 * Elle gère également l'enregistrement des propriétés "contacts" dans un fichier externe "phone.properties"
 * @author Thomas Charmes
 *
 */
public class CarnetAdresses extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Propriété pour stocker les informations
	static Properties contacts = new Properties();
	
	// Création d'une HashMap pour les contacts
	static HashMap<String,String> listeDExemplesEnDur = new HashMap<String,String>();
	
	/**
	 * méthode utilisée pour insérer un contact à une liste puis ajouter cette liste à la property
	 * insertion possible si le nom n'existe pas déjà dans les contacts,
	 * sinon on modifie les propriétés du nom existant
	 * @param nom
	 * @param numero, doit être de longueur 10 exactement
	 * @return true si l'insertion a bien eu lieu
	 * @throws ImportContactException
	 * @throws ModificationContactException
	 * @see modifierContact(String nom, String numero)
	 */
	public static boolean insererContact(String nom, String numero) throws ImportContactException, ModificationContactException {
		
		if (numero.trim().length() == 10) {	
			if (!contacts.containsKey(nom)) {
				// Création du contact à partir des données nom et numero
				HashMap<String, String> contact = new HashMap<String, String>();
				contact.put(nom, numero);
				// Insertion du contact dans la liste en dur
				listeDExemplesEnDur.putAll(contact);
				
				// Ajout de cette liste au répertoire
				contacts.putAll(listeDExemplesEnDur);
			}
			else modifierContact(nom, numero);
			return true;
		}
		else {
			throw new ImportContactException("Erreur lors de l'importation des contacts : le numéro de téléphone ne contient pas exactement 10 chiffres");
		}
	}
	
	/**
	 * méthode qui modifie le numero de téléphone du nom passé en paramètre.
	 * Le nouveau numero est également passé en paramètre.
	 * @param nom, doit exister dans le répertoire avant la modification
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
				throw new ModificationContactException("Le numéro de téléphone saisi ne comporte pas exactement 10 chiffres");
			}
			else {
				contacts.setProperty(nom, numero);
				return true;
			}
		}
	}
	
	/**
	 * méthode qui supprime un contact du répertoire
	 * @param nom, doit exister dans le répertoire avant la suppression
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
	 * méthode qui enregistre les contacts dans un fichier externe "phone.properties"
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
	 * méthode principale pour exécuter le programme
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
		
		System.out.println("Après supprression du contact Valentine : ");
		for (Object info : contacts.entrySet()) {
			System.out.println(info);
		}
		
		modifierContact("Thomas","0677889934");

		System.out.println("Après modification du numéro de Thomas :");
		for (Object info : contacts.entrySet()) {
			System.out.println(info);
		}
		
		enregistrer();	
	}
}
