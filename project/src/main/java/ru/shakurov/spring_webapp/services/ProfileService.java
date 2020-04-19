package ru.shakurov.spring_webapp.services;

import ru.shakurov.spring_webapp.dto.ProfileDto;

public interface ProfileService {
   ProfileDto getUserInfo(Long userId);
}
