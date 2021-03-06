package communitymanagement.dao;

import communitymanagement.model.Resident;
import communitymanagement.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ResidentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addResident(Resident resident) {

        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(resident);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Resident getResidentByUserName(String userName) {
        User user = null;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(builder.equal(root.get("userName"), userName));
            user = session.createQuery(criteriaQuery).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            return user.getResident();
        }

        return null;
    }

    public void deleteResident(int residentId) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Resident resident = session.get(Resident.class, residentId);
            session.delete(resident);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateResident(Resident resident) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(resident);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
