package br.com.henrique.cartao.interceptor;

import br.com.henrique.cartao.exception.TokenException;
import br.com.henrique.cartao.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {

    if (request.getRequestURI().equals("/cartao/login")) {
      return true;
    }

    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    jwtTokenUtil.getId(request)
      .orElseThrow(() -> new TokenException("Token inválido ou inexistente"));

    return true;
  }


}
