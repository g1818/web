package com.gs.password.security;



import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import com.gs.crick.dto.UserDetailsDTO;
import com.valtech.vregcoss.password.security.UserSecurity;

public class PasswordSecurity {

	private static final Logger LOGGER = Logger.getLogger(PasswordSecurity.class.getName());

	public String generateHashedPassword(UserDetailsDTO userSecurity, String randomPw) {
		String newPass = randomPw.concat(userSecurity.getEmail_id());
		try {
			return createHash(newPass.toCharArray());
		} catch (CannotPerformOperationException e) {
			LOGGER.info(e);
		}
		return randomPw;
	}


	public static String createHash(char[] password) throws CannotPerformOperationException {
		// Generate a random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[24];
		random.nextBytes(salt);
		// Hash the password
		byte[] hash = pbkdf2(password, salt, 64000,
				18);
		int hashSize = hash.length;

		// format: algorithm:iterations:hashSize:salt:hash
		return "sha1:" + 64000 + ":" + hashSize + ":" + toBase64(salt) + ":"
				+ toBase64(hash);
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
			throws CannotPerformOperationException {
		try {
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException ex) {
			throw new CannotPerformOperationException("Hash algorithm not supported.", ex);
		} catch (InvalidKeySpecException ex) {
			throw new CannotPerformOperationException("Invalid key spec.", ex);
		}
	}

	private static byte[] fromBase64(String hex) {
		return DatatypeConverter.parseBase64Binary(hex);
	}

	private static String toBase64(byte[] array) {
		return DatatypeConverter.printBase64Binary(array);
	}

	@SuppressWarnings("serial")
	public static class CannotPerformOperationException extends Exception {
		public CannotPerformOperationException(String message) {
			super(message);
		}

		public CannotPerformOperationException(String message, Throwable source) {
			super(message, source);
		}
	}

	public String generatePassword() {
		LOGGER.info("Inside generatePassword(..)");
		int count = 0;
		final int pwdLen = 8;
		String newPwd;
		final Random ran = new Random();
		final char[] text = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9' };
		final StringBuilder strbuff = new StringBuilder();
		for (int i = 0; i < pwdLen; i++) {
			strbuff.append(text[ran.nextInt(text.length)]);
		}
		newPwd = strbuff.toString();
		for (char c : newPwd.toCharArray()) {
			final String chr = Character.toString(c);
			try {
				final int num = Integer.parseInt(chr);
				if (num <= 9) {
					count += 1;
				}
			} catch (NumberFormatException nfe) {
				LOGGER.info("Inside generatePassword(..) Exception");
			}
		}
		if (count < 2) {
			newPwd = generatePassword();
		}
		return newPwd;
	}

	public boolean verifyPassword(char[] password, String correctHash, String saltFrmdb) throws InvalidHashException {

		byte[] salt = null;
		byte[] hash4EnteredPw = null;
		try {
			salt = fromBase64(saltFrmdb);
		} catch (IllegalArgumentException ex) {
			throw new InvalidHashException("Base64 decoding of salt failed.", ex);
		}

		// Compute the hash of the provided password, using the same salt,
		// iteration count, and hash length
		try {
			hash4EnteredPw = pbkdf2(password, salt, 64000, 18);
		} catch (CannotPerformOperationException ex) {
			LOGGER.info(ex);
		}

		// Compare the hashes in constant time. The password is correct if
		// both hashes match.
		return slowEquals(fromBase64(correctHash), hash4EnteredPw);
	}

	private static boolean slowEquals(byte[] correctHash, byte[] hash4EnteredPw) {
		int diff = correctHash.length ^ hash4EnteredPw.length;
		for (int i = 0; i < correctHash.length && i < hash4EnteredPw.length; i++)
			diff |= correctHash[i] ^ hash4EnteredPw[i];
		return diff == 0;
	}

	public boolean verifyPassword(String password, String correctHash, String saltFrmdb, UserDetailsDTO userSecurity) {

		String newPass = password.concat(userSecurity.getEmail_id());
		try {
			return verifyPassword(newPass.toCharArray(), correctHash, saltFrmdb);
		} catch (InvalidHashException e) {
			LOGGER.info(e);
		}
		return false;
	}

	@SuppressWarnings("serial")
	public static class InvalidHashException extends Exception {
		public InvalidHashException(String message) {
			super(message);
		}

		public InvalidHashException(String message, Throwable source) {
			super(message, source);
		}
	}


}
