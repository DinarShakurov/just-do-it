package ru.shakurov.spring_webapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shakurov.spring_webapp.dto.GoalDto;
import ru.shakurov.spring_webapp.dto.ProfileDto;
import ru.shakurov.spring_webapp.dto.UserDto;
import ru.shakurov.spring_webapp.models.GoalState;
import ru.shakurov.spring_webapp.models.User;
import ru.shakurov.spring_webapp.repositories.UserRepository;
import ru.shakurov.spring_webapp.services.GoalService;
import ru.shakurov.spring_webapp.services.ProfileService;

import java.util.List;
import java.util.Map;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private GoalService goalService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ProfileDto getUserInfo(Long userId) {
        Map<GoalState, List<GoalDto>> map = goalService.getUsersGoalsSortedByLeftTime(userId);
        User user = userRepository.findById(userId).get();
        return ProfileDto.builder()
                .active(map.get(GoalState.ACTIVE))
                .waiting(map.get(GoalState.WAITING))
                .deleted(map.get(GoalState.DELETED))
                .completed(map.get(GoalState.COMPLETED))
                .user(UserDto.from(user))
                .build();
    }
}
