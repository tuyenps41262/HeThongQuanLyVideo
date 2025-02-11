<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý user</title>
<style>
.table td, .table th {
  padding: 30px; 
}
.table {
  border-spacing: 0 30px; /* Khoảng cách giữa các hàng là 10px */
}

.td-text {
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.table-scroll {
  max-height: 500px;
  overflow-y: auto;
}
.table {
  border-spacing: 0 30px; /* Khoảng cách giữa các hàng */
}
.table td, .table th {
  padding: 100px; /* Khoảng cách giữa các ô */
}
</style>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<jsp:include page="layout/header.jsp" />

		<div class="row mt-3">
			<!-- Cột bên trái (form nhập liệu) -->
			<div class="col-md-4">
				<form>
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="id" placeholder="Tên đăng nhập" name="id"
							required value="${user.id}">
						<label for="id">Tên đăng nhập</label>
						<span class="text-danger">${messError}</span>
					</div>
					<div class="form-floating mb-3">
						<input type="password" class="form-control" id="password" placeholder="Mật khẩu" name="password"
							required value="${user.password}">
						<label for="password">Mật khẩu</label>
					</div>
					<div class="form-floating mb-3">
						<input type="text" class="form-control" id="fullname" placeholder="Họ tên" name="fullname"
							required value="${user.fullname}">
						<label for="fullname">Họ tên</label>
					</div>
					<div class="form-floating mb-3">
						<input type="email" class="form-control" name="email" id="email" placeholder="Email" required
							value="${user.email}">
						<label for="email">Email</label>
					</div>
					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" ${user.admin ? 'checked' : ''} name="admin" id="inlineRadio1"
							value="true">
						<label class="form-check-label" for="inlineRadio1">Quản trị viên</label>
					</div>
					<div class="form-check form-check-inline mb-3">
						<input class="form-check-input" type="radio" name="admin" ${!user.admin ? 'checked' : ''} id="inlineRadio2"
							value="false">
						<label class="form-check-label" for="inlineRadio2">Người dùng</label>
					</div>
					<span class="text-success">${messSuccess}</span>
					<div class="">
						<button formaction="${pageContext.request.contextPath}/admin/userManager/create" formmethod="post"
							class="btn btn-primary mb-2" type="submit">Tạo mới</button>
						<button formaction="${pageContext.request.contextPath}/admin/userManager/update" formmethod="post"
							class="btn btn-warning mb-2" type="submit">Cập nhật</button>
						<button formaction="${pageContext.request.contextPath}/admin/userManager/delete" formmethod="post"
							class="btn btn-danger mb-2" type="submit">Xóa</button>
						<button class="btn btn-light border mb-2" type="submit">Đặt lại</button>
					</div>
				</form>
			</div>

			<!-- Cột bên phải (danh sách user) -->
			<div class="col-md-8">
	<div class="table-scroll">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th scope="col">STT</th> 
					<th scope="col">Tên đăng nhập</th>
					<th scope="col">Mật khẩu</th>
					<th scope="col">Họ tên</th>
					<th scope="col">Vai trò</th>
					<th scope="col">Địa chỉ Email</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" varStatus="stt" items="${listUsers}">
					<tr>
						<td>${stt.index + 1}</td>
						<td>${user.id}</td>
						<td>******</td>
						<td>${user.fullname}</td>
						<td>${user.admin ? "Quản trị viên" : "Người dùng"}</td>
						<td>${user.email}</td>
						<td>
							<a href="${pageContext.request.contextPath}/admin/userManager/update?id=${user.id}">Edit</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>


		</div>

		<jsp:include page="layout/footer.jsp" />
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

</body>
</html>
