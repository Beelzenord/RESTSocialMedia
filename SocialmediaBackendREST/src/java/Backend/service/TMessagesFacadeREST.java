/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.service;

import Backend.entities.TMessages;
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
@Path("entities.tmessages")
public class TMessagesFacadeREST extends AbstractFacade<TMessages> {

    @PersistenceContext(unitName = "12-17PU")
    private EntityManager em;

    public TMessagesFacadeREST() {
        super(TMessages.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TMessages entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, TMessages entity) {
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
    public TMessages find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON})
    public List<TMessages> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TMessages> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("getMessagesFromAll/{receiver_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<TMessages> getMessagesFromAll(@PathParam("receiver_id") Long receiver_id) {
        em = getEntityManager();
        Query q = em.createNamedQuery("TMessages.findFromAll");
        q.setParameter("Receiver_id", receiver_id);
        Collection<TMessages> tmp = q.getResultList();
        return tmp;
    }
    
    @GET
    @Path("getMessagesFromOneSender/{receiver_id}/{sender_id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<TMessages> getMessagesFromOneSender(@PathParam("receiver_id") Long receiver_id, @PathParam("sender_id") Long sender_id) {
        em = getEntityManager();
        Query q = em.createNamedQuery("TMessages.findFromOneSender");
        q.setParameter("Receiver_id", receiver_id);
        q.setParameter("Sender_id", sender_id);
        Collection<TMessages> tmp = q.getResultList();
        return tmp;
    }
    
    @POST
    @Path("setMessageToIsRead")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void setMessageToIsRead(TMessages entity) {
        em = getEntityManager();
//        try {
            //em.getTransaction().begin();
            //TMessages m = em.find(TMessages.class, id);
            entity.setIsRead(true);
            em.merge(entity);
            //em.getTransaction().commit();
//        } finally {
//            if (em != null)
//                em.close();
//        }
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}