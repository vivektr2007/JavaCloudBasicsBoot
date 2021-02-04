<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<meta charset="utf-8">
<link type="text/css" href="css/slideStyles.css" rel="stylesheet" />
<%-- <jsp:include page="/WEB-INF/view/commonIncludesHead.jsp"></jsp:include> --%>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/waltzerjs.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("div[test='carousel']").each(function() {
			$('#' + $(this).attr("id")).waltzer({
				scroll : 4,
				circular : false
			});
		});	
	});
	
</script>
</head>

<header>
	
	<nav>
		<div id="menu_container">
			<jsp:include page="/WEB-INF/view/menu.jsp"></jsp:include>
		</div>
	</nav>
</header>
<body>

	<c:forEach items="${MapBookList}" var="citem" varStatus="count">
		<div>
			<div class="bookHeading">
				<p>${citem.key}</p>
			</div>
			<div test='carousel' id="carousel${count.index}" class='outerWrapper'>
				<c:forEach items="${citem.value}" var="vitem" varStatus="vcount">
					<div id="bookItem${vcount.index}" class="item">
						<c:if test="${vitem.thumbpath!=null}">
							<a href="${awsEndPoint}${vitem.bookpath}" target="_blank">
							<img src="${awsEndPoint}${vitem.thumbpath}" alt="test" width="95" height="85" /></a>
							<p class="bookPara">${vitem.bookname}</p>
						</c:if>
						<c:if test="${vitem.thumbpath==null}">
							<a href="${awsEndPoint}${vitem.bookpath}" target="_blank"><img
								src="images/pdf.jpg" alt="test" width="100" height="100" /></a>
							<p class="bookPara">${vitem.bookname}</p>
						</c:if>
					</div>
				</c:forEach>
			</div>
			
		</div>
	</c:forEach>
</body>
</html>
