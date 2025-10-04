package com.smartfoodsaver.model;

import jakarta.persistence.*;
import java.util.*;

public class Recipe {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String title;
  @Column(length=2000) private String description;
  private Integer prepMinutes;
  private Integer calories;
  private String imageUrl;

  @ElementCollection
  private Set<String> tags = new HashSet<>();
}
