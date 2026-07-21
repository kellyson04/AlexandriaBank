package dev.kellyson.alexandriabank.cliente;

import dev.kellyson.alexandriabank.cliente.dto.ClienteCriadoResponse;
import dev.kellyson.alexandriabank.usuario.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClienteMapper {

    public static ClienteCriadoResponse paraResposta(Usuario usuario) {
        return new ClienteCriadoResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCriadoEm()
        );
    }
}
