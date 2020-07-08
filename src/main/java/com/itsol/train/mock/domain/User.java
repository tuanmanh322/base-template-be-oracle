package com.itsol.train.mock.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TBL_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "USER_SEQ", allocationSize = 1, name = "USER_SEQ")
    private Long id;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD", length = 70)
    private String password;

    @Column(name = "EMAIL", length = 50)
    private String email;

    @Column(name = "ACTIVATED")
    private Boolean activated = false;

    @Column(name = "LANG_KEY", length = 10)
    private String langKey;

    @Column(name = "IMAGE_URL", length = 256)
    private String imageUrl;

    @Column(name = "ACTIVATION_KEY", length = 20)
    @JsonIgnore
    private String activationKey;

    @Column(name = "RESET_KEY", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "RESET_DATE")
    private LocalDate resetDate = null;

    @CreationTimestamp
    @Column(name = "CREATED_DATE", updatable = false)
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE", insertable = false)
    private LocalDate updatedDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "TBL_USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_NAME", referencedColumnName = "NAME")})
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();
}