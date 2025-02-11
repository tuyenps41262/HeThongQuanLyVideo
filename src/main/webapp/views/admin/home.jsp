<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý video</title>
<style>
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
  border-spacing: 0 10px;
}

.table td, .table th {
  padding: 10px;
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
            <input type="text" class="form-control" id="id" name="id" placeholder="id" required value="${video.id}">
            <label for="id">ID Youtube</label>
            <span class="text-danger">${messError}</span>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="title" name="title" placeholder="title" required value="${video.title}">
            <label for="title">Tiêu đề video</label>
          </div>
          <div class="form-floating mb-3">
            <input type="text" class="form-control" id="poster" name="poster" placeholder="poster" required value="${video.poster}">
            <label for="poster">Poster video</label>
          </div>
          <input type="hidden" step="1" min="0" name="views" class="form-control" id="views" placeholder="views" required value="${video.views}">
          
          <div class="form-check form-check-inline mb-3">
            <input class="form-check-input" type="radio" ${!video.active ? 'checked' : ''} name="active" id="inlineRadio2" value="false">
            <label class="form-check-label" for="inlineRadio2">Không hoạt động</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" ${video.active ? 'checked' : ''} name="active" id="inlineRadio1" value="true">
            <label class="form-check-label" for="inlineRadio1">Hoạt động</label>
          </div>

          <div class="mb-3">
            <label for="description" class="form-label">Mô tả</label>
            <textarea class="form-control" name="description" id="description" rows="3">${video.description}</textarea>
          </div>

          <span class="text-success">${messSuccess}</span>

          <div>
            <button formaction="${pageContext.request.contextPath}/admin/videoManager/create" formmethod="post" class="btn btn-primary mb-2" type="submit">Tạo mới</button>
            <button formaction="${pageContext.request.contextPath}/admin/videoManager/update" formmethod="post" class="btn btn-warning mb-2" type="submit">Cập nhật</button>
            <button formaction="${pageContext.request.contextPath}/admin/videoManager/delete" formmethod="post" class="btn btn-danger mb-2" type="submit">Xóa</button>
            <button formaction="${pageContext.request.contextPath}/admin" class="btn btn-light border mb-2" type="submit">Làm lại</button>
          </div>
        </form>
      </div>

      <!-- Cột bên phải (danh sách video) -->
      <div class="col-md-8">
        <div class="table-scroll">
          <table class="table table-bordered">
            <thead>
              <tr>
             	<th scope="col">STT</th> 
                <th scope="col">ID Youtube</th>
                <th scope="col">Tiêu đề video</th>
                <th scope="col">Lượt xem</th>
                <th scope="col">Trạng thái</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="video" varStatus="stt" items="${listVideo}">
                <tr>
                <td>${stt.index + 1}</td>
                  <td>${video.id}</td>
                  <td>${video.title}</td>
                  <td>${video.views}</td>
                  <td>${video.active ? "Hoạt động" : "Không hoạt động"}</td>
                  <td>
                    <a href="${pageContext.request.contextPath}/admin/videoManager/update?id=${video.id}">Edit</a>
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
