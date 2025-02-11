<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-expand-lg rounded" style="background-color: #ADD8E6;">
            <div class="container-fluid">
<a class="navbar-brand fw-bold" style="color: #00008B;" href="${pageContext.request.contextPath}/">Tất cả video</a>
              <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
              <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                	<c:choose>
				        <c:when test="${not empty sessionScope.currentUser}">
				        	<c:if test="${sessionScope.currentUser.admin}">
					           	<li class="nav-item">
			                    	<a class="nav-link fw-bold text-primary" href="${pageContext.request.contextPath}/admin">Admin</a>
			                  	</li>
		                  	</c:if>
		                  	<li class="nav-item">
		                    	<a class="nav-link fw-bold text-primary" href="${pageContext.request.contextPath}/videoFavorite">Video yêu thích của tôi</a>
		                  	</li>
				        </c:when>
				      </c:choose>
                  <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-primary fw-bold" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                     Tài khoản của tôi
                    </a>
                    <ul class="dropdown-menu">
                    <c:choose>
				        <c:when test="${not empty sessionScope.currentUser}">
				          	<li><a class="dropdown-item" href="${pageContext.request.contextPath}/changePassword">Thay đổi mật khẩu</a></li>
                     		<li><a class="dropdown-item" href="${pageContext.request.contextPath}/editProfile">Chỉnh sửa hồ sơ</a></li>
                      		<li><a class="dropdown-item" href="${pageContext.request.contextPath}/signout">Đăng xuất</a></li>
				        </c:when>
				        <c:otherwise>
				         	<li><a class="dropdown-item" href="${pageContext.request.contextPath}/signin">Đăng nhập</a></li>
				         	<li><a class="dropdown-item" href="${pageContext.request.contextPath}/register">Đăng ký</a></li>
				         	<li><a class="dropdown-item" href="${pageContext.request.contextPath}/forgotPassword">Quên mật khẩu</a></li>
				        </c:otherwise>
				      </c:choose>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
        </nav>