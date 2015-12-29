package com.example.phonetopc.model;

public class PasswordTransfer {
	public static final String[][] Str = { { "H", "C", "h", "P", "0", "z" },
			{ "6", "x", "N", "a", "U", "i" }, { "1", "G", "9", "w", "o", "T" },
			{ "7", "B", "5", "e", "Y", "F" }, { "s", "v", "J", "d", "*", "W" },
			{ "g", "t", "M", "u", "l", "D" }, { "E", "c", "2", "8", "p", "Z" },
			{ "&", "O", "|", "f", "R", "4" }, { "#", "Q", "n", "q", "S", "3" },
			{ "A", "j", "V", "b", "k", "L" }, { "m", "y", "I", "r", "X", "K" } };

	public static String wordToPwd(String word) {
		String pwd = "";
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < 11; j++) {
				for (int k = 0; k < 6; k++) {
					if (Str[j][k].equals(String.valueOf(word.charAt(i)))) {
						int jj = Math.abs((j + 1)) % 11;
						int kk = 0;
						if ((k - 3) < 0)
							kk = (k + 3) % 6;
						else if ((k - 3) > 0)
							kk = (k - 3) % 6;
						pwd += Str[jj][kk];
					}
				}
			}
		}
		return pwd;
	}

}
