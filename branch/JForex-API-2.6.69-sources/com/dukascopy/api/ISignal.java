package com.dukascopy.api;

import java.util.List;

/**
 * Signals, generated by strategy
 * 
 * @author roman.terlan
 *
 */

public interface ISignal {

	public enum Type {
		ORDER_BUY,    
		ORDER_SELL, 
		ORDER_CLOSE, 
		ORDER_MODIFY, 
		ORDER_MERGE, 
		ORDER_CANCEL;
	}
	
	public IOrder getOrder();
	
	public List<IOrder> getOldOrders();

	public ISignal.Type getType();	
}