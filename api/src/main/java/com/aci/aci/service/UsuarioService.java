package com.aci.aci.service;
import com.aci.aci.model.Usuario;
import com.aci.aci.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = //declaro también el atributo passwordEncoder para usarlo más adelante
        new BCryptPasswordEncoder(12);
    
    @Autowired 
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getById(Integer userId){
        return usuarioRepository.findById(userId);
    }

    public Optional<Usuario> findByEmail(String email) { //para el inicio de sesión busca el usuario con ese email
        return usuarioRepository.findByEmail(email);
    }

    //iniciar sesión -- para comprobar que la contraseña es correcta se pasan como args el email y la contraseña que haya introducido el usuario en la pantalla
    public boolean checkUser(String email, String contrasenyaEscrita) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email); //comprueba el usuario por su email
            if (usuario.isEmpty()) {
                return false;
            }

            return passwordEncoder.matches(contrasenyaEscrita, usuario.get().getContrasenyaHash()); //y si lo localiza compara la contraseña escrita con el hash de su contraseña guardado
    }

    //para crear usuarios
    public Usuario createUsuario(Usuario usuario) { //los parámetros son todos los campos que están declarados para usuario, no hace falta nombrarlos si ponemos usuario como parámetro
        //para que también guarde las contraseñas de usuarios nuevos recién creados como hash
        usuario.setContrasenyaHash(
            passwordEncoder.encode(usuario.getContrasenyaHash())
        );
        return usuarioRepository.save(usuario);
    }

    //eliminar usuario
    public void deleteUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Usuario " + id + " no encontrado.");
        }
    }

    //cambiar contraseña
    public void changePassword(Integer id, String nuevaContrasenya){
        Usuario usuario = usuarioRepository.findById(id) //primero hay que ubicar el usuario del que se va a cambiar la contraseña
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        usuario.setContrasenyaHash(passwordEncoder.encode(nuevaContrasenya)); //se cambia por el valor introducido y se codifica en un hash
        usuarioRepository.save(usuario); //y se guarda el usuari ode nuevo
    }

}
