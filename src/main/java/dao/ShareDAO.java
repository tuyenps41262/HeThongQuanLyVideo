package dao;

import java.util.List;

import dto.ReportShareFriend;
import entity.Share;
import entity.Video;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import utils.XJPA;

public class ShareDAO implements DAOInterface<Share, Integer> {
	EntityManager em = XJPA.getEntityManager();

	@Override
	protected void finalize() throws Throwable {
		em.close();
	}

	@Override
	public List<Share> findAll() {
		String jpql = "SELECT o FROM Share o";
		TypedQuery<Share> query = em.createQuery(jpql, Share.class);
		return query.getResultList();
	}

	@Override
	public Share findById(Integer id) {
		return em.find(Share.class, id);
	}

	@Override
	public void create(Share t) {
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
		Share entity = em.find(Share.class, id);
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
	public void update(Share t) {
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
	}

	public List<Video> findVideosSharedIn2024() {
		String jpql = "SELECT s.video FROM Share s WHERE YEAR(s.shareDate) = 2024 ORDER BY s.shareDate";
		TypedQuery<Video> query = em.createQuery(jpql, Video.class);
		return query.getResultList();
	}

	public List<Object[]> getShareForVideos() {
		String jpql = "SELECT v.title, COUNT(s.id) AS share_count, MIN(s.shareDate) AS min_share_date, MAX(s.shareDate) AS max_share_date "
				+ "FROM Share s JOIN s.video v " + "GROUP BY v.title ORDER BY v.title";
		TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
		return query.getResultList();
	}

	public List<ReportShareFriend> findReportShareFriendsByVideoId(String videoId) {
		String jpql = "SELECT new dto.ReportShareFriend(u.fullname, u.email, s.emails, (SELECT MAX(f.shareDate) FROM Favorite f WHERE f.video.id = :videoId AND f.user.id = u.id), s.shareDate) FROM Share s JOIN s.user u WHERE s.video.id = :videoId";
		TypedQuery<ReportShareFriend> query = em.createQuery(jpql, ReportShareFriend.class);
		query.setParameter("videoId", videoId);
		return query.getResultList();
	}
}
