package util;

import memoryBank.IRegisterView;
import memoryBank.Register;

/**
 * hso.ra.java.simulator.pic16f8x
 * util
 * Mike Bruder, Philipp Schweizer
 * 31.10.2018
 */
public class RegisterDataParser
{

	 private static final int BANK_ZERO_START = 0x00;

	 private static final int BANK_ONE_START = 0x80;

	 private static int[] oldBankZero = new int[80];

	 private static int[] oldBankOne = new int[80];

	 public static RowList<IRegisterView> getRegisterModel(int[] bankZero, int[] bankOne)
	 {

		  RowList<IRegisterView> rowList = new RowList<>();

		  for (int i = 0; i < bankOne.length; i++) {

				rowList.add(new Register((BANK_ZERO_START + i), "0x" + Integer.toHexString(bankZero[i]),
								bankZero[i] != oldBankZero[i]),
						new Register((BANK_ONE_START + i), "0x" + Integer.toHexString(bankOne[i]),
								bankOne[i] != oldBankOne[i]));

		  }

		  oldBankZero = bankZero;
		  oldBankOne = bankOne;

		  return rowList;
	 }
}
