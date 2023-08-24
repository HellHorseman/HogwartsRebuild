package ru.hogwarts.school.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.Model.Avatar;

import java.io.IOException;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar getAvatar(Long id);

    Avatar findStudentAvatar(Long studentId);
}