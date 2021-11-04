package tim.vedagerp.api.controller.debt;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.Category;
import tim.vedagerp.api.entities.Debt;
import tim.vedagerp.api.entities.JournalRow;
import tim.vedagerp.api.entities.NameSpace;
import tim.vedagerp.api.mapper.DebtMapper;
import tim.vedagerp.api.model.DebtDTO;
import tim.vedagerp.api.repositories.AccountRepository;
import tim.vedagerp.api.repositories.CategoryRepository;
import tim.vedagerp.api.repositories.DebtRepository;
import tim.vedagerp.api.repositories.JournalRowRepository;
import tim.vedagerp.api.repositories.NameSpaceRepository;
import tim.vedagerp.api.services.AccountService;
import tim.vedagerp.api.services.DebtService;
import tim.vedagerp.api.services.JournalService;
import tim.vedagerp.api.services.NameSpaceService;

@SpringBootTest
public class DebtTest {

    private DebtMapper debtMapper = Mappers.getMapper(DebtMapper.class);
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Autowired
    JournalRowRepository journalRowRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    NameSpaceRepository nameSpaceRepository;

    @Autowired
    NameSpaceService nsService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DebtRepository debtRepository;

    @Autowired
    JournalService journalService;

    @Autowired
    AccountService accountService;

    @Autowired
    DebtService debtService;

    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

    @Test
    public void givenDebtMappingToDebtDTO_whenMaps_thenCorrect() throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Debt entity = new Debt();
        entity.setId(10L);
        entity.setName("Nom");
        entity.setDescription("Description");
        entity.setAmount(15000);
        entity.setCurrentAmount(2500);
        entity.setCreditor("Fevill");
        entity.setStartDate(new Date());
        entity.setRate(3);
        entity.setAccount(new Account());
        entity.setNamespace(new NameSpace());

        DebtDTO dto = debtMapper.toDebtDTO(entity);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getAmount(), dto.getAmount(), 0);
        assertEquals(entity.getCurrentAmount(), dto.getCurrentAmount(), 0);
        assertEquals(entity.getCreditor(), dto.getCreditor());
        assertEquals(entity.getStartDate().toString(), format.parse(dto.getStartDate()).toString());
        assertEquals(entity.getRate(), dto.getRate(), 0);
    }

    @Test
    public void givenDebtDTOMappingToDebt_whenMaps_thenCorrect() throws ParseException {

        DebtDTO dto = new DebtDTO();
        dto.setId(10L);
        dto.setName("Nom");
        dto.setDescription("Description");
        dto.setAmount(15000);
        dto.setCurrentAmount(2500);
        dto.setCreditor("Fevill");
        dto.setStartDate("27-10-2021 22:45:00");
        dto.setRate(3);
        dto.setAccount(new Account());
        dto.setNamespace(new NameSpace());
        Debt entity = debtMapper.toDebt(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getAmount(), entity.getAmount(), 0);
        assertEquals(dto.getCurrentAmount(), entity.getCurrentAmount(), 0);
        assertEquals(dto.getCreditor(), entity.getCreditor());
        assertEquals(format.parse(dto.getStartDate()).toString(), entity.getStartDate().toString());
        assertEquals(dto.getRate(), entity.getRate(), 0);
    }

    @Test
    public void addNewDebt_whenAccount_isCreate() throws Exception {

        journalRowRepository.deleteAll();
        debtRepository.deleteAll();
        accountRepository.deleteAll();

        NameSpace ns = new NameSpace();
        if (nameSpaceRepository.existsByName("Ns test name")) {
            ns = nameSpaceRepository.findByName("Ns test name");
        } else {
            ns.setDescription("Ns test description");
            ns.setName("Ns test name");
            ns = nameSpaceRepository.saveAndFlush(ns);
        }

        Category cat = new Category();
        if (categoryRepository.existsByPrime("prime")) {
            cat = categoryRepository.findByPrime("prime");
        } else {
            cat.setNamespace(ns);
            cat.setPrime("prime");
            cat.setSecond("second");
            cat = categoryRepository.saveAndFlush(cat);
        }

        Account ac1 = new Account();
        ac1.setNamespace(ns);
        ac1.setLabel("Account 1 test label");
        ac1.setLabelBilan("Account 1 test labelBilan");
        ac1.setNumber("1640-xxx1");
        ac1.setCategory(cat);
        ac1 = accountService.add(ac1);

        Account ac2 = new Account();
        ac2.setNamespace(ns);
        ac2.setLabel("Account 2 test label");
        ac2.setLabelBilan("Account 2 test labelBilan");
        ac2.setNumber("5120-xxx2");
        ac2.setCategory(cat);
        ac2 = accountService.add(ac2);

        Account ac3 = new Account();
        ac3.setNamespace(ns);
        ac3.setLabel("Account 3 test label");
        ac3.setLabelBilan("Account 3 test labelBilan");
        ac3.setNumber("3640-xxx3");
        ac3.setCategory(cat);
        ac3 = accountService.add(ac3);

        // ** Ajouter 1OOO euro
        JournalRow journalRow = new JournalRow();
        journalRow.setAmount(1000);
        journalRow.setCredit(ac2);
        journalRow.setDebit(ac1);
        journalRow.setDateOperation(new Date());
        journalRow.setLabel("label 1000");
        journalRow.setNamespace(ns);

        // ** Ajouter 8OO euro
        JournalRow journalRow1 = new JournalRow();
        journalRow1.setAmount(800);
        journalRow1.setCredit(ac1);
        journalRow1.setDebit(ac2);
        journalRow1.setDateOperation(new Date());
        journalRow1.setLabel("label 800");
        journalRow1.setNamespace(ns);

        // Ajouter 1300 euro
        JournalRow journalRow2 = new JournalRow();
        journalRow2.setAmount(1300);
        journalRow2.setCredit(ac3);
        journalRow2.setDebit(ac2);
        journalRow2.setDateOperation(new Date());
        journalRow2.setLabel("label 1300");
        journalRow2.setNamespace(ns);

        // Insert*
        journalService.add(journalRow);
        journalService.add(journalRow1);
        journalService.add(journalRow2);

        assertTrue(debtRepository.existsByAccountNumber("1640-xxx1"));
        assertTrue(debtRepository.existsByAccountNumber("5120-xxx2"));
        assertEquals(200, debtRepository.findByAccountId(ac1.getId()).getCurrentAmount(), 0);
        assertEquals(1100, debtRepository.findByAccountId(ac2.getId()).getCurrentAmount(), 0);

    }

    @Test
    public void updateDebtAmonte_whenJournal_isUpdate() throws Exception {

        this.addNewDebt_whenAccount_isCreate();

        NameSpace ns = new NameSpace();
        ns = nameSpaceRepository.findByName("Ns test name");

        Account ac1 = accountRepository.findByLabel("Account 1 test label");
        Account ac2 = accountRepository.findByLabel("Account 2 test label");

        // ** Mofication de solde 1OOO à 10000 euro
        JournalRow journalRow = journalRowRepository.findByLabel("label 1000");
        journalRow.setAmount(10000);
        journalRow.setDateOperation(new Date());
        journalRow.setLabel("label 10000");

        // ** Ajouter 500 euro
        JournalRow journalRow3 = new JournalRow();
        journalRow3.setAmount(500);
        journalRow3.setCredit(ac2);
        journalRow3.setDebit(ac1);
        journalRow3.setDateOperation(new Date());
        journalRow3.setLabel("label 500");
        journalRow3.setNamespace(ns);

        journalService.add(journalRow3);
        journalService.update(journalRow);

        assertEquals(9700, debtRepository.findByAccountId(ac1.getId()).getCurrentAmount(), 0);
        assertEquals(-8400, debtRepository.findByAccountId(ac2.getId()).getCurrentAmount(), 0);
    }

    @Test
    public void updateDebt() throws ParseException {

        journalRowRepository.deleteAll();
        debtRepository.deleteAll();
        accountRepository.deleteAll();

        NameSpace ns = new NameSpace();
        if (nameSpaceRepository.existsByName("Ns test name")) {
            ns = nameSpaceRepository.findByName("Ns test name");
        } else {
            ns.setDescription("Ns test description");
            ns.setName("Ns test name");
            ns = nameSpaceRepository.saveAndFlush(ns);
        }

        Account ac1 = new Account();
        ac1.setNamespace(ns);
        ac1.setLabel("Account 1 test label to update debt");
        ac1.setLabelBilan("Account 1 test labelBilan to update debt");
        ac1.setNumber("1640-xxx1Udebt");
        ac1 = accountService.add(ac1);

        // Cration de la dêtte
        Debt debtExpected = new Debt();
        debtExpected.setAccount(ac1);
        debtExpected.setCurrentAmount(15000);
        debtExpected.setCreditor("Test");
        debtExpected.setStartDate(format.parse("29-10-2021 11:30:00"));
        debtExpected.setNamespace(ns);
        debtExpected = debtRepository.saveAndFlush(debtExpected);

        // Modification de la dêtte
        debtExpected.setAmount(20000);
        debtExpected.setCurrentAmount(5000);
        debtExpected.setName("Test debt");
        debtExpected.setDescription("Test debt description");
        debtExpected.setCreditor("Test update");
        debtExpected.setStartDate(format.parse("29-10-2021 11:35:00"));
        debtExpected.setRate(8);

        DebtDTO debtDto = new DebtDTO();
        debtDto = debtService.update(debtExpected);

        // Test
        assertEquals(debtExpected.getAmount(), debtDto.getAmount(), 0);
        assertEquals(debtExpected.getCurrentAmount(), debtDto.getCurrentAmount(), 0);
        assertEquals(debtExpected.getRate(), debtDto.getRate(), 0);
        assertEquals(debtExpected.getName(), debtDto.getName());
        assertEquals(debtExpected.getCreditor(), debtDto.getCreditor());
        assertEquals(debtExpected.getDescription(), debtDto.getDescription());
        assertEquals(debtExpected.getStartDate().toString(), format.parse(debtDto.getStartDate()).toString());

    }

    @Test
    public void deleteDebt() throws Exception {

        // Création de la dêtte
        this.addNewDebt_whenAccount_isCreate();

        Account ac1 = accountRepository.findByLabel("Account 1 test label");
        Account ac2 = accountRepository.findByLabel("Account 2 test label");

        // Suppression de la dêtte
        journalRowRepository.deleteAll();
        accountService.delete(ac1.getId());
        /*
         * accountService.delete(ac2.getId());
         * 
         * assertTrue(accountRepository.existsById(ac1.getId()));
         * assertTrue(accountRepository.existsById(ac2.getId()));
         */
    }
}