package br.com.henrique.cartao.services;

import br.com.henrique.cartao.dtos.UserDto;
import br.com.henrique.cartao.exception.KeycloakException;
import br.com.henrique.cartao.exception.UserNotFoundException;
import br.com.henrique.cartao.utils.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KeycloakTokenService {

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    public TokenResponse getToken(UserDto userDto) throws KeycloakException, UserNotFoundException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("username", userDto.getUsername());
            body.add("password", userDto.getPassword());

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(
                    keycloakUrl,
                    HttpMethod.POST,
                    requestEntity,
                    TokenResponse.class
            );

            TokenResponse tokenResponse = responseEntity.getBody();

            return tokenResponse;

        } catch (ResourceAccessException ex) {
            throw new KeycloakException("Não foi possível conectar-se ao Keycloak");
        }catch (HttpClientErrorException ex) {
            throw new UserNotFoundException("Credencias incorretas");
        }
    }
}

