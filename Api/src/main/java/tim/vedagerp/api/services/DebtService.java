package tim.vedagerp.api.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.Category;
import tim.vedagerp.api.entities.DebtDto;
import tim.vedagerp.api.model.Debt;
import tim.vedagerp.api.repositories.DebtRepository;
import tim.vedagerp.api.repositories.CategoryRepository;

@Service
public class DebtService {

	@Autowired
	DebtRepository debtRepository;

	// Liste des dêttes
	public Page<DebtDto> listSortOrder(String sort, String order, int page, int size, Long id) {
		Pageable pageable = null;
		pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

		return debtRepository.findAll(pageable);
	}

	// Récupérer une dêtte
	public DebtDto get(long id) throws NoSuchElementException {
		return debtRepository.findById(id).get();
	}

	// Créer une dêtte
	public DebtDto add(DebtDto body) {
		return debtRepository.saveAndFlush(body);
	}

	// Modifier une dêtte
	public DebtDto update(DebtDto body) {
		return debtRepository.saveAndFlush(body);
	}

	// Supprimer une dêtte
	public String delete(long id) throws EmptyResultDataAccessException {
		debtRepository.deleteById(id);
		return "Success";
	}

}
