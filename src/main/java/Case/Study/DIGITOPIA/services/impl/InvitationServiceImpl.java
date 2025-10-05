package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.repositories.InvitationRepository;
import Case.Study.DIGITOPIA.services.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
}
