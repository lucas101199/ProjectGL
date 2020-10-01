package CC;
import Operative.ImplPourSimulation;
public class FakeControleCommande implements  ControleCommande{
    private ImplPourSimulation _ascenceur;
    private int numEtage;
    private Direction direction;

    private enum Direction{
        Montee,
        Descente,
        Arret
    }

    public FakeControleCommande(ImplPourSimulation ascenceur){
        _ascenceur = ascenceur;
        _ascenceur.setControleCommande(this);
        numEtage = 0;
        direction = Direction.Arret;
    }

    @Override
    public void traiterReq(Requete req) {
        var ordre =(String) req.getChamp("Ordre");
        switch(ordre){
            case "Monter":
                _ascenceur.monter();
                direction = Direction.Montee;
                break;
            case "Descendre":
                _ascenceur.descendre();
                direction = Direction.Descente;
                break;
            case "ProchainEtage":
                _ascenceur.arreterProchainNiveau();
                break;
            default:
                _ascenceur.arreterUrgence();
        }
    }

    @Override
    public void mettreAJourEtage() {
        if(direction == Direction.Montee)
            numEtage++;
        else if(direction == Direction.Descente)
            numEtage--;
    }

    @Override
    public int getEtage() {
        return numEtage;
    }

    @Override
    public Etat getEtat() {
        return null;
    }
}
