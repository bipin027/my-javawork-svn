/**
 * The file InverseHeadAndShouldersPatternIndicator.java was created on Mar 1, 2010 at 5:13:46 PM
 * by @author Marks Vilkelis
 */
package com.dukascopy.indicators.pattern;

import com.dukascopy.api.indicators.IndicatorInfo;


public class InverseHeadAndShouldersPatternIndicator extends AbstractHeadAndShouldersPatternIndicator {

	/**
	 *  0			 6
	 *   \  2    4  /
 	 *    \/\    /\/
	 *    1  \  /  5
	 *        \/
	 * 	      3
	 */
	@Override
	public boolean checkPattern(
			double[] cleanPattern, 
			double[] dirtyPattern, 
			int dirtyPatternStartIndex, 
			int dirtyPatternSize
			
	) {
	    double patternSpread = getPatternPriceSpread(cleanPattern);
		if (	
				cleanPattern[0] > cleanPattern[1] && 
				cleanPattern[1] < cleanPattern[2] && 
				cleanPattern[2] > cleanPattern[3] && 
				cleanPattern[3] < cleanPattern[4] &&
				cleanPattern[4] > cleanPattern[5] && 
				
				cleanPattern[3] <= cleanPattern[5] - (cleanPattern[4] - cleanPattern[5]) * getHeadHeightRatio() && // head has to be lower than shoulders
				cleanPattern[3] <= cleanPattern[1] - (cleanPattern[2] - cleanPattern[1]) * getHeadHeightRatio() && // head has to be lower than shoulders
				
				cleanPattern[2] <= cleanPattern[0] &&

				aproxEqual(cleanPattern[1], cleanPattern[5], patternSpread) &&
				aproxEqual(cleanPattern[2], cleanPattern[4], patternSpread) &&
				
				(
						!isWholePatternCalculation() || 
						(
							cleanPattern[5] < cleanPattern[6] &&
							cleanPattern[4] <= cleanPattern[6]
						)
				)
		) {
			/*
			 * First line must be higher than asymptote
			 */
			double[] asymptoteLinePriceValues = indicesToValues(cleanPattern, new int[]{2, 4});
			double avgPoint = average(asymptoteLinePriceValues);
			if (cleanPattern[0] > avgPoint) {
				return true;
			}

			return true;
			
		}
		return false;
	}
	
	@Override
	protected IndicatorInfo createIndicatorInfo() {
		return new IndicatorInfo("INVERSE_HEAD&SHLDRS", "Inverse Head and Shoulders Pattern", getDefaultPatternRecognitionGroup(), true, false, false, 1, getOptInputParameterInfos().length, getNumberOfOutputs());
	}

	@Override
	public int getMinPatternPivotPointNumber() {
		return isWholePatternCalculation() ? 7 : 6;
	}
	
	@Override
    public int getMaxPatternPivotPointNumber() {
        return getMinPatternPivotPointNumber();
    }
	
	@Override
    public boolean checkEmergingPatternPostTrend(
            double[] cleanPattern,
            double[][] sourceData,
            int dirtyPatterStartIndex,
            int dirtyPatternSize,
            IndexValue[] patternPoints,
            IndexValue[] upperAsymptotePoints,
            IndexValue[] bottomAsymptotePoints
    ) {
        return true;
    }

}
