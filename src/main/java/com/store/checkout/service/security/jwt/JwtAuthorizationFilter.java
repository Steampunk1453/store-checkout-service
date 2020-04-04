package com.store.checkout.service.security.jwt;

import com.store.checkout.service.security.SecurityConstants;
import com.store.checkout.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = httpServletRequest.getHeader(SecurityConstants.HEADER_AUTHORIZATION_KEY);

		if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader
				.startsWith(SecurityConstants.TOKEN_BEARER_PREFIX)) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		final String token = authorizationHeader.replace(SecurityConstants.TOKEN_BEARER_PREFIX + " ", "");

		String userName = TokenProvider.getUserName(token);
		UserDetails user = userService.loadUserByUsername(userName);

		UsernamePasswordAuthenticationToken authenticationToken = TokenProvider.getAuthentication(token, user);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
