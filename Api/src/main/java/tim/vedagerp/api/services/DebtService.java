package tim.vedagerp.api.services;

import java.util.Date;
import java.util.NoSuchElementException;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.Debt;
import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.helper.DateFormer;
import tim.vedagerp.api.mapper.DebtMapper;
import tim.vedagerp.api.model.DebtDTO;
import tim.vedagerp.api.repositories.DebtRepository;

@Service
public class DebtService {

	@Autowired
	DebtRepository debtRepository;

	@Autowired
	JournalService journalService;

	DebtMapper debtMapper = Mappers.getMapper(DebtMapper.class);

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
	public Debt add(Account account) {
		Debt debt = new Debt();

		if (account == null) {
			return debt;
		}
		if (account.getNumber().startsWith("1640") || account.getNumber().startsWith("5120")) {

			debt = new Debt();

			debt.setName(account.getNumber());
			//debt.setAccount(account);
			debt.setNamespace(account.getNamespace());
			debt.setStartDate(new Date());

		} else {
			return debt;
		}
		//debt = debtRepository.saveAndFlush(debt);

		return debt;
	}

	// Modifier une dêtte
	public Debt update(Debt body) {
		Debt debt = null;
		debt = debtRepository.saveAndFlush(body);
		return debt;
	}

	// Supprimer une dêtte
	public String delete(long id) throws EmptyResultDataAccessException {
		debtRepository.deleteById(id);
		return "Success";
	}

	// Calcule du solde
	public void calculDebtSolde(Account account) {

		Debt debt = debtRepository.findById(account.getDebt().getId()).get();

		if(debt==null){
			return ;
		}

		Date start = DateFormer.getStartDateOfYear();
		Date end = DateFormer.getEndDateOfYear();

		// Solde du compte
		float amount = this.journalService.getSoldeByNsidSd(account.getNamespace().getId(), start, end, account.getId());
		debt.setCurrentAmount(amount);
		
		debtRepository.saveAndFlush(debt);
	}

	public void calculDebtSolde(JournalRow journalRow) {

		this.calculDebtSolde(journalRow.getCredit());
		this.calculDebtSolde(journalRow.getDebit());
	
	}

}
