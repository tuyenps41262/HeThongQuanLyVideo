package dao;

import java.util.List;

import entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utils.XJPA;

public class UsersDAO implements DAOInterface<Users, String> {
	EntityManager em = XJPA.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
	}

	@Override
	public List<Users> findAll() {
		String jpql = "SELECT o FROM Users o";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		return query.getResultList();
	}

	@Override
	public Users findById(String id) {
		return em.find(Users.class, id);
	}

	@Override
	public void create(Users t) {
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	@Override
	public boolean deleteById(String id) {
		Users entity = em.find(Users.class, id);
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		}
	}

	@Override
	public void update(Users t) {
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public Users findByIdOrEmail(String idOrEmail) {
		String jpql = "SELECT u FROM Users u WHERE u.id = :id OR u.email = :email";
		TypedQuery<Users> query = em.createQuery(jpql, Users.class);
		query.setParameter("id", idOrEmail);
		query.setParameter("email", idOrEmail);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
