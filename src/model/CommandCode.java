package model;

import java.util.stream.Stream;

public enum CommandCode {

	ADDWF(0x07),
	ANDWF(0x05),
	CLRF(0x018),
	CLRW(0x010),
	COMF(0x09),
	DECF(0x03),
	DECFSZ(0x0B),
	INCF(0x0A),
	INCFSZ(0x0F),
	IORWF(0x04),
	MOVF(0x08),
	MOVWF(0x008),
	NOP(0x00),
	RLF(0x0D),
	RRF(0x0C),
	SUBWF(0x02),
	SWAPF(0x0E),
	XORWF(0x06),
	BCF(0x10),
	BSF(0x14),
	BTFSC(0x18),
	BTFSS(0x1C),
	ADDLW(0x3E),
	ANDLW(0x39),
	CALL(0x20),
	CLRWDT(0x0064),
	GOTO(0x28),
	IORLW(0x38),
	MOVLW(0x30),
	RETFIE(0x0009),
	RETLW(0x34),
	RETURN(0x0008),
	SLEEP(0x0063),
	SUBLW(0x3C),
	XORLW(0x3A),
	DEFAULT(0xFFFF);
	
	private int value;
	
	
	CommandCode(int value){
	
		this.value = value;
	
	}
	
	public CommandCode getCommandCodesByValue(int value) {
		
		return Stream.of(CommandCode.values()).filter(c -> c.value==value).findFirst().orElse(CommandCode.DEFAULT);
	}
	
	
}
