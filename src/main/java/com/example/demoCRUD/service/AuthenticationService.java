package com.example.demoCRUD.service;

import com.example.demoCRUD.dto.request.AuthenticationResquest;
import com.example.demoCRUD.dto.request.IntrospectResquest;
import com.example.demoCRUD.dto.response.AuthenticationResponse;
import com.example.demoCRUD.dto.response.IntrospectResponse;
import com.example.demoCRUD.exception.AppException;
import com.example.demoCRUD.exception.ErrorCode;
import com.example.demoCRUD.reponsitory.UserReponsitory;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
        UserReponsitory userReponsitory;
        @NonFinal

        protected  String SIGNER_KEY =
                "kuHxo8Dl9MNwLGfA8aBN24XzFC38jhkS3SoDxZNYzPcdprNiohVdKPWt13AHEQYe";

        public IntrospectResponse introspect(IntrospectResquest resquest) throws JOSEException, ParseException {
            var token = resquest.getToken();
            JWSVerifier verifier =  new MACVerifier(SIGNER_KEY.getBytes());

            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified = signedJWT.verify(verifier);

            return
                    IntrospectResponse.builder()
                            .valid(verified && expiryTime.after(new Date()))
                            .build();

        }
        public AuthenticationResponse AuthenticationResponse  (AuthenticationResquest resquest){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            var user = userReponsitory.findByUsername(resquest.getUsername())
                    .orElseThrow(()-> new AppException(ErrorCode.USERNAME_NOT_VALIDAL));


            boolean authenticated =  passwordEncoder.matches(resquest.getPassword(),
                    user.getPassword());
            if(!authenticated)
                throw  new AppException(ErrorCode.UNAUTHENTICATED);
            var token = generateToken(resquest.getUsername());

            return AuthenticationResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();
        }
        private String generateToken(String username){
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer("Gmail.com")
                    .issueTime(new Date())
                    .expirationTime(new Date(
                            Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                    ))
                    .claim("customer", "Custom")
                    .build();
            Payload payload = new Payload(jwtClaimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(header, payload);

            try {
                jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
                return jwsObject.serialize();
            } catch (JOSEException e) {

                throw new RuntimeException(e);
            }
        }
}
