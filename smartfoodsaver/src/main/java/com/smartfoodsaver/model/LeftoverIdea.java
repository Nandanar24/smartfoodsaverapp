package com.smartfoodsaver.model;

import jakarta.persistence.*;

public class LeftoverIdea {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String title;
  @Column(length=4000) private String body;
}
