package com.spring.tming.domain.sample.repository;

import com.spring.tming.domain.sample.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {

    Sample findBySampleId(Long sampleId);
}
