package com.acme.yupanaapi.helpers;

public class UserTesting {
	public static class Users{
		private static int idSeller = 1;
		public static int getIdSeller() {
			return idSeller;
		}
		public static void setIdSeller(int idSeller) {
			Users.idSeller = idSeller;
		}
	}
}
