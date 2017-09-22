package byaj;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEmailTools
public class CommonAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonAppApplication.class, args);
	}
}
