/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kartei;

import Daten.Eintrag;
import Daten.Patient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author root
 */
@Path("patienten")
public class PatientResource {

    @Context


    private UriInfo context;

    static LinkedList<Patient> patienten;

    static {
        patienten = new LinkedList();
        Patient p = new Patient(0, "Sepp", "Maier");
        p.addEintrag(new Eintrag(new Date(), "Erstbesuch"));
        patienten.add(p);

        p = new Patient(1, "Simon", "Montag");
        p.addEintrag(new Eintrag(new Date(), "Impfung"));
         patienten.add(p);
         p = new Patient(2, "Herbert", "Finkelstein");
        p.addEintrag(new Eintrag(new Date(), "Impfung"));
        patienten.add(p);
        
         p = new Patient(3, "Hansi", "Feuerstein");
        p.addEintrag(new Eintrag(new Date(), "Impfung"));
        patienten.add(p);
        
    }

    /**
     * Creates a new instance of PatientResource
     */
    public PatientResource() {
    }

    /**
     * Retrieves representation of an instance of kartei.PatientResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("patient/{userID}")
    public synchronized JsonObject getPatientData(@PathParam("userID") int userID) {
        JsonObjectBuilder e = Json.createObjectBuilder();

        if (userID >= 0 && userID < patienten.size()) {
            Patient p = (Patient) patienten.get(userID);
            e.add("id", p.getiD());
            e.add("vorname", p.getVorname());
            e.add("nachname", p.getNachname());
            JsonArrayBuilder a = Json.createArrayBuilder();
            for (Eintrag c : p.getListe()) {

                JsonObjectBuilder q = Json.createObjectBuilder();
                q.add("Datum", c.getDate().toString());
                q.add("Eintrag", c.getEintrag());
                a.add(q);
            }
            e.add("Daten", a);

            return e.build();
        } else {
            e.addNull("Patient");
            return e.build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("patientenIDs")
    public synchronized JsonObject getPatientListID() {

        JsonArrayBuilder a = Json.createArrayBuilder();
        for (Patient p : patienten) {
            JsonObjectBuilder e = Json.createObjectBuilder();
            e.add("id", p.getiD());
            a.add(e);

        }
        JsonObjectBuilder q = Json.createObjectBuilder();
        q.add("Liste der IDs", a);
        return q.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("newpatient/{vorname}/{nachname}")
    public synchronized JsonObject setPatient(@PathParam("vorname") String vorname,
            @PathParam("nachname") String nachname) {
        Patient p = new Patient(patienten.getLast().getiD() + 1, vorname, nachname);
        patienten.add(p);
        System.out.println("" + p);

        JsonObjectBuilder e = Json.createObjectBuilder();
        e.addNull("ok");
        return e.build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("newentry/{id}/{eintrag}")
    public synchronized JsonObject setEintrag(@PathParam("eintrag") String eintrag,
            @PathParam("id") int id) {

        JsonObjectBuilder e = Json.createObjectBuilder();

        try {
            Patient p = patienten.get(id);
            p.addEintrag(new Eintrag(new Date(), eintrag));
            e.addNull("ok");
            return e.build();
        } catch (IndexOutOfBoundsException f) {
            e.addNull("Patient nicht vorhanden");
            return e.build();
        }
    }

    /**
     * PUT method for updating or creating an instance of PatientResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
