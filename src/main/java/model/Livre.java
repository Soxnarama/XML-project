package model;

/**
 * Représente un livre de la bibliothèque (correspond à l'élément XSD "livre").
 */
public class Livre {

    private String titre;
    private String auteur;
    private String editeur;

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getEditeur() { return editeur; }
    public void setEditeur(String editeur) { this.editeur = editeur; }
}
