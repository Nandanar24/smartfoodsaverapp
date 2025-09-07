package com.smartfoodsaver.model;

import jakarta.persistence.*;
//import lombok.*;

//@Entity @Getter @Setter @NoArgsConstructor
public class StorageIdea {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String title;
  private String category;                  // FRIDGE, FREEZER, PANTRY, PRODUCE
  @Column(length=4000) private String body;
  private String imageUrl;
}
