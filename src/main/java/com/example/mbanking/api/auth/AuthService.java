package com.example.mbanking.api.auth;

import com.example.mbanking.api.auth.web.AuthDto;
import com.example.mbanking.api.auth.web.LogInDto;
import com.example.mbanking.api.auth.web.RegisterDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    void register(RegisterDto registerDto );
    void verify(String email);
    void checkVerify(String email, String verifiedCode);
    AuthDto login(LogInDto logInDto);
}
