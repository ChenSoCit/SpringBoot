package com.example.demoCRUD.mapper;

import com.example.demoCRUD.dto.request.UserCreateRequest;
import com.example.demoCRUD.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "String")
public interface UserMapper {
    User toUser(UserCreateRequest request);
}
