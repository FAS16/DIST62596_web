package galgeleg;

import brugerautorisation.transport.rmi.Brugeradmin;
import java.io.IOException;
import java.rmi.*;
import java.util.Scanner;
import javax.jws.WebMethod;

//Op til server
public interface GalgeInterface extends Remote {

    String getBrugernavn() throws java.rmi.RemoteException;

    void setBrugernavn(String brugernavn) throws java.rmi.RemoteException;

    String getAdgangskode() throws java.rmi.RemoteException;

    void setAdgangskode(String adgangskode) throws java.rmi.RemoteException;

    Brugeradmin getBrugerAdmin() throws java.rmi.RemoteException;

    void setBrugerAdmin(Brugeradmin brugerAdmin) throws java.rmi.RemoteException;

    boolean isSessionHentet() throws java.rmi.RemoteException;

    void setSessionHentet(boolean sessionHentet) throws java.rmi.RemoteException;

    String getBrugteBogstaver() throws java.rmi.RemoteException;

    String getSynligtOrd() throws java.rmi.RemoteException;

    String getOrdet() throws java.rmi.RemoteException;

    int getAntalForkerteBogstaver() throws java.rmi.RemoteException;

    boolean erSidsteBogstavKorrekt() throws java.rmi.RemoteException;

    boolean erSpilletVundet() throws java.rmi.RemoteException;

    boolean erSpilletTabt() throws java.rmi.RemoteException;

    boolean erSpilletSlut() throws java.rmi.RemoteException;

    void setOrdet(String ordet) throws java.rmi.RemoteException;

    void setBrugteBogstaver(String brugteBogstaver) throws java.rmi.RemoteException;

    void setSynligtOrd(String synligtOrd) throws java.rmi.RemoteException;

    void setAntalForkerteBogstaver(int antalForkerteBogstaver) throws java.rmi.RemoteException;

    void setSidsteBogstavVarKorrekt(boolean sidsteBogstavVarKorrekt) throws java.rmi.RemoteException;

    void setSpilletErVundet(boolean spilletErVundet) throws java.rmi.RemoteException;

    void setSpilletErTabt(boolean spilletErTabt) throws java.rmi.RemoteException;

    void nulstil() throws java.rmi.RemoteException;

    void opdaterSynligtOrd() throws java.rmi.RemoteException;

    void gætBogstav(String bogstav) throws java.rmi.RemoteException;

    void logStatus() throws java.rmi.RemoteException;

    String hentUrl(String url) throws java.rmi.RemoteException;

    void logInd(String brugernavn, String adgangskode) throws java.rmi.RemoteException;

    String visFigur() throws java.rmi.RemoteException;

    String outputTilKlient() throws java.rmi.RemoteException;

    void hentSidsteSession() throws java.rmi.RemoteException;

}
