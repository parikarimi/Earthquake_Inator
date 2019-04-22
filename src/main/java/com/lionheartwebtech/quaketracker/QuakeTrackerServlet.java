package com.lionheartwebtech.quaketracker;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import freemarker.core.ParseException;
import freemarker.template.*;
import java.util.logging.Level;

import org.apache.log4j.Logger;

/**
 *
 * @author Parichehr Karimi
 */
public class QuakeTrackerServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(QuakeTrackerServlet.class.getName());

    private static Connection jdbcConnection = null;
    private static Configuration fmConfig = new Configuration(Configuration.getVersion());
    private static final String TEMPLATE_DIR = "/WEB-INF/templates";

    @Override
    public void init(ServletConfig config) throws UnavailableException {
        logger.info("==============================");
        logger.info("Starting " + QuakeTrackerServlet.class.getSimpleName() + " servlet init");
        logger.info("==============================");

        logger.info("Getting real path for templateDir");
        String templateDir = config.getServletContext().getRealPath(TEMPLATE_DIR);
        logger.info("...real path is: " + templateDir);

        logger.info("Initializing Freemarker, templateDir: " + templateDir);
        try {
            fmConfig.setDirectoryForTemplateLoading(new File(templateDir));
            logger.info("Successfully Loaded Freemarker");
        } catch (IOException e) {
            logger.error("Template directory not found, directory: " + templateDir + ", exception: " + e);
        }

        logger.info("Connecting to the database...");
        
        String jdbcDriver = "org.mariadb.jdbc.Driver";
        logger.info("Loading JDBC Driver: " + jdbcDriver);
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            logger.error("Unable to find JDBC driver on classpath.");
            return;
        }
        
        String connString = "jdbc:mariadb://";
        connString += "lionheartwebtech-db.cv18zcsjzteu.us-west-2.rds.amazonaws.com:3306";
        connString += "/orcas";
        connString += "?user=orcas&password=orcas";
        connString += "&useSSL=true&trustServerCertificate=true";
     
        try {
            jdbcConnection = DriverManager.getConnection(connString);
        } catch (SQLException e) {
            logger.error("Unable to connect to SQL Database with JDBC string: " + connString);
            throw new UnavailableException("Unable to connect to database.");
        }
        
        logger.info("...connected!");
        logger.info("==============================");
        logger.info("Finished init");
        logger.info("==============================");
    }

    @Override
    public void destroy() {
        logger.info("##############################");
        logger.info("Destroying " + QuakeTrackerServlet.class.getSimpleName() + " servlet");
        logger.info("##############################");

        logger.info("Disconnecting from the database.");
        try {
            jdbcConnection.close();
        } catch (SQLException e) {
            logger.error("Exception thrown while trying to close SQL Connection: " + e, e);
        }
        logger.info("Disconneced!");
        logger.info("##############################");
        logger.info("...done");
        logger.info("##############################");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long timeStart = System.currentTimeMillis();
        logger.debug("IN - doGet()");

        String command = request.getParameter("cmd");
        String api = request.getParameter("api");

        if (command == null) {
            logger.info("Loading home page...");
            command = "home";
        }

        String template = "";
        Map<String, Object> model = new HashMap<>();
        String jsonEvents = null;

        switch (command) {
            case "home":
                template = "home.tpl";
                break;
            case "map":
                template = "map.tpl";
                break;
            case "alerts":
                template = "alerts.tpl";
                break;
            case "about":
                template = "about.tpl";
                break;

            default:
                logger.info("Invalid GET command received: " + command);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }

        if (api != null) {
            switch (api) {
                case "quakeData":
                    QuakeList events = new QuakeList();
                    jsonEvents = events.toJSON();
                    sendJSONdata(response, jsonEvents);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
            }
        }

        if (jsonEvents != null) {
            sendJSONdata(response, jsonEvents);

        } else {
            processTemplate(response, template, model);
        }

        processTemplate(response, template, model);
        long time = System.currentTimeMillis() - timeStart;
        logger.info("OUT - doGet() - " + time + "ms");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long timeStart = System.currentTimeMillis();
        logger.debug("IN - doPost()");

        String command = request.getParameter("cmd");
        if (command == null) {
            logger.info("No cmd parameter received");
            command = "";
        }
        logger.debug("command: " + command);

        String template = "";
        Map<String, Object> model = new HashMap<>();

        switch (command) {
            case "createMember":
                Member newmember = getUserFromRequest(request);
                logger.debug("newmember: " + newmember);
                template = "map.tpl";
                if (newmember != null) {
                    try {
                        logger.debug("Got member. Attempting to insert!!!!!");
                        QuakeTrackerDAO.addMember(jdbcConnection, newmember);
                        
                    } catch (SQLException ex) {
//                        java.util.logging.Logger.getLogger(QuakeTrackerServlet.class.getName()).log(Level.SEVERE, null, ex);    
                        logger.error("There was an unexpected error adding this list to the database." + ex.getMessage());
                        model.put("message", "There was an unexpected error adding this list to the database. Please try again.");
                    }
                } else {
                    model.put("message", "The form was incomplete. Please fill in all boxes, then press Submit.");
                }
                break;
            default:
                logger.info("Invalid POST command received: " + command);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }

        processTemplate(response, template, model);
        long time = System.currentTimeMillis() - timeStart;
        logger.debug("OUT - doPost() - " + time + "ms");
    }

    
    private Member getUserFromRequest(HttpServletRequest request) {
        logger.info("Reading user input data from alert form: " + request);
        String firstName = request.getParameter("fName");
        String lastName = request.getParameter("lName");
        String email = request.getParameter("emailAddress");
        String phoneNum = request.getParameter("phone");
        String ipAddress;
        
        ipAddress = request.getRemoteAddr();

        
        logger.info("Got user IP address:"  + ipAddress);
        
        if (email == null) {
            logger.info("The submitted alert form is incomplete");
            return null;
        }
        
        try {
            if (QuakeTrackerDAO.userExists(jdbcConnection, email)) {
                return null;
            }
        } catch (SQLException ex) {
                logger.error("ERROR: Unable to add the new member to the database- " + ex.getMessage());
        }
        
        IPLocation loc = new IPLocation(ipAddress);

        double longitude = loc.getLongitude();
        double latitude = loc.getLatitude();
        logger.info("Got user geolocation coordinates: longitude: " + longitude + "\tLatitude: " + latitude);
        return new Member(false, email, firstName, lastName, latitude, longitude, latitude, longitude, 50, ipAddress, phoneNum);
    }

    private void processTemplate(HttpServletResponse response, String template, Map<String, Object> model) {
        logger.debug("Processing Template: " + template);

        try (PrintWriter out = response.getWriter()) {
            Template view = fmConfig.getTemplate(template);
            view.process(model, out);
        } catch (TemplateException e) {
            logger.error("Template Error:", e);
        } catch (MalformedTemplateNameException e) {
            logger.error("Malformed Template Error:", e);
        } catch (ParseException e) {
            logger.error("Parsing Error:", e);
        } catch (IOException e) {
            logger.error("IO Error:", e);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void sendJSONdata(HttpServletResponse response, String JSON) {
        logger.debug("message: Sening JSON Data, data: " + JSON);

        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            out.print(JSON);
            out.flush();
        } catch (IOException ex) {
            logger.error("message: Error writing servlet output, error: " + ex);
        }
    }

}
