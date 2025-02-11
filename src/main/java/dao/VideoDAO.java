package dao;

import java.util.List;

import entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utils.XJPA;

public class VideoDAO implements DAOInterface<Video, String> {
	EntityManager em = XJPA.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
	}

	@Override
	public List<Video> findAll() {
		String jpql = "SELECT o FROM Video o";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		return query.getResultList();
	}

	public List<Video> findAllActive() {
		String jpql = "SELECT o FROM Video o WHERE o.active = true";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		return query.getResultList();
	}

	public List<Video> random10Video(String excludeId) {
		String jpql = "SELECT v FROM Video v WHERE v.id != :excludeId AND active = true ORDER BY FUNCTION('RAND')";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("excludeId", excludeId);
		query.setMaxResults(10);
		return query.getResultList();
	}

	@Override
	public Video findById(String id) {
		return em.find(Video.class, id);
	}

	@Override
	public void create(Video t) {
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
		Video entity = em.find(Video.class, id);
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
	public void update(Video t) {
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public List<Video> findByTitleKeyword(String keyword) {
		String jpql = "SELECT v FROM Video v WHERE v.title LIKE :keyword";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("keyword", "%" + keyword + "%");
		return query.getResultList();
	}

	public List<Video> findVideosLikedByUser(String userId) {
		String jpql = "SELECT f.video FROM Favorite f WHERE f.user.id = :userId";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

}
