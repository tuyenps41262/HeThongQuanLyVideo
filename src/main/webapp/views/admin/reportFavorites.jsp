<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Báo cáo yêu thích</title>
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
                <a class="flex-sm-fill text-sm-center nav-link active"
                    href="${pageContext.request.contextPath}/admin/reportFavorites">Yêu thích</a>
                <a class="flex-sm-fill text-sm-center nav-link"
                    href="${pageContext.request.contextPath}/admin/reportFavoriteUser">Người dùng yêu thích</a> 
                <a class="flex-sm-fill text-sm-center nav-link"
                    href="${pageContext.request.contextPath}/admin/reportShareFriend">Chia sẻ bạn bè</a>
            </nav>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col" style="width: 40%">Tiêu đề video</th>
                        <th scope="col" style="width: 25%">Số lượt yêu thích</th>
                        <th scope="col">Ngày mới nhất</th>
                        <th scope="col">Ngày cũ nhất</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" varStatus="loop" items="${listReportFavorites}">
                        <tr>
                            <td>${item.title }</td>
                            <td>${item.favoriteCount }</td>
                            <td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${item.latestDate }" /></td>
                            <td><fmt:formatDate pattern = "dd-MM-yyyy" value = "${item.oldestate }" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <jsp:include page="layout/footer.jsp" />
    </div>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

</body>
</html>
