package tim.vedagerp.api.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import tim.vedagerp.api.entities.Debt;
import tim.vedagerp.api.helper.DateFormer;
import tim.vedagerp.api.model.DebtDTO;
import tim.vedagerp.api.services.DebtService;




@Mapper
public abstract class DebtMapper {

    
	

    public DebtDTO toDebtDTO(Debt debt) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormer.DATE_FORMAT);
        DebtDTO debtDto = new DebtDTO();
        debtDto.setId(debt.getId());
        debtDto.setName(debt.getName());
        debtDto.setDescription(debt.getDescription());
        debtDto.setAmount(debt.getAmount());
        debtDto.setDueAmount(debt.getDueAmount());
        debtDto.setCurrentAmount(debt.getCurrentAmount());
        debtDto.setCreditor(debt.getCreditor());
        debtDto.setDeadlineAmount(debt.getDeadlineAmount());
        debtDto.setStartDate(simpleDateFormat.format(debt.getStartDate()));
        debtDto.setRate(debt.getRate());

        //debtDto.setAccount(debt.getAccount());
        debtDto.setNamespace(debt.getNamespace());
        debtDto.updateProperties();
        
        // Récupération du nombre d'échéance max
        //debtDto.setMaxOfDeadline(max);   
    
        
        return debtDto;
    }

    public Debt toDebt(DebtDTO debtDto) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormer.DATE_FORMAT);
        Debt debt = new Debt();
        debt.setId(debtDto.getId());
        debt.setName(debtDto.getName());
        debt.setDescription(debtDto.getDescription());
        debt.setAmount(debtDto.getAmount());
        debt.setDueAmount(debtDto.getDueAmount());
        debt.setCurrentAmount(debtDto.getCurrentAmount());
        debt.setCreditor(debtDto.getCreditor());
        debt.setDeadlineAmount(debtDto.getDeadlineAmount());
        debt.setStartDate(simpleDateFormat.parse(debtDto.getStartDate()));
        debt.setRate(debtDto.getRate());
        //debt.setAccount(debtDto.getAccount());
        debt.setNamespace(debtDto.getNamespace());
        return debt;
    }

    public abstract List<DebtDTO> toDebtDTO(Collection<Debt> transactions);

    public abstract List<Debt> toDebt(Collection<DebtDTO> transactions);


}
