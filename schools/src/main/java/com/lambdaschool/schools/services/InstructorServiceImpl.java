package com.lambdaschool.schools.services;


import com.lambdaschool.schools.exceptions.ResourceNotFoundException;
import com.lambdaschool.schools.models.AdviceData;
import com.lambdaschool.schools.models.AdviceSlip;
import com.lambdaschool.schools.models.Instructor;
import com.lambdaschool.schools.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Transactional
@Service(value="instructorService")
public class InstructorServiceImpl
		implements InstructorService {

	private final InstructorRepository instructorRepo;
	private final RestTemplate         restTemplate = new RestTemplate();

	@Autowired
	public InstructorServiceImpl(InstructorRepository instructorRepo) {
		this.instructorRepo = instructorRepo;
	}

	@Transactional
	@Override
	public Instructor addAdvice(long id) {
		Instructor currentInst = instructorRepo.findById(id)
		                                       .orElseThrow(() -> new ResourceNotFoundException(
				                                       "Instructor with id " + id + " Not Found!"));
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		restTemplate.getMessageConverters()
		            .add(converter);

		String                                 requestUrl     = "https://api.adviceslip.com/advice";
		ParameterizedTypeReference<AdviceData> responseType   = new ParameterizedTypeReference<>() {};
		ResponseEntity<AdviceData>             responseEntity = restTemplate.exchange(
				requestUrl,
				HttpMethod.GET,
				null,
				responseType
		);
		AdviceSlip                             adviceSlip     = responseEntity.getBody()
		                                                                      .getSlip();
		currentInst.setAdvice(adviceSlip.getAdvice());
		instructorRepo.save(currentInst);
		return currentInst;
	}

}
