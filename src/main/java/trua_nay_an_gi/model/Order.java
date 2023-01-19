package trua_nay_an_gi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name="order")
public class Order {


  	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int name;
	
}
