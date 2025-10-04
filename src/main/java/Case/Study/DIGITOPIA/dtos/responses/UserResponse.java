package Case.Study.DIGITOPIA.dtos.responses;

import Case.Study.DIGITOPIA.models.Organization;
import Case.Study.DIGITOPIA.models.enums.Role;
import Case.Study.DIGITOPIA.models.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private String email;
    private UserStatus userStatus; //bu silinmeli mi? kendi mi seçiyo yo bence silinmeli
    private String fullName;
    private String normalizedName;// bunu kendi mi yazıyo? bence bu sadece bizim işlememiz için?
    private Role role;
    private Date createdAt; //bunlar kalmalı mı ya???
    private Date updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private Set<Organization> organization = new HashSet<>();
}
