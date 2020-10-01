package TestsUnitaires;
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
    }
}
