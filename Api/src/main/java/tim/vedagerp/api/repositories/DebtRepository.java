package tim.vedagerp.api.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tim.vedagerp.api.entities.Debt;
import tim.vedagerp.api.model.IMaxInfo;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    Boolean existsByName(String string);

    Optional<Debt> findById(Long id);

    List<Debt> findAllByNameStartsWith(String string);

    List<Debt> findAllByNamespaceId(Long nsId);

    /*
	 * Liste des dêttes négatif
	 */
	@Query(value = "SELECT * FROM public.debt  "
    +"WHERE current_amount < 0 "
    +"AND debt.namespace_id = :nsid "
    +"ORDER BY debt.name", nativeQuery = true)
	List<Debt> getDebts(@Param("nsid") Long nsId);

    @Query(value = "SELECT MAX (ROUND ( ABS(current_amount)/(deadline_amount) )) as nbrmax, start_date as startDate FROM public.debt "
    +" WHERE debt.namespace_id=:nsid"
    +" AND debt.deadline_amount!=0" 
    +" GROUP BY startDate"
    +" ORDER BY nbrmax DESC "
    +" LIMIT 1", nativeQuery = true)
	IMaxInfo getMaxDeadlineNumber(@Param("nsid") Long nsId);


}
