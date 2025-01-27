package com.rabobank.util;

public class Constants {
	private Constants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String API_ACCOUNTS = "/api/accounts";

	public static final String HAS_AUTHORITY_SCOPE_USER_READ = "hasAuthority('SCOPE_user.read')";

	public static final String HAS_AUTHORITY_SCOPE_USER_WRITE = "hasAuthority('SCOPE_user.write')";

	public static final String TRANSFER_ENDPOINT = "/transfer";
	public static final String WITHDRAW_ENDPOINT = "/withdraw";
	public static final String BALANCE = "/balance";

	public static final String TRANSFER = "TRANSFER";
	public static final String WITHDRAW = "WITHDRAW";

	public static final String AMOUNT = "amount";
	public static final String TO_ACCOUNT_NUMBER = "toAccountNumber";
	public static final String FROM_ACCOUNT_NUMBER = "fromAccountNumber";
	public static final String CARD_TYPE = "cardType";
	public static final String CUSTOMER_ID = "customerId";
}
