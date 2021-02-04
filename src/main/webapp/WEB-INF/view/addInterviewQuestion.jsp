<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="/WEB-INF/view/adminMenu.jsp"></jsp:include>
<head>
<script>
	$(function() {
		
		tinymce.init({
			selector : 'textarea'
		});
		
		var $table = $('#contentTabId'), counter = 1;
		$("#addMoreId").click(function(event) {
			event.preventDefault();
			counter++;
			var newRow = '<tr>';
			var addMoreType = $("#addMoreType").val();
			if (addMoreType == 'Image') {
				newRow = newRow
						+ '<td><select class="ui dropdown" id="moreTypeId" name="moreTypeId">'
						+ '<option value="Image">Image</option>'
						+ '</select></td>'
						+ '<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId'+counter+'" class="orderId" name="orderId"><br>'
						+ '<input type="file" name="file"/><input type="hidden" name="longDescId"/></td>'
			} else if (addMoreType == 'File') {
				newRow = newRow
						+ '<td><select class="ui dropdown" id="moreTypeId" name="moreTypeId">'
						+ '<option value="File">File</option>'
						+ '</select></td>'
						+ '<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId'+counter+'" class="orderId" name="orderId"><br>'
						+ '<input type="file" name="file"/><input type="hidden" name="longDescId"/></td>'
			} else if (addMoreType == 'Video') {
				newRow = newRow
						+ '<td><select class="ui dropdown" id="moreTypeId" name="moreTypeId">'
						+ '<option value="Video">Video</option>'
						+ '</select></td>'
						+ '<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId'+counter+'" class="orderId" name="orderId"><br>'
						+ '<input type="file" name="file"/><input type="hidden" name="longDescId"/></td>'
			} else if (addMoreType == 'Code') {
						newRow = newRow
						+ '<td><select id="moreTypeId" name="moreTypeId">'
						+ '<option value="Code">Code</option>'
						+ '</select></td>'
						+ '<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId'+counter+'" class="orderId" name="orderId"><br>'
						+ '<textarea id="longDescId'+counter+'" name="longDescId" rows="5" cols="40"></textarea></td>'
			} else {
						newRow = newRow
						+ '<td><select id="moreTypeId" name="moreTypeId">'
						+ '<option value="Text">Text</option>'
						+ '</select></td>'
						+ '<td><div class="ui teal medium label">Order*</div><input type="text" id="orderId'+counter+'" class="orderId" name="orderId"><br>'
						+ '<textarea id="longDescId'+counter+'" name="longDescId" rows="5" cols="40"></textarea></td>'
			}

			newRow = newRow
					+ '<td><a href="javascript:void(0);" class="remCF" onclick="removeRow(this)">Remove</a></td>'
					+ '</tr>';
			$table.append(newRow);
			tinyMCE.execCommand('mceAddEditor', false, 'longDescId'+counter);
		});
		$("#submit").click(function() {
			var keys = [];
			var flag = true;
			$(".orderId").each(function() {
				var value = $(this).val();
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
					options = options + "<option value='"+k+"'>" + v
							+ " </option>"
				});
				$("#subMenuId").html(options);
			}
		});

	}
	
	$(document).on('click', '#createBlogId', function () {
		tinyMCE.triggerSave();
		if($("#company_name").val().length<=0){
			alert("Enter title");
			return;
		}else if($("#interview_question").val().length<=0){
			alert("Enter Tags");
			return;
		}else if($("#shortDescId").val().length<=0){
			alert("Enter Short Description");
			return;
		}
		$("#addBlogFormId").submit();
		
	});
</script>
</head>

<body>
	<div class="ui grid">
	
	<div id="loadingDiv" class="ui active inverted dimmer">
		    <div class="ui large text loader">Loading</div>
	</div>
		 
	<div class="one wide column"></div>
	<div class="fourteen wide column">
	
	<div class="ui top attached tabular menu">
		  <div class="active item" data-tab="addBlogTab">Add Interview Question</div>
	</div>
	<form class="demo" action="addInterviewQuestionSubmit.html" method="post" id="addBlogFormId" enctype="multipart/form-data">
	<div class="ui bottom attached active tab segment" data-tab="addBlogTab">
		
				<table id="contentTabId" class="ui very basic table">
				  <thead>
				    <tr>
				      <th>Action</th>
				      <th>Values</th>
				   </tr>
				   </thead>
				   <tbody>
				   		<tr>
							<td><div class="ui teal medium label" data-tooltip="Menu information" data-position="top left">Company Name</div></td>
							<td><input type="text" id="company_name" name="company_name"/></td>
						</tr>
						<tr>
							<td><div class="ui teal medium label" data-tooltip="Menu information" data-position="top left">Question</div></td>
							<td><input type="text" id="interview_question" name="interview_question"/></td>
						</tr>
						<tr>
							<td><div class="ui teal medium label" data-tooltip="Short information upto 3-4 lines" data-position="top left">Short Desc*</div></td>
							<td><textarea	id="shortDescId" name="shortDescId" rows="5" cols="80"></textarea>
							
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
							<!-- <input type="button" id="addMoreId" name="addMoreId" value="Add More"> -->
							<div id="addMoreId" class="ui tiny orange button"><i class="icon add"></i>Add</div>
							</td>
						</tr>
						<tr>
							<td><div class="ui teal medium label" data-tooltip="Long Description information" data-position="top left">Long Desc*</div></td>
							<td>
								<div class="ui teal medium label" data-tooltip="Type Description information" data-position="top left">Type*</div>
							</td>
						</tr>
						<tr>
							<td><select class="ui dropdown" id="moreTypeId"	name="moreTypeId">
									<option value="Text">Text</option>
							</select></td>
							<td>
								<div class="ui teal medium label">Order*</div><input type="text" id="orderId" class="orderId" name="orderId">
								<textarea id="longDescId" name="longDescId" rows="5" cols="80"></textarea>
								<!-- <button name="Click"></button> -->
							</td>
						</tr>
				    </tbody>
					</table>
					<div id="createBlogId" class="ui tiny green button">Save</div>
					<!-- <input class="submitbutton" type="submit" id="submit" value="submit" > -->
				</div>
				</form>
			</div>
		
	</div>
	
	<div class="one wide column"></div>
	<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	
</body>
</html>
