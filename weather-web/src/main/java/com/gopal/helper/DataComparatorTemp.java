package com.gopal.helper;

import java.util.Comparator;

import com.gopal.dto.Data;

public class DataComparatorTemp implements Comparator<Data> {

	public int compare(Data o1, Data o2) {
		if (o2.getX() != null && o1.getX() != null) {
			return Double.valueOf(o1.getX()).intValue() - Double.valueOf(o2.getX()).intValue();
		}
		return o2.getX() == null ? 1 : -1;
	}

}
