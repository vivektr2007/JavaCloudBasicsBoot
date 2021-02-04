<%@page import="java.util.Iterator"%>
<%@page import="com.pixel.model.MenuBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.pixel.blog.serviceImpl.MenuLoader"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<head>
<meta charset="utf-8">
<title>Java Cloud Basics</title>
</head>
<script src="${pageContext.request.contextPath }/semantic/jquery-3.3.1.min.js"></script>
<link href="${pageContext.request.contextPath }/semantic/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

<script src="${pageContext.request.contextPath }/semantic/bootstrap.min.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	getBlogCount();
	getTotalViews();
	
	var count = 1000;
	
	$(document).on('click', '.reply', function() {
		count=parseInt(count)+1;
		var commentId = $(this).attr("commentId");
		var replyBoxId = "reply"+count;
	    var replyVar = '<form class="ui form">'+
	   	'<div class="field">'+
	      '<textarea name="comment" id="'+replyBoxId+'" rows="3" cols="10"></textarea>'+
	    '</div>'+
	    '<div class="ui blue labeled submit icon button" onclick="addReply(\''+replyBoxId+'\',\''+commentId+'\')" >'+
	      '<i class="icon edit"></i> Add Reply'+
	    '</div>'+
	  '</form>';
	    $(this).parents(".content").append(replyVar);
	    $(this).hide();
	});
});

function addReply(replyBoxId, commentId){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"checkLogin.html",
		data : "",
		success : function(data) {
			if($.trim(data) == 'notLoggedIn'){
				$("#from").val("reply");
				$("#replyBoxIdHidden").val(replyBoxId);
				$("#commentIdHidden").val(commentId);
				$("#loginModal").css("display","block");
			}
			else{
				submitReply(replyBoxId, commentId);
			}
		}
	});
}

function submitReply(replyBoxId, commentId){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"postReply.html", 
		data : {"reply":$("#"+replyBoxId).val(), "commentId":commentId},
		success : function(data) {
			loadContent();
		}
	});
}

function loadContent(){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"getBlogComment.json?topicId=" + $("#topicId").val(),
		data : "",
		success : function(data) {
			var comments = '<h3 class="ui dividing header">Comments</h3>';
			
			$.each(data, function(index, value){
				comments = comments  + '<div class="comment">'+
			    	'<a class="avatar">'+
			      		'<img src="images/avatar/elliot.jpg">'+
			    	'</a>'+
			    	'<div class="content">'+
			      		'<a class="author">'+value.commentBy+'</a>'+
			      		'<div class="metadata">'+
			        		'<span class="date">'+value.commentDate+'</span>'+
			        	'</div>'+
			      		'<div class="text">'+
			        		'<p>'+value.commentDesc+'</p>'+
			      		'</div>'+
			      		'<div class="actions">'+
			        		'<a class="reply" commentId="'+value.seq+'">Reply</a>'+
			      		'</div>'+
			    	'</div>';
			    	$.each(value.replyBeanList, function(innerindex, innerItem){
			    		comments = comments  + '<div class="comments">'+
				      		'<div class="comment">'+
				        		'<a class="avatar">'+
				          			'<img src="images/avatar/jenny.jpg">'+
				        		'</a>'+
				        		'<div class="content">'+
				          			'<a class="author">'+innerItem.replyBy+'</a>'+
				          			'<div class="metadata">'+
				            			'<span class="date">'+innerItem.replyDate+'</span>'+
				          			'</div>'+
				          			'<div class="text">'+
				          			innerItem.replyDesc
				          				+
				          			'</div>'+
				          			/* '<div class="actions" >'+
				            			'<a class="reply">Reply</a>'+
				          			'</div>'+
				          			 */
				        		'</div>'+
				      		'</div>'+
				    	'</div>';
			    	})
			    	
			  	comments = comments  + '</div>';
			});
			  
			comments = comments  + '<form class="ui form">'+
			   	'<div class="field">'+
			      '<textarea name="comment" id="comment"></textarea>'+
			    '</div>'+
			    '<div class="ui blue labeled submit icon button" onclick="addComment()" >'+
			      '<i class="icon edit"></i> Add Comment'+
			    '</div>'+
			  '</form>';
			$("#topicComments").html(comments);
		}
	});
}
function addComment(){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"checkLogin.html",
		data : "",
		success : function(data) {
			if($.trim(data) == 'notLoggedIn'){
				
				$("#from").val("comment");
				$("#loginModal").css("display","block");
			}
			else{
				submitComment();
			}
		}
	});
}

function submitComment(){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"postComment.html", 
		data : {"comment":$("#comment").val(), "topicId":$("#topicId").val()},
		success : function(data) {
			loadContent();
		}
	});
}


function updateContentLike(contentId){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"checkLogin.html",
		data : "",
		success : function(data) {
			$("#commentIdHidden").val(contentId);
			if($.trim(data) == 'notLoggedIn'){
				$("#from").val("like");
				$('#loginModal').modal('show');
				/* $("#loginModal").css("display","block"); */
			}
			else{
				submitLike();
			}
		}
	});
	
}

function submitLike(){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"submitLike.html",
		data : {"contentId" : $("#commentIdHidden").val()},
		success : function(data) {
			location.reload();
		}
	});
}

function getBlogCount(){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"getBlogCount.html",
		data : "",
		success : function(data) {
			$("#blogCountDiv").html(data);
		}
	});
}

function getTotalViews(){
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"getTotalViews.html",
		data : "",
		success : function(data) {
			$("#totalViewsDiv").html(data);
		}
	});
}

</script>


<style>

.mega-dropdown {
  position: static !important;
}
.mega-dropdown-menu {
    padding: 20px 0px;
    width: 100%;
    box-shadow: none;
    -webkit-box-shadow: none;
}
.mega-dropdown-menu > li > ul {
  padding: 0;
  margin: 0;
}
.mega-dropdown-menu > li > ul > li {
  list-style: none;
}
.mega-dropdown-menu > li > ul > li > a {
  display: block;
 color:#737476;
  padding: 3px 5px;
}
.mega-dropdown-menu > li ul > li > a:hover,
.mega-dropdown-menu > li ul > li > a:focus {
  text-decoration: none;
}
.mega-dropdown-menu .dropdown-header {
  font-size: 18px;
  color: #000;
  padding: 5px 60px 5px 5px;
  line-height: 30px;
}


.navbar {
    position: relative;
    min-height: 0px !important;
    margin-bottom: 0px !important;
}
.dropdown-menu{
width:1350px !important;
float:left;
}
.colum-1{
float:left;
width:250px;
padding:0px 0px 0px 20px;
min-height:200px;
border-right:1px solid #ddd;

}
.navbar{
border:none !important;
}
.mega-dropdown-menu{
padding:0px !important;
}
.navbar-nav > li > a{
padding:10px 10px !important;
border-right:1px solid #ddd;
color:#000;
}
.navbar-nav > li > a:hover{
	border-right:1px solid #ddd !important;
}
</style>

<!-- <script src="js/jquery-3.1.1.min.js"></script>
<script src="js/jquery-1.12.4.js"></script> -->
<script src="${pageContext.request.contextPath }/js/jquery-ui-1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/semantic/semantic.min.css">
<script src="${pageContext.request.contextPath }/semantic/semantic.min.js"></script>
<script src="${pageContext.request.contextPath }/semantic/components/transition.js"></script>
<script src="${pageContext.request.contextPath }/js/common.js"></script>
<script>
$(function() {
 	 $('#browseId').popup({
	    inline     : true,
	    hoverable  : false,
	    position   : 'bottom left',
	    delay: {
	      show: 300,
	      hide: 5000
	    }
	  });
 	
	 $(".ui .labeled .tiny .ui .red .button").click(function(){
		$('#loginModal').modal('show');
		
	});
	 
	$("#signInId").click(function(){
		$('#loginModal').modal('show');
		
	});
	
	$("#signUpId").click(function(){
		$('#signUpModal').modal('show');
		
	});
	
	$("#contactId").click(function(){
		$('#contactModal').modal('show');
		
	});
	
	$("#questionDayId").click(function(){
		
		$.ajax({
			type : "POST",
			url : $("#contextPath").val()+"getQuestionOfTheDay.html",
			success : function(data) {
				$("#questionOfTheDayModel").html(data);
				$('#questionOfTheDayModel').modal('show');
			}
		});
	});
	
	$("#interviewId").click(function(){
		$('#CommonModalId').modal('show');
		
	});
	
	$("#registerButton").click(function(){
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		var userName = $("#userName").val();
		var password = $("#password").val();
		
		$.ajax({
			type : "POST",
			url : $("#contextPath").val()+"checkUserIdExists.html",
			data : {"userId":userName},
			success : function(data) {
				if($.trim(data) == 'Exists'){
					$("#registerErrId").css("display","block");
				}
				else{
					$.ajax({
						type : "POST",
						url : $("#contextPath").val()+"RegisterUser.html",
						data : {"userName":userName, "password":password, "firstName":firstName, "lastName":lastName},
						success : function(data) {
							location.reload();
						}
					});
				}
			}
		});
		
		
	});
	
$("#loginBtn").click(function(){
		
		var userId = $("#userId").val();
		var password = $("#passwordLogin").val();
		
		$.ajax({
			type : "POST",
			url : $("#contextPath").val()+"UserLogin.html",
			data : {"userId":userId, "password":password},
			success : function(data) {
				if($.trim(data) == 'success'){
					var from = $("#from").val();
					if(from == 'reply'){
						submitReply($("#replyBoxIdHidden").val(), $("#commentIdHidden").val());
					}
					else if(from == 'comment'){
						submitComment();
					}
					else if(from == 'like'){
						submitLike();
					}
					
					$("#loginModal").css("display","none");
					location.reload();
				}
				else{
					$('#loginModal').modal('show');
					$("#loginErrId").css("display","block");
				}
			}
		});
		
	});
	
});

function checkUserIdExists(){
	var userName = $("#userName").val();
	$.ajax({
		type : "POST",
		url : $("#contextPath").val()+"checkUserIdExists.html",
		data : {"userId":userName},
		success : function(data) {
			if($.trim(data) == 'Exists'){
				$("#registerErrId").css("display","block");
			}
			else{
				$("#registerErrId").css("display","none");
			}
		}
	});
}
</script>

<div class="ui menu">
	<nav class="navbar">

		<div class="collapse navbar-collapse js-navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="dropdown mega-dropdown"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <i
						class="cubes blue icon"></i> Menu<span class="caret"></span></a>
					<ul class="dropdown-menu mega-dropdown-menu">
						<%
				Map<String, List<MenuBean>> menus = MenuLoader.menus;
				Iterator<Map.Entry<String, List<MenuBean>>> itr = menus.entrySet().iterator();
				while (itr.hasNext()) {
					Map.Entry<String, List<MenuBean>> me = itr.next();
			%>		
						<li class="colum-1">
							<ul>
								<li class="dropdown-header"><%=me.getKey()%></li>
								
								<%
									List<MenuBean> list = me.getValue();
										Iterator<MenuBean> itrList = list.iterator();
										while (itrList.hasNext()) {
											MenuBean mbean = itrList.next();
								%>
								
								<li><a href="${pageContext.request.contextPath }/viewTopics/<%=mbean.getParentUrl() %>/<%=mbean.getMenuDescUrl()%>"><%=mbean.getMenuDesc() %></a></li>
								<%}%>
							</ul>
						</li>
						<%}%>
					</ul></li>

				<li class="dropdown mega-dropdown"><a href="${pageContext.request.contextPath }/homePage.html"> <i
						class="envira gallery green icon"></i> Javacloudbasics.com<span
						class="caret"></span></a></li>
			</ul>

		</div>
		<!-- /.nav-collapse -->
	</nav>
	<div class="right menu" style="float: right !important;">
		<!-- <a class="item" id="" href="http://www.jnbthegurukul.com" target="_blank"> <img src="images/avatar/logo.png" title="JNB The Gurukul" alt="JNB The Gurukul"/></a>
	<a class="item" id="" href="https://www.jnbtechnologies.com/" target="_blank"> <img src="images/avatar/logo_jnb.png" title="JNB Technologies Pvt. Ltd." alt="JNB Technologies Pvt. Ltd."/></a> -->
		<!-- <a class="item" id="" href="#"> &nbsp;</a> -->
		<a class="item" id="questionDayId"> <i class="gift yellow icon"></i>
			Question of Day
		</a> <a class="item" href="${pageContext.request.contextPath }/interviewQuestion.html"> <i
			class="lightning red icon"></i> Interview Questions
		</a> <a class="item" id="contactId"> <i class="mail blue icon"></i>
			Contact Us
		</a> 
		<c:if test="${sessionScope.loggerInUserId != null && sessionScope.loggerInUserId != '' }">
		<a class="item">Welcome, ${sessionScope.loggerInUserName }</a>
		<a class="item" href="${pageContext.request.contextPath }/Logout.html"> <i class="sign in blue icon"></i> Logout</a>
	</c:if>
	<c:if test="${sessionScope.loggerInUserId == null || sessionScope.loggerInUserId == '' }">
		<a class="item" id="signInId"> <i class="sign in blue icon"></i> Sign-In</a>
	</c:if>

	</div>
</div>

<div id="loginModal" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
    Sign-in
  </div>
  <div class="ui segment">
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input type="text"  name="userId" id="userId" placeholder="john.capri@gmail.com"/>
            <input type="hidden" name="from" value="${from}" />
          </div>
        </div><br>
        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="passwordLogin" id="passwordLogin" placeholder="Password">
          </div>
        </div>
        <input type="hidden" name="from" id="from"/>
    	<input type="hidden" name="replyBoxIdHidden" id="replyBoxIdHidden"/>
    	<input type="hidden" name="commentIdHidden" id="commentIdHidden"/>
  </div>
  <div id="loginErrId" class="ui red message" style="display:none;">
		  <i class="close icon"></i>
		  <div class="header">
		    Authentication Error!
		  </div>
		  <p>Please use valid UserId like john.capri or credentials
	</p></div>
  <div class="actions">
    <div class="ui black deny button">
      Cancel
    </div>
    <div class="ui positive right labeled icon button" id="loginBtn">
      Sign-in
      <i class="checkmark icon"></i>
    </div>
  </div>
  <div class="ui bottom attached warning message">
	  <i class="icon help"></i>
	  Not signed up ? <a id="signUpId">Login here</a> instead.
	</div>
</div>

<div id="signUpModal" class="ui modal">
	<div class="ui attached message">
	  <div class="header">
	    Welcome to Javacloudbasics.com
	  </div>
	  <p>Fill out the form below to sign-up for a new account</p>
	</div>
	<form class="ui form attached fluid segment">
	  <div class="two fields">
	    <div class="field">
	      <label>First Name</label>
	      <input placeholder="First Name" name="firstName" id="firstName" type="text">
	    </div>
	    <div class="field">
	      <label>Last Name</label>
	      <input placeholder="Last Name" name="lastName" id="lastName" type="text">
	    </div>
	  </div>
	  <div class="field">
	    <label>Username</label>
	    <input placeholder="Username" name="userName" id="userName" onblur="checkUserIdExists()" type="text"/>
	  </div>
	  <div class="field">
	    <label>Password</label>
	    <input type="password" name="password" id="password">
	  </div>
	  <div id="registerErrId" class="ui red message" style="display:none;">
		  <i class="close icon"></i>
		  <div class="header">
		    Registration Error!
		  </div>
		  <p>User Name already exists. Please take different one.
	</p></div>
	  <div class="inline field">
	    <div class="ui checkbox">
	      <input type="checkbox" id="terms">
	      <label for="terms">I agree to the terms and conditions</label>
	    </div>
	  </div>
	  <div class="ui blue submit button" id="registerButton">Submit</div>
	</form>
</div>

<div id="contactModal" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
    Contact Us
  </div>
  <div class="image content">
    <!-- <div class="ui medium image">
      <img src="/images/avatar/large/chris.jpg">
    </div> -->
    <div class="description">
      <p>If you have come this far, it means that you liked what you are reading. Why not reach little more and connect with me directly on <i class="facebook blue icon"></i><a href="https://www.facebook.com/javacloudbasics" target="_blank">facebook</a>. </p>
      <p>We would love to hear your thoughts and opinions on my articles directly.</p>
      <p>We are trying our best to provide you best content on single platform.</p>
      <p>You can directly mail us at:</p>
      <p><a class="ui image label"><img src="${pageContext.request.contextPath }/images/Vivek.jpg">Vivek Tripathi</a>&nbsp;&nbsp;<i class="mail blue icon"></i>vivektr2007@gmail.com</p>
     <!--  <p><a class="ui image label"><img src="images/Piyush.jpg">Piyush Vij</a>&nbsp;&nbsp;<i class="mail blue icon"></i>piyushvij@gmail.com</p> -->
    </div>
  </div>
  <div class="actions">
    <div class="ui positive right labeled icon button">
      Thank you!
      <i class="checkmark icon"></i>
    </div>
  </div>
</div>

<div id="CommonModalId" class="ui modal">
  <i class="close icon"></i>
  <div class="header">
    We are working on this!!
  </div>
  <div class="image content">
    <!-- <div class="ui medium image">
      <img src="/images/avatar/large/chris.jpg">
    </div> -->
    <div class="description">
      <p>We are working on this for you. We will return shortly. Enjoy reading rest of blogs.</p>
     </div>
  </div>
  <div class="actions">
    <div class="ui positive right labeled icon button">
      Thank you!
      <i class="checkmark icon"></i>
    </div>
  </div>
</div>

<div id="questionOfTheDayModel" class="ui modal">
  
</div>

<input type="hidden" name="contextPath" id="contextPath" value="${pageContext.request.contextPath }/"/>

<script>
$(document).ready(function(){
    $(".dropdown").hover(            
        function() {
            $('.dropdown-menu', this).not('.in .dropdown-menu').stop(true,true).slideDown("400");
            $(this).toggleClass('open');        
        },
        function() {
            $('.dropdown-menu', this).not('.in .dropdown-menu').stop(true,true).slideUp("400");
            $(this).toggleClass('open');       
        }
    );
});
</script>
