package com.store.checkout.service.security.jwt;

import com.store.checkout.service.security.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class TokenProvider {

	private TokenProvider() {
	}

	public static String generateToken(Authentication authentication) {
		final String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(SecurityConstants.AUTHORITIES_KEY, authorities)
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.SIGNING_KEY)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setIssuer(SecurityConstants.ISSUER_TOKEN)
				.compact();
	}

	public static UsernamePasswordAuthenticationToken getAuthentication(final String token,
                                                                        final UserDetails userDetails) {
		final var jwtParser = Jwts.parser().setSigningKey(SecurityConstants.SIGNING_KEY);
		final var claimsJws = jwtParser.parseClaimsJws(token);
		final var claims = claimsJws.getBody();
		final Collection<SimpleGrantedAuthority> authorities =
				Arrays.stream(claims.get(SecurityConstants.AUTHORITIES_KEY).toString().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	public static String getUserName(final String token) {
		final var jwtParser = Jwts.parser().setSigningKey(SecurityConstants.SIGNING_KEY);
		final var claimsJws = jwtParser.parseClaimsJws(token);
		return claimsJws.getBody().getSubject();
	}

}
