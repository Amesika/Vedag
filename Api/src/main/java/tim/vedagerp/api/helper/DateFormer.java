package tim.vedagerp.api.helper;

import java.util.Calendar;
import java.util.Date;

public class DateFormer {
  
    /** Date du début de l'année */
    public static Date getStartDateOfYear(){
        Date startDate = null;
        Calendar cal = Calendar.getInstance();
        startDate = new Date();
        cal.set(Calendar.DAY_OF_YEAR, 1);    
        startDate = cal.getTime();
        return startDate;
    }

    /** Date de fin d'année */
    public static Date getEndDateOfYear(){
        Date endDate = null;
        Calendar cal = Calendar.getInstance();
        endDate = new Date();
        cal.set(Calendar.MONTH, 11); 
        cal.set(Calendar.DAY_OF_MONTH, 31);
        endDate = cal.getTime();
        return endDate;
    }
}
