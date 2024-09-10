package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    @Mock
    OwnerService service;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @InjectMocks
    OwnerController controller;

    @Test
    void testProcessFindFormWildCardString(){
        //given
        Owner owner = new Owner(1L, "firstName", "lastName");
        List<Owner> owners = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(service.findAllByLastNameLike(captor.capture())).willReturn(owners);
        //when
        controller.processFindForm(owner, bindingResult, null);
        //then
        assertThat("%lastName%").isEqualTo(captor.getValue());
    }

    @Test
    void testProcessFindFormWithAnnotation(){
        //given
        Owner owner = new Owner(1L, "firstName", "lastName");
        List<Owner> owners = new ArrayList<>();
        given(service.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(owners);
        //when
        controller.processFindForm(owner, bindingResult, null);
        //then
        assertThat("%lastName%").isEqualTo(stringArgumentCaptor.getValue());
    }

    @Test
    void processCreationFormNoValidationError() {
        //given
        Owner owner = new Owner(5L, "firstName", "lastName");
        given(service.save(owner)).willReturn(owner);
        given(bindingResult.hasErrors()).willReturn(false);
        //when
        String returnedView = controller.processCreationForm(owner, bindingResult);
        //then
        then(service).should().save(any(Owner.class));
        assertThat(returnedView).isEqualTo(REDIRECT_OWNERS_5);
    }

    @Test
    void processCreationFormWithValidationError() {
        //given
        Owner owner = new Owner(5L, "firstName", "lastName");
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        String returnedView = controller.processCreationForm(owner, bindingResult);
        //then
        then(service).should(never()).save(any(Owner.class));
        assertThat(returnedView).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }
}