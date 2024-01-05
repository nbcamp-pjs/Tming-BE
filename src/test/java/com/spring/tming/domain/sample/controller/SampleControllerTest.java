package com.spring.tming.domain.sample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.tming.domain.BaseMvcTest;
import com.spring.tming.domain.sample.dto.request.SampleSaveReq;
import com.spring.tming.domain.sample.dto.response.SampleSaveRes;
import com.spring.tming.domain.sample.service.SampleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = {SampleController.class})
class SampleControllerTest extends BaseMvcTest {
    @MockBean private SampleService sampleService;

    @Test
    @DisplayName("샘플 저장 테스트")
    void 샘플_저장() throws Exception {
        Long sampleId = 1L;
        String title = "title";
        String text = "text";
        SampleSaveReq sampleSaveReq = SampleSaveReq.builder().title(title).text(text).build();
        SampleSaveRes sampleSaveRes = SampleSaveRes.builder().sampleId(sampleId).build();
        when(sampleService.saveSample(any())).thenReturn(sampleSaveRes);
        this.mockMvc
                .perform(
                        post("/v1/sample")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(sampleSaveReq)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
