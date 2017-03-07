package fr.ulille.iut;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("taches")
public class TacheRessource {

	private static Map<String, Tache> taches = new HashMap<>();
	
	@Context
    public UriInfo uriInfo;
	
	public TacheRessource() {
	}

	@POST
    public Response createTache(Tache tache) {
        // Si l'utilisateur existe déjà, renvoyer 409
        if ( taches.containsKey(tache.getNom()) ) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        else {
            taches.put(tache.getNom(), tache);

            // On renvoie 201 et l'instance de la ressource dans le Header HTTP 'Location'
            URI instanceURI = uriInfo.getAbsolutePathBuilder().path(tache.getNom()).build();
            return Response.created(instanceURI).build();
        }
    }
	
	@GET
    public List<Tache> getTache() {
      return new ArrayList<Tache>(taches.values());
    }
}
