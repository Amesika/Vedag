package tim.vedagerp.api.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tim.vedagerp.api.helper.DateFormer;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaxInfoDTO {
    

    private int nbrmax;

    private String startDate;

    public MaxInfoDTO(IMaxInfo dataIMaxinfo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormer.DATE_FORMAT);
        this.startDate = simpleDateFormat.format(dataIMaxinfo.getStartDate());
        this.nbrmax = dataIMaxinfo.getNbrmax() +1;

    }

}
