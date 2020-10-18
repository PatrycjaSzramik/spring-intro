package pl.sda.projects.adverts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                    .antMatchers("/h2-console", "/h2-console/**");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder())
                    .usersByUsernameQuery("SELECT username,password, active from USERS where username=?")    //wyciaganie danych na podst uzytkoniwka wpisanego
                    .authoritiesByUsernameQuery("select username, 'ROLE_USER' from users where username=?");  //zarzadzanie uprawnieniami
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()          //authorizeRequests() czysci wszystkie konfiguracje
                    .antMatchers("/register").permitAll()    //register/* dla dzieci register/** dla zagniezdzen
                    .antMatchers("/login").permitAll()
                    .anyRequest().authenticated()     //aby domyslnie zablokowac wszystko
                    .and()
                .formLogin()
                    .defaultSuccessUrl("/")
                    .and()
                .logout()
                    .logoutSuccessUrl("/login");
    }
}
