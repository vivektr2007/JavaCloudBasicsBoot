<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="/WEB-INF/view/adminMenu.jsp"></jsp:include>
<head>
<script>
	$(document).on('click', '#deleteBlogId', function () {
		$("#deleteBlogFormId").submit();
	});
</script>
</head>

<body>
<div class="ui grid">
	<!-- 
	<div id="loadingDiv" class="ui active inverted dimmer">
		    <div class="ui large text loader">Loading</div>
	</div> -->
		 
	<div class="one wide column"></div>
	<div class="fourteen wide column">
	
		<div class="ui top attached tabular menu">
			  <div class="active item" data-tab="deleteBlogTab">Delete Blog</div>
		</div>
		<div class="ui bottom attached active tab segment" data-tab="deleteBlogTab">
			<form action="deleteBlog.html" method="post" id="deleteBlogFormId">
			<table id="contentTabId" class="ui very basic table">
				  <thead>
				    <tr>
				      	<th><div class="ui teal medium label">Content Id</div></th>
						<th><div class="ui teal medium label">Title</div></th>
						<th><div class="ui teal medium label">Creation Date</div></th>
						<th><div class="ui teal medium label">Select for Delete</div></th>
						<th><div class="ui teal medium label">Edit</div></th>
						<th><div class="ui teal medium label">YouTube Content</div></th>
				   </tr>
				   </thead>
				   <tbody>
				   		<c:forEach items="${contentList}" var="item" varStatus="count">
							<tr>
								<td>${item.contentId }</td>
								<td>${item.titleId }</td>
								<td>${item.creationDate }</td>
								<td><input type="checkbox" name="delCheck" value="${item.contentId}"></td>
								<td><a href="editBlog.html?id=${item.contentId}">Edit</a></td>
								<td><a href="getYouTube.html?contentId=${item.contentId}">YouTube</a></td>
							</tr>
						</c:forEach>
				   	</tbody>
			</table>
			<div id="deleteBlogId" class="ui tiny red button">Delete</div>
		</form>
		</div>
	</div>
	
	<div class="one wide column"></div>
</div>
		
</body>
</html>
