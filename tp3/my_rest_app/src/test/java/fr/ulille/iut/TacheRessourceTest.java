package fr.ulille.iut;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TacheRessourceTest {

	private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);

        Field field = TacheRessource.class.getDeclaredField("taches");
        field.setAccessible(true);
        field.set("null",new HashMap<>());
    }

    @SuppressWarnings("deprecation")
	@After
    public void tearDown() throws Exception {
        server.stop();
    }
    
    @Test
    public void testCreateUser() {
        Tache tache = new Tache("Tache 1","Finir ce tp");
        // Conversion de l'instance de User au format JSON pour l'envoi
        Entity<Tache> tacheEntity = Entity.entity(tache, MediaType.APPLICATION_JSON);

        // Envoi de la requête HTTP POST pour la création de l'utilisateur
        Response response = target.path("/taches").request().post(tacheEntity);

        // Vérification du code de retour HTTP
        assertEquals(201, response.getStatus());

        // Vérification que la création renvoie bien l'URI de la nouvelle instance dans le header HTTP 'Location'
        // ici : http://localhost:8080/myapp/users/jsteed
        URI uriAttendue = target.path("/taches").path(tache.getNom()).getUri();
        assertTrue(uriAttendue.equals(response.getLocation()));
    }

}
