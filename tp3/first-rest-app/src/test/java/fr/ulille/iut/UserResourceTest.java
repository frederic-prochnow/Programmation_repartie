package fr.ulille.iut;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;

import org.glassfish.grizzly.http.server.HttpServer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

public class UserResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);

        // Utiliser un attribut statique pour stocker les utilisateurs dans la ressource UserResource
        // est une très mauvaise pratique. Cela pose problème pour les tests.
        // Du coup, je suis obligé de remettre à zéro cet attribut "à la main"...
        Field field = UserResource.class.getDeclaredField("users");
        field.setAccessible(true);
        field.set("null",new HashMap<>());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test de création d'un utilisateur (retour HTTP et envoi de l'URI de la nouvelle instance)
     */
    @Test
    public void testCreateUser() {
        User user = new User("jsteed", "Steed", "jsteed@mi5.uk");
        // Conversion de l'instance de User au format JSON pour l'envoi
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);

        // Envoi de la requête HTTP POST pour la création de l'utilisateur
        Response response = target.path("/users").request().post(userEntity);

        // Vérification du code de retour HTTP
        assertEquals(201, response.getStatus());

        // Vérification que la création renvoie bien l'URI de la nouvelle instance dans le header HTTP 'Location'
        // ici : http://localhost:8080/myapp/users/jsteed
        URI uriAttendue = target.path("/users").path(user.getLogin()).getUri();
        assertTrue(uriAttendue.equals(response.getLocation()));
    }
    
    /**
     * Test de création en double d'un utilisateur. Doit renvoyer 409
     */
    @Test
    public void testCreateSameUser() {
    	User user = new User("jsteed", "Steed", "jsteed@mi5.uk");
    	Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
    	
    	// Envoi de la requête HTTP POST pour la création de l'utilisateur
    	int first = target.path("/users").request().post(userEntity).getStatus();
    	
    	// Vérification du code de retour HTTP
    	assertEquals(201, first);
    	
    	int same = target.path("/users").request().post(userEntity).getStatus();
    	assertEquals(409, same);
    	
    	User user2 = new User("jsteed", "Steed", "jsteed@mi5.uk");
    	Entity<User> user2Entity = Entity.entity(user2, MediaType.APPLICATION_JSON);
    	
    	int same2 = target.path("/users").request().post(userEntity).getStatus();
    	assertEquals(409, same2);
    }
    
    /**
     * Vérifie qu'initialement on a une liste d'utilisateurs vide
     */
     @Test
     public void testGetEmptyListofUsers() {
             List<User> list = target.path("/users").request().get(new GenericType<List<User>>(){});
             assertTrue(list.isEmpty());
     }
     
     /**
      * Vérifie que je renvoie bien une liste contenant tous les utilisateurs (ici 2)
      */
     @Test
     public void testGetTwoUsers() {
       User user1 = new User("jsteed", "Steed", "jsteed@mi5.uk");
             Entity<User> userEntity = Entity.entity(user1, MediaType.APPLICATION_JSON);
             target.path("/users").request().post(userEntity);

             User user2 = new User("epeel", "Peel", "epeel@mi5.uk");
             userEntity = Entity.entity(user2, MediaType.APPLICATION_JSON);

       target.path("/users").request().post(userEntity);

             List<User> list = target.path("/users").request().get(new GenericType<List<User>>(){});
             assertEquals(2, list.size());
     }
     
     /**
      * Vérifie la récupération d'un utilisateur spécifique
      */
     @Test
     public void testGetOneUser() {
       User user = new User("jsteed", "Steed", "jsteed@mi5.uk");
       Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
       target.path("/users").request().post(userEntity);

       User result = target.path("/users").path("jsteed").request().get(User.class);
       assertEquals(user, result);
     }

     /**
      * Vérifie que la récupération d'un utilisateur inexistant renvoie 404
      */
     @Test
     public void testGetInexistantUser() {
       int notFound = target.path("/users").path("tking").request().get().getStatus();
       assertEquals(404, notFound);
     }
     
     /**
     *
     * Vérifie que la suppression d'une ressource est effective
     */
    @Test
    public void testDeleteOneUser() {
      User user = new User("jsteed", "Steed", "jsteed@mi5.uk");
      Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
      target.path("/users").request().post(userEntity);

      int code = target.path("/users").path("jsteed").request().delete().getStatus();
      assertEquals(204, code);

      int notFound = target.path("/users").path("jsteed").request().get().getStatus();
      assertEquals(404, notFound);  
    }

    /**
     *
     * Vérifie que la suppression d'un utilisateur inexistant renvoie 404
     */
    @Test
    public void testDeleteInexistantUser() {
      int notFound = target.path("/users").path("tking").request().delete().getStatus();
      assertEquals(404, notFound);
    }
    
    /**
    *
    * Vérifie que la modification d'un utilisateur inexistant renvoie 404
    */
   @Test
   public void testModifyInexistantUser() {
     User inexistant = new User("jsteed", "Steed", "jsteed@mi5.uk");
     Entity<User> userEntity = Entity.entity(inexistant, MediaType.APPLICATION_JSON);
     int notFound = target.path("/users").path("jsteed").request().put(userEntity).getStatus();
     assertEquals(404, notFound);
   }

  /**
    *
    * Vérifie que la modification d'une ressource est effective
    */
   @Test
   public void testModifyUser() {
     User user = new User("epeel", "Peel", "epeel@mi5.uk");
     Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
     target.path("/users").request().post(userEntity);

     User modified = new User("epeel", "Peel", "epeel@cia.usa");
     userEntity = Entity.entity(modified, MediaType.APPLICATION_JSON);

     int noContent = target.path("/users").path("epeel").request().put(userEntity).getStatus();
     assertEquals(204, noContent);

     User retrieved = target.path("/users").path("epeel").request().get(User.class);
     assertEquals(modified, retrieved);
   }
    
}

