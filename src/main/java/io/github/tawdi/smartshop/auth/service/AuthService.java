package io.github.tawdi.smartshop.auth.service;

import io.github.tawdi.smartshop.auth.dto.LoginRequest;
import io.github.tawdi.smartshop.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
