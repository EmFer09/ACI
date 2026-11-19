package com.aci.aci.controller;
import com.aci.aci.model.Usuario;
import com.aci.aci.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController 
@RequestMapping ("/api/usuario")
public class UsuarioController {

    @Autowired 
    private UsuarioService usuarioService;

    @GetMapping ("/{userId}") //encontrar usuarios por id para cargar su info en la pantalla perfil
    public ResponseEntity<Usuario> findByid(@PathVariable Integer userId) {
        Optional<Usuario> usuario = usuarioService.getById(userId);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    } 

    //para el Login
    @PostMapping ("/login")
    public ResponseEntity<String> checkUser(@RequestBody Map<String, String> datos) {
        String email = datos.get("email"); //obtenemos lo que ha introducido el usuario
        String contrasenyaEscrita = datos.get("contrasenyaEscrita");

        boolean todoOk = usuarioService.checkUser(email, contrasenyaEscrita); //se comprueba si los datos introducidos son correctos
        if (todoOk) {
            return ResponseEntity.ok("Inicio de sesión correcto.");
        } else {
            return ResponseEntity.status(401).body("Datos incorrectos.");
        }
    }

    //crear usuario
    @PostMapping 
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    //cambiar contraseña
    @PutMapping ("/{userId}/contrasena")
    public ResponseEntity<String> changePassword(@PathVariable Integer userId, @RequestBody Map<String, String> datos) {
        String nuevaContrasenya = datos.get("nuevaContrasenya");

        usuarioService.changePassword(userId, nuevaContrasenya);
        return ResponseEntity.ok("Contraseña cambiada correctamente.");
    }
    
    //eliminar usuario
    @DeleteMapping ("/{userId}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable  Integer userId) {
        usuarioService.deleteUsuario(userId);
        return ResponseEntity.noContent().build();
    }
    
}
