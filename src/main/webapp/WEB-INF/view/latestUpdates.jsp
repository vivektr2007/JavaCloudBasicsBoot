<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="content">
	<div class="header">
		<i class="asterisk loading blue icon"></i>Latest Updates
	</div>
	<div class="meta">1 days ago</div>
	<div class="description">
		<div class="ui list">
			<c:forEach items="${newTopicContentList}" var="item"
				varStatus="count">
				<div class="item">
					<div class="content">
						<a
							href="${pageContext.request.contextPath }/contentDetails/${item.titleIdUrl}"><i
							class="checkmark box blue icon"></i>${item.titleId}</a>
					</div>
				</div>
			</c:forEach>
		</div>

	</div>
</div>