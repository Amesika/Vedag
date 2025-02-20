package tim.vedagerp.api.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.FiscalYear;
import tim.vedagerp.api.entities.JournalPrevRow;
import tim.vedagerp.api.entities.NameSpace;
import tim.vedagerp.api.model.AccountSolde;
import tim.vedagerp.api.model.Bilan;
import tim.vedagerp.api.model.BilanDetail;
import tim.vedagerp.api.model.IResultatMonth;
import tim.vedagerp.api.model.Ibalance;
import tim.vedagerp.api.model.ResultatRow;
import tim.vedagerp.api.model.ResultatNsRow;
import tim.vedagerp.api.repositories.AccountRepository;
import tim.vedagerp.api.repositories.FiscalYearRepository;
import tim.vedagerp.api.repositories.JournalPrevRowRepository;

@Service
public class JournalPrevService {

	@Autowired
	JournalPrevRowRepository journalPrevRowRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountService accountService;

	@Autowired
	FiscalYearRepository fiscalYearRepository;

	@Autowired
	FiscalYearService fiscalYearService;

	@Autowired
	NameSpaceService nameSpaceService;

	private static Logger logger = LogManager.getLogger(JournalPrevService.class);

	// Le journal
	public Page<JournalPrevRow> listSortOrder(String sort, String order, int page, int size, Long fyId, Long nsId) {
		Pageable pageable = null;
		logger.info("delAccount");
		if (order.equals("asc")) {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		}
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();

		return journalPrevRowRepository.findAllByNamespaceIdAndDateOperationBetween(pageable, nsId, start, end);
	}

	// Le journal
	public List<JournalPrevRow> list() {

		return journalPrevRowRepository.findAll();
	}

	// Le journal by namespace
	public List<JournalPrevRow> listByNamespaceId(Long id) {

		return journalPrevRowRepository.findAllByNamespaceId(id);
	}

	// Ajouter une écriture comptable
	public JournalPrevRow get(long id) throws NoSuchElementException {
		return journalPrevRowRepository.findById(id).get();
	}

	// Ajouter une écriture comptable
	public JournalPrevRow add(JournalPrevRow body) {
		return journalPrevRowRepository.saveAndFlush(body);
	}

	// Modifier une écriture comptable
	public JournalPrevRow update(JournalPrevRow body) {
		return journalPrevRowRepository.saveAndFlush(body);
	}

	// Supprimer une écriture comptable
	public String delete(long id) throws EmptyResultDataAccessException {
		journalPrevRowRepository.deleteById(id);
		return "Success";
	}

	public List<JournalPrevRow> addAll(List<JournalPrevRow> body) {
		return journalPrevRowRepository.saveAll(body);
	}

	// Récupération du grand livre
	public List<JournalPrevRow> getLedgerByNsAndFy(Long nsId, Long fyId, Long accountid, int month) {
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();
		month = month + 1;
		return journalPrevRowRepository.getLedger(nsId, accountid, start, end, month);
	}

	public List<ResultatRow> getAccount(Long nsId, Long fyId, String account) {

		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();
		return journalPrevRowRepository.getResultatProduits(nsId, start, end);
	}

	public List<ResultatNsRow> getResultatByNs(Long nsId){

		List<ResultatNsRow> rows = new ArrayList<>();

		List<FiscalYear> fys = fiscalYearService.listByNamespaceId(nsId);

		for (FiscalYear fiscalYear : fys) {
			ResultatNsRow row = new  ResultatNsRow();
			List<IResultatMonth> rnr =  journalPrevRowRepository.getResultatByMonth(nsId, fiscalYear.getId());
			row.setFy(fiscalYear);
			row.setResultatsSolde(rnr);
			rows.add(row);
		}

		return rows;

	}

	public List<ResultatRow> getResultatByMonth(Long nsId, Long fyId) {

		Date dateTmp = new Date();
		dateTmp = fiscalYearRepository.findById(fyId).get().getStartDate();

		Date start = null;
		Date end = null;

		List<ResultatRow> rows = new ArrayList<>();

		String[] months = new DateFormatSymbols().getMonths();

		for (int i = 0, j = 1; i < months.length-1; i++,j++) {
			String month = months[i];
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

			String year = df.format(dateTmp);

			Calendar calendar = Calendar.getInstance();  
        	 
			Date d = null;
		
			String date1 = "01/"+j+"/"+year;
			ResultatRow e = new ResultatRow();

			try {
				d = simpleDateFormat.parse(date1);
				calendar.setTime(d);  

				String start1 = calendar.getMinimum(Calendar.DATE)+"/"+j+"/"+year;
				String end1 = calendar.getActualMaximum(Calendar.DATE)+"/"+j+"/"+year;

        		start = simpleDateFormat.parse(start1);  
        		end = simpleDateFormat.parse(end1);

				List<Account> accountsCharges = accountService.listSubLabelBilanStartWith(nsId, "CHARGE");
				List<Account> accountsProduits = accountService.listSubLabelBilanStartWith(nsId, "PRODUIT");

				e = getRSolde(nsId, start, end,accountsCharges,accountsProduits);
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			logger.info(d);
			e.setMonth(month);
			rows.add(e);
		}
		return rows;
	}

	// ** */
	public ResultatRow getResultat(Long nsId, Long fyId) {

		// Récuppération des comptes de charges
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();

		List<Account> accountsCharges = accountService.listSubLabelBilanStartWith(nsId, "CHARGE");
		List<Account> accountsProduits = accountService.listSubLabelBilanStartWith(nsId, "PRODUIT");

		return getRSolde(nsId, start, end,accountsCharges,accountsProduits);
	}

	public ResultatRow getRSolde(Long nsId, Date start, Date end,List<Account> accountsCharges,List<Account> accountsProduits){

		float soldeCharges = 0;
		float soldeProduits = 0;
		float solde = 0;

		ResultatRow row = new ResultatRow();

		for (Account account : accountsCharges) {
			soldeCharges += this.getSoldeByNsidSd(nsId, start, end, account.getId());
		}
		for (Account account : accountsProduits) {
			soldeProduits += this.getSoldeByNsidSd(nsId, start, end, account.getId());
		}

		solde = soldeProduits - soldeCharges;
		// A registre du compte de resultat dans le journal
		Account debit = null;
		Account credit = null;
		Account accountTmp = null;
		JournalPrevRow jr = null;

		NameSpace ns = this.nameSpaceService.get(nsId);

		if (solde >= 0) {
			// gaint/*
			debit = accountService.listSubStartWith(nsId, "1200-0001").get(0);
			credit = accountService.listSubStartWith(nsId, "1010-0002").get(0);
			accountTmp = accountService.listSubStartWith(nsId, "1290-0001").get(0);
			List<JournalPrevRow> jrs = journalPrevRowRepository
					.findByDebitIdAndNamespaceIdAndDateOperationBetween(debit.getId(), nsId, start, end);
			List<JournalPrevRow> jrs2 = journalPrevRowRepository
					.findByCreditIdAndNamespaceIdAndDateOperationBetween(accountTmp.getId(), nsId, start, end);
			if (jrs.size() == 0) {
				jr = new JournalPrevRow(end, "Compte de résultat", debit, credit, solde, ns);
			} else {
				jr = jrs.get(0);
				jr.setAmount(solde);
			}
			if (jrs2.size() != 0) {
				journalPrevRowRepository.delete(jrs2.get(0));
			}
			journalPrevRowRepository.save(jr);
			row.setSolde(solde);
		} else {
			// perte
			debit = accountService.listSubStartWith(nsId, "1010-0002").get(0);
			credit = accountService.listSubStartWith(nsId, "1290-0001").get(0);
			accountTmp = accountService.listSubStartWith(nsId, "1200-0001").get(0);
			List<JournalPrevRow> jrs = journalPrevRowRepository
					.findByCreditIdAndNamespaceIdAndDateOperationBetween(credit.getId(), nsId, start, end);
			List<JournalPrevRow> jrs2 = journalPrevRowRepository
					.findByDebitIdAndNamespaceIdAndDateOperationBetween(accountTmp.getId(), nsId, start, end);
			if (jrs.size() == 0) {
				jr = new JournalPrevRow(end, "Compte de résultat", debit, credit, solde, ns);
			} else {
				jr = jrs.get(0);
				jr.setAmount(solde);
			}
			if (jrs2.size() != 0) {
				journalPrevRowRepository.delete(jrs2.get(0));
			}
			journalPrevRowRepository.save(jr);
			
			solde = this.roundFloat(solde, 2);

			row.setSolde(solde);
		}
		return row;
	}

	public Bilan getBilan(Long nsId, Long fyId) {

		Date start = new Date();
		Date end = new Date();
		float soldePassif = 0;
		float soldeActif = 0;
		Bilan bilan = new Bilan();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();

		// Calcul du solde passif
		List<Account> accountsPassif = accountService.listSubLabelBilanStartWith(nsId, "PASSIF");
		for (Account account : accountsPassif) {
			soldePassif += this.getSoldeByNsidSd(nsId, start, end, account.getId());
		}

		// Calcul du solde Actif
		List<Account> accountsTmp = null;
		accountsTmp = accountService.listSubLabelBilanStartWith(nsId, "ACTIF");
		for (Account account : accountsTmp) {
			soldeActif += this.getSoldeByNsidSd(nsId, start, end, account.getId());
		}

		bilan.setPassif(soldePassif);
		bilan.setActif(soldeActif);

		return bilan;
	}

	/**
	 * Detail des comptes actif
	 * 
	 * @param nsId
	 * @param fyId
	 * @return
	 */
	public List<BilanDetail> getBilanActifDetail(Long nsId, Long fyId) {

		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();

		List<BilanDetail> bilanDetail = new ArrayList<>();

		List<Account> accountsTmp = null;
		accountsTmp = accountService.listSubLabelBilanStartWith(nsId, "ACTIF");
		for (Account account : accountsTmp) {
			BilanDetail detail = new BilanDetail();
			detail.setAccount(account);
			detail.setSolde(this.getSoldeByNsidSd(nsId, start, end, account.getId()));
			bilanDetail.add(detail);
		}
		return bilanDetail;
	}

	/**
	 * Details des comptes passif
	 * 
	 * @param nsId
	 * @param fyId
	 * @return
	 */
	public List<BilanDetail> getBilanPassifDetail(Long nsId, Long fyId) {

		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();

		List<Account> accountsPassif = accountService.listSubLabelBilanStartWith(nsId, "PASSIF");
		List<BilanDetail> bilanDetail = new ArrayList<>();
		for (Account account : accountsPassif) {
			BilanDetail detail = new BilanDetail();
			detail.setAccount(account);
			detail.setSolde(this.getSoldeByNsidSd(nsId, start, end, account.getId()));
			bilanDetail.add(detail);
		}

		return bilanDetail;
	}

	public static Date parseDate(String date) {
		try {
			return (Date) new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	// Récupération de la balance
	public List<Ibalance> getBalance(Long nsId, Long fyId) {
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();
		return journalPrevRowRepository.getBalance(nsId, start, end);
	}

	public List<JournalPrevRow> listByMonth(int month, Long fyId, Long nsId) {

		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();
		month = month + 1;
		return journalPrevRowRepository.getJournalByNsidFyidMonth(nsId, start, end, month);
	}

	public List<AccountSolde> banqAccountSold(Long nsId, Long fyId) {

		// Liste des comptes
		Date start = new Date();
		Date end = new Date();
		start = fiscalYearRepository.findById(fyId).get().getStartDate();
		end = fiscalYearRepository.findById(fyId).get().getEndDate();

		List<Account> accounts = accountRepository.getSubAccounts(nsId, "5120");

		List<AccountSolde> accountSoldes = new ArrayList<>();

		// Liste des soldes plus comptes
		for (Account account : accounts) {
			AccountSolde accountSolde = new AccountSolde();
			accountSolde.setAccount(account);
			accountSolde.setSolde(this.getSoldeByNsidSd(nsId, start, end, account.getId()));
			accountSoldes.add(accountSolde);
		}

		return accountSoldes;
	}

	/**
	 * Solde d'un sous comptes comptable
	 * 
	 */
	public float getSoldeByNsidSd(Long nsId, Date start, Date end, Long subAccountId) {
		float solde = 0;
		float soldeCredit = 0;
		float soldeDebit = 0;
		Account account = new Account();
		account = accountService.get(subAccountId);
		soldeCredit = journalPrevRowRepository.getSoldeCreditByNsidFyid(nsId, subAccountId, start, end);
		soldeDebit = journalPrevRowRepository.getSoldeDebitByNsidFyid(nsId, subAccountId, start, end);
		/** -A */
		if (account.getLabelBilan().equals("PRODUIT") || account.getLabelBilan().equals("PASSIF")) {
			solde = soldeCredit - soldeDebit;
		} else {
			solde = soldeDebit - soldeCredit;
		}

		// solde = soldeDebit-soldeCredit;
		if (account.getNumber().startsWith("1200-0001")) {
			solde = soldeDebit - soldeCredit;
		}

		return this.roundFloat(solde, 2);
	}


	public float getSoldeByNsidFyid(Long nsId, Date start, Date end, Long subAccountId) {
		float solde = 0;
		float soldeCredit = 0;
		float soldeDebit = 0;
		Account account = new Account();
		account = accountService.get(subAccountId);
		soldeCredit = journalPrevRowRepository.getSoldeCreditByNsidFyid(nsId, subAccountId, start, end);
		soldeDebit = journalPrevRowRepository.getSoldeDebitByNsidFyid(nsId, subAccountId, start, end);
		/** -A */
		if (account.getLabelBilan().equals("PRODUIT") || account.getLabelBilan().equals("PASSIF")) {
			solde = soldeCredit - soldeDebit;
		} else {
			solde = soldeDebit - soldeCredit;
		}

		// solde = soldeDebit-soldeCredit;
		if (account.getNumber().startsWith("1200-0001")) {
			solde = soldeDebit - soldeCredit;
		}

		return this.roundFloat(solde, 2);
	}


	public float getSoldeByNsidFyid(Long nsId,Long fyId, Long subAccountId) {
		
		Date start = new Date();
		Date end = new Date();

		start = new Date(fiscalYearRepository.findById(fyId).get().getStartDate().getTime());
		end = new Date(fiscalYearRepository.findById(fyId).get().getEndDate().getTime());
		
		return getSoldeByNsidFyid(nsId,start, end, subAccountId);
	}

	public float getSoldeByNsidMonth(Long nsId,String startstr,String endstr, Long subAccountId) {


		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate1 = LocalDate.parse(startstr, DateTimeFormatter.ofPattern("d-MM-yyyy"));

		LocalDate localDate2 = LocalDate.parse(endstr, DateTimeFormatter.ofPattern("d-MM-yyyy"));

        //local date + atStartOfDay() + default time zone + toInstant() = Date
		Date start = Date.from(localDate1.atStartOfDay(defaultZoneId).toInstant());
		Date end = Date.from(localDate2.atStartOfDay(defaultZoneId).toInstant());
		
		return getSoldeByNsidFyid(nsId,start, end, subAccountId);
	}

	private float roundFloat(float f, int places) {

		BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
		bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
		return bigDecimal.floatValue();
	}

}
