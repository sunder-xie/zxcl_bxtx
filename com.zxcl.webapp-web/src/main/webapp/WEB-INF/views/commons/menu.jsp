<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/PaginationTag.tld" prefix="mycPage"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
	<%
	if (request.getContextPath().endsWith("/")) {
		request.setAttribute("ctx", request.getContextPath());
	} else {
		request.setAttribute("ctx", request.getContextPath() + "/");
	}
%>
<c:choose>
	<c:when test="${'1' eq menuControl}"></c:when>
	<c:otherwise><script>var ctx = "${ctx}";</script><div style="height:53px;"></div><div class="bomnav clear"><a class="lt nav1" href="${ctx }index.do?nocheck=1">首页</a><a class="lt nav2" href="${ctx }activity/historyActivity.do">广场</a><a class="lt nav3" href="${ctx }myself.do">我的</a></div></c:otherwise>
</c:choose>
