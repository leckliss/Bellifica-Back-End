package com.backend.bellifica.security;

import com.backend.bellifica.user.Users;
import com.backend.bellifica.user.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Autorization";
    private  static  final String BASIC = "Basic";

    private final UsersRepository usersRepository;

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isBasicAuthentication(request)){
            String[] credentials = decodeBase64(getHeader(request).replace(BASIC, ""))
                    .split(":");

            String email = credentials[0];
            String senha = credentials[1];

            Users users = usersRepository.findByEmailFetchRoles(email);

            if(users == null ){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Usuario inexistente");
                return;
            }

            boolean valid = checkSenha(users.getSenha(), senha);

            if (!valid){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Senha incorreta");
            }
            setAuthentication(users);

        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(Users users) {
        Authentication authentication = createAuthenticationToken(users);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthenticationToken(Users users) {
        UserPrincipal userPrincipal = UserPrincipal.create(users);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private boolean checkSenha(String usersSenha, String loginSenha) {
        return passwordEncoder().matches(loginSenha, usersSenha);
    }

    private String decodeBase64(String base64) {
        byte[] decodeBytes = Base64.getDecoder().decode(base64);
        return new String(decodeBytes);
    }

    private boolean isBasicAuthentication(HttpServletRequest request){
        String header = getHeader(request);
        return header != null && header.startsWith(BASIC);
    }

    private String getHeader(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION);
    }
}
