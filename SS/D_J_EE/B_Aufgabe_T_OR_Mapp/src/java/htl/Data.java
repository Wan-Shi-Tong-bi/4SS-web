/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author hinte
 */
@ManagedBean(name = "data")
@RequestScoped
public class Data {

    List<Car> listCars;

    @PersistenceContext
    EntityManager em;
    
    @PersistenceUnit
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    public Data() {
    }

    public List<Car> getListCars() {
        EntityManager em = emf.createEntityManager();
//        try {
            TypedQuery<Car> carQuery = em.createQuery("SELECT c from Car c", Car.class);
            listCars = carQuery.getResultList();
            return listCars;
//        } catch (Exception ex) {
//
//        }
//        return null;
    }

    public void insertData(String bezeichnung, int baujahr, String color) {
        try {
            int x = utx.getStatus();
            utx.begin();
            em.joinTransaction();
            em.persist(new Car(bezeichnung, baujahr, color));
            utx.commit();
        } catch (NotSupportedException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setListCars(List<Car> listCars) {
        this.listCars = listCars;
    }

}
