package com.smartfoodsaver.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

public class MealPlan {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @ManyToOne(fetch = FetchType.LAZY) private User user; // null when guest
  private LocalDate startsOn;
  private LocalDate endsOn;
  private boolean guest;

  @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MealPlanEntry> entries = new ArrayList<>();
}
