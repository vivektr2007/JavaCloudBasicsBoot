<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="/WEB-INF/view/adminMenu.jsp"></jsp:include>
<head>
<script>
	
$( function() {
	$('.ui .attached .embed').embed();
	var counter =1;
	$("#addFileId").click(
		function(event) {
			event.preventDefault();
			counter++;
			var newRow = '<tr><td>'+counter+'</td><td>https://www.youtube.com/embed/<input id="Youtubeurl" type="text" name="txtYoutubecode" /></td>';
			newRow = newRow + '<td><a href="javascript:void(0);" class="remCF" onclick="removeRow(this)">Remove</a></td></tr>';
			$("#childRowAddId").append(newRow);
	});
    
} );

function removeRow(obj) {
	$(obj).parent().parent().remove();
}

$(document).on('click', '#addYouTubeId', function () {
	$("#addYouTubeFormId").submit();
});

</script>
</head>
<body>

<div class="ui grid">
	
	<div id="loadingDiv" class="ui active inverted dimmer">
		    <div class="ui large text loader">Loading</div>
	</div>
		 
	<div class="three wide column"></div>
	<div class="ten wide column">
	
		<div class="ui top attached tabular menu">
			  <div class="active item" data-tab="addYouTubeTab"><i class="youtube red icon"></i>YouTube</div>
		</div>
		<div class="ui bottom attached active tab segment" data-tab="addYouTubeTab">
		<form action="addYouTube.html" method="post" id="addYouTubeFormId">
			<div class="ui orange medium label">ContentId : </div>${contentId}
			<div class="ui green message">
			  <div class="header">
			    Youtube URL
			  </div>
			  <p>Note: Don't enter "https://www.youtube.com/embed/"</p>
			  <p>If URL is "https://www.youtube.com/embed/-TxHjyC8WY4"  Enter only -TxHjyC8WY4</p>
			</div>
			
			<table id="contentTabId" class="ui very basic table">
				  <thead>
				  	<tr>
				  		<td>
		    				<button class="mini ui green button" id="addFileId"><i class="icon add"></i>Add</button>
		    			</td>
				  	</tr>
				    <tr>
				      	<th><div class="ui orange medium label">S.No</div></th>
						<th><div class="ui orange medium label">Youtube Code</div></th>
						<th><div class="ui orange medium label">Action</div></th>
				   </tr>
				   </thead>
				   <tbody id="childRowAddId">
				   <c:if test="${empty youtubeList}">
					   	<tr>
							<td>1</td>
							<td>https://www.youtube.com/embed/<input id="Youtubeurl" type="text" name="txtYoutubecode" /></td>
							<td><a href="javascript:void(0);" class="remCF" onclick="removeRow(this)">Remove</a></td>
						</tr>
					</c:if>
					<c:if test="${not empty youtubeList}">
						 <c:forEach var="list" items="${youtubeList}" varStatus="count">
						<tr>
							<td>${count.count}</td>
							<td>https://www.youtube.com/embed/<input id="Youtubeurl" type="text" name="txtYoutubecode" value="${list.youTubeCode}"/></td>
							<td><a href="javascript:void(0);" class="remCF" onclick="removeRow(this)">Remove</a></td>
						</tr>
						</c:forEach>
					</c:if>
				   </tbody>
			</table>
			<div id="addYouTubeId" class="ui tiny green button">Submit</div>
			<input type="hidden" name="txtContentId" value="${contentId}"/>
		</form>
		<br><br>
		<c:if test="${not empty youtubeList}">
		<div class="ui three stackable cards">
		 <c:forEach var="list" items="${youtubeList}" varStatus="count">
			  <div class="card">
			    <div class="ui embed" data-url="${list.youTubeUrl}" ></div>
			  </div>
		</c:forEach>
		</div>
		</c:if>
	</div>
	<div class="three wide column"></div>
</div>

</body>
</html>