package com.example.demo.Config;

import com.example.demo.Entity.Employee;
import com.example.demo.Repository.EmployeeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service  // Bu anotasyon, sınıfın bir servis bileşeni olduğunu ve Spring tarafından yönetileceğini belirtir.
@Data //Lombok getter setter cart curt filan
public class EmployeeDetailsService implements UserDetailsService {

    @Autowired // Spring'in bağımlılık enjeksiyonu mekanizması; EmployeeRepository'nin otomatik olarak enjekte edilmesini sağlar.
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        // Kullanıcı adını alarak, veritabanında bu kullanıcı adına sahip bir kullanıcıyı bulmaya çalışır.

        // Kullanıcının sahip olduğu rollerin yetkilere (GrantedAuthority) dönüştürülmesi.
        Set<GrantedAuthority> authorities = employee.getRoles().stream()
                .map((roles) -> new SimpleGrantedAuthority(roles.getName()))// Her bir rolü SimpleGrantedAuthority'ye dönüştürür.
                .collect(Collectors.toSet());// Yetkileri bir Set koleksiyonuna toplar.

        // UserDetails nesnesini döner, bu nesne Spring Security tarafından kullanıcı kimlik doğrulama işlemlerinde kullanılır.
        return new org.springframework.security.core.userdetails.User(
                username,
                employee.getPassword(),
                authorities
        );

    }

}
