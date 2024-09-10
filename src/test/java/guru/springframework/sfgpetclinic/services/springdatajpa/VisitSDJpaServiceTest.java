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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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
        //given
        Set<Visit> visits = new HashSet<>();
        visits.add(new Visit(1L, LocalDate.now()));
        visits.add(new Visit(2L, LocalDate.now()));
        given(visitRepository.findAll()).willReturn(visits);
        //when
        Set<Visit> foundVisits = service.findAll();
        //then
        then(visitRepository).should().findAll();
        assertThat(foundVisits).hasSize(2);
    }

    @DisplayName("Test to find by Id")
    @Test
    void findById() {
        //given
        Visit visit = new Visit(1L, LocalDate.now());
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        //when
        Visit foundVisit = service.findById(1L);
        //then
        then(visitRepository).should().findById(1L);
        assertThat(foundVisit).isNotNull();
    }

    @DisplayName("Test to save Visit")
    @Test
    void save() {
        //given
        Visit visit = new Visit(1L, LocalDate.now());
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        //when
        Visit savedVisit = service.save(visit);
        //then
        then(visitRepository).should().save(any(Visit.class));
        assertThat(savedVisit).isNotNull();
    }

    @DisplayName("Test to delete Visit object")
    @Test
    void delete() {
        //given
        Visit visit = new Visit();
        //when
        service.delete(visit);
        //then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @DisplayName("Test to delete Visit by Id")
    @Test
    void deleteById() {
        //when
        service.deleteById(1L);
        //then
        then(visitRepository).should().deleteById(1L);
    }
}