package net.guides.springboot2.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.* ;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import net.guides.springboot2.crud.exception.ResourceNotFoundException;
import net.guides.springboot2.crud.model.Candidate;
//import net.guides.springboot2.crud.repository.EmployeeRepository;
import net.guides.springboot2.crud.repository.CandidateRepository ;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/v1")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;
    List<Candidate> matches ;

    @GetMapping("/interviews")
    public List<Candidate> getAllParticipants() {
        return candidateRepository.findAll();
    }

    @GetMapping("/interviews/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable(value = "id") Long candidateId)
            throws ResourceNotFoundException {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found for this id :: " + candidateId));
        return ResponseEntity.ok().body(candidate);
    }

    @PostMapping("/interviews")
    public Candidate createCandidate(@Validated @RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @GetMapping("/interviewstag")
    public List<Candidate> getCandidateByTag(@RequestParam(value = "tag" , defaultValue = "NOSUB")String tag ,@RequestParam(value = "time" , defaultValue = "NOSUB")String time_date1) {
        List<Candidate> matches ;
        List<Candidate> resultant = new LinkedList<>() ;
        System.out.println("Hey ! ");
        System.out.println(tag);
        System.out.println(time_date1);
        matches =  candidateRepository.findByTag(tag) ;
        int length = matches.size() ;
        System.out.println("Length = " + length);
        for(int candidate = 0 ; candidate < length ; candidate ++)
        {
	    System.out.println("Point!") ; 
            System.out.println(matches.get(candidate).getTime_date1());
            if(time_date1.equals(matches.get(candidate).getTime_date1()) || time_date1.equals("NOSUB"))
            {
                System.out.println("In here !");
                resultant.add(matches.get(candidate)) ;
            }

        }
        return  resultant ;
    }



	/*
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setEmailId(employeeDetails.getEmailId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	*/
}

