package cl.duoc.cloudnative.cursos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/cursos/**").hasAnyRole("ADMIN", "INSTRUCTOR", "ESTUDIANTE")
                        .requestMatchers(HttpMethod.POST, "/api/cursos").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/cursos/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/cursos/*/contenidos/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/inscripciones").hasAnyRole("ADMIN", "ESTUDIANTE")
                        .requestMatchers(HttpMethod.GET, "/api/inscripciones/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/examenes").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/examenes/**").hasAnyRole("ADMIN", "INSTRUCTOR", "ESTUDIANTE")
                        .requestMatchers(HttpMethod.POST, "/api/examenes/*/rendir").hasAnyRole("ADMIN", "ESTUDIANTE")
                        .requestMatchers(HttpMethod.POST, "/api/bff/examenes/*/rendir").hasAnyRole("ADMIN", "ESTUDIANTE")
                        .requestMatchers(HttpMethod.POST, "/api/bff/colas/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::authoritiesFromExtensionRole);
        return converter;
    }

    private Collection<GrantedAuthority> authoritiesFromExtensionRole(Jwt jwt) {
        Object roleClaim = jwt.getClaims().get("extension_Role");
        if (roleClaim instanceof String role) {
            return List.of(toAuthority(role));
        }
        if (roleClaim instanceof Collection<?> roles) {
            return roles.stream()
                    .flatMap(role -> role == null ? Stream.empty() : Stream.of(toAuthority(role.toString())))
                    .toList();
        }
        return List.of();
    }

    private GrantedAuthority toAuthority(String role) {
        String normalizedRole = role.trim().toUpperCase();
        if (normalizedRole.startsWith("ROLE_")) {
            return new SimpleGrantedAuthority(normalizedRole);
        }
        return new SimpleGrantedAuthority("ROLE_" + normalizedRole);
    }
}
