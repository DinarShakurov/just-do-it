package ru.shakurov.spring_webapp.vk.services;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public interface VkService {
    void authenticateWithVkCode(String code) throws ClientException, ApiException;
}
