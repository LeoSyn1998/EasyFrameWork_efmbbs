package com.bbs.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

@WebFilter(urlPatterns = "/")
public class EncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //POST编码直接设置要转换的编码即可
        if (request.getMethod().equals("POST")){
            request.setCharacterEncoding("utf-8");
            filterChain.doFilter(request,servletResponse);
        }else {
            //get方法要对所有参数进行重新编码,使用包装类进行编码
            filterChain.doFilter(new RequestWrapper(request),servletResponse);
        }
        //设置响应时的编码格式
        servletResponse.setCharacterEncoding("utf-8");
    }

    public void destroy() {

    }

    /*编写一个内部包装类，重写部分api，对参数进行重新编码*/
    private class RequestWrapper extends HttpServletRequestWrapper{

        private String encoding(String param) throws UnsupportedEncodingException {
            byte[] bytes = param.getBytes("iso-8859-1");
            return new String(bytes, "utf-8");
        }

        @Override
        public String getParameter(String name) {
            String parameter = super.getParameter(name);
            try {
                if (parameter != null){
                    return encoding(parameter);
                }
            }catch (UnsupportedEncodingException e){
                e.fillInStackTrace();
            }
            return null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> parameterMap = super.getParameterMap();
            Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
            if (entrySet.isEmpty()) return parameterMap;
            for (Map.Entry<String,String[]> entry:entrySet){
                String[] values = entry.getValue();
                if (values.length==0) continue;
                for (String string : values){
                    try {
                        string = encoding(string);
                    }catch (UnsupportedEncodingException e){
                        e.fillInStackTrace();
                    }
                }
            }
            return parameterMap;
        }

        @Override
        public String[] getParameterValues(String name) {
            Map<String, String[]> parameterMap = getParameterMap();
            return parameterMap.get(name);
        }

        public RequestWrapper(HttpServletRequest request) {
            super(request);
        }
    }

}

