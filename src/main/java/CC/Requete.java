package CC;

import java.util.HashMap;

public class Requete {
    private HashMap<String, Object> champs = new HashMap<>();

    public void ajouterChamp(String nomChamp, Object contenu){
        champs.put(nomChamp, contenu);
    }

    public Object getChamp(String nomChamp){
        return champs.get(nomChamp);
    }
}
