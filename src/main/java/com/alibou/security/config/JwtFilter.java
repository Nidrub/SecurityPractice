package com.alibou.security.config;
//this should be in a seperate package but config works this i the last part

import com.alibou.security.service.JWTService;
import com.alibou.security.service.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//this is going to allow it to act like a filter (this is an abstract method so we need to implement.
@Component
public class JwtFilter extends OncePerRequestFilter {
    //here is where we add the service of JWT that we have from maven
    @Autowired
    private JWTService jwtService;
    //wea re going to add a context to get the bean
    @Autowired
    ApplicationContext context;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //we are only going to work with the request
        /* Wha we get from the tocken
        * we get the
        * Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbm5pZSIsImlhdCI6MTc0MzM4MjU2MiwiZXhwIjoxODgyODUzMTY3MjczMjB9.Rz2frLggv6x5f5iaKj9MyM52QAST4QNTCwtqXJOvAdU*/

        String authHeader = request.getHeader("Authorization");//we only want the header that talks about authorization
        //we are going to work with these
        String token = null;
        String username = null;
        //check if AuthHeader is not null and is Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);//start the substring from seven after Bearer to get the token
            username = jwtService.extractUserName(token);

        }
        //check the username
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //create the USerDetails (we are going to add in on top in a specific way to avoid cyclic redundancy) wea re going
            //to use context to fire it up and get the entire user object, but we are going to use username
            UserDetails userDetails = context.getBean(MyUserDetailService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                //this object should also know the request object so we are adding this
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //once it verified you have to set the authentication
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        //once it is done you will simply pass the filter
        filterChain.doFilter(request, response);
    }
}
