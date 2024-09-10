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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
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
    void testDeleteByObject(){
        //given
        Speciality speciality = new Speciality();
        //when
        service.delete(speciality);
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testDeleteByIdAtLeastOnce() {
        //given - none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void testDeleteByIdTimes() {
        //given - none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        then(specialtyRepository).should(times(2)).deleteById(1L);
    }

    @Test
    void testDeleteByIdAtMost() {
        //given - none
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNever() {
        //when
        service.deleteById(1L);
        service.deleteById(1L);
        //then
        then(specialtyRepository).should(never()).deleteById(5L);
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void delete(){
        //when
        service.delete(new Speciality());
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }
//    commented this test as it is repeated using BDD with mockito
//    @Test
//    void testFindById(){
//        Speciality speciality = new Speciality();
//        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
//        Speciality foundSpeciality = service.findById(1L);
//        assertThat(foundSpeciality).isNotNull();
//        verify(specialtyRepository).findById(1L);
//    }

    @Test
    void testFindByIdBdd(){
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(anyLong())).willReturn(Optional.of(speciality));
        //when
        Speciality foundSpeciality = service.findById(1L);
        //then
        assertThat(foundSpeciality).isNotNull();
        then(specialtyRepository).should(times(1)).findById(anyLong());
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void testDoThrow(){
        doThrow(new RuntimeException("Boom")).when(specialtyRepository).delete(any());
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void testDoThrowBdd(){
        //given
        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("Boom"));
        //then
        assertThrows(RuntimeException.class, ()-> specialtyRepository.findById(1L));
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD(){
        willThrow(new RuntimeException("Boom")).given(specialtyRepository).delete(any(Speciality.class));
        assertThrows(RuntimeException.class, ()->specialtyRepository.delete(new Speciality()));
        then(specialtyRepository).should().delete(any());
    }
}