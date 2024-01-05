package com.spring.tming.domain.sample.service;

import com.spring.tming.domain.sample.dto.response.SampleGetRes;
import com.spring.tming.domain.sample.dto.response.SampleSaveRes;
import com.spring.tming.domain.sample.entity.Sample;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SampleServiceMapper {

    SampleServiceMapper INSTANCE = Mappers.getMapper(SampleServiceMapper.class);

    SampleSaveRes toSampleSaveRes(Sample sampleEntity);

    SampleGetRes toSampleGetRes(Sample sampleEntity);

    List<SampleGetRes> toSampleGetReses(List<Sample> sampleEntities);
}
