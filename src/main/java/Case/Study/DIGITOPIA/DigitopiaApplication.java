package Case.Study.DIGITOPIA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DigitopiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitopiaApplication.class, args);
	}

}
