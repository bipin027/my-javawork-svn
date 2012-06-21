/*
 * Copyright 2011 Dukascopy Bank SA. All rights reserved.
 * DUKASCOPY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.dukascopy.api.system.tester;

/**
 * 
 * Provides control over a strategy testing process 
 */
public interface ITesterExecutionControl {
	/**
	 * Pause the strategy execution process
	 */
	public void pauseExecution();
	
	/**
	 * 
	 * @return true if the strategy is in the paused state, false otherwise  
	 */
	public boolean isExecutionPaused();
	
	/**
	 * Continue the strategy execution process
	 */
	public void continueExecution();
	
	/**
	 * Cancel the strategy execution process
	 */
	public void cancelExecution();
	
	/**
	 * 
	 * @return true if strategy execution was canceled, false otherwise
	 */
	public boolean isExecutionCanceled();
}
