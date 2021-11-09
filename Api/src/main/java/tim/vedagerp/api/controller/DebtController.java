package tim.vedagerp.api.controller;


import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import tim.vedagerp.api.model.DebtDTO;
import tim.vedagerp.api.model.Message;
import tim.vedagerp.api.services.DebtService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/debt")
public class DebtController {

	@Autowired
	DebtService debtService;
	
	private static Logger logger = LogManager.getLogger(DebtController.class);

	@GetMapping()
	public ResponseEntity<?> getDebt(
			@RequestParam("sort") String sort,
			@RequestParam("order") String order,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("id") Long id) {
		logger.info("getDebt");
		Page<Debt> debts = debtService.listSortOrder(sort,order,page,size,id);
		return new ResponseEntity<>(debts, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<?> putDebt(@RequestBody Debt body) {
		logger.info("putDebt");
		try {
			Debt debt = debtService.update(body);
			return new ResponseEntity<>(debt, HttpStatus.OK);
		} catch (HttpMessageNotReadableException ex) {
			return new ResponseEntity<>("Le body n'existe pas.", HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDebt(@PathVariable("id") long id) {
		logger.info("getDebt");
		try {
			Debt debt = debtService.get(id);
			return new ResponseEntity<>(debt, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			Message res = new Message();
			res.setText(String.format("Pas de valeur pour id: %d", id));
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delDebt(@PathVariable("id") long id) {
		logger.info("delDebt");

		try {
			Message res = new Message();
			res.setText(debtService.delete(id));
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			return new ResponseEntity<>(String.format("Id %d n'existe pas.", id), HttpStatus.OK);
		}

	}

}
