package com.example.demo2;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(ArchivController.class)
public class ArchivControllerTests {

    @Autowired
    private ArchivController archivController;

    @MockBean
    private ArchivRepository archivRepository;

    private Archiv archiv;

    @BeforeEach
    void setUp() {
        archiv = new Archiv();
        archiv.setTaskId(1);
        archiv.setUserId(1);
        archiv.setTaskName("Test Task");
        archiv.setDescription("Test Description");
        archiv.setDueDate(LocalDateTime.now().plusDays(1));
        archiv.setIsCompleted(false);
        archiv.setArchivedAt(LocalDateTime.now());
    }

    @Test
    void testAddNewArchiv() {
        String response = archivController.addNewArchiv(1, 1, "Test Task", "Test Description", 
                                          LocalDateTime.now().plusDays(1), false, LocalDateTime.now());
        assertThat(response).isEqualTo("Saved");
    }

    @Test
    void testGetAllArchiv() {
        Mockito.when(archivRepository.findAll()).thenReturn(List.of(archiv));
        Iterable<Archiv> response = archivController.getAllArchiv();
        assertThat(response).contains(archiv);
    }

    @Test
    void testDeleteArchiv() {
        Mockito.when(archivRepository.existsById(1)).thenReturn(true);
        String response = archivController.deleteArchiv(1);
        assertThat(response).isEqualTo("Deleted");
    }

    @Test
    void testDeleteNonExistingArchiv() {
        Mockito.when(archivRepository.existsById(1)).thenReturn(false);
        String response = archivController.deleteArchiv(1);
        assertThat(response).isEqualTo("Archiv entry not found");
    }

    @Test
    void testUpdateArchiv() {
        Mockito.when(archivRepository.existsById(1)).thenReturn(true);
        Mockito.when(archivRepository.findById(1)).thenReturn(Optional.of(archiv));
        String response = archivController.updateArchiv(1, 1, "Updated Task", 
                                      "Updated Description", LocalDateTime.now().plusDays(2), true, LocalDateTime.now());
        assertThat(response).isEqualTo("Updated");
    }

    @Test
    void testUpdateNonExistingArchiv() {
        Mockito.when(archivRepository.existsById(1)).thenReturn(false);
        String response = archivController.updateArchiv(1, 1, "Updated Task", 
                                      "Updated Description", LocalDateTime.now().plusDays(2), true, LocalDateTime.now());
        assertThat(response).isEqualTo("Archiv entry not found");
    }
}