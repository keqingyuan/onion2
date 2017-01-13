package com.ftsafe.iccbd.utils.security.des;


public abstract class AbstractDes {
	
	
	public abstract byte[] encrypt(byte[] paramArrayOfByte);

	public abstract byte[] decrypt(byte[] paramArrayOfByte);
}
