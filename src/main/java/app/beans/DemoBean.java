package app.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import base.scope.SpringViewScoped;


@Component
@SpringViewScoped
public class DemoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer i;
	
	public DemoBean(){
		System.out.println("Funca");
		i=4;
	}
	
	@PostConstruct
	public void init(){
		
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}	

}
