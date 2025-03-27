package dev.gabbriellps.gestao.hospitalar.api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        log.info("Inicializado ModelMapper - API");

        final ModelMapper mp = new ModelMapper();
        return mp;
    }

}
