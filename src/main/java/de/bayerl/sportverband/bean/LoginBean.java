package de.bayerl.sportverband.bean;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Boolean adminIsLoggedIn;

    public String validateCredentials(){
        if(this.email != null && this.password != null) {
            if (this.email.equals("admin@admin") && this.password.equals("xyz123")) {
                this.adminIsLoggedIn = true;
                return "tabelle.xhtml?faces-redirect=true";
            } else {
                this.adminIsLoggedIn = false;
                return "index.xhtml?faces-redirect=true";
            }
        }else {
            return "index.xhtml?faces-redirect=true";
        }
    }

    public Boolean isAdmindLoggedIn(){
        return adminIsLoggedIn;
    }

    public void logout(){
        this.adminIsLoggedIn = false;
    }

}
