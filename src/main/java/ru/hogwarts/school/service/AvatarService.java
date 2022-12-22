package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadAvatar (Long studentId, MultipartFile file) throws IOException;

    Avatar findAvatar(Long studentId);

    List<Avatar> getListOfAvatars(Integer pageNumber, Integer pageSize);
}
