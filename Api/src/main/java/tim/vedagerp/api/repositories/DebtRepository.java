package tim.vedagerp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import tim.vedagerp.api.entities.Debt;

public interface DebtRepository extends JpaRepository<Debt, Long> {



}
