package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    @Mock(lenient = true)
    OwnerService service;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @InjectMocks
    OwnerController controller;

    @BeforeEach
    void setUp(){
        given(service.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation->{
                    List<Owner> owners = new ArrayList<>();
                    String name = invocation.getArgument(0);
                    if (name.equals("%lastName%")) {
                        owners.add(new Owner(1L, "firstName", "lastName"));
                        return owners;
                    } else if (name.equals("%foundMultiple%")) {
                        owners.add(new Owner(1L, "firstName1", "lastName1"));
                        owners.add(new Owner(2L, "firstName2", "lastName2"));
                        return owners;
                    } else if (name.equals("%notFound%")){
                        return owners;
                    }
                    throw new RuntimeException("Invalid Argument");
                });
    }

    @Test
    void testProcessFindFormWildCardString(){
        //given
        Owner owner = new Owner(1L, "firstName", "lastName");
        //when
        String viewName = controller.processFindForm(owner, bindingResult, null);
        //then
        assertThat("%lastName%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat(viewName).isEqualTo("redirect:/owners/1");
        verifyNoInteractions(model);
    }

    @Test
    void testProcessFindFormWithAnnotation(){
        //given
        Owner owner = new Owner(1L, "firstName", "foundMultiple");
        InOrder inOrder  = Mockito.inOrder(service, model);
        //when
        String viewName = controller.processFindForm(owner, bindingResult, model);
        //then
        assertThat("%foundMultiple%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat(viewName).isEqualTo("owners/ownersList");
        inOrder.verify(service).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void testProcessFindFormWildCardNotFound(){
        //given
        Owner owner = new Owner(1L, "firstName", "notFound");
        //when
        String viewName = controller.processFindForm(owner, bindingResult, Mockito.mock(Model.class));
        //then
        assertThat("%notFound%").isEqualTo(stringArgumentCaptor.getValue());
        assertThat(viewName).isEqualTo("owners/findOwners");
        verifyNoInteractions(model);
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