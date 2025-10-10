package Case.Study.DIGITOPIA.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;
import java.util.UUID;

public class AuditorAwareImpl implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) return Optional.empty();

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String userIdHeader = request.getHeader("X-User-Id");

        if (userIdHeader != null) {
            try {
                return Optional.of(UUID.fromString(userIdHeader));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
