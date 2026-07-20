package model;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

public class TestBibliotheque {
    public static void main(String[] args) {
        try {
            // 1. Création des livres de test (données du sujet)
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

            // 2. Génération du fichier XML
            biblio.save();
            System.out.println("XML généré avec succès : bibliotheque.xml");

            // 3. Validation du XML produit contre le XSD
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("bibliotheque.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File("bibliotheque.xml")));

            System.out.println("VALIDATION REUSSIE : le XML respecte le XSD !");

        } catch (Exception e) {
            System.out.println("ERREUR : " + e.getMessage());
            e.printStackTrace();
        }
    }
}