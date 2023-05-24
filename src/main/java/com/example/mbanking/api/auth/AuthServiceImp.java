package com.example.mbanking.api.auth;

import com.example.mbanking.api.auth.web.AuthDto;
import com.example.mbanking.api.auth.web.LogInDto;
import com.example.mbanking.api.auth.web.RegisterDto;
import com.example.mbanking.api.user.User;
import com.example.mbanking.api.user.UserMapStruct;
import com.example.mbanking.utils.MainUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService{

    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MainUtil mainUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtEncoder jwtEncoder;

    @Value("${spring.mail.username}")
    private String appMail;

    @Override
    public void register(RegisterDto registerDto) {
        User user = userMapStruct.registerDtoToUser(registerDto);
        user.setIsVerified(false);
        user.setPassword(encoder.encode(user.getPassword()));

        log.info("User: {}", user.getEmail());
        if (authMapper.register(user)){
            //Create user role
            for (Integer role : registerDto.roleIds()){
                authMapper.createUserRole(user.getId(),role);

            }
        }
    }

    @Override
    public void verify(String email) {
        User user = authMapper.selectByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Email has not been found"));

        String verifiedCode = UUID.randomUUID().toString();

        if (authMapper.updateVerifiedCode(email,verifiedCode)){
            user.setVerifiedCode(verifiedCode);
        }else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "User cannot be verified");
        }


        MainUtil.Meta<?> meta = MainUtil.Meta.builder()
                .to(email)
                .from(appMail)
                .subject("Account Verification")
                .templateUrl("auth/verify")
                .data(user)
                .build();

        try {
            mainUtil.send(meta);
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    @Override
    public void checkVerify(String email, String verifiedCode) {
        System.out.println(authMapper.selectByEmailAndVerifiedCode(email, verifiedCode));
        User user = authMapper.selectByEmailAndVerifiedCode(email, verifiedCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not exist in the database"));

        if (!user.getIsVerified()) {
            authMapper.verify(email, verifiedCode);
        }

    }

    @Override
    public AuthDto login(LogInDto logInDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(logInDto.email(),logInDto.password());
       authentication = daoAuthenticationProvider.authenticate(authentication);




//        log.info("Authentication: {}", authentication);
//        log.info("Authentication: {}", authentication.getName());
//        log.info("Authentication: {}", authentication.getCredentials());

        // Logic on basic authorization header
//        String basicAuthFormat = authentication.getName() + ":" + authentication.getCredentials();
//        String encoding = Base64.getEncoder().encodeToString(basicAuthFormat.getBytes());
//
//        log.info("Basic {}", encoding);
//
//        return new AuthDto(String.format("Basic %s", encoding));


        //Create time now
        Instant now = Instant.now();

        //Define Scope
//        String scope = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority )
//                .collect(Collectors.joining(""));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("WRITE"));
//        authorities.add(new SimpleGrantedAuthority("UPDATE"));
//        authorities.add(new SimpleGrantedAuthority("READ"));
//        authorities.add(new SimpleGrantedAuthority("DELETE"));
        authorities.add(new SimpleGrantedAuthority("FULL_CONTROL"));


        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority )
                .collect(Collectors.joining(""));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .claim("scope",scope)
                .build();

        String accessToken= jwtEncoder.encode(
                JwtEncoderParameters.from(jwtClaimsSet)
        ).getTokenValue();



        return new AuthDto("Bearer" , accessToken);
    }

}
