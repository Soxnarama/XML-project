package generated;

import java.util.ArrayList;
import java.util.List;

public class Bibliotheque {

    private List<Livre> livre = new ArrayList<>();


    public List<Livre> getLivre(){
        return livre;
    }

    public void setLivre(List<Livre> livre){
        this.livre = livre;
    }
}