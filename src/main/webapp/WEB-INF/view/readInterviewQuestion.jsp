<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>

<head>
<meta name="description" content="${content.interviewQuestion}"/>
<meta name="keywords" content="${content.interviewQuestion}"/>

<jsp:include page="/WEB-INF/view/newmenu.jsp"></jsp:include>
<jsp:include page="/WEB-INF/view/prettycode.jsp"></jsp:include>

<script>


	$(function() {
		
		var count = 1;
		$(".tabular.menu .item").tab();
		$('.ui .attached .embed').embed();
		$("div[test='CodeDivClass']").each(function() {
			$('#' + $(this).attr("id")).find("pre").addClass("brush: java");
		});	
		
	});
	
	
</script>
</head>
<body>
<div class="ui grid">
	<!-- <div id="loadingDiv" class="ui active inverted dimmer">
		    <div class="ui large text loader">Loading</div>
	</div> -->
		 
	<div class="one wide column"></div>
	<div class="eleven wide column">
		<input type="hidden" name="contentId" value="${content.pk}" id="topicId"/>
		
		<div class="ui top attached tabular menu">
		  <div class="active item" data-tab="BlogTab"><i class="write blue icon"></i>Blog</div>
		  <div class="item" data-tab="YoutubeTab"><i class="youtube red icon"></i>Youtube</div>
		  <div class="item" data-tab="RelatedContentTab"><i class="info green icon"></i>Related Content</div>
		</div>
		
		<div id="BlogTabId" class="ui bottom attached active tab segment" data-tab="BlogTab">
		<form action="viewTopics" method="post">
			<h1>${content.interviewQuestion}</h1>
			<c:forEach items="${content.answerDetails}" var="item"
				varStatus="count">
				<c:if test="${item.addMoreType == 'Image' }">
					<p>
						<img class="ui fluid image" src="${awsEndPoint}${item.longDescId}" alt="${content.interviewQuestion}" title="${content.interviewQuestion}"/>
					</p>
				</c:if>
				<c:if test="${item.addMoreType == 'File' }">
					<p>
						<a href="${awsEndPoint}${item.longDescId}" target="_blank"><img
							src="images/Download.png" height="80" width="80"
							alt="Download Now !!"></a>
					</p>
					<h2>Download File</h2>
				</c:if>
				<c:if test="${item.addMoreType == 'Video' }">
					<p>
						<video width="500" height="300" controls>
							<source src="${awsEndPoint}${item.longDescId}" alt="${content.interviewQuestion}" title="${content.interviewQuestion}" type="video/mp4">
						</video>
					</p>
				</c:if>
				<c:if test="${item.addMoreType == 'Text' }">
					<p>${item.longDescId}</p>
				</c:if>
				<c:if test="${item.addMoreType == 'Code' }">
					<div test="CodeDivClass" id="CodeDiv${count.index}">${item.longDescId}</div>
					<%-- <pre class="brush: java;">${item.longDescId}</pre> --%>
				</c:if>
			</c:forEach>
			</form>
		</div>
			
		<div id="YoutubeTabId" class="ui bottom attached tab segment" data-tab="YoutubeTab">
			<c:if test="${not empty youtubeList}">
			<div class="ui three stackable cards">
			 <c:forEach var="list" items="${youtubeList}" varStatus="count">
				  <div class="card">
				    <div class="ui embed" data-url="${list.youTubeUrl}" ></div>
				  </div>
			</c:forEach>
			</div>
			</c:if>
			
			<c:if test="${empty youtubeList}">
			<div class="ui small orange message">
			  <div class="header">
			    No Data Found
			  </div>
			  <p>Currently, No Data found. We will map the content soon for you.</p>
			  <p>Thank You !! Enjoy Reading.</p>
			</div>
			</c:if>
		</div>
			
		<div id="RelatedContentTabId" class="ui bottom attached tab segment" data-tab="RelatedContentTab">
		<c:if test="${not empty relatedTagContentIds}">
			<ul>
				<c:forEach items="${relatedTagContentIds}" var="item" varStatus="count">
					<li><a href="readTopic.html?id=${item.contentId}&title=${item.titleId}">${item.titleId}</a></li>
				</c:forEach>
			</ul>
		</c:if>
		
		<c:if test="${empty relatedTagContentIds}">
			<div class="ui small orange message">
			  <div class="header">
			    No Data Found
			  </div>
			  <p>Currently, No Data found. We will map the content soon for you.</p>
			  <p>Thank You !! Enjoy Reading.</p>
			</div>
		</c:if>
		</div>
		
		<br><br>
	    	
		<div class="ui comments" id="topicComments">
		</div>
		
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
		
		<form action="viewTopics" method="post">
			<div class="ui icon input">
			  <input type="text" id="searchKeywordTXT" onkeypress="searchOnEnter(event)" name="searchKeyword" placeholder="Search..." >
			  <i class="inverted circular search link icon"></i>
			  <input type="hidden" name="searchKeyword" id="searchKeywordHidden" value="${searchKeyword}" />
			</div>
		</form>
		
		<div class="ui card">
		  <div class="content">
		    <div class="header"><i class="book blue icon"></i>Authors</div>
		    <div class="meta">
		      <span class="category">Developers</span>
		    </div>
		    <div class="description">
		      <p>We are continuously working to give you best content and Videos related to Topics.</p>
		      <p>All Content will on single platform.</p>
		    </div>
		  </div>
		  <div class="extra content">
		    	 <div class="center floated author">
			      <img class="ui avatar image" src="images/Piyush.jpg"> Piyush Vij &nbsp;&nbsp;&nbsp;
			      <img class="ui avatar image" src="images/Vivek.jpg"> Vivek Tripathi
		    </div>
			</div>
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
	</div>
</body>
</html>
