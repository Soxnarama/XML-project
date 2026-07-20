package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Sérialisation de la bibliothèque en XML.
 * Auteur : Dan Azoumi Ibrahim (Azoumi)
 */
public class Bibliotheque {

    private List<Livre> livres = new ArrayList<>();

    public List<Livre> getLivres() {
        return livres;
    }

    /**
     * Convertit les objets Java (livres) en fichier XML via DOM + Transformer.
     */
    public void save() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element racine = doc.createElement("bibliotheque");
        doc.appendChild(racine);

        for (Livre l : livres) {
            Element livre = doc.createElement("livre");

            Element titre = doc.createElement("titre");
            titre.setTextContent(l.getTitre());
            livre.appendChild(titre);

            Element auteur = doc.createElement("auteur");
            auteur.setTextContent(l.getAuteur());
            livre.appendChild(auteur);

            Element editeur = doc.createElement("editeur");
            editeur.setTextContent(l.getEditeur());
            livre.appendChild(editeur);

            racine.appendChild(livre);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(doc), new StreamResult(new File("bibliotheque.xml")));
    }

    // Test rapide : génère bibliotheque.xml puis vérifie qu'il est bien formé
    public static void main(String[] args) throws Exception {
        Livre livre1 = new Livre();
        livre1.setTitre("titre 1");
        livre1.setAuteur("auteur 1");
        livre1.setEditeur("editeur 1");

        Livre livre2 = new Livre();
        livre2.setTitre("titre 2");
        livre2.setAuteur("auteur 2");
        livre2.setEditeur("editeur 2");

        Bibliotheque biblio = new Bibliotheque();
        biblio.getLivres().add(livre1);
        biblio.getLivres().add(livre2);
        biblio.save();

        System.out.println("bibliotheque.xml généré.");

        // Vérification "bien formé" : le fichier doit se re-parser sans erreur
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.newDocumentBuilder().parse(new File("bibliotheque.xml"));
        System.out.println("XML bien formé : OK");
    }
}
