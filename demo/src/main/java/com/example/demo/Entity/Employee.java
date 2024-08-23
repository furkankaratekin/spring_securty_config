package com.example.demo.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity //Bu sınıf bir JPA varlık sınıfı olduğunu gösterir. Yani veritabanında bir tane tabloya denk geldiği anlamındadır
@Data //Lombok . Getter ,setter toString, equals, hashCode gibi metotları otomatik oluşturur.
@AllArgsConstructor //Constructor oluşturur. içinde this olan bir constructor
@NoArgsConstructor //Parametresiz bir constructor oluşturur.
@Table(name = "employee") //Veritabanında employee tablosuna erişim sağlar.

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    //Bu kod,bir çalılşan ile roller arasında çoktan çoğa bir ilişkiyi temsil
    //ederve bu ilişkileri yönetmek için bir ara tablo kullanılır.Çalışan
    //birden fazla role sahip olabilir ve roller de birden fazla çalışanla
    //ilişkili olabilir. İlişkilendirilmiş roller, çalışan nesnesi yüklendiğinde
    //hemen yüklenir ve çalışan üzerinde yapılan işlemler otomatik olarak
    //roller üzerinde de yapılır.
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Roles> roles;

}

//Entity class'lar direk veritabanındaki tabloları temsil etmek için kullanılır.