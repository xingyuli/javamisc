package org.swordess.toy.javamisc.jwt;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

import java.text.ParseException;

public class JwtParserDemo {

    public static void main(String[] args) throws Exception {
        // You can get a token from: https://jwt.io/
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        // cannot parse expired token
        // parseWithJjwt(token);

        parseWithJoseJwt(token);
    }

    private static void parseWithJjwt(String token) {
        // https://github.com/jwtk/jjwt/issues/280
        int i = token.lastIndexOf('.');
        String tokenWithoutSignature = token.substring(0, i + 1);
        Jwt<Header, Claims> untrusted = Jwts.parserBuilder().build().parseClaimsJwt(tokenWithoutSignature);
        System.out.println(untrusted.getBody());
    }

    private static void parseWithJoseJwt(String token) throws ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        System.out.println(jwsObject);

        Payload payload = jwsObject.getPayload();
        System.out.println(payload.toJSONObject());
    }

}
