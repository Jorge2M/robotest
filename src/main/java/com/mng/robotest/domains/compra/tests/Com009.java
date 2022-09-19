package com.mng.robotest.domains.compra.tests;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.steps.shop.checkout.CheckoutSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class Com009 extends TestBase {
    private final CheckoutSteps checkoutSteps = new CheckoutSteps();
    private final CompraSteps compraSteps = new CompraSteps();

    public Com009() throws Exception {
        dataTest.userConnected = "e2e.es.test@mango.com";
        dataTest.passwordUser = "hsXPv7rUoYw3QnMKRhPT";
        dataTest.userRegistered = true;
        dataTest.pais = PaisGetter.get(PaisShop.ESPANA);
        dataTest.idioma = dataTest.pais.getListIdiomas().get(0);
    }

    @Override
    public void execute() throws Exception {
        accessLoginAndClearBolsa();
        altaArticulosBolsaAndClickComprar();
    }
    private void accessLoginAndClearBolsa() throws Exception {
        access();
        new SecBolsaSteps().clear();
    }
    private void altaArticulosBolsaAndClickComprar() throws Exception {
        new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
        new SecBolsaSteps().selectButtonComprar();
    }

}
