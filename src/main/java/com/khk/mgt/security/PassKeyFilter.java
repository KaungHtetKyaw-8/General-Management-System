package com.khk.mgt.security;


import com.khk.mgt.ds.AccessKey;
import com.khk.mgt.dto.common.AccessLogDto;
import com.khk.mgt.service.AccessLogService;
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
import java.time.LocalDateTime;

@Component
public class PassKeyFilter extends OncePerRequestFilter {

    @Autowired
    private PassKeyService passkeyService;

    @Autowired
    private AccessLogService accessLogService;

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

                AccessKey accessKey = passkeyService.findByHashPassKey(key);

                if (accessKey.getUserName().equals("ADMIN")) {
                    request.getSession().setAttribute("ROLE", "ADMIN");
                }

                AccessLogDto accessLogDto = new AccessLogDto();
                accessLogDto.setAccessKeyId(accessKey.getId());
                accessLogDto.setAccessedAt(LocalDateTime.now());
                accessLogDto.setRemoteIp(request.getRemoteAddr());

                accessLogService.saveAccessLogInfo(accessLogDto);

                response.sendRedirect("/home"); // Back to requested page
                return;
            } else {
                response.sendRedirect("/passkey"); // redirect to input form
                return;
            }
        }

        if (path.startsWith("/admin")) {
            boolean isAdmin = "ADMIN".equals(session.getAttribute("ROLE"));
            if (!isAdmin) {
                response.sendRedirect("/home/auth/error");
            }
        }

        filterChain.doFilter(request, response);
    }
}


