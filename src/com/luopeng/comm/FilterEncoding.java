package com.luopeng.comm;

import java.io.IOException;

import javax.servlet.*;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author wangsf
 * @version 1.0
 */


public class FilterEncoding implements Filter {
	private String code;

	public void destroy() {
		code = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (StringUtils.isNotEmpty(code)) {
			if (request.getCharacterEncoding() == null) {
				request.setCharacterEncoding(code);
			}
			response.setContentType("text/html");
			response.setCharacterEncoding(code);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		code = config.getInitParameter("code");
		
	}

}
