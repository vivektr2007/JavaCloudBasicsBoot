<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="/WEB-INF/view/adminMenu.jsp"></jsp:include>
<head>
<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
<script>
	$(function() {
		
		tinymce.init({
			selector : 'textarea'
		});
		
		getSubMenu();

		var $table = $('#contentTabId'), counter = 1;

		$("#addMoreId")
				.click(
						function(event) {
							event.preventDefault();
							counter++;
							var newRow = '<tr>';
							var addMoreType = $("#addMoreType").val();
							if (addMoreType == 'Image') {
								newRow = newRow
										+ '<td><select class="ui dropdown" id="moreTypeId" name="moreTypeId">'
										+ '<option value="Image">Image</option>'
										+ '</select></td>'
										+ '<td><input type="file" name="file"/><input type="hidden" name="longDescId"/></td>'
							} else if (addMoreType == 'File') {
								newRow = newRow
										+ '<td><select class="ui dropdown" id="moreTypeId" name="moreTypeId">'
										+ '<option value="File">File</option>'
										+ '</select></td>'
										+ '<td><input type="file" name="file"/><input type="hidden" name="longDescId"/></td>'
							} else if (addMoreType == 'Video') {
								newRow = newRow
										+ '<td><select class="ui dropdown" id="moreTypeId" name="moreTypeId">'
										+ '<option value="Video">Video</option>'
										+ '</select></td>'
										+ '<td><input type="file" name="file"/><input type="hidden" name="longDescId"/></td>'
							} else if (addMoreType == 'Code') {
								newRow = newRow
										+ '<td><select id="moreTypeId" name="moreTypeId">'
										+ '<option value="Code">Code</option>'
										+ '</select></td>'
										+ '<td><textarea id="longDescId'+counter+'" name="longDescId" rows="5" cols="40"></textarea></td>'
							} else {
								newRow = newRow
										+ '<td><select id="moreTypeId" name="moreTypeId">'
										+ '<option value="Text">Text</option>'
										+ '</select></td>'
										+ '<td><textarea id="longDescId'+counter+'" name="longDescId" rows="5" cols="40"></textarea></td>'
							}

							newRow = newRow
									+ '<td><input style="width: 50px" type="text" id="orderId'+counter+'" class="orderId" name="orderId"></td>'
									+ '<td><a href="javascript:void(0);" class="remCF" onclick="removeRow(this)">Remove</a></td>'
									+ '</tr>';
							$table.append(newRow);
							tinyMCE.execCommand('mceAddEditor', false,
									'longDescId' + counter);
						});
		$("#submit").click(function() {
			var keys = [];
			var flag = true;
			$(".orderId").each(function() {
				var value = $(this).val();
				
				if(value== null || value==''){
					alert("Order can not be Empty");
					flag = false;
				}
				var found = $.inArray(value, keys) > -1;
				if (found == true || found == 'true') {
					alert("Order can not be duplicate.");
					flag = false;
				}
				keys.push(value);
			});
			if (!flag) {
				return false;
			}
		});
	});

	function removeRow(obj) {
		$(obj).parent().parent().remove();
	}

	function getSubMenu() {

		$.ajax({
			type : "POST",
			url : "getSubMenu.html?menuId=" + $("#menuId").val(), //Relative or absolute path to response.php file
			data : "",
			success : function(data) {
				var parsedJson = $.parseJSON(data);
				var options = "<option value='-1' >select</option>";
				$.each(parsedJson, function(k, v) {
					if($("#subMenuIdHidden").val() == k){
						options = options + "<option selected='selected' value='"+k+"'>" + v
							+ " </option>"
					}
					else{
						options = options + "<option value='"+k+"'>" + v
						+ " </option>"
					}
				});
				$("#subMenuId").html(options);
			}
		});

	}
	
	$(document).on('click', '#editBlogId', function () {
		
		if($("#menuId").val().length<=0){
			alert("Select Menu");
			return;
		}else if($("#subMenuId").val().length<=0){
			alert("Select Sub Menu");
			return;
		}else if($("#titleId").val().length<=0){
			alert("Enter title");
			return;
		}else if($("#tags").val().length<=0){
			alert("Enter Tags");
			return;
		}else if($("#shortDescId").val().length<=0){
			alert("Enter Short Description");
			return;
		}
		$("#editBlogFormId").submit();
		
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
			  <div class="active item" data-tab="editBlogTab">Edit Blog</div>
		</div>
		
		<div class="ui bottom attached active tab segment" data-tab="editBlogTab">
		
		<form class="demo" action="editSaveBlog.html" method="post"	id="editBlogFormId" enctype="multipart/form-data">
		<table id="contentTabId" class="ui very basic table">
		  <thead>
		    <tr>
		      <th>Action</th>
		      <th>Values</th>
		   </tr>
		  </thead>
		<tbody>
			<tr>
			<td><div class="ui teal medium label">Menu</div></td>
			<td>
			<input type="hidden" name="subMenuIdHidden" id="subMenuIdHidden" value="${formModel.subMenuId}"/>
			
			<select class="ui dropdown" id="menuId" name="menuId"
				onchange="getSubMenu()">
					<option>select</option>
					<c:forEach var="par" items="${parent}">
						<c:if test="${par.key==formModel.parent}">
							<option selected="selected" value="${par.key}" label="${par.value}"></option>
						</c:if>
						<c:if test="${par.key!=formModel.parent}">
							<option value="${par.key}" label="${par.value}"></option>
						</c:if>
					</c:forEach>
			</select></td>
			</tr>
			<tr>
				<td><div class="ui teal medium label">Sub Menu</div></td>
				<td><select class="ui dropdown" id="subMenuId" name="subMenuId">
						<option value="Threads">Select</option>
				</select></td>
			</tr>
			<tr>
				<td><div class="ui teal medium label">Title</div></td>
				<td><input type="text" id="titleId" name="titleId" value="${formModel.titleId}"></td>

			</tr>
			<tr>
				<td><div class="ui teal medium label">Tags*</div></td>
				<td><input type="text" id="tags" name="tags" value="${formModel.tags}">Please  only Put tags " ; " Seprated. E.g. Java;Spring;Cloud</td>
			</tr>
			<tr>
				<td><div class="ui teal medium label">Short Desc</div></td>
				<td style="width: 1000px; height: 200px"><textarea
						id="shortDescId" name="shortDescId" rows="5" cols="40">${formModel.shortDescId}</textarea>
			</tr>
			<tr>
				<td><select class="ui dropdown" id="addMoreType"
					name="addMoreType">
						<option value="Text">Text</option>
						<option value="Image">Image</option>
						<option value="Video">Video</option>
						<option value="File">File</option>
						<option value="Code">Code</option>
				</select></td>
				<td>
				<!-- <input type="button" id="addMoreId" name="addMoreId" ><i class="icon add"></i>Add -->
				<div id="addMoreId" class="ui tiny orange button"><i class="icon add"></i>Add</div>
				</td>
				
			</tr>
			<tr>
				<td><div class="ui teal medium label" data-tooltip="Long Description information" data-position="top left">Long Desc*</div></td>
				<td><div class="ui teal medium label" data-tooltip="Type Description information" data-position="top left">Type*</div></td>
			</tr>

			<c:forEach items="${formModelList}" var="item" varStatus="count">
				<tr>
					
					<td><select class="ui dropdown" id="moreTypeId"	name="moreTypeId">
							<option value="${item.addMoreType}">${item.addMoreType}</option>
					</select></td>
					
					<c:if test="${item.addMoreType == 'Image' }">
						
						<td><input type="text" id="orderId"	class="orderId" name="orderId" value="${item.orderId}"><br>
						<input type="text" value="${item.longDescId}" name="filePathTxt" readonly="readonly"/></td>
					</c:if>

					<c:if test="${item.addMoreType == 'File' }">
						<td><input type="text" id="orderId"	class="orderId" name="orderId" value="${item.orderId}"><br>
						<input type="text" value="${item.longDescId}" name="filePathTxt" readonly="readonly"/></td>
					</c:if>

					<c:if test="${item.addMoreType == 'Video' }">
						<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId"	class="orderId" name="orderId" value="${item.orderId}"><br>
						<input type="text" value="${item.longDescId}" name="filePathTxt" readonly="readonly"/></td>
					</c:if>

					<c:if test="${item.addMoreType == 'Text' }">
						<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId"	class="orderId" name="orderId" value="${item.orderId}"><br>
						<textarea id="longDescIdx${count.index}" name="longDescId" rows="5" cols="40">${item.longDescId}</textarea>
					</c:if>

					<c:if test="${item.addMoreType == 'Code' }">
						<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId"	class="orderId" name="orderId" value="${item.orderId}"><br>
						<textarea id="longDescIdy${count.index}" name="longDescId" rows="5" cols="40">${item.longDescId}</textarea>
					</c:if>

					<td><a href="javascript:void(0);" class="remCF" onclick="removeRow(this)">Remove</a></td>
					
					<c:if test="${item.addMoreType == 'Code' }">
						<td>Note: Select All --> Format --> Blocks --> Pre</td>
					</c:if>
				</tr>
			</c:forEach>
   		</tbody>
   		</table>
   		
			<input type="hidden" value="${formModel.contentId}" id="contentId" name="contentId"/>	
			<div id="editBlogId" class="ui tiny green button">Save</div>
		</form>
			
		</div>
	</div>
	
	<div class="one wide column"></div>
</div>
		
</body>
</html>
