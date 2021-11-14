package tim.vedagerp.api.controller;


import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tim.vedagerp.api.entities.Debt;
import tim.vedagerp.api.mapper.DebtMapper;
import tim.vedagerp.api.model.DebtDTO;
import tim.vedagerp.api.model.Message;
import tim.vedagerp.api.services.DebtService;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/debt")
public class DebtController {

	@Autowired
	DebtService debtService;

	private DebtMapper debtMapper = Mappers.getMapper(DebtMapper.class);
	
	private static Logger logger = LogManager.getLogger(DebtController.class);

	@GetMapping()
	public ResponseEntity<?> getDebt(@RequestParam("nsId") Long nsId) {
		logger.info("getDebt");
		List<Debt> debts = debtService.list(nsId);
		List<DebtDTO> debtsDTO = debtMapper.toDebtDTO(debts);
		return new ResponseEntity<>(debtsDTO, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<?> putDebt(@RequestBody DebtDTO body) throws ParseException {
		logger.info("putDebt");
		try {
			Debt debt = debtService.update(debtMapper.toDebt(body));
			DebtDTO debtDTO = debtMapper.toDebtDTO(debt);
			return new ResponseEntity<>(debtDTO, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDebt(@PathVariable("id") long id) throws ParseException {
		logger.info("getDebt");
		try {
			Debt debt = debtService.get(id);
			DebtDTO debtDTO = debtMapper.toDebtDTO(debt);
			logger.info("getDebt : "+debtDTO );
			return new ResponseEntity<>(debtDTO, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			Message res = new Message();
			res.setText(String.format("Pas de valeur pour id: %d", id));
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

	}

	@GetMapping("/reload")
	public ResponseEntity<?> getReloadDebt(@RequestParam("nsId") long nsId) {
		logger.info("getReloadDebt");
		try {
			Message res = new Message();
			res.setText(debtService.reloadDebt(nsId));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			Message res = new Message();
			res.setText(String.format("Pas de valeur pour id: %d", nsId));
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

	}

}
