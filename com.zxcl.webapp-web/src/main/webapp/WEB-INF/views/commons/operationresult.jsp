
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- 操作结果页面 --%>
<script type="text/javascript" src="${ctx}js/jquery-ui.js"></script>
<script type="text/javascript">
  $(function() {
  });
  <c:if test="${succ eq '1' }" >
  /*成功后跳转页面*/
  function forward(){
	  window.location.href='${ctx} ${forward}';
  }
  </c:if>
  <c:if test="${succ eq '0' }" >
  /*失败时返回面*/
  function goback(){
	  history.go(-1);
  }
  </c:if>
</script>

<div class="g-mn-top f-subline">当前位置：<%= request.getAttribute("level2menu")!=null?(String.valueOf(request.getAttribute("level2menu"))+" > "):"" %> 操作结果</div>
    <div class="g-con f-oh">
    	<h2 class="f-mb-20 f-fs8 f-fwn f-oh f-mt-80"  style=" width:365px;  margin:80px auto 0;">
    	<c:if test="${succ eq '1'}" >
    	<img alt="" src="${ctx }images/right.png" width="64" height="64"  style="float:left; display:inline-block;">
       	 </c:if>
		<c:if test="${succ eq '0'}" >
    	<img alt="" src="${ctx }images/wrong.png" width="64" height="64" style="float:left; display:inline-block;">
       	 </c:if>
       	 <span style="height: 64px; line-height:64px; float:left; display:inline-block;">
    	${msg }
       	 </span>
    	</h2>
        	
        <div class="f-cb" style="height: 120px;">
        <c:if test="${!empty  detailmsg}" >
        	<div class="f-cb f-ml-102 f-mt-50 f-fs5">
        		■ ${detailmsg }
        	</div>
        </c:if>
        </div>
        <div class="g-btn f-mt-30">
        	<div class="u-btn" align="center">
        		<c:if test="${succ eq '1'}" >
            	<input type="button"  onclick="forward()"  value="返回" class="u-btn-w u-btn-new f-line-no f-mr-20 " />
            	 </c:if>
  				<c:if test="${succ eq '0'}" >
            	<input type="button"  onclick="goback()" value="返回" class="u-btn-w  u-btn-new f-line-no f-mr-20" />
            	 </c:if>
            </div>
        </div>
      </div>
