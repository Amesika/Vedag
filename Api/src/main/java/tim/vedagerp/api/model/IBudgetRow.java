package tim.vedagerp.api.model;

public interface IBudgetRow {
	
	float getSolde();
    float getSoldeprev();
    int getMonth();
    Long getId();
    String getLabel();
    public default float getSoldediff(){
        return getSolde()-getSoldeprev();
   };
   
}
