package com.example.demo.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController //Bu sınıfın bir RESTful web servisi denetleyicisi olduğunu belirtir.
@RequestMapping("api/v1/employees") //temel url
public class EmployeeController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//Bu metot için rol tabanlı yetkilendirme yapar. Yalnızca USER veya ADMIN rolüne sahip kullanıcılar kullanabilir.
    public String getAllEmployees() {
        return "Bütün Elemanları Erişildi.";
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveEmployees() {
        return "Yeni eleman eklendi";
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateEmployees() {
        return "Eleman Güncellendi.";
    }
}
