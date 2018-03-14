package galgeleg;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Galgelogik {

    ArrayList<String> klientOutput = new ArrayList<String>();
    ArrayList<String> muligeOrd = new ArrayList<String>();
    private String ordet;
    private String brugteBogstaver;
    private String synligtOrd;
    private int antalForkerteBogstaver;
    private boolean sidsteBogstavVarKorrekt;
    private boolean spilletErVundet;
    private boolean spilletErTabt;

    private boolean sessionHentet;
    private Brugeradmin brugerAdmin;
    private String brugernavn;
    private String adgangskode;
    private Bruger bruger;

    public Galgelogik() {

        try {
            String data = DRRest.hentOrdFraDR();
            System.out.println("data = " + data);
            data = data.replaceAll("<.+?#>,:/", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
            System.out.println("data = " + data);
            muligeOrd.clear();
            muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));
            System.out.println("muligeOrd = " + muligeOrd);

        } catch (Exception ex) {
            Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            muligeOrd.add("bil");
            System.out.println("muligeOrd = " + muligeOrd);

        }

        nulstil();
    }

    public void nulstil() {

        setBrugteBogstaver("");
        setAntalForkerteBogstaver(0);
        setSpilletErVundet(false);
        setSpilletErTabt(false);
        setOrdet(muligeOrd.get(new Random().nextInt(muligeOrd.size())));
        klientOutput.clear();
        setSynligtOrd("");
        setSidsteBogstavVarKorrekt(false);
        setSpilletErVundet(false);
        setSpilletErTabt(false);
        opdaterSynligtOrd();
    }

    public Brugeradmin getBrugerAdmin() {
        return brugerAdmin;
    }

    public void setBrugerAdmin(Brugeradmin brugerAdmin) {
        this.brugerAdmin = brugerAdmin;
    }

    public String getBrugernavn() {
        return brugernavn;
    }

    public void setBrugernavn(String brugernavn) {
        this.brugernavn = brugernavn;
    }

    public String getAdgangskode() {
        return adgangskode;
    }

    public void setAdgangskode(String adgangskode) {
        this.adgangskode = adgangskode;
    }

    public boolean isSessionHentet() {
        return sessionHentet;
    }

    public void setSessionHentet(boolean sessionHentet) {
        this.sessionHentet = sessionHentet;
    }

    public String getBrugteBogstaver() {
        return brugteBogstaver;
    }

    public String getSynligtOrd() {
        return synligtOrd;
    }

    public String getOrdet() {
        return ordet;
    }

    public int getAntalForkerteBogstaver() {
        return antalForkerteBogstaver;
    }

    public boolean erSidsteBogstavKorrekt() {
        return sidsteBogstavVarKorrekt;
    }

    public boolean erSpilletVundet() {
        return spilletErVundet;
    }

    public boolean erSpilletTabt() {
        return spilletErTabt;
    }

    public boolean erSpilletSlut() {
        return spilletErTabt || spilletErVundet;
    }

    public void setOrdet(String ordet) {
        this.ordet = ordet;
        if (brugerAdmin != null) {
            try {
                brugerAdmin.setEkstraFelt(brugernavn, adgangskode, "ordet", ordet);
            } catch (RemoteException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void setBrugteBogstaver(String brugteBogstaver) {
        this.brugteBogstaver = brugteBogstaver;
        if (brugerAdmin != null) {

            try {
                brugerAdmin.setEkstraFelt(brugernavn, adgangskode, "brugteBogstaver", brugteBogstaver);
            } catch (RemoteException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void setSynligtOrd(String synligtOrd) {
        this.synligtOrd = synligtOrd;
        if (brugerAdmin != null) {

            try {
                brugerAdmin.setEkstraFelt(brugernavn, adgangskode, "synligtOrd", synligtOrd);
            } catch (RemoteException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void setAntalForkerteBogstaver(int antalForkerteBogstaver) {
        this.antalForkerteBogstaver = antalForkerteBogstaver;
        if (brugerAdmin != null) {
            try {
                brugerAdmin.setEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver", antalForkerteBogstaver);
            } catch (RemoteException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void setSidsteBogstavVarKorrekt(boolean sidsteBogstavVarKorrekt) {
        this.sidsteBogstavVarKorrekt = sidsteBogstavVarKorrekt;
        if (brugerAdmin != null) {
            try {
                brugerAdmin.setEkstraFelt(brugernavn, adgangskode, "sidsteBogstavVarKorrekt", sidsteBogstavVarKorrekt);
            } catch (RemoteException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void setSpilletErVundet(boolean spilletErVundet) {
        this.spilletErVundet = spilletErVundet;

        if (brugerAdmin != null) {
            try {
                brugerAdmin.setEkstraFelt(brugernavn, adgangskode, "spilletErVundet", this.spilletErVundet);

            } catch (RemoteException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void setSpilletErTabt(boolean spilletErTabt) {
        this.spilletErTabt = spilletErTabt;

        if (brugerAdmin != null) {

            try {
                brugerAdmin.setEkstraFelt(brugernavn, adgangskode, "spilletErTabt", spilletErTabt);

            } catch (RemoteException ex) {
                Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void opdaterSynligtOrd() {
        setSynligtOrd("");
        setSpilletErVundet(true);
        for (int n = 0; n < ordet.length(); n++) {
            String bogstav = ordet.substring(n, n + 1);
            if (brugteBogstaver.contains(bogstav)) {
                setSynligtOrd(synligtOrd + bogstav);
            } else {
                setSynligtOrd(synligtOrd + "*");
                setSpilletErVundet(false);
            }
        }
    }

    public void gætBogstav(String bogstav) {
        klientOutput.clear();

        if (bogstav.length() != 1) {
            return;
        }
        System.out.println("Der gættes på bogstavet: " + bogstav);
        klientOutput.add("Der gættes på bogstavet: " + bogstav + "\n"); // klient output
        if (brugteBogstaver.contains(bogstav)) {
            return;
        }
        if (spilletErVundet || spilletErTabt) {
            return;
        }

        setBrugteBogstaver(brugteBogstaver + bogstav);

        if (ordet.contains(bogstav)) {
            setSidsteBogstavVarKorrekt(true);
            System.out.println("Bogstavet var korrekt: " + bogstav);
            klientOutput.add("Bogstavet var korrekt\n"); // klient output
        } else {
            // Vi gættede på et bogstav der ikke var i ordet.
            setSidsteBogstavVarKorrekt(false);
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
            klientOutput.add("Bogstavet var IKKE korrekt\n"); // klient output
            setAntalForkerteBogstaver(antalForkerteBogstaver + 1);
            if (antalForkerteBogstaver >= 6) {
                setSpilletErTabt(true);
            }
        }
        opdaterSynligtOrd();
    }

    public void logStatus() {
        klientOutput.clear();

        System.out.println("---------- ");
        if (erSpilletSlut()) {
            System.out.println("- ordet (skjult) = " + ordet);
        }
        System.out.println("- ordet (skjult) = " + ordet);
        System.out.println("- synligtOrd = " + synligtOrd);
        System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
        System.out.println("- brugteBogstaver = " + brugteBogstaver);
        if (spilletErTabt) {
            System.out.println("- SPILLET ER TABT");
        }
        if (spilletErVundet) {
            System.out.println("- SPILLET ER VUNDET");
        }
        System.out.println("---------- ");

        // Klient output
        klientOutput.add("---------- \n"); // klient output
        //klientOutput.add("- ordet (skjult) = " + ordet + "\n"); // klient output
        klientOutput.add("- synligtOrd = " + synligtOrd + "\n"); // klient output
        klientOutput.add("- forkerteBogstaver = " + antalForkerteBogstaver + "\n"); // klient output
        klientOutput.add("- brugteBogstaver = " + brugteBogstaver + "\n"); // klient output
        if (spilletErTabt) {
            klientOutput.add("- SPILLET ER TABT\n"); // klient output
        }
        if (spilletErVundet) {
            klientOutput.add("- SPILLET ER VUNDET\n"); // klient output
        }
        klientOutput.add("--------------- \n");
    }

   
    public String visFigur() {

        String image = "";
        switch (antalForkerteBogstaver) {
            case 0:

                image = " _____\n"
                        + " |/  |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";

                break;

            case 1:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 2:

                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " |   O\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";

                break;

            case 3:
                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 4:

                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O--\n"
                        + " |\n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 5:

                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O--\n"
                        + " |    \\ \n"
                        + " |\n"
                        + " |\n"
                        + "========\n";
                break;

            case 6:

                image = " _____\n"
                        + " |/  |\n"
                        + " |   0\n"
                        + " | --O--\n"
                        + " |  / \\ \n"
                        + " |\n"
                        + " |\n"
                        + "========\n";

                break;

        }
        return image;
    }

    public String outputTilKlient() {
        return klientOutput.toString();
    }

    public void hentSidsteSession() {

        try {
            this.ordet = (String) brugerAdmin.getEkstraFelt(brugernavn, adgangskode, "ordet");
            this.brugteBogstaver = (String) brugerAdmin.getEkstraFelt(brugernavn, adgangskode, "brugteBogstaver");
            this.synligtOrd = (String) brugerAdmin.getEkstraFelt(brugernavn, adgangskode, "synligtOrd");
            this.antalForkerteBogstaver = (int) brugerAdmin.getEkstraFelt(brugernavn, adgangskode, "antalForkerteBogstaver");
            this.sidsteBogstavVarKorrekt = (boolean) brugerAdmin.getEkstraFelt(brugernavn, adgangskode, "sidsteBogstavVarKorrekt");
            this.spilletErVundet = (boolean) brugerAdmin.getEkstraFelt(brugernavn, adgangskode, "spilletErVundet");
            this.spilletErTabt = (boolean) brugerAdmin.getEkstraFelt(brugernavn, adgangskode, "spilletErTabt");
            System.out.println("\nSidste session er hentet:");
            sessionHentet = true;
            outputTilKlient();
        } catch (RemoteException ex) {
            Logger.getLogger(Galgelogik.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
