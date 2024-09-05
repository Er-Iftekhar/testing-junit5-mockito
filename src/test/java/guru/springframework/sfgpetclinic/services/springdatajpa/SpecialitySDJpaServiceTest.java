package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    // Method 1
    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    //Method 2 (using @BeforeEach)
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }

    // Method 3 (using inline mocks)
//    SpecialtyRepository specialtyRepository = mock(SpecialtyRepository.class);
    //    @InjectMocks
//    SpecialitySDJpaService service = mock(SpecialitySDJpaService.class);

    @Test
    void testDeleteByIdAtLeastOnce() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void testDeleteByIdTimes() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, times(2)).deleteById(1L);
    }

    @Test
    void testDeleteByIdAtMost() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNever() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialtyRepository, atMost(5)).deleteById(1L);
        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void delete(){
        service.delete(new Speciality());
    }
}