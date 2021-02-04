<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="/WEB-INF/view/adminMenu.jsp"></jsp:include>
<head>
<script>
$(document).on('click', '#addMenuId', function () {
	$("#addMenuFormId").submit();
});

function getSubMenu() {

	$.ajax({
		type : "POST",
		url : "getSubMenu.html?menuId=" + $("#menuId").val(), //Relative or absolute path to response.php file
		data : "",
		success : function(data) {
			var parsedJson = $.parseJSON(data);
			var options = "<option value='-1' >select</option>";
			$.each(parsedJson, function(k, v) {
				options = options + "<option value='"+k+"'>" + v
						+ " </option>"
			});
			$("#subMenuId").html(options);
		}
	});

}

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
			  <div class="active item" data-tab="addMenuTab">Add Menu</div>
		</div>
		<div class="ui bottom attached active tab segment" data-tab="addMenuTab">
				<form action="addMenu.html" method="post" id="addMenuFormId">
			
			<table id="contentTabId" class="ui very basic table">
				  <thead>
				    <tr>
				      	<th><div class="ui orange medium label">Action</div></th>
						<th><div class="ui orange medium label">Value</div></th>
						<th></th>
				   </tr>
				   </thead>
				   <tbody>
				   	<tr>
						<td><div class="ui teal medium label">Existing Menu</div></td>
						<td><select class="ui dropdown" id="menuId" name="menuId"
								onchange="getSubMenu()">
									<option>Select Menu</option>
									<c:forEach var="par" items="${parent}">
										<option value="${par.key}" label="${par.value}"></option>
									</c:forEach>
							</select></td>
						<td><select class="ui dropdown" id="subMenuId" name="subMenuId">
									<option value="">Select Sub Menu</option>
							</select></td>
					</tr>
				   	<tr>
						<td><div class="ui teal medium label">Parent</div></td>
						<td><input type="text" name="parent"/></td>
						<td></td>
					</tr>
					<tr>
						<td><div class="ui teal medium label">Sub Menu</div></td>
						<td><input type="text" name="subMenu"/></td>
						<td></td>
					</tr>
					<tr>
						<td><div class="ui teal medium label">Target</div></td>
						<td><select name="target">
							<option value="_self">Same Tab</option>
							<option value="_blank">New Tab</option>
						</select></td>
						<td></td>
					</tr>
					<tr>
						<td><div class="ui teal medium label">Visible</div></td>
						<td><select name="isVisible">
							<option value="Y">Yes</option>
							<option value="N">No</option>
						</select></td>
						<td></td>
					</tr>
				   </tbody>
			</table>
			<div id="addMenuId" class="ui tiny green button">Add</div>
		</form>
		</div>
	</div>
	<div class="three wide column"></div>
</div>
</body>
</html>
