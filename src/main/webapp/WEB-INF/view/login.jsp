<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
<link rel="icon" href="images/favicon.ico" type="image/x-icon">
<head>
  <!-- Standard Meta -->
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

  <!-- Site Properties -->
  <title>Login - javacloudbasics</title>
  <link rel="stylesheet" type="text/css" href="semantic/components/reset.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/site.css">

  <link rel="stylesheet" type="text/css" href="semantic/components/container.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/grid.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/header.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/image.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/menu.css">

  <link rel="stylesheet" type="text/css" href="semantic/components/divider.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/segment.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/form.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/input.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/button.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/list.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/message.css">
  <link rel="stylesheet" type="text/css" href="semantic/components/icon.css">

<script src="https://code.jquery.com/jquery-3.1.1.min.js" integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8=" crossorigin="anonymous"></script> 
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="semantic/semantic.min.css">
  <script src="semantic/semantic.min.js"></script>
  <script src="semantic/components/form.js"></script>
  <script src="semantic/components/transition.js"></script>
  <script>
  $(function(){
	  
	  $(window).load(function() {
			
			var selectorVal = $('#loginStatus').val();
			
			if(selectorVal == "Pass"){
				$("#loginErrId").hide();	
			}else if(selectorVal == "Error"){
				$("#loginErrId").show();	
			}
			
		});
	  
	  $('.message .close')
	  .on('click', function() {
	    $(this)
	      .closest('.message')
	      .transition('fade')
	    ;
	  })
	;
  });
  </script>

  <style type="text/css">
    body {
      background-color: white;
    }
    body > .grid {
      height: 100%;
    }
    .image {
      margin-top: -100px;
    }
    .column {
      max-width: 450px;
    }
  </style>
  <script>
  $(document)
    .ready(function() {
      $('.ui.form')
        .form({
          fields: {
            email: {
              identifier  : 'email',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter your e-mail'
                },
                {
                  type   : 'email',
                  prompt : 'Please enter a valid e-mail'
                }
              ]
            },
            password: {
              identifier  : 'password',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter your password'
                }
              ]
            }
          }
        })
      ;
    })
  ;
  </script>
</head>
<body>

<div class="ui middle aligned center aligned grid">
  <div class="column">
    <h2 class="ui orange image header">
      <!--<img src="images/Kronos_Logo.png" class="image">-->
      <div class="content">
        Log-in to your account
      </div>
    </h2>
    <form class="ui large form" action="login.html" method="POST">
      <div class="ui stacked segment">
        <div class="field">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input type="text" name="userId" placeholder="UserId like john.capri"/>
            <input type="hidden" name="from" value="${from }" />
          </div>
        </div>
        <div class="field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="password" placeholder="Password">
          </div>
        </div>
        <!-- <div class="ui fluid large green submit button">Login</div> -->
        <button id="generateBtn" class="fluid positive ui button">Log-in</button>
      </div>

    </form>
	
	<!-- <div class="ui floating message">
		<p>*For best view use Internet Explorer 8.0 or higher</p>
	</div> -->
  </div>

</div>
</body>
</html>
