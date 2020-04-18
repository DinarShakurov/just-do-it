package ru.shakurov.spring_webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private List<GoalDto> active;
    private List<GoalDto> waiting;
    private List<GoalDto> deleted;
    private List<GoalDto> completed;
    private UserDto user;

}
