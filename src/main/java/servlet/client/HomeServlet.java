package servlet.client;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.FavoriteDAO;
import dao.ShareDAO;
import dao.UsersDAO;
import dao.VideoDAO;
import entity.Favorite;
import entity.Share;
import entity.Users;
import entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Mailer;

@WebServlet({ "", "/signin", "/register", "/forgotPassword", "/editProfile", "/changePassword", "/videoFavorite",
		"/videoDetail", "/signout", "/likeVideo", "/shareVideo" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoDAO videoDAO = new VideoDAO();
	private UsersDAO usersDAO = new UsersDAO();
	private FavoriteDAO favoriteDAO = new FavoriteDAO();
	private ShareDAO shareDAO = new ShareDAO();

	public HomeServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("/signin")) {
			request.getRequestDispatcher("/views/client/login.jsp").forward(request, response);
			return;
		} else if (uri.contains("/register")) {
			request.getRequestDispatcher("/views/client/register.jsp").forward(request, response);
			return;
		} else if (uri.contains("/forgotPassword")) {
			request.getRequestDispatcher("/views/client/forgotPassword.jsp").forward(request, response);
			return;
		} else if (uri.contains("/editProfile")) {
			Users currentUser = (Users) request.getSession().getAttribute("currentUser");
			request.setAttribute("user", currentUser);
			request.getRequestDispatcher("/views/client/editProfile.jsp").forward(request, response);
			return;
		} else if (uri.contains("/changePassword")) {
			Users currentUser = (Users) request.getSession().getAttribute("currentUser");
			request.setAttribute("user", currentUser);
			request.getRequestDispatcher("/views/client/changePassword.jsp").forward(request, response);
			return;
		} else if (uri.contains("/videoFavorite")) {
			Users existingUser = (Users) request.getSession().getAttribute("currentUser");
			if (existingUser == null) {
				response.sendRedirect(request.getContextPath() + "/");
			}
			request.setAttribute("listVideFavorite", videoDAO.findVideosLikedByUser(existingUser.getId()));
			request.getRequestDispatcher("/views/client/videoFavorite.jsp").forward(request, response);
			return;
		} else if (uri.contains("/videoDetail")) {
			Users existingUser = (Users) request.getSession().getAttribute("currentUser");
			String id = request.getParameter("id");
			Video video = videoDAO.findById(id);
			if (video != null) {
				video.setViews(video.getViews() + 1);
				videoDAO.update(video);
				request.setAttribute("video", video);
				if (existingUser != null) {
					request.setAttribute("likedVideo",
							favoriteDAO.findByUserIdAndVideoId(existingUser.getId(), video.getId()) != null);
				}
				request.setAttribute("listVideoRandom", videoDAO.random10Video(video.getId()));
				request.getRequestDispatcher("/views/client/videoDetail.jsp").forward(request, response);
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}
		} else if (uri.contains("/signout")) {
			request.getSession().invalidate();
			request.getSession().removeAttribute("currentUser");
			response.sendRedirect(request.getContextPath() + "/");
			return;
		} else if (uri.contains("/likeVideo")) {
			Users existingUser = (Users) request.getSession().getAttribute("currentUser");
			String videoId = request.getParameter("id");
			Video video = videoDAO.findById(videoId);
			if (existingUser == null) {
				response.sendRedirect(request.getContextPath() + "/");
			}
			if (video == null) {
				response.sendRedirect(request.getContextPath() + "/");
			}
			Favorite favorite = favoriteDAO.findByUserIdAndVideoId(existingUser.getId(), video.getId());
			if (favorite != null) {
				favoriteDAO.deleteById(favorite.getId());
			} else {
				favorite = new Favorite(existingUser, video, new Date());
				favoriteDAO.create(favorite);
			}
			String referer = request.getHeader("Referer");
			if (referer != null && referer.contains("/videoDetail")) {
				response.sendRedirect(request.getContextPath() + "/videoDetail?id=" + videoId);
			} else if (referer != null && referer.contains("/videoFavorite")) {
				response.sendRedirect(request.getContextPath() + "/videoFavorite");
			} else {
				response.sendRedirect(request.getContextPath() + "/");
			}

			return;
		}
		Users existingUser = (Users) request.getSession().getAttribute("currentUser");
		if (existingUser != null) {
			List<Favorite> listLiked = favoriteDAO.findFavoritesByUserId(existingUser.getId());
			Set<String> likedSet = new HashSet<>();
			for (Favorite favorite : listLiked) {
				likedSet.add(favorite.getVideo().getId());
			}
			request.setAttribute("listLiked", likedSet);
		}
		request.setAttribute("listVideo", videoDAO.findAllActive());
		request.getRequestDispatcher("/views/client/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (uri.contains("/register")) {
			String errorEmail = "Email này đã tồn tại!";
			String errorUsername = "Username này đã tồn tại!";
			String success = "Tạo tài khoản thành công";
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String fullname = request.getParameter("fullname");
			Users existingUserById = usersDAO.findByIdOrEmail(id);
			Users existingUserByEmail = usersDAO.findByIdOrEmail(email);
			if (existingUserById != null) {
				request.setAttribute("errorUsername", errorUsername);
			} else if (existingUserByEmail != null) {
				request.setAttribute("errorEmail", errorEmail);
			} else {
				usersDAO.create(new Users(id, password, email, fullname, false));
				request.setAttribute("success", success);
			}
			request.setAttribute("user", new Users(id, password, email, fullname));
			request.getRequestDispatcher("/views/client/register.jsp").forward(request, response);
			return;
		} else if (uri.contains("/signin")) {
			String errorMessage = "Tài khoản hoặc mật khẩu không chính xác!";
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			Users existingUser = usersDAO.findByIdOrEmail(id);
			if (existingUser != null && existingUser.getPassword().equals(password)) {
				request.getSession().setAttribute("currentUser", existingUser);
				response.sendRedirect(request.getContextPath() + "/");
				return;
			} else {
				request.setAttribute("errorMessage", errorMessage);
			}
			request.setAttribute("user", new Users(id, password));
			request.getRequestDispatcher("/views/client/login.jsp").forward(request, response);
			return;
		} else if (uri.contains("/forgotPassword")) {
			String errorMessage = "Tài khoản và email bạn nhập không khớp!";
			String successMessage = "Mật khẩu của bạn đã được gửi qua email!";
			String id = request.getParameter("id");
			String email = request.getParameter("email");
			Users existingUser = usersDAO.findByIdOrEmail(id);
			if (existingUser != null && existingUser.getEmail().equals(email)) {
				String emailBody = "<h3>Mật khẩu cũ của bạn là: " + existingUser.getPassword() + "</h3>";
				Mailer.send(existingUser.getEmail(), "Gửi thông tin mật khẩu cũ", emailBody);
				request.setAttribute("successMessage", successMessage);
			} else {
				request.setAttribute("errorMessage", errorMessage);
			}
			request.setAttribute("user", new Users(id, null, email, null));
			request.getRequestDispatcher("/views/client/forgotPassword.jsp").forward(request, response);
			return;
		} else if (uri.contains("/changePassword")) {
			String errorConfirmNewPassword = "Mật khẩu không trùng khớp!";
			String errorPassword = "Mật khẩu không chính xác!";
			String success = "Đổi password thành công";
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			String confirmNewPassword = request.getParameter("confirmNewPassword");
			Users existingUser = usersDAO.findByIdOrEmail(id);
			if (!existingUser.getPassword().equals(password)) {
				request.setAttribute("errorPassword", errorPassword);
			} else if (!newPassword.equals(confirmNewPassword)) {
				request.setAttribute("errorConfirmNewPassword", errorConfirmNewPassword);
			} else {
				existingUser.setPassword(newPassword);
				usersDAO.update(existingUser);
				request.setAttribute("success", success);
			}

			request.setAttribute("password", password);
			request.setAttribute("newPassword", newPassword);
			request.setAttribute("confirmNewPassword", confirmNewPassword);
			request.setAttribute("user", new Users(id, null, null, null));
			request.getRequestDispatcher("/views/client/changePassword.jsp").forward(request, response);
			return;
		} else if (uri.contains("/editProfile")) {
			String id = request.getParameter("id");
			String fullname = request.getParameter("fullname");
			String email = request.getParameter("email");
			String success = "Cập nhật thành công!";
			String errorEmail = "Email này đã tồn tại!";
			Users existingUser = usersDAO.findByIdOrEmail(id);
			Users userWithSameEmail = usersDAO.findByIdOrEmail(email);
			if (userWithSameEmail != null && !userWithSameEmail.getId().equals(existingUser.getId())) {
				request.setAttribute("errorEmail", errorEmail);
			} else {
				existingUser.setFullname(fullname);
				existingUser.setEmail(email); // Email có thể là email hiện tại hoặc mới không trùng
				usersDAO.update(existingUser);
				request.setAttribute("success", success);
			}
			request.setAttribute("user", existingUser);
			request.getRequestDispatcher("/views/client/editProfile.jsp").forward(request, response);
			return;
		} else if (uri.contains("/shareVideo")) {
			Users existingUser = (Users) request.getSession().getAttribute("currentUser");
			String email = request.getParameter("email");
			String videoId = request.getParameter("videoId");
			Video video = videoDAO.findById(videoId);
			String articleUrl = "http://localhost:8080" + request.getContextPath() + "/videoDetail?id=" + videoId;
			String emailBody = "<h3>Bạn được chia sẽ video:</h3>" + "<a href='" + articleUrl
					+ "'>Click vào đây để xem</a>";
			Mailer.send(email, "Bạn được chia sẽ 1 video", emailBody);
			Share share = new Share(existingUser, video, email, new Date());
			shareDAO.create(share);
			String referer = request.getHeader("Referer");
			if (referer != null && referer.contains("/videoDetail")) {
				response.sendRedirect(request.getContextPath() + "/videoDetail?id=" + videoId + "&modal=true");
			} else if (referer != null && referer.contains("/videoFavorite")) {
				response.sendRedirect(request.getContextPath() + "/videoFavorite?&modal=true");
			} else {
				response.sendRedirect(request.getContextPath() + "/?modal=true");
			}
			return;
		}
		
		String idOrEmail = request.getParameter("idOrEmail");
	    String password = request.getParameter("password");
	    String keyword = request.getParameter("keyword");
		 if (keyword != null && !keyword.trim().isEmpty()) {
		        VideoDAO videoDAO = new VideoDAO();
		        List<Video> list = videoDAO.findByTitleKeyword(keyword);
		        request.setAttribute("listVideo", list); 
		    } else {
		    	VideoDAO videoDAO = new VideoDAO();
		        List<Video> listVideos = videoDAO.findAll();
		        request.setAttribute("listVideo", listVideos);
		        request.getRequestDispatcher("/views/client/home.jsp").forward(request, response);
		    }

		    // Trả về trang home.jsp
		    request.getRequestDispatcher("/views/client/home.jsp").forward(request, response);

	}

}
