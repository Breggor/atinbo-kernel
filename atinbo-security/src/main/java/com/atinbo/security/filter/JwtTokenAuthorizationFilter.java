package com.atinbo.security.filter;

import com.atinbo.security.jwt.JwtTokenOps;
import com.atinbo.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * jwt token拦截
 *
 * @author breggor
 */
public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenOps jwtTokenOps;

    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    @Value("${jwt.tokenHead:Bearer}")
    private String tokenHead;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // The part after "Bearer "
            final String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenOps.getUsernameFromToken(authToken);

            logger.info("checking authentication " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
                // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
                // 本例中，我们还是通过Spring Security的 @UserDetailsService 进行了数据查询
                // 但简单验证的话，你可以采用直接验证token是否合法来避免昂贵的数据查询
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

                if (jwtTokenOps.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}