/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.service;

import Backend.entities.TPersonalLog;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author fauzianordlund
 */
@Stateless
@Path("entities.tpersonallog")
public class TPersonalLogFacadeREST extends AbstractFacade<TPersonalLog> {

    @PersistenceContext(unitName = "12-17PU")
    private EntityManager em;

    public TPersonalLogFacadeREST() {
        super(TPersonalLog.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TPersonalLog entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, TPersonalLog entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TPersonalLog find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TPersonalLog> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TPersonalLog> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("/getPostsFromOneUser/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<TPersonalLog> getPostsFromOneUser(@PathParam("id") Long id){
        
        em = getEntityManager();
        Query q = em.createNamedQuery("TPersonalLog.findFromOneSender");
        q.setParameter("Sender_id", id);
        Collection<TPersonalLog> tmp = q.getResultList();
        return tmp;
    }
    
    @GET
    @Path("/getPostsFromOneUsername/{username}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<TPersonalLog> getPostsFromOneUsername(@PathParam("username") String username){
        
        em = getEntityManager();
        Query q = em.createNamedQuery("TPersonalLog.findFromOneSenderName");
        q.setParameter("Sender_Name", username);
        Collection<TPersonalLog> tmp = q.getResultList();
        return tmp;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}