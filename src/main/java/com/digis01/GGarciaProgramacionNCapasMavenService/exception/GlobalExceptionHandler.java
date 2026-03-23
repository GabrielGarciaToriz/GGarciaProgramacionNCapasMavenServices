package com.digis01.GGarciaProgramacionNCapasMavenService.exception;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Atrapa contraseñas incorrectas o usuarios no encontrados en el login
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDTO> handleBadCredentials(BadCredentialsException ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.UNAUTHORIZED.value(), 
                "Usuario o contraseña incorrectos. Verifica tus credenciales."
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Atrapa la excepción que lanzamos en el AppConfig si no encuentra el usuario
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFound(UsernameNotFoundException ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.UNAUTHORIZED.value(), 
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Opcional: Un "paracaídas" genérico para cualquier otro error del servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex) {
        ErrorDTO error = new ErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "Ocurrió un error inesperado en el servidor: " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}