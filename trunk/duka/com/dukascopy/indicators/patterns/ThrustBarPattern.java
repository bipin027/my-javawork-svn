package com.dukascopy.indicators.patterns;

import com.dukascopy.api.indicators.IIndicator;
import com.dukascopy.api.indicators.IIndicatorContext;
import com.dukascopy.api.indicators.IndicatorInfo;
import com.dukascopy.api.indicators.IndicatorResult;
import com.dukascopy.api.indicators.InputParameterInfo;
import com.dukascopy.api.indicators.OptInputParameterInfo;
import com.dukascopy.api.indicators.OutputParameterInfo;

import java.awt.Color;

/**
 * Created by: S.Vishnyakov
 * Date: Jul 26, 2010
 * Time: 2:15:51 PM
 */
public class ThrustBarPattern implements IIndicator {
    private IndicatorInfo indicatorInfo;
    private InputParameterInfo[] inputParameterInfos;
    private OutputParameterInfo[] outputParameterInfos;
    private double[][][] inputs = new double[1][][];
    private int[][] outputs = new int[2][];
    private double[][] outputDouble = new double[4][];

    public void onStart(IIndicatorContext context) {
        indicatorInfo = new IndicatorInfo("THRUSTBAR", "Thrust Bar", "Pattern Recognition", true, false, true, 1, 0, 6);
        indicatorInfo.setUnstablePeriod(true);
        inputParameterInfos = new InputParameterInfo[]{new InputParameterInfo("Price", InputParameterInfo.Type.PRICE)};

        outputParameterInfos =
                new OutputParameterInfo[]{new OutputParameterInfo("Thrust Bar Bullish Patter", OutputParameterInfo.Type.INT, OutputParameterInfo.DrawingStyle.PATTERN_BOOL) {
                    {
                        setColor(Color.GREEN);
                    }
                }, new OutputParameterInfo("Thrust Bar Bearish Patter", OutputParameterInfo.Type.INT, OutputParameterInfo.DrawingStyle.PATTERN_BOOL) {
                    {
                        setColor(Color.RED);
                    }
                }, new OutputParameterInfo("Bullish FTS", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LEVEL_DASH_LINE) {
                    {
                        setColor(Color.GREEN);
                    }
                }, new OutputParameterInfo("Bullish LTS", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LEVEL_LINE) {
                    {
                        setColor(Color.GREEN);
                    }
                }, new OutputParameterInfo("Bearis FTS", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LEVEL_DASH_LINE) {
                    {
                        setColor(Color.RED);
                    }
                }, new OutputParameterInfo("Bearish LTS", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LEVEL_LINE) {
                    {
                        setColor(Color.RED);
                    }
                },};
    }

    public IndicatorResult calculate(int startIndex, int endIndex) {
        //calculating startIndex taking into account lookback value
        if (startIndex - getLookback() < 0) {
            startIndex -= startIndex - getLookback();
        }
        if (startIndex > endIndex) {
            return new IndicatorResult(0, 0);
        }

        //Inputs: 0 open, 1 close, 2 high, 3 low, 4 volume
        int i, j;
        for (i = startIndex, j = 0; i <= endIndex; i++, j++) {
            boolean isBull = false;
            // bullish
            if ((inputs[0][1][i] > inputs[0][2][i - 1])) {
                isBull = true;
            }
            // bearsish
            boolean isBear = false;
            if ((inputs[0][1][i] < inputs[0][3][i - 1])) {
                isBear = true;
            }

            if (isBull) {
                if (j > 0) {
                    outputs[0][j] = 1;
                    if (outputDouble[0][j - 1] != 0) {
                        // first
                        outputDouble[0][j] = outputDouble[0][j - 1];
                        // last
                        outputDouble[1][j] = inputs[0][3][i];
                    } else {
                        // first
                        outputDouble[0][j] = inputs[0][3][i];
                        // last
                        outputDouble[1][j] = 0;
                    }
                } else {
                    // first
                    outputDouble[0][j] = inputs[0][3][i];
                    // last
                    outputDouble[1][j] = 0;
                }
            } else {
                outputs[0][j] = 0;
                outputDouble[1][j] = 0;
                outputDouble[0][j] = 0;
            }

            if (isBear) {
                if (j > 0) {
                    outputs[1][j] = 1;
                    if (outputDouble[2][j - 1] != 0) {
                        // first
                        outputDouble[2][j] = outputDouble[2][j - 1];
                        // last
                        outputDouble[3][j] = inputs[0][2][i];
                    } else {
                        // first
                        outputDouble[2][j] = inputs[0][2][i];
                        // last
                        outputDouble[3][j] = 0;
                    }
                } else {
                    // first
                    outputDouble[2][j] = inputs[0][2][i];
                    // last
                    outputDouble[3][j] = 0;
                }
            } else {
                outputs[1][j] = 0;
                outputDouble[2][j] = 0;
                outputDouble[3][j] = 0;
            }
        }
        return new IndicatorResult(startIndex, j);
    }

    public IndicatorInfo getIndicatorInfo() {
        return indicatorInfo;
    }

    public InputParameterInfo getInputParameterInfo(int index) {
        if (index <= inputParameterInfos.length) {
            return inputParameterInfos[index];
        }
        return null;
    }

    public int getLookback() {
        return 4;
    }

    public int getLookforward() {
        return 0;
    }

    public OptInputParameterInfo getOptInputParameterInfo(int index) {
        return null;
    }

    public OutputParameterInfo getOutputParameterInfo(int index) {
        if (index <= outputParameterInfos.length) {
            return outputParameterInfos[index];
        }
        return null;
    }

    public void setInputParameter(int index, Object array) {
        inputs[index] = (double[][]) array;
    }

    public void setOptInputParameter(int index, Object value) {

    }

    public void setOutputParameter(int index, Object array) {
        if (index < 2) {
            outputs[index] = (int[]) array;
        } else {
            outputDouble[index - 2] = (double[]) array;
        }
    }
}

