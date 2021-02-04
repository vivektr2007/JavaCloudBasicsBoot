package com.pixel.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		/*String URI = request.getRequestURI();
		if (URI.indexOf("openapi") != -1) {
			return true;
		}

		if (!URI.endsWith("/") && URI.indexOf("CheckLogin") == -1 && URI.indexOf("logout") == -1
				&& URI.indexOf("login") == -1) {

			UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
			if (userBean == null || userBean.getId() == null) {
				response.sendRedirect(request.getContextPath() + "/login.html?autoLogout=true");
				return false;
			}
			if (URI.lastIndexOf("/") != -1) {
				String selectedUri = URI.substring(URI.lastIndexOf("/") + 1);
				request.setAttribute("selectedUri", selectedUri);
				request.setAttribute("selectedUriParent", userBean.getMenuActionWithParentMap().get(selectedUri));
			}
		}*/

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest reuqest, HttpServletResponse response, Object obj, Exception ex)
			throws Exception {
		// LOGGER.info("after completion");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
		// LOGGER.info("post handle");
	}
}
