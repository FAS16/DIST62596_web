/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dist.galgeweb_12;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import javax.ws.rs.Path;
import galgeleg.GalgeInterface;
import spillerbase.SpillerbaseI;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 *
 * @author fahadali
 */
@Path("logind")
public class LogInd {

    
    SpillerbaseI spillerbaseI;
    GalgeInterface galgeInterface;

    public LogInd() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String logInd(@FormParam("brugernavn") String brugernavn, @FormParam("adgangskode") String adgangskode) throws IOException, NotBoundException {

        if (brugerValidering(brugernavn, adgangskode)) {

            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache m = mf.compile("u.mustache");
            HashMap<String, Object> mustacheData = new HashMap<String, Object>();
            mustacheData.put("brugernavn", brugernavn);
            StringWriter writer = new StringWriter();
            m.execute(writer, mustacheData).flush();
            return writer.toString();

        } else {

            MustacheFactory mf = new DefaultMustacheFactory();
            Mustache m = mf.compile("x.mustache");
            HashMap<String, Object> mustacheData = new HashMap<String, Object>();
            mustacheData.put("brugernavn", brugernavn);
            StringWriter writer = new StringWriter();
            m.execute(writer, mustacheData).flush();
            return writer.toString();

        }

    }

    
    public boolean brugerValidering(String brugernavn, String adgangskode) {

        try {
            System.out.println(brugernavn + " logger ind.");
            Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
            Bruger b = ba.hentBruger(brugernavn, adgangskode);

            galgeInterface = spillerbaseI.findSpil(brugernavn);
            galgeInterface.setBrugerAdmin(ba);
            galgeInterface.setBrugernavn(brugernavn);
            galgeInterface.setAdgangskode(adgangskode);

            if (!galgeInterface.getBrugteBogstaver().equals("")) {
                System.out.println("[Aktiv session fundet - henter og sender til klienten])"); // ikke- færdiggjort spil
                galgeInterface.hentSidsteSession();
            } else if (galgeInterface.getBrugteBogstaver().equals("")) {
                System.out.println("[Ingen session at hente, ny session startes]"); //færdiggjort/nyt spil/første spil
                galgeInterface.setSessionHentet(false);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
