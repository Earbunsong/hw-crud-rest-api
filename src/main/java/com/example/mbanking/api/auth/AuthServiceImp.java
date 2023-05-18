package com.example.mbanking.api.auth;

import com.example.mbanking.api.auth.web.RegisterDto;
import com.example.mbanking.api.user.User;
import com.example.mbanking.api.user.UserMapStruct;
import com.example.mbanking.utils.MainUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService{

    private final AuthMapper authMapper;
    private final UserMapStruct userMapStruct;
    private final PasswordEncoder encoder;
    private final MainUtil mainUtil;

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
}
