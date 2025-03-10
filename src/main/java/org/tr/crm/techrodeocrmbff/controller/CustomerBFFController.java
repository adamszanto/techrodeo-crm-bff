package org.tr.crm.techrodeocrmbff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.tr.crm.techrodeocrmbff.repository.dto.CustomerDTO;

import java.util.List;

@RestController
@RequestMapping("/bff/customers")
public class CustomerBFFController {

    private final WebClient webClient;

    public CustomerBFFController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://techrodeo.hu/v1").build();
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
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customer) {
        return webClient.post()
                .uri("/customer")
                .bodyValue(customer)
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        return webClient.delete()
                .uri("/customer/" + id)
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
