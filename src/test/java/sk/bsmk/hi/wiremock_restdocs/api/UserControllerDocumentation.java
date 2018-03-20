package sk.bsmk.hi.wiremock_restdocs.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class UserControllerDocumentation {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void createUser() throws Exception {

    final CreateUserRequest request = ImmutableCreateUserRequest.builder()
      .name("some-name")
      .about("blah blah blah")
      .build();

    mvc.perform(
      post(UserController.PATH)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsBytes(request))
        .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andDo(document("create-user",
        requestFields(
          fieldWithPath("name").description("Name description"),
          fieldWithPath("about").description("About description")
        ),
        responseFields(
          fieldWithPath("name").description("Name description"),
          fieldWithPath("about").description("About description"),
          fieldWithPath("createdAt").description("CreatedAt description")
        )
      ));
  }

}