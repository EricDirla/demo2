package com.example.demo2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Archiv {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer archiv_id;

  private Integer task_id;

  private Integer user_id;

  private String task_name;

  private String description;

  private LocalDateTime due_date;

  private Boolean is_completed;

  private LocalDateTime archived_at;

  // Getters and Setters

  public Integer getArchivId() {
    return archiv_id;
  }

  public void setArchivId(Integer archiv_id) {
    this.archiv_id = archiv_id;
  }

  public Integer getTaskId() {
    return task_id;
  }

  public void setTaskId(Integer task_id) {
    this.task_id = task_id;
  }

  public Integer getUserId() {
    return user_id;
  }

  public void setUserId(Integer user_id) {
    this.user_id = user_id;
  }

  public String getTaskName() {
    return task_name;
  }

  public void setTaskName(String task_name) {
    this.task_name = task_name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDueDate() {
    return due_date;
  }

  public void setDueDate(LocalDateTime due_date) {
    this.due_date = due_date;
  }

  public Boolean getIsCompleted() {
    return is_completed;
  }

  public void setIsCompleted(Boolean is_completed) {
    this.is_completed = is_completed;
  }

  public LocalDateTime getArchivedAt() {
    return archived_at;
  }

  public void setArchivedAt(LocalDateTime archived_at) {
    this.archived_at = archived_at;
  }
}
