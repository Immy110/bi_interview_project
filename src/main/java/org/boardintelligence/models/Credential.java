package org.boardintelligence.models;

import org.boardintelligence.configs.PropertiesFileReader;
import org.boardintelligence.utils.StringEncodeDecodeUtils;

public class Credential {

    private String email;
    private String password;

    public Credential() {
    }

    public Credential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Credential getCredentials(String client, String user) {
        PropertiesFileReader propertiesFileReader = PropertiesFileReader.getInstance();
        String email = propertiesFileReader.getConfigurationProperties("credentials." + client + "." + user + ".email");

        String encodedPassword = propertiesFileReader.getConfigurationProperties("credentials." + client + "." + user + ".password");
        String password = StringEncodeDecodeUtils.decode(encodedPassword);

        return new Credential(email, password);
    }

    public static String getUserFirstName(String client, String user) {
        PropertiesFileReader propertiesFileReader = PropertiesFileReader.getInstance();
        String email = propertiesFileReader.getConfigurationProperties("credentials." + client + "." + user + ".email");
        String userName = email.substring(0, email.indexOf("@"));
        if (userName.contains(".")) {
            userName = email.substring(0, email.indexOf("."));
        } else userName = userName;
        return userName;
    }

    public static String getUserFullName(String client, String user) {
        PropertiesFileReader propertiesFileReader = PropertiesFileReader.getInstance();
        String email = propertiesFileReader.getConfigurationProperties("credentials." + client + "." + user + ".email");
        String userFirstName = email.substring(0, email.indexOf("@")).split("\\.")[0];
        String userLastName = email.substring(0, email.indexOf("@")).split("\\.")[1];
        return userFirstName + " " + userLastName;
    }
}

