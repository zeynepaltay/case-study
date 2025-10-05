package Case.Study.DIGITOPIA.services.impl;

import Case.Study.DIGITOPIA.repositories.OrganizationRepository;
import Case.Study.DIGITOPIA.services.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
}
