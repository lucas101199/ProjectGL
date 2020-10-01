package CC;

import java.util.HashMap;


enum Etat{

}

public interface ControleCommande {
    void traiterReq(Requete req);
    void mettreAJourEtage();
    int getEtage();
    Etat getEtat();
}
