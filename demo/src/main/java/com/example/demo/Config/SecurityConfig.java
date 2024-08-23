package com.example.demo.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //Bu anatasyon bu sınıfın bir konfigürasyon sınıfı olduğunu belirtir
@EnableWebSecurity //Spring Security için web güvenliğini etkinleştirir.
@EnableMethodSecurity //Metot bazlı güvenlik anatasyonlarını etkinleştirir.
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //Şifrelei güvenli bir şekilde hash'ler
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //CSRF koruması devre dışı bırakılır.(Genellikle stateless API'ler için kullanılır.)
        http.csrf(csrf -> csrf.disable())
                //Gelen http isteklerini yetkilendirme kurallarına göre konttol eder
                .authorizeHttpRequests((authorize) -> {
                    //"api/v1/**" yoluna gelen istekler için yetkilendirme yapmadan izin veirlir.
                    authorize.requestMatchers("api/v1/**").permitAll();

                    //Diğer tüm istekler için, kimlik doğrulaması gereklidir.
                    authorize.anyRequest().authenticated();
                })

            //Temel HTTP kimlik doğrulamasını (Basic Auth) kullanır
        .httpBasic(Customizer.withDefaults());

        //Yapılandırmayı tamamlar ve SecurityFilterChain nesnesine döner
        return http.build();


      }
    // AuthenticationManager, kimlik doğrulama işlemlerini yönetmek için kullanılan ana bileşendir.

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
          return configuration.getAuthenticationManager();
      }
}


/*
@Configuration -> Bu sınıfın bir spring konfigurasyon sınıfı olduğunu belirtir.
Bu sınıftaki bean tanımlamaları Spring IOC konteynerlarıne eklenir.

@EnableWebSecurtiy -> Web tabanlı güvenlik yapılandırmasını etkinleştirir.

@EnableMethodSecurity -> Metot düzeyinde güvenlij anatasyonlarını (örneğin,
@PreAuthorize, @Secured) kullanabilmeyi amaçlar

@"PasswordEncoder"Bean'i -> Şifrleri güvenli bir şekilde hash'lemek için "BcrypPasswordEncoder"
 kullanır. Bu şifrelerin güvenli bir şekilde saklanmasını sağlar.

SecurityFilterChain Bean'i: Bu, gelen HTTP isteklerine yönelik güvenlik yapılandırmasını belirler.

CSRF koruması devre dışı bırakılır (genellikle stateless API'ler için bu yapılır).
"api/v1/**" yolu için tüm istekler izinli, diğer yollar ise kimlik doğrulaması gerektirir.
HTTP Basic Authentication kullanılır.
UserDetailsService Bean'i: Bu bean, bellek içi kullanıcı yönetimi sağlar. İki kullanıcı tanımlanır:

john: USER rolüne sahip bir kullanıcı.
sam: ADMIN rolüne sahip bir kullanıcı.

 */



















