package org.tr.crm.techrodeocrmbff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.tr.crm.techrodeocrmbff.repository.dto.CustomerDTO;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/bff/customers")
public class CustomerBFFController {

    private final WebClient webClient;

    public CustomerBFFController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/v1").build();
    }

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return webClient.get()
                .uri("/customers")
                .retrieve()
                .bodyToFlux(CustomerDTO.class)
                .collectList()
                .block();
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = webClient.post()
                .uri("/customer")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(CustomerDTO.class)
                .block();

        return ResponseEntity.ok(savedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        webClient.delete()
                .uri("/customer/" + id)
                .retrieve()
                .toEntity(String.class)
                .block();

        return ResponseEntity.noContent().build();
    }
}
