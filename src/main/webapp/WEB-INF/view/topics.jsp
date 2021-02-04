<%@page import="java.net.URLDecoder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="/WEB-INF/view/newmenu.jsp"></jsp:include>
<body>

<script>
	
	$(function() {
		$(".tabular.menu .item").tab();
		$('').transition('jiggle');
	});
	
	$(document).on('click', ' .ui .horizontal .statistics .statistic', function () {
		alert("Data Saved Successfully");
	});
	
</script>

<div class="ui grid">
	
	<div class="one wide column"></div>
	<div class="eleven wide column">
	
		<c:if test="${not empty searchKeyword}">
		 <div class="ui top attached tabular menu">
		  <div class="active item" data-tab="searchTab">Search (${rowCount})</div>
		 </div>
		 
		  <div class="ui bottom attached active tab segment" data-tab="searchTab">
		  		<div class="ui relaxed divided list">
		  		<c:if test="${not empty contentList}">
		  		<c:forEach items="${contentList}" var="item" varStatus="count">
				<div class="item">
				    <i class="large github middle aligned icon"></i>
				    <div class="content">
				      <a class="header" href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}">${item.titleId}</a>
				      <div class="description"><i class="calendar outline blue icon"></i>Posted on : ${item.creationDate}</div>
				    </div>
				  </div>
				</c:forEach>
				</c:if>
				<c:if test="${empty contentList}">
					<div class="ui small orange message">
					  <div class="header">
					    No Data Found for keyword : ${searchKeyword}
					  </div>
					  <p>Currently, No Data found. Search with better Keyword</p>
					  <p>Thank You !! Enjoy Reading.</p>
					</div>
				</c:if>
				</div>
				<%
					int pageNo = request.getAttribute("pageNo") != null ? (Integer) request.getAttribute("pageNo") : 1;
					int rowCount = request.getAttribute("rowCount") != null ? (Integer) request.getAttribute("rowCount") : 0;
					int pageCount = rowCount / 15;
					if (pageCount * 15 < rowCount) {
						pageCount = pageCount + 1;
					}
				%>
				<div class="ui borderless menu">
						<%if ((pageNo - 1) > 0) {%>
							<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword }&menuId=${menuId}&pageNo=<%=pageNo-1%>">«</a>
						<%}%>
						<%
							for (int i = 0; i < pageCount; i++) {
								int pageNum = i + 1;
								if (pageNo == pageNum) {
						%>
							<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword }&menuId=${menuId}&pageNo=<%=pageNum%>"><%=pageNum%></a>
						<%} else {%>
							<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword }&menuId=${menuId}&pageNo=<%=pageNum%>"><%=pageNum%></a>
						<%
							}
						}
						%>
						<%if ((pageNo) < pageCount) {%>
							<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword}&menuId=${menuId}&pageNo=<%=pageNo+1%>">»</a>
						<%}%>
				</div>
		  </div>
		</c:if>
		
		
		<c:if test="${empty searchKeyword}">
		<div id="selectorTab" class="ui top attached tabular menu">
		<c:if test="${selectorId == 'RequestFromHomePage'}">
		  <c:if test="${not empty parentMenu1ContentList}">
		  	<a class="item active" data-tab="javaTab"><i class="coffee brown icon"></i>Java(${parentMenu1ContentListCount})</a>
		  </c:if>
		   <c:if test="${not empty parentMenu4ContentList}">
		  	<a class="item" data-tab="springTab"><i class="leaf green icon"></i>Spring(${parentMenu4ContentListCount})</a>
		  </c:if>
		   <c:if test="${not empty parentMenu2ContentList}">
		  	<a class="item" data-tab="designpatternTab"><i class="sticky note yellow icon"></i>Design Patterns(${parentMenu2ContentListCount})</a>
		  </c:if>
		   <c:if test="${not empty parentMenu3ContentList}">
		 	 <a class="item" data-tab="cloudTab"><i class="cloud blue icon"></i>Cloud Forums(${parentMenu3ContentListCount})</a>
		  </c:if>
		 </c:if>
		 <c:if test="${rowCount >0 && selectorId != 'RequestFromHomePage' && empty searchKeyword}">
		 	<a class="item active" data-tab="commonTab">${menuName}</a>
		 </c:if>
		</div>
		
		<c:if test="${not empty parentMenu1ContentList}">
		<div class="ui bottom attached active tab segment" data-tab="javaTab">
			<c:forEach items="${parentMenu1ContentList}" var="item" varStatus="count">
				<div class="ui raised segment">
					<div id="content${count.index}">
						<%if (request.getAttribute("hotTopic") != null && request.getAttribute("hotTopic").equals("Y")) {%>
						<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%} else {%>
						<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%}%>
						<p><i class="calendar outline blue icon"></i>Posted on : ${item.creationDate}</p>
						<p>${item.shortDescId }</p>
						<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><button class="mini ui basic green button">Read More</button></a>
						&nbsp;&nbsp;
						
						<div class="ui labeled button" tabindex="0">
						  <div class="mini ui basic red button" onclick="updateContentLike('${item.contentId}')"><i class="heart icon" id="chkLike"></i>Like</div>
						  <a class="ui basic red left pointing label">${item.likeCount }</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		
		<c:if test="${not empty parentMenu4ContentList}">
		<div class="ui bottom attached tab segment" data-tab="springTab">
			<c:forEach items="${parentMenu4ContentList}" var="item" varStatus="count">
				<div class="ui raised segment">
					<div id="content${count.index}">
						<%if (request.getAttribute("hotTopic") != null && request.getAttribute("hotTopic").equals("Y")) {%>
							<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%} else {%>
							<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%
							}
						%>
						<p><i class="calendar outline blue icon"></i>Posted on : ${item.creationDate}</p>
						<p>${item.shortDescId }</p>
						<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><button class="mini ui basic green button">Read More</button></a>
						&nbsp;&nbsp;
						
						<div class="ui labeled button" tabindex="0">
						  <div class="mini ui basic red button" onclick="updateContentLike('${item.contentId}')"><i class="heart icon" id="chkLike"></i>Like</div>
						  <a class="ui basic red left pointing label">${item.likeCount }</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		
		<c:if test="${not empty parentMenu2ContentList}">
		<div class="ui bottom attached tab segment" data-tab="designpatternTab">
			<c:forEach items="${parentMenu2ContentList}" var="item" varStatus="count">
				<div class="ui raised segment">
					<div id="content${count.index}">
						<%if (request.getAttribute("hotTopic") != null && request.getAttribute("hotTopic").equals("Y")) {%>
							<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%} else {%>
							<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%
							}
						%>
						<p><i class="calendar outline blue icon"></i>Posted on : ${item.creationDate}</p>
						<p>${item.shortDescId }</p>
						<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><button class="mini ui basic green button">Read More</button></a>
						&nbsp;&nbsp;
						
						<div class="ui labeled button" tabindex="0">
						  <div class="mini ui basic red button" onclick="updateContentLike('${item.contentId}')"><i class="heart icon" id="chkLike"></i>Like</div>
						  <a class="ui basic red left pointing label">${item.likeCount }</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		
		<c:if test="${not empty parentMenu3ContentList}">
		<div class="ui bottom attached tab segment" data-tab="cloudTab">
			<c:forEach items="${parentMenu3ContentList}" var="item" varStatus="count">
				<div class="ui raised segment">
					<div id="content${count.index}">
						<%if (request.getAttribute("hotTopic") != null && request.getAttribute("hotTopic").equals("Y")) {%>
						<h3 class="ui blue header">${item.titleId}</h3>
						<%} else {%>
						<h3 class="ui blue header">${item.titleId}</h3>
						<%
							}
						%>
						<p><i class="calendar outline blue icon"></i>Posted on : ${item.creationDate}</p>
						<p>${item.shortDescId }</p>
						<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><button class="mini ui basic green button">Read More</button></a>
						&nbsp;&nbsp;
						
						<div class="ui labeled button" tabindex="0">
						  <div class="mini ui basic red button" onclick="updateContentLike('${item.contentId}')"><i class="heart icon" id="chkLike"></i>Like</div>
						  <a class="ui basic red left pointing label">${item.likeCount }</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		
		<c:if test="${not empty contentList}">
		<div class="ui bottom attached active tab segment" data-tab="commonTab">
			<c:forEach items="${contentList}" var="item" varStatus="count">
				<div class="ui raised segment">
					<div id="content${count.index}">
						<%if (request.getAttribute("hotTopic") != null && request.getAttribute("hotTopic").equals("Y")) {%>
							<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%} else {%>
							<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><h3 class="ui blue header">${item.titleId}</h3></a>
						<%
							}
						%>
						<p><i class="calendar outline blue icon"></i>Posted on : ${item.creationDate}</p>
						<p>${item.shortDescId }</p>
						<a href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><button class="mini ui basic green button">Read More</button></a>
						&nbsp;&nbsp;
						
						<div class="ui labeled button" tabindex="0">
						  <div class="mini ui basic red button" onclick="updateContentLike('${item.contentId}')"><i class="heart icon" id="chkLike"></i>Like</div>
						  <a class="ui basic red left pointing label">${item.likeCount }</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		
		</c:if><!-- End of Empty SearchKeyWordIF -->
		<c:if test="${rowCount >0 && selectorId != 'RequestFromHomePage' && empty searchKeyword}">
			<%
				int pageNo = request.getAttribute("pageNo") != null ? (Integer) request.getAttribute("pageNo") : 1;
				int rowCount = request.getAttribute("rowCount") != null ? (Integer) request.getAttribute("rowCount") : 0;
				int pageCount = rowCount / 15;
				if (pageCount * 15 < rowCount) {
					pageCount = pageCount + 1;
				}
			%>
			<div class="ui borderless menu">
					<%if ((pageNo - 1) > 0) {%>
						<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword }&menuId=${menuId}&menuName=${menuName}&pageNo=<%=pageNo-1%>">«</a>
					<%}%>
					<%
						for (int i = 0; i < pageCount; i++) {
							int pageNum = i + 1;
							if (pageNo == pageNum) {
					%>
						<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword }&menuId=${menuId}&menuName=${menuName}&pageNo=<%=pageNum%>"><%=pageNum%></a>
					<%} else {%>
						<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword }&menuId=${menuId}&menuName=${menuName}&pageNo=<%=pageNum%>"><%=pageNum%></a>
					<%
						}
					}
					%>
					<%if ((pageNo) < pageCount) {%>
						<a class="item"	href="${pageContext.request.contextPath }/viewTopicsByPageNo.html?searchKeyword=${searchKeyword }&menuId=${menuId}&menuName=${menuName}&pageNo=<%=pageNo+1%>">»</a>
					<%}%>
				
			</div>
		</c:if>
	</div>
	<div class="three wide column">
	
		<div class="ui horizontal statistics">
		  <div class="orange statistic">
		    <div class="value" id="totalViewsDiv">
		      0
		    </div>
		    <div class="label">
		      Views
		    </div>
		  </div>
		  <div class="yellow statistic">
		    <div class="value" id="blogCountDiv">
		      0
		    </div>
		    <div class="label">
		      Blogs
		    </div>
		  </div>
		</div>
		
		<form action="viewTopics" id="searchForm" method="post">
			<div class="ui icon input">
			  <input type="text" id="searchKeywordTXT" onkeypress="searchOnEnter(event)" name="searchKeyword" placeholder="Search..." >
			  <i class="inverted circular search link icon"></i>
			  <input type="hidden" name="searchKeyword" id="searchKeywordHidden" value="${searchKeyword}" />
			</div>
		</form>
		
		<div class="ui card">
		  <jsp:include page="/WEB-INF/view/author.jsp"></jsp:include>
		</div>
		
		<div class="ui card">
		  <jsp:include page="/WEB-INF/view/latestUpdates.jsp"></jsp:include>
		</div>
		
		
		<div class="ui card">
		  <div class="content">
		    <div class="header"><i class="tags blue icon"></i>Search by Tags</div>
		    	<div class="meta">2 days ago</div>
		    		<div class="description">
					<c:forEach items="${tagsSet}" var="item" varStatus="count">
						<a class="ui teal tiny tag label" onclick="tagKeyword('${item}')">${item}</a>&nbsp;&nbsp;
					</c:forEach>
					</div>
			</div>
		</div>
		
		</div>
	<div class="one wide column"></div>
	<input id="selectorId" type="hidden" value="${selectorId}">
</div>
</body>
</html>
