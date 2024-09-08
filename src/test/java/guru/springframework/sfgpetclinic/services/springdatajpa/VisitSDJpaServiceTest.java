package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @DisplayName("Test to find all")
    @Test
    void findAll() {
        Set<Visit> visits = new HashSet<>();
        visits.add(new Visit(1L, LocalDate.now()));
        visits.add(new Visit(2L, LocalDate.now()));
        when(visitRepository.findAll()).thenReturn(visits);
        Set<Visit> foundVisits = service.findAll();
        verify(visitRepository).findAll();
        assertThat(foundVisits).hasSize(2);
    }

    @DisplayName("Test to find by Id")
    @Test
    void findById() {
        Visit visit = new Visit(1L, LocalDate.now());
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
        Visit foundVisit = service.findById(1L);
        verify(visitRepository).findById(anyLong());
        assertThat(foundVisit).isNotNull();
    }

    @DisplayName("Test to save Visit")
    @Test
    void save() {
        Visit visit = new Visit(1L, LocalDate.now());
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = service.save(visit);
        verify(visitRepository).save(any(Visit.class));
        assertThat(savedVisit).isNotNull();
    }

    @DisplayName("Test to delete Visit object")
    @Test
    void delete() {
        Visit visit = new Visit();
        service.delete(visit);
        verify(visitRepository).delete(any(Visit.class));
    }

    @DisplayName("Test to delete Visit by Id")
    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(visitRepository).deleteById(anyLong());
    }
}