package ph.cafe.io.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationFilter: AuthenticationFilter
) {


    private val AUTH_WHITE_URL = arrayOf(
        "/sign-up",
        "/sign-in",
        "/reissue"
    )

    @Bean
    @Primary
    protected fun config(http: HttpSecurity): HttpSecurity {
        return http
            .csrf {
                it.disable()
            }
            .formLogin {
                it.disable()
            }
            .httpBasic {
                it.disable()
            }
            .authorizeHttpRequests {
                it
                .requestMatchers("/product").hasRole("OWNER")
                .requestMatchers(*AUTH_WHITE_URL).permitAll()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(ExceptionHandlerFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

    }


}