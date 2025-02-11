package servlet.admin;

import java.io.IOException;

import org.apache.commons.beanutils.BeanUtils;

import dao.FavoriteDAO;
import dao.ShareDAO;
import dao.UsersDAO;
import dao.VideoDAO;
import dto.ReportFavoriteUser;
import entity.Users;
import entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({ "/admin", "/admin/videoManager/create", "/admin/videoManager/update", "/admin/videoManager/delete",
		"/admin/userManager", "/admin/userManager/create", "/admin/userManager/update", "/admin/userManager/delete",
		"/admin/reportFavorites", "/admin/reportFavoriteUser", "/admin/reportShareFriend" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersDAO usersDAO = new UsersDAO();
	private VideoDAO videoDAO = new VideoDAO();
	private FavoriteDAO favoriteDAO = new FavoriteDAO();
	private ShareDAO shareDAO = new ShareDAO();

	public AdminServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("/userManager")) {
			if (uri.contains("/userManager/update")) {
				Users users = usersDAO.findById(request.getParameter("id"));
				if (users != null) {
					request.setAttribute("user", users);
				} else {
					response.sendRedirect(request.getContextPath() + "/admin/userManager");
					return;
				}
			}
			request.setAttribute("listUsers", usersDAO.findAll());
			request.getRequestDispatcher("/views/admin/userManager.jsp").forward(request, response);
			return;
		} else if (uri.contains("/videoManager/update")) {
			Video video = videoDAO.findById(request.getParameter("id"));
			if (video != null) {
				request.setAttribute("video", video);
			} else {
				response.sendRedirect(request.getContextPath() + "/admin");
				return;
			}
		} else if (uri.contains("/reportFavorites")) {
			request.setAttribute("listReportFavorites", favoriteDAO.generateFavoriteReport());
			request.getRequestDispatcher("/views/admin/reportFavorites.jsp").forward(request, response);
			return;
		} else if (uri.contains("/reportFavoriteUser")) {
			request.setAttribute("listVideo", videoDAO.findAll());
			request.getRequestDispatcher("/views/admin/reportFavoriteUser.jsp").forward(request, response);
			return;
		} else if (uri.contains("/reportShareFriend")) {
			request.setAttribute("listVideo", videoDAO.findAll());
			request.getRequestDispatcher("/views/admin/reportShareFriend.jsp").forward(request, response);
			return;
		}
		request.setAttribute("listVideo", videoDAO.findAll());
		request.getRequestDispatcher("/views/admin/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("/videoManager/create")) {
			try {
				Video video = new Video();
				BeanUtils.populate(video, request.getParameterMap());
				video.setViews(0);
				if (videoDAO.findById(video.getId()) != null) {
					request.setAttribute("video", video);
					request.setAttribute("messError", "Video này đã tồn tại trên hệ thông");
				} else {
					videoDAO.create(video);
					request.setAttribute("messSuccess", "Thêm video thành công");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("listVideo", videoDAO.findAll());
		} else if (uri.contains("/videoManager/update")) {
			try {
				Video video = videoDAO.findById(request.getParameter("id"));
				if (video != null) {
					video.setTitle(request.getParameter("title"));
					video.setPoster(request.getParameter("poster"));
					video.setViews(Integer.valueOf(request.getParameter("views")));
					video.setActive(Boolean.valueOf(request.getParameter("active")));
					video.setDescription(request.getParameter("description"));
					videoDAO.update(video);
					request.setAttribute("messSuccess", "Cập nhật video thành công");
				} else {
					video = new Video();
					BeanUtils.populate(video, request.getParameterMap());
					request.setAttribute("video", video);
					request.setAttribute("messError", "Mã video không tồn tại");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("listVideo", videoDAO.findAll());
		} else if (uri.contains("/videoManager/delete")) {
			try {
				Video video = videoDAO.findById(request.getParameter("id"));
				if (video != null) {
					if (videoDAO.deleteById(video.getId())) {
						request.setAttribute("messSuccess", "Xóa video thành công");
					} else {
						request.setAttribute("messError", "Video này không thể xóa vì khóa ngoại");
					}
				} else {
					request.setAttribute("video", video);
					request.setAttribute("messError", "Mã video không tồn tại");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("listVideo", videoDAO.findAll());
		} else if (uri.contains("/userManager/create")) {
			try {
				Users user = new Users();
				BeanUtils.populate(user, request.getParameterMap());
				if (usersDAO.findById(user.getId()) != null) {
					request.setAttribute("user", user);
					request.setAttribute("messError", "Mã Username này đã tồn tại trên hệ thông");
				} else {
					usersDAO.create(user);
					request.setAttribute("messSuccess", "Thêm user thành công");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("listUsers", usersDAO.findAll());
			request.getRequestDispatcher("/views/admin/userManager.jsp").forward(request, response);
			return;
		} else if (uri.contains("/userManager/update")) {
			try {
				Users user = usersDAO.findById(request.getParameter("id"));
				if (user != null) {
					user.setPassword(request.getParameter("password"));
					user.setFullname(request.getParameter("fullname"));
					user.setEmail(request.getParameter("email"));
					user.setAdmin(Boolean.valueOf(request.getParameter("admin")));
					usersDAO.update(user);
					request.setAttribute("messSuccess", "Cập nhật user thành công");
				} else {
					user = new Users();
					BeanUtils.populate(user, request.getParameterMap());
					request.setAttribute("user", user);
					request.setAttribute("messError", "Username không tồn tại");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("listUsers", usersDAO.findAll());
			request.getRequestDispatcher("/views/admin/userManager.jsp").forward(request, response);
			return;
		} else if (uri.contains("/userManager/delete")) {
			try {
				Users user = usersDAO.findById(request.getParameter("id"));
				if (user != null) {
					if (usersDAO.deleteById(user.getId())) {
						request.setAttribute("messSuccess", "Xóa user thành công");
					} else {
						request.setAttribute("messError", "User này không thể xóa vì khóa ngoại");
					}
				} else {
					request.setAttribute("user", user);
					request.setAttribute("messError", "Mã user không tồn tại");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("listUsers", usersDAO.findAll());
			request.getRequestDispatcher("/views/admin/userManager.jsp").forward(request, response);
			return;
		} else if (uri.contains("/reportFavoriteUser")) {
			String id = request.getParameter("id");
			request.setAttribute("listVideo", videoDAO.findAll());
			request.setAttribute("video", videoDAO.findById(id));
			request.setAttribute("listReportFavoriteUser", favoriteDAO.findUsersByVideoId(id));
			request.getRequestDispatcher("/views/admin/reportFavoriteUser.jsp").forward(request, response);
			return;
		} else if (uri.contains("/reportShareFriend")) {
			String id = request.getParameter("id");
			request.setAttribute("listVideo", videoDAO.findAll());
			request.setAttribute("video", videoDAO.findById(id));
			request.setAttribute("listReportFavoriteUser", shareDAO.findReportShareFriendsByVideoId(id));
			request.getRequestDispatcher("/views/admin/reportShareFriend.jsp").forward(request, response);
		}
		request.getRequestDispatcher("/views/admin/home.jsp").forward(request, response);
	}
}
