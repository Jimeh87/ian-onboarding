package ianonboarding.user.controller.phoneController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ianonboarding.user.core.phone.Phone;
import ianonboarding.user.core.phone.PhoneService;
import ianonboarding.user.core.phone.PhoneValidator;

@RestController
@RequestMapping("/api/v1/users/{userId}/phones")
public class PhoneController {
	
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private PhoneValidator phoneValidator;
	@Autowired
	private PhoneDtoAssembler phoneDtoAssembler;
	
	@GetMapping("/{phoneId}")
	public PhoneDto getPhone(@PathVariable("phoneId") UUID phoneId) {
		Phone phone = phoneService.getPhone(phoneId);
		return phoneDtoAssembler.assemble(phone);
	}
	
	@GetMapping
	public List<PhoneDto> getPhones(@PathVariable("userId") UUID userId){
		return phoneService.getPhonesByUserId(userId)
				.stream()
				.map(phone -> phoneDtoAssembler.assemble(phone))
				.collect(Collectors.toList());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PhoneDto createPhone(@PathVariable("userId") UUID userId, @RequestBody PhoneDto phoneDto) {
		phoneDto.setUserId(userId);
		phoneValidator.validateAndThrow(phoneDto);
		Phone phone = phoneDtoAssembler.disassemble(phoneDto);
		phone = phoneService.save(phone);
		return phoneDtoAssembler.assemble(phone);
	}
	
	@PutMapping("{phoneId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePhoneNumber(@PathVariable("userId") UUID userId, @PathVariable("phoneId") UUID phoneId, @RequestBody PhoneDto phoneDto) {
		phoneDto.setUserId(userId);
		phoneDto.setPhoneId(phoneId);
		phoneValidator.validateAndThrow(phoneDto);
		Phone phone = phoneDtoAssembler.disassemble(phoneDto);
		phone = phoneService.save(phone);
	}
}
