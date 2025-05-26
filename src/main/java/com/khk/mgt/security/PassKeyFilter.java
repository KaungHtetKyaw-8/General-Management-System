package com.khk.mgt.security;

import com.khk.mgt.service.PassKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PassKeyFilter extends OncePerRequestFilter {

    @Autowired private PassKeyService passkeyService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        // Allow access to passkey form and static resources
        if (path.startsWith("/passkey") || path.startsWith("/custom")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);

        boolean isAllowed = session != null && "GRANTED".equals(session.getAttribute("PASSKEY"));

        if (!isAllowed) {
            String key = request.getParameter("key");
            if (key != null && passkeyService.isValid(key)) {
                request.getSession(true).setAttribute("PASSKEY", "GRANTED");
                response.sendRedirect(path); // Back to requested page
                return;
            } else {
                response.sendRedirect("/passkey"); // redirect to input form
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}


