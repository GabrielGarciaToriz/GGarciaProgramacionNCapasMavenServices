package com.digis01.GGarciaProgramacionNCapasMavenService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO {
    private int status;
    private String message;
}
