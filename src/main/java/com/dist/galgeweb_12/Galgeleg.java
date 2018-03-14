/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dist.galgeweb_12;

import brugerautorisation.transport.rmi.Brugeradmin;
import com.github.mustachejava.DefaultMustacheFactory;
import java.io.IOException;
import java.io.StringWriter;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import galgeleg.GalgeInterface;
import spillerbase.SpillerbaseI;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author fahadali
 */
@Path("Galgeleg")
public class Galgeleg {

    private static final String URL = "rmi://130.225.170.248:1099/SpillerbaseServer";
    SpillerbaseI spillerbaseI;
    GalgeInterface galgeInterface;
    String brugernavn, adgangskode;
    ArrayList<String> spillere;

    public Galgeleg() throws RemoteException, NotBoundException, MalformedURLException {
        registrering(brugernavn);

    }

    public void registrering(String brugernavn) throws RemoteException, NotBoundException, MalformedURLException { 

        spillerbaseI = (SpillerbaseI) Naming.lookup(URL);
        spillere = spillerbaseI.hentAlleSpillere();

        if (!spillere.contains(brugernavn)) {
            spillerbaseI.registrerSpiller(brugernavn);
        } else {

            System.out.println("Velkommen tilbage!");

        }

        this.galgeInterface = spillerbaseI.findSpil(brugernavn);
        galgeInterface.setBrugerAdmin((Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin")); ///RMI kald bliver gjort i logInd, redundant!! Gør noget ved det makker
        galgeInterface.setBrugernavn(brugernavn);
        galgeInterface.setAdgangskode(adgangskode);

    }

    public String startSpil(GalgeInterface galgeInterface, String bogstav) throws Exception {

        String value = "";

        while (true) {

            if (galgeInterface.erSpilletTabt()) {
                value = "Du tabte! Ordet var: " + galgeInterface.getOrdet();
                galgeInterface.nulstil();
                break;
            } else if (galgeInterface.erSpilletVundet()) {
                value = "Hvor er du god!, du vandt!";
                break;

            } else {
                galgeInterface.gætBogstav(bogstav);
                break;
            }

        }

        return value;
    }

    @GET
    public String initSpil() throws IOException, NotBoundException {

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("game.mustache");
        HashMap<String, Object> mustacheData = new HashMap<String, Object>();
        mustacheData.put("synligtOrd", galgeInterface.getSynligtOrd());
        mustacheData.put("brugteBogstaver", galgeInterface.getBrugteBogstaver());
        mustacheData.put("antalForkerteBogstaver", galgeInterface.getAntalForkerteBogstaver());

        if (!galgeInterface.erSpilletTabt() && !galgeInterface.erSpilletVundet()) {

            mustacheData.put("antalForkerteBogstaver", galgeInterface.getAntalForkerteBogstaver());
        }

        StringWriter writer = new StringWriter();
        m.execute(writer, mustacheData).flush();

        return writer.toString();
    }

    @POST
    public String gætBogstav(@FormParam("guess") String bogstav) throws Exception {
        if (bogstav != null) {
            startSpil(galgeInterface, bogstav);
        }
        return initSpil();
    }

}
