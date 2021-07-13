package com.worldoffice.worldofficeapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Product {
  
  @Id
  @Column(name = "product_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String brand;
  @Column(nullable = false)
  private Double price;
  @Column(nullable = false)
  private Integer quantity;
  @Column(nullable = false)
  private String state;
  @Column(nullable = false)
  private Float discountPercentage;

}
