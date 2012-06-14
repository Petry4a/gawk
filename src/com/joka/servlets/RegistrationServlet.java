package com.joka.servlets;

import com.joka.services.RegistrationService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

/**
 * @author jonas
 */
public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RegistrationServlet.class.getName());

    private static final String JSON_PROPERTY_EMAIL = "email";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        Reader reader = new InputStreamReader(req.getInputStream());

        JSONObject jsonObject = (JSONObject)JSONValue.parse(reader);

        reader.close();

        String email = (String)jsonObject.get(JSON_PROPERTY_EMAIL);

        RegistrationService.getInstance().registerEmail(email);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
