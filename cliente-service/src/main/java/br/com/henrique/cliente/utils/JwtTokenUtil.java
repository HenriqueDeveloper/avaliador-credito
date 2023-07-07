package br.com.henrique.cliente.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
@Component
public class JwtTokenUtil {

    private final String TOKEN_HEADER = "Authorization";

    @Value("${keycloak.jwt_public_key}")
    private String publicKey;

    private RSAPublicKey getPublicKeyFromString() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public Optional<String> getId(HttpServletRequest request) throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        final String requestTokenHeader = request.getHeader(TOKEN_HEADER);
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }

        String id = getId(requestTokenHeader.substring(7));
        return Optional.of(id);
    }

    public String getId(String token) throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        return getAllClaimsFromToken(token).get("sub", String.class);
    }

    private Boolean isTokenExpired(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) throws  IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) throws  IllegalArgumentException, NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.parser().setSigningKey(getPublicKeyFromString()).parseClaimsJws(token).getBody();
    }

}
