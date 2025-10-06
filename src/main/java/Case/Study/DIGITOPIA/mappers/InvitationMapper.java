package Case.Study.DIGITOPIA.mappers;

import Case.Study.DIGITOPIA.dtos.requests.InvitationRequest;
import Case.Study.DIGITOPIA.dtos.responses.InvitationResponse;
import Case.Study.DIGITOPIA.models.Invitation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvitationMapper {
    Invitation toEntity(InvitationRequest request);
    InvitationResponse toResponse(Invitation invitation);
}
