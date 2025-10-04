package com.smartfoodsaver.model;

import jakarta.persistence.*;

public class StorageIdea {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String title;
  private String category;                  // FRIDGE, FREEZER, PANTRY, PRODUCE
  @Column(length=4000) private String body;
  private String imageUrl;
}
