package com.joka.servlets;

import com.joka.services.GawkService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author jonas
 */
public class NotificationServlet extends HttpServlet {

    private static final String RESPONSE_MESSAGE = "<html><h1>Success!</h1></html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        //Call service to perform check for new episodes
        GawkService.getInstance().performCheck();

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html");

        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter((resp.getOutputStream())));

        PrintWriter writer = resp.getWriter();
        writer.write(RESPONSE_MESSAGE);
        writer.close();
    }
}
