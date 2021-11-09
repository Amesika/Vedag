package tim.vedagerp.api.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import tim.vedagerp.api.entities.Debt;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    Boolean existsByAccountNumber(String string);

    Debt findByAccountId(Long id);

    Boolean existsByName(String string);

    Optional<Debt> findById(Long id);

    List<Debt> findAllByNameStartsWith(String string);

}
