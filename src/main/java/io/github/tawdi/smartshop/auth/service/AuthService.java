package io.github.tawdi.smartshop.auth.service;

import io.github.tawdi.smartshop.dto.auth.LoginRequest;
import io.github.tawdi.smartshop.dto.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
