<%--
  Created by IntelliJ IDEA.
  User: sebastianlos
  Date: 15.05.17
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="headAndStyle.jsp" %>
<%@ include file="header.jsp" %>
<br>
<h2 class="error">Uuuups... Something went wrong.</h2>
<br>
<img style="display: block; margin: 0 auto" width="450px" height="192px" src="img/giphy.gif" />
<br>
<form method="get" action="/start">
    <input type="submit" class="btn btn-primary btn-lg" value="Start your calculation">
</form>

<%@ include file="footer.jsp" %>