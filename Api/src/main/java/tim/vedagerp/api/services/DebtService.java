package tim.vedagerp.api.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import tim.vedagerp.api.model.IMaxInfo;
import tim.vedagerp.api.model.MaxInfoDTO;
import tim.vedagerp.api.repositories.AccountRepository;
import tim.vedagerp.api.repositories.DebtRepository;

@Service
public class DebtService {

	@Autowired
	DebtRepository debtRepository;

	@Autowired
	JournalService journalService;

	@Autowired
	AccountRepository accountRepository;

	DebtMapper debtMapper = Mappers.getMapper(DebtMapper.class);

	// Liste des dêttes
	public List<Debt> list(Long nsId) {
		return debtRepository.getDebts(nsId);
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

			debt.setName(account.getNumber() + " " + account.getLabel());
			// debt.setAccount(account);
			debt.setNamespace(account.getNamespace());
			debt.setStartDate(new Date());

		} else {
			return debt;
		}
		// debt = debtRepository.saveAndFlush(debt);

		return debt;
	}

	// Modifier une dêtte
	public Debt update(Debt body) {
		Debt debt = null;
		debt = debtRepository.saveAndFlush(body);
		return debt;
	}

	// Calcule du solde
	public void calculDebtSolde(Account account) {

		Debt debt = null;

		if (account.getDebt() == null) {
			debt = this.add(account);
			account.setDebt(debt);
			accountRepository.save(account);

		}

		debt = debtRepository.findById(account.getDebt().getId()).get();

		if (debt == null) {
			return;
		}

		Date start = DateFormer.getStartDateOfYear();
		Date end = DateFormer.getEndDateOfYear();

		// Solde du compte
		float amount = this.journalService.getSoldeByNsidSd(account.getNamespace().getId(), start, end,
				account.getId());

		if (debt.getName().startsWith("1640")) {
			debt.setCurrentAmount(amount*(-1));
		} else {
			debt.setCurrentAmount(amount);
		}

		debtRepository.saveAndFlush(debt);
	}

	public void calculDebtSolde(JournalRow journalRow) {

		this.calculDebtSolde(journalRow.getCredit());
		this.calculDebtSolde(journalRow.getDebit());

	}

	// Mise à jour de dêttes
	public String reloadDebt(Long nsId) {

		// la liste des comptes 1620 & 5120
		List<String> options = new ArrayList<String>() {
			{
				add("1640");
				add("5120");
			}
		};

		for (String option : options) {
			List<Account> accounts = accountRepository
					.findAllByNamespaceIdAndNumberStartsWithAndAccountIsNotNullOrderByNumber(nsId, option);

			// boucle sur la liste
			for (Account account : accounts) {


				if(account.getDebt()==null){
					Debt debt =  this.add(account);
					account.setDebt(debt);
				}else{
				// mise à jour du nom
				account.getDebt().setName(account.getNumber() + " " + account.getLabel());
				}
				accountRepository.saveAndFlush(account);
				// Mise à jour du montant
				this.calculDebtSolde(account);
			}
		}

		return "Reload Succes.";
	}


	// Récupération du nombre max des écheances
	public MaxInfoDTO getMaxDeadlineNumber(Long nsId) {

		IMaxInfo dataIMaxinfo = debtRepository.getMaxDeadlineNumber(nsId);
		MaxInfoDTO maxInfoDto = new MaxInfoDTO(dataIMaxinfo);
		return maxInfoDto;
	}
}
