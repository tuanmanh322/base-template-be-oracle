package com.itsol.train.mock.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "M_UNIT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIT_ID")
    private Long unitId;
    @Basic
    @Column(name = "UNIT_NAME")
    private String unitName;
    @Basic
    @Column(name = "PATH")
    private String path;

    @Basic
    @Column(name = "PARENT_UNIT_ID")
    private Long parentUnitId;
}
