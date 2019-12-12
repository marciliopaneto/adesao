package com.task.adesao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by marcus on 17/09/18.
 */
@Controller
public class IndexController {
    @Autowired
    Environment env;

    @GetMapping("/")
    public String index() {
//        connect("HEXT");
        return "upload";
    }

/*
    public void connect(String ambiente) {
        String jdbcClassName = env.getProperty("spring.datasource.driver-class-name");
        String url = env.getProperty("spring.datasource.url");
        String user = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        Connection connection = null;
        try {
            //Load class into memory
            Class.forName(jdbcClassName);
            //Establish connection
            connection = DriverManager.getConnection(url, user, password);
//            connection.nativeSQL();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                System.out.println("Connected successfully.");
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/
}
