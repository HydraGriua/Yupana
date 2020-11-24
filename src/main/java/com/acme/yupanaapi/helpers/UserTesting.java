package com.acme.yupanaapi.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.acme.yupanaapi.security.SellerDetails;

public class UserTesting {
	public static class Users{
		private static int idSeller = 1;
		private static Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		private static SellerDetails sellerDetails = (SellerDetails)authentication.getPrincipal();
		public static int getIdSeller() {
			System.err.println("ID del usuario en sesion: " + sellerDetails.getId());
			return (int) sellerDetails.getId();
		}
		public static void setIdSeller(int idSeller) {
			Users.idSeller = idSeller;
		}
		
	}
}
