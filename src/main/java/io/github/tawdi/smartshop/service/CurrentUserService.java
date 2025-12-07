package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.domain.entity.Client;
import jakarta.servlet.http.HttpServletRequest;

public interface CurrentUserService {

    Client getCurrentClient(HttpServletRequest request);

    String getCurrentClientId(HttpServletRequest request);
}
