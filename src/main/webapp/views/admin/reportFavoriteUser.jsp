<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Báo cáo người dùng yêu thích</title>
<style>
.td-text {
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}
</style>
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
    crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <jsp:include page="layout/header.jsp" />

        <div class="mx-auto mt-3" style="width: 600px;">
            <nav class="nav nav-pills flex-column flex-sm-row mb-3">
                <a class="flex-sm-fill text-sm-center nav-link"
                    href="${pageContext.request.contextPath}/admin/reportFavorites">Yêu thích</a>
                <a class="flex-sm-fill text-sm-center nav-link active"
                    href="${pageContext.request.contextPath}/admin/reportFavoriteUser">Người dùng yêu thích</a>
                <a class="flex-sm-fill text-sm-center nav-link"
                    href="${pageContext.request.contextPath}/admin/reportShareFriend">Chia sẻ bạn bè</a>
            </nav>
            <form id="videoForm"
                action="${pageContext.request.contextPath}/admin/reportFavoriteUser"
                method="post" class="mb-3">
                <select class="form-select" aria-label="Chọn video" name="id"
                    onchange="submitForm()">
                    <option selected disabled>Chọn video</option>
                    <c:forEach var="item" varStatus="loop" items="${listVideo}">
                        <option ${video.id == item.id ? 'selected': '' } value="${item.id }">${item.title }</option>
                    </c:forEach>
                </select>
            </form>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Tên người dùng</th>
                        <th scope="col">Họ tên</th>
                        <th scope="col">Email</th>
                        <th scope="col">Ngày yêu thích</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty listReportFavoriteUser}">
                            <c:forEach var="item" varStatus="loop" items="${listReportFavoriteUser}">
                                <tr>
                                    <td>${item.id}</td>
                                    <td>${item.fullname}</td>
                                    <td>${item.email}</td>
                                    <td>
                                        <fmt:formatDate pattern="dd-MM-yyyy" value="${item.favoriteDate}" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4" class="text-center">Không có dữ liệu</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>

            </table>
        </div>

        <jsp:include page="layout/footer.jsp" />
    </div>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script>
        function submitForm() {
            document.getElementById("videoForm").submit();
        }
    </script>

</body>
</html>
