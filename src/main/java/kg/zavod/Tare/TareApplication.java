package kg.zavod.Tare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//TODO: совместить ветку докер и внести в env данные для jwt
@SpringBootApplication
public class TareApplication {

	public static void main(String[] args) {
		SpringApplication.run(TareApplication.class, args);
	}

}
