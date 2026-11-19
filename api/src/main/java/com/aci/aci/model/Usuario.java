package com.aci.aci.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;
    private String email;
    private String contrasenyaHash;

    public Usuario(){}

    public Usuario(String userName, String email, String contrasenyaHash){
        this.userName = userName;
        this.email = email;
        this.contrasenyaHash = contrasenyaHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenyaHash() {
        return contrasenyaHash;
    }

    public void setContrasenyaHash(String contrasenyaHash) {
        this.contrasenyaHash = contrasenyaHash;
    }


}





