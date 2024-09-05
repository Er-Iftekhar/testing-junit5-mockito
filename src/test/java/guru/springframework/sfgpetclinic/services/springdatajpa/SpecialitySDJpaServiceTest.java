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
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    // Method 1
    @Mock
    SpecialtyRepository specialtyRepository = mock(SpecialtyRepository.class);

    @InjectMocks
    SpecialitySDJpaService service = mock(SpecialitySDJpaService.class);

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
    void testDeleteById() {
        service.deleteById(1L);
    }

    @Test
    void delete(){
        service.delete(new Speciality());
    }
}