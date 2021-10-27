package tim.vedagerp.api.entities;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardAccount {
    private String name;
    private BigDecimal balance;
}