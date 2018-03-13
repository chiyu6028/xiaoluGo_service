package com.xiaolu.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AjaxController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req , resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req , resp);
    }


    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        System.out.println("000000000000000000000000000000");
    }

    protected void doResponse(HttpServletResponse response, Map result)
            throws IOException
    {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("dsfs");
        out.flush();
        out.close();
    }

    protected void doResponse(HttpServletResponse response, String result) throws IOException
    {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }
}
