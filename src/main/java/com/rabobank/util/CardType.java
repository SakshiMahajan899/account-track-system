/**
 * 
 */
package com.rabobank.util;





//@RequiredArgsConstructor
public enum CardType {
	
	DEBIT("DEBIT"),
	CREDIT("CREDIT");

	/**
	 * @param string
	 */
	CardType(String type) {
		this.type=type;
	}

	private final String type;
	

}
