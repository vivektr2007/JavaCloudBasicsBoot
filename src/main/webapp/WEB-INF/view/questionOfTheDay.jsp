<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${questionOfTheDay.heading != null && questionOfTheDay.heading != '' }">
	<i class="close icon"></i>
	<div class="header">${questionOfTheDay.heading }</div>
	<div class="image content">
		<!-- <div class="ui medium image">
      <img src="/images/avatar/large/chris.jpg">
    </div> -->
		<div class="description">
			<!-- <p>We are working on this for you. We will return shortly. Enjoy
			reading rest of blogs.</p> -->
			${questionOfTheDay.question_details }

		</div>
	</div>
	<div class="actions">
		<div class="ui positive right labeled icon button">
			Thank you! <i class="checkmark icon"></i>
		</div>
	</div>
</c:if>
<c:if test="${questionOfTheDay.heading == null || questionOfTheDay.heading == '' }">
	<i class="close icon"></i>
	<div class="header">Question of the Day.</div>
	<div class="image content">
		<!-- <div class="ui medium image">
      <img src="/images/avatar/large/chris.jpg">
    </div> -->
		<div class="description">
			<p>We are working on this for you. We will return shortly. Enjoy
			reading rest of blogs.</p>

		</div>
	</div>
	<div class="actions">
		<div class="ui positive right labeled icon button">
			Thank you! <i class="checkmark icon"></i>
		</div>
	</div>
</c:if>