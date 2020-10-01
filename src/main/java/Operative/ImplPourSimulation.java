package Operative;

import CC.ControleCommande;

import java.util.Timer;
import java.util.TimerTask;

public class ImplPourSimulation implements InterfaceMaterielle{
    private Timer temporisateur;
    private EtatMoteur etat;
    private ControleCommande controleComm;

    public enum EtatMoteur{
        enMontee,
        enDescente,
        estStoppe
    }

    public ImplPourSimulation(){
        etat = EtatMoteur.estStoppe;
    }
    public ImplPourSimulation(double tpsDistEtage , double tpsRalentissement){

    }

    @Override
    public void monter() {
        if(etat == EtatMoteur.enDescente)
            arreterUrgence();
        else
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
    public void arreterProchainNiveau() {
        controleComm.mettreAJourEtage();
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

    public void setControleCommande(ControleCommande cc){
        controleComm = cc;
    }


}
