package tim.vedagerp.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "namespace")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NameSpace {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

		
	@Column(nullable = false)
	private String name;
	
	@Column()
	private String description;

	
}
