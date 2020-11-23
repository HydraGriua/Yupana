package com.acme.yupanaapi.resource;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InfoFilter {
	public String orderBy;
	public String registerTypeBy;
	public String payMethodBy;
	public String rateTypeBy;
	public String currencyBy;
	public String dateStart;
	public String dateEnd;
}
