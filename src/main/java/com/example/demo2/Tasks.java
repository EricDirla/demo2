package com.example.demo2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

@Entity
public class Tasks {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer task_id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable=false)
  private Users user;

  private String task_name;

  private String description;

  private LocalDateTime due_date;

  private Boolean is_completed;

  public Integer getTaskId() {
    return task_id;
  }

  public void setTaskId(Integer task_id) {
    this.task_id = task_id;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
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
}
