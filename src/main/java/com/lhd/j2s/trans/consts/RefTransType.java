package com.lhd.j2s.trans.consts;

/**
 * 参照翻译类型
 * @author lhd
 */
public enum RefTransType {

	/**
	 * 参照翻译类型
	 */
	PRODUCT("Product"),
	USER("User");

	private final String type;

	public String getType() {
		return type;
	}

	RefTransType(String type) {
		this.type = type;
	}
}
