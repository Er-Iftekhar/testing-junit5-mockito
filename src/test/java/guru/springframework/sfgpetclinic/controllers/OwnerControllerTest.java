package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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

    @InjectMocks
    OwnerController controller;

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