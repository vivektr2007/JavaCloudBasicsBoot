<%@page import="java.util.Iterator"%>
<%@page import="com.pixel.model.MenuBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.pixel.blog.serviceImpl.MenuLoader"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="sf-menu" id="nav">
<%
Map<String, List<MenuBean>> menus = MenuLoader.menus;
Iterator<Map.Entry<String, List<MenuBean>>> itr = menus.entrySet().iterator();
while(itr.hasNext()){
	Map.Entry<String, List<MenuBean>> me = itr.next();
%>	
	<li><a href="#"><%=me.getKey() %></a>
		<ul>
		<%
			List<MenuBean> list = me.getValue();
			Iterator<MenuBean> itrList = list.iterator();
			while(itrList.hasNext()){
				MenuBean mbean = itrList.next();
				%>
				<li><a href="viewTopics.html?id=<%=mbean.getMenuId() %>"> sdfds <%=mbean.getMenuDesc() %></a></li>
				<%
			}
		%>
      </ul>
    </li>   
<%
}
%>
<li><a href="book.html">Free eBook</a></li>
<li><a href="contact.html">Contact Us</a></li>
</ul>
