<%--
  Created by IntelliJ IDEA.
  User: igafalkowska
  Date: 23.05.17
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="headAndStyle.jsp" %>
<%@ include file="header.jsp" %>
<%@ include file="headerOptions.jsp" %>

<c:choose>
    <c:when test="${usersList != null}">
        <h3>Information about users logged in to Trip Calculator</h3>
        <div class="row" style="padding-bottom: 120px; margin:0;">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <table class="table table-bordered">
                    <thead>
                        <tr class="info">
                            <th class="col-md-2">First Name</th>
                            <th class="col-md-2">Second Name</th>
                            <th class="col-md-2">Email</th>
                            <th class="col-md-2">Recent login date</th>
                            <th class="col-md-2">Recent login hour</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${usersList}" var="user" begin="0" end="${usersList.size()}">
                            <tr class="active">
                                <td><c:out value="${user.get(0)}"></c:out></td>
                                <td><c:out value="${user.get(1)}"></c:out></td>
                                <td><c:out value="${user.get(2)}"></c:out></td>
                                <td><c:out value="${user.get(3)}"></c:out></td>
                                <td><c:out value="${user.get(4)}"></c:out></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-md-2"></div>
        </div>
    </c:when>

    <c:otherwise>
        <h2 class="error">Reports module is not connected at the moment</h2>
    </c:otherwise>

</c:choose>


<%@ include file="footer.jsp" %>
