package tim.vedagerp.api.services;


import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.Debt;
import tim.vedagerp.api.repositories.DebtRepository;


@Service
public class DebtService {

	@Autowired
	DebtRepository debtRepository;

	// Liste des dêttes
	public Page<Debt> listSortOrder(String sort, String order, int page, int size, Long id) {
		Pageable pageable = null;
		pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

		return debtRepository.findAll(pageable);
	}

	// Récupérer une dêtte
	public Debt get(long id) throws NoSuchElementException {
		return debtRepository.findById(id).get();
	}

	// Créer une dêtte
	public Debt add(Debt body) {
		return debtRepository.saveAndFlush(body);
	}

	// Modifier une dêtte
	public Debt update(Debt body) {
		return debtRepository.saveAndFlush(body);
	}

	// Supprimer une dêtte
	public String delete(long id) throws EmptyResultDataAccessException {
		debtRepository.deleteById(id);
		return "Success";
	}

}
