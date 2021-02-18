package br.com.petwebapp.services.validaton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.petwebapp.domain.Cliente;
import br.com.petwebapp.dto.ClienteDTO;
import br.com.petwebapp.repository.ClienteRepository;
import br.com.petwebapp.resources.exception.FieldMessage;

public class ProdutoUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		
		List<FieldMessage> list = new ArrayList<>();

//		inclua os testes aqui, incluindo erros na lista

//		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
//			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido!"));
//		}
//		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
//			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido!"));
//		}
		
		Cliente cli = repo.findByEmail(objDto.getEmail());
		if(cli != null && !objDto.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Este e-mail já existe!"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}
