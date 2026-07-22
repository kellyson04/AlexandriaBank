package dev.kellyson.alexandriabank.cliente;

import dev.kellyson.alexandriabank.cliente.dto.ClienteCriadoResponse;
import dev.kellyson.alexandriabank.cliente.dto.CriarClienteRequest;
import dev.kellyson.alexandriabank.doc.ClienteControllerDoc;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteControllerDoc {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteCriadoResponse> criarCliente(@RequestBody @Valid CriarClienteRequest clienteRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(clienteRequest));
    }
}
