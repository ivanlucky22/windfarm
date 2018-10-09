package com.pexapark.windfarm;

import com.pexapark.windfarm.config.WindFarmConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ WindFarmConfiguration.class})
public class WindFarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindFarmApplication.class, args);
    }
}
