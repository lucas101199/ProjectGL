package Operative;
import CC.ControleCommande;
public interface InterfaceMaterielle {
    void monter();
    void descendre();
    void arreterUrgence();
    void arreterProchainNiveau();
    void envoyerSignalPretAPartir();
    void envoyerSignalEtagePasse();
    void setControleCommande(ControleCommande cc);
}
