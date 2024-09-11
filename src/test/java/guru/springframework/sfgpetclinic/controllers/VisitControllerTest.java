package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Spy
    PetMapService petService;

    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController controller;

    @Test
    void testLoadPetWithVisit(){
        //given
        Pet pet = new Pet(12L);
        Pet pet13 = new Pet(13L);

        petService.save(pet);
        petService.save(pet13);

        given(petService.findById(anyLong())).willCallRealMethod();
        Map<String, Object> model = new HashMap<>();
        //when
        Visit visit = controller.loadPetWithVisit(12L, model);
        //then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        then(petService).should().findById(anyLong());
        assertThat(visit.getPet().getId()).isEqualTo(12L);
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    void testLoadPetWithVisitStubbing(){
        //given
        Pet pet = new Pet(12L);
        Pet pet13 = new Pet(13L);

        petService.save(pet);
        petService.save(pet13);

        given(petService.findById(anyLong())).willReturn(pet13);
        Map<String, Object> model = new HashMap<>();
        //when
        Visit visit = controller.loadPetWithVisit(13L, model);
        //then
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        then(petService).should().findById(anyLong());
        assertThat(visit.getPet().getId()).isEqualTo(13L);
        verify(petService, times(1)).findById(anyLong());
    }
}