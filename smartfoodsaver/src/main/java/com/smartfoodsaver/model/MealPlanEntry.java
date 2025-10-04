package com.smartfoodsaver.model;

import jakarta.persistence.*;
import java.time.LocalDate;

public class MealPlanEntry {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @ManyToOne(fetch = FetchType.LAZY) private MealPlan mealPlan;
  private LocalDate date;                   // within plan range
  private String mealType;                  // BREAKFAST/LUNCH/DINNER
  @ManyToOne private Recipe recipe;
  private String notes;
}
