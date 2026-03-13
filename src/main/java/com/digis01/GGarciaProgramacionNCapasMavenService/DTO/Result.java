package com.digis01.GGarciaProgramacionNCapasMavenService.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    public boolean correct;
    public String errorMessage;
    @JsonIgnore
    public Exception ex;
    public Object object;
    public List<?> objects;
    
    public Result(){
        
    }
}
