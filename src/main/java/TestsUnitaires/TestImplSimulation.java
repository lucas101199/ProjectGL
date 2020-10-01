package TestsUnitaires;
import CC.FakeControleCommande;
import CC.Requete;
import org.junit.*;
import Operative.ImplPourSimulation;
public class TestImplSimulation {

    @Test
    public void testMonter(){
        var ascenceur = new ImplPourSimulation();
        ascenceur.monter();
        Assert.assertEquals(ImplPourSimulation.EtatMoteur.enMontee, ascenceur.getEtatMoteur());
        ascenceur.descendre();
        Assert.assertEquals(ImplPourSimulation.EtatMoteur.estStoppe, ascenceur.getEtatMoteur());

    }

    @Test
    public void testDescendre(){
        var ascenceur = new ImplPourSimulation();
        ascenceur.descendre();
        Assert.assertEquals(ImplPourSimulation.EtatMoteur.enDescente, ascenceur.getEtatMoteur());
        ascenceur.monter();
        Assert.assertEquals(ImplPourSimulation.EtatMoteur.estStoppe, ascenceur.getEtatMoteur());
    }

    @Test
    public void testArretProchainEtage(){
        var ascenceur = new ImplPourSimulation();
        var fakeControlCommand = new FakeControleCommande(ascenceur);
        var requetePrchEtage = new Requete();
        requetePrchEtage.ajouterChamp("Ordre", "ProchainEtage");
        fakeControlCommand.traiterReq(requetePrchEtage);
        Assert.assertEquals(0,fakeControlCommand.getEtage());

        var requeteMonter = new Requete();
        requeteMonter.ajouterChamp("Ordre", "Monter");
        fakeControlCommand.traiterReq(requeteMonter);
        fakeControlCommand.traiterReq(requetePrchEtage);
        Assert.assertEquals(1, fakeControlCommand.getEtage());

    }
}
