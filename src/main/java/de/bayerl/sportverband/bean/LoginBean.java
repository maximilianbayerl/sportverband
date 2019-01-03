package de.bayerl.sportverband.bean;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Boolean adminIsLoggedIn;

    public String validateCredentials(){
        if(this.username != null && this.password != null) {
            if (this.username.equals("admin") && this.password.equals("xyz123")) {
                this.adminIsLoggedIn = true;
                return "tabelle.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage(
                        "Falscher Benutzername oder falsches Passwort."));
                this.adminIsLoggedIn = false;
                return null;
            }
        }else {
            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage(
                    "Falscher Benutzername oder falsches Passwort."));
            return null;
        }
    }

    public Boolean isAdmindLoggedIn(){
        return adminIsLoggedIn;
    }

    public void logout(){
        this.adminIsLoggedIn = false;
    }

}
