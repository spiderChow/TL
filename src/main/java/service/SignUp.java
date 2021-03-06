package service;

import net.sf.json.JSONObject;
import entity.service.Status;
import util.db.DBConnect;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;

@WebServlet(name = "SignUp", urlPatterns = "/sign_up")
public class SignUp extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/html;charset=utf-8");

        String schoolID = request.getParameter("schoolID");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        if (schoolID == null) {
            schoolID = "";
        }
        if (account == null) {
            account = "";
        }
        if (password == null) {
            password = "";
        }

        //TODO: 2017/11/23 create the table in DB to look up the (type,name,info,contact) via schoolID
        // temp values
        String type = "STUDENT";
        String name = "zhoujunying";
        String info = "I am Junying chou";
        String contact = "qq511392148";
        String id = "14302010057";
        // how to get the  data access info?
        String url = "jdbc:mysql://123.207.6.234:3306/tl?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String dbPwd = "root";

        DBConnect dbConnect = new DBConnect(url, user, dbPwd);
        String stringFormat = "INSERT INTO user (id, password, account, name,info,contact) VALUES (\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')";
        String sql = String.format(stringFormat, id, password, account,name,info,contact);

        Status retStatus = new Status();

        try {
            dbConnect.executeUpdate(sql);
            retStatus.setStatus(true);
            retStatus.setInfo("注册成功");
        }catch (SQLException e) {
            retStatus.setStatus(false);
            retStatus.setInfo(e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonRetStatus = JSONObject.fromObject(retStatus);
        PrintWriter out = response.getWriter();
        out.print(jsonRetStatus.toString());
    }

}
