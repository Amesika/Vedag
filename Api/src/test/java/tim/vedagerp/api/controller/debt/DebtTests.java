package tim.vedagerp.api.controller.debt;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import tim.vedagerp.api.entities.Account;
import tim.vedagerp.api.entities.Debt;
import tim.vedagerp.api.mapper.DebtMapper;
import tim.vedagerp.api.model.DebtDTO;

public class DebtTests {

    private DebtMapper debtMapper = Mappers.getMapper(DebtMapper.class);
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Test
    public void givenDebtMappingToDebtDTO_whenMaps_thenCorrect() throws ParseException  {

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
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
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
}
