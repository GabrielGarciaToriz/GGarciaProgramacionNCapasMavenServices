package com.digis01.GGarciaProgramacionNCapasMavenService.service;

import com.digis01.GGarciaProgramacionNCapasMavenService.dto.Result;
import com.digis01.GGarciaProgramacionNCapasMavenService.repository.ColoniaRepository;
import com.digis01.GGarciaProgramacionNCapasMavenService.repository.EstadoRepository;
import com.digis01.GGarciaProgramacionNCapasMavenService.repository.MunicipioRepository;
import com.digis01.GGarciaProgramacionNCapasMavenService.repository.PaisRepository;
import com.digis01.GGarciaProgramacionNCapasMavenService.repository.RolRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {

    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private MunicipioRepository municipioRepository;
    @Autowired
    private ColoniaRepository coloniaRepository;
    @Autowired
    private RolRepository rolRepository;

    public Result getPaises() {
        return procesarConsulta(() -> paisRepository.findAllByOrderByNombreAsc());
    }

    public Result getEstadosByPais(int IdPais) {
        return procesarConsulta(() -> estadoRepository.findByPaisIdPaisOrderByNombreAsc(IdPais));
    }

    public Result getMunicipioByEstado(int IdEstado) {
        return procesarConsulta(() -> municipioRepository.findByEstadoIdEstadoOrderByNombreAsc(IdEstado));
    }

    public Result getColoniaByMunicipio(int IdMunicipio) {
        return procesarConsulta(() -> coloniaRepository.findByMunicipioIdMunicipioOrderByNombreAsc(IdMunicipio));
    }

    public Result getColoniaByCP(String codigoPostal) {
        return procesarConsulta(() -> coloniaRepository.findByCodigoPostalOrderByNombreAsc(codigoPostal));
    }
    
    public Result getRoles(){
        return procesarConsulta(()-> rolRepository.findAll());
    }

    private Result procesarConsulta(java.util.function.Supplier<java.util.List<?>> consulta) {
        Result result = new Result();
        try {
            result.objects = new ArrayList<>(consulta.get());
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

}
