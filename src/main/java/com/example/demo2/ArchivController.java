package com.example.demo2;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/archiv")
public class ArchivController {

  @Autowired

  private ArchivRepository archivRepository;

  @PostMapping(path = "/add")
  public @ResponseBody String addNewArchiv(
      @RequestParam Integer task_id,
      @RequestParam Integer user_id,
      @RequestParam String task_name,
      @RequestParam String description,
      @RequestParam LocalDateTime due_date,
      @RequestParam Boolean is_completed,
      @RequestParam LocalDateTime archived_at) {

    Archiv a = new Archiv();
    a.setTaskId(task_id);
    a.setUserId(user_id);
    a.setTaskName(task_name);
    a.setDescription(description);
    a.setDueDate(due_date);
    a.setIsCompleted(is_completed);
    a.setArchivedAt(archived_at);
    archivRepository.save(a);
    return "Saved";
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Archiv> getAllArchiv() {
    return archivRepository.findAll();
  }

  @DeleteMapping(path = "/delete")
  public @ResponseBody String deleteArchiv(@RequestParam Integer task_id) {
    if (archivRepository.existsById(task_id)) {
      archivRepository.deleteById(task_id);
      return "Deleted";
    } else {
      return "Archiv entry not found";
    }
  }

  @PutMapping(path = "/update")
  public @ResponseBody String updateArchiv(
      @RequestParam Integer task_id,
      @RequestParam Integer user_id,
      @RequestParam String task_name,
      @RequestParam String description,
      @RequestParam LocalDateTime due_date,
      @RequestParam Boolean is_completed,
      @RequestParam LocalDateTime archived_at) {

    if (archivRepository.existsById(task_id)) {
      Archiv a = archivRepository.findById(task_id).get();
      a.setUserId(user_id);
      a.setTaskName(task_name);
      a.setDescription(description);
      a.setDueDate(due_date);
      a.setIsCompleted(is_completed);
      a.setArchivedAt(archived_at);
      archivRepository.save(a);
      return "Updated";
    } else {
      return "Archiv entry not found";
    }
  }
}
