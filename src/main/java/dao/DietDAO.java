package dao;

import entidades.Diet;
import entidades.Exercises;
import org.hibernate.Session;
import util.HibernateUtil;
import org.hibernate.query.Query;
import javax.persistence.PersistenceException;
import java.util.List;


public class DietDAO implements DietDAOInterface{

    @Override
    public Diet searchById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Diet d = session.find(Diet.class, id);
        session.close();

        return d;
    }

    @Override
    public Diet createDiet(Diet diet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(diet);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return diet;
    }

    @Override
    public List<Diet> showAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Diet> all = session.createQuery("from Diet", Diet.class).list();
        session.close();

        return all;
    }

    @Override
    public List<Diet> showAll(int page, int size) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Diet", Diet.class);
        query.setMaxResults(size);
        query.setFirstResult((page-1)*size);
        List<Diet> diets = query.list();
        session.close();
        return diets;
    }

    @Override
    public Long totalDiets(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Long> query = session.createQuery("select count(d) from Diet d", Long.class);
        Long counter = query.getSingleResult();
        session.close();
        return counter;
    }

    @Override
    public Diet updateDietByID(Diet diet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(diet);
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }
        session.close();
        return diet;
    }

    @Override
    public boolean deleteDietByID(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Diet diet = this.searchById(id);
            if (diet!=null){
                session.delete(diet);
            }else {
                return false;
            }
            session.getTransaction().commit();
        }catch (PersistenceException pe){
            pe.printStackTrace();
            session.beginTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
}

