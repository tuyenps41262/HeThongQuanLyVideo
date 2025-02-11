package dao;

import java.util.List;

import dto.ReportFavoriteUser;
import dto.ReportFavorites;
import entity.Favorite;
import entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utils.XJPA;

public class FavoriteDAO implements DAOInterface<Favorite, Integer> {
	EntityManager em = XJPA.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
	}

	@Override
	public List<Favorite> findAll() {
		String jpql = "SELECT o FROM Favorite o";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		return query.getResultList();
	}

	@Override
	public Favorite findById(Integer id) {
		return em.find(Favorite.class, id);
	}

	@Override
	public void create(Favorite t) {
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	@Override
	public boolean deleteById(Integer id) {
		Favorite entity = em.find(Favorite.class, id);
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
	public void update(Favorite t) {
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public List<Video> findTop10MostFavoritedVideos() {
		String jpql = "SELECT f.video FROM Favorite f GROUP BY f.video ORDER BY COUNT(f) DESC";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		query.setMaxResults(10);
		return query.getResultList();
	}

	public List<Video> findVideosWithNoFavorites() {
		String jpql = "SELECT v FROM Video v LEFT JOIN Favorite f ON v.id = f.video.id WHERE f.id IS NULL";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		return query.getResultList();
	}

	public Favorite findByUserIdAndVideoId(String userId, String videoId) {
		String jpql = "SELECT f FROM Favorite f WHERE f.user.id = :userId AND f.video.id = :videoId";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("userId", userId);
		query.setParameter("videoId", videoId);
		List<Favorite> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}

	public List<Favorite> findFavoritesByUserId(String userId) {
		String jpql = "SELECT f FROM Favorite f WHERE f.user.id = :userId";
		TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}

	public List<ReportFavorites> generateFavoriteReport() {
		String jpql = "SELECT new dto.ReportFavorites(f.video.title, COUNT(f), MAX(f.shareDate), MIN(f.shareDate)) FROM Favorite f GROUP BY f.video.title";
		TypedQuery<ReportFavorites> query = em.createQuery(jpql, ReportFavorites.class);
		return query.getResultList();
	}

	public List<ReportFavoriteUser> findUsersByVideoId(String videoId) {
		String jpql = "SELECT new dto.ReportFavoriteUser(u.id, u.email, u.fullname, f.shareDate) FROM Favorite f JOIN f.user u WHERE f.video.id = :videoId ORDER BY f.shareDate DESC";
		TypedQuery<ReportFavoriteUser> query = em.createQuery(jpql, ReportFavoriteUser.class);
		query.setParameter("videoId", videoId);
		return query.getResultList();
	}
}
