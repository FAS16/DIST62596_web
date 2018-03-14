/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg;


import java.rmi.Remote;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.ws.rs.core.Response;

/**
 *
 * @author fahadali
 */
public class DRRest implements Remote {
    
    


    public static String hentOrdFraDR() throws JSONException, java.rmi.RemoteException {
        //ArrayList<String> ord = new ArrayList<String>();
        Client client = ClientBuilder.newClient();
        Response res = client.target("https://www.dr.dk/mu-online/api/1.4/list/view/mostviewed?limit=3").request(MediaType.APPLICATION_JSON).get();
        String svar = res.readEntity(String.class);

        
            JSONObject json = new JSONObject(svar);
            JSONArray array = json.getJSONArray("Items");
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = (JSONObject) array.get(i);
                String slug = o.getString("SeriesSlug");
                String titel = o.getString("SeriesTitle");
                System.out.println("Titel: " + titel);
                
                    String beskrivelse = new JSONObject(client.target("https://www.dr.dk/mu-online/api/1.4/programcard/" + slug)
                                    .request(MediaType.APPLICATION_JSON).get().readEntity(String.class))
                                    .getString("Description");

                    //String nyBeskr = description.trim().replaceAll("[^a-zæøå]", " ").replaceAll(" [a-zæøå] ", " ").replaceAll(" +", " ");
                    beskrivelse = beskrivelse.replaceAll("<.+?#>,:/", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
                    System.out.println("Beskrivelse = " + beskrivelse);
                    //ord.clear();
                    //ord.addAll(new HashSet<String>(Arrays.asList(beskrivelse.split(" "))));
                   // System.out.println("ord = " + ord);
                    if (beskrivelse != null) {
                        return beskrivelse;
                    }

                } 
            
        
        return null;
    }
}
