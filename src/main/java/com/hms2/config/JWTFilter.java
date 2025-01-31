package com.hms2.config;

import com.hms2.Entity.User;
import com.hms2.Repository.UserRepository;
import com.hms2.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired private JWTService jwtService;
    @Autowired private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)throws ServletException, IOException{

        String token = request.getHeader("Authorization");

        if(token!=null && token.startsWith("Bearer")){
            String jwtToken = token.substring(8, token.length() - 1);
            String username = jwtService.getUsername(jwtToken);
            Optional<User> opUser = userRepository.findByUsername(username);
            if(opUser.isPresent()){
                User user = opUser.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole())));

                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        filterChain.doFilter(request, response);

    }

}
