package Operative;

import java.util.Timer;
import java.util.TimerTask;

public class ImplPourSimulation implements InterfaceMaterielle{
    private Timer t;
    private  boolean enMontee, enDescente, estStoppe;
    private EtatMoteur etat;
    public enum EtatMoteur{
        enMontee,
        enDescente,
        estStoppe
    }

    @Override
    public void monter() {
        etat = EtatMoteur.enMontee;
    }

    @Override
    public void descendre() {
        if(etat == EtatMoteur.enMontee)
            arreterUrgence();
        else
            etat = EtatMoteur.enDescente;

    }

    @Override
    public void arreterUrgence() {
        etat = EtatMoteur.estStoppe;
    }

    @Override
    public void arretetProchainNiveau() {
        etat = EtatMoteur.estStoppe;
    }

    @Override
    public void envoyerSignalPretAPartir() {

    }

    @Override
    public void envoyerSignalEtagePasse() {

    }

    //Pour tester le bon fonctionnement du matériel
    public EtatMoteur getEtatMoteur(){
        return  etat;
    }


}
