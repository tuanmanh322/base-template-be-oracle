package com.itsol.train.mock.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TBL_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRO_SEQ")
    @SequenceGenerator(sequenceName = "PRODUCT_SEQ", allocationSize = 1, name = "PRO_SEQ")
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @NotNull
    @Size(max = 100)
    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private Double price;
}
