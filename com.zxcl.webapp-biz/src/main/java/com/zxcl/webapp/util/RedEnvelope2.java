package com.zxcl.webapp.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 红包算法
 * @author Chang Wen
 */
public class RedEnvelope2 {
	
	private Random random = new Random();
	
	private BigDecimal moneyLeft;//剩余未分配金额
	
	private int timesLeft; //剩余抽奖次数
	
	private BigDecimal min = BigDecimal.ZERO;//红包最小值
	
	private BigDecimal max = BigDecimal.ZERO;//红包最大值
	
	public RedEnvelope2(BigDecimal totalMoney, int totalTimes, BigDecimal min, BigDecimal max) {
		this.moneyLeft = totalMoney;
		this.timesLeft = totalTimes;
		
		this.min = min;
		this.max = max;
	}
	

	public BigDecimal nextMoney() {
		
		if(timesLeft == 0){
			return new BigDecimal(0);
		}

		if(timesLeft == 1){
			return moneyLeft.setScale(0,BigDecimal.ROUND_HALF_UP);
		}
		
		if(moneyLeft.compareTo(min) == -1 ) {
			min = moneyLeft;
		}
		
		//保证余下的次数每次都至少抽到min
		BigDecimal moneyLeftMin = min.multiply(new BigDecimal(timesLeft - 1)) ;
		
		BigDecimal max = moneyLeft.divide(new BigDecimal(timesLeft * 2),0, BigDecimal.ROUND_HALF_UP);
		
		if(moneyLeft.subtract(max) .compareTo(moneyLeftMin)  == -1 ) {
			max = moneyLeft .subtract(moneyLeftMin);
		}
		
		BigDecimal money = new BigDecimal(random.nextDouble() * max.doubleValue()).setScale(0, BigDecimal.ROUND_HALF_UP);
		
		if(money .compareTo(min) == -1 ){
			money = min;
		}

		if(money .compareTo(this.max) == 1 ){
			money = this.max;
		}
		
		moneyLeft = moneyLeft .subtract(money);
		timesLeft--;
		
		return money.setScale(0, BigDecimal.ROUND_HALF_UP);
	}

	

	


	
	public static void main(String[] args) {

		int totalTimes = 2;
		String total = "74";
		
		RedEnvelope2 e = new RedEnvelope2(
				new BigDecimal(total), //奖金总金额 
				totalTimes, //预计有多少抽奖次数,
				new BigDecimal(1), //最小金额,
				new BigDecimal(total) //最大金额
				);
		
		BigDecimal a = BigDecimal.ZERO ;
		
		for(int i=0; i < totalTimes; i++) {
			BigDecimal m = e.nextMoney();
			a = a.add(m);
			//System.out.println("[" + i + "]=" + m + " :" + a);
			System.out.println(m);
		}
	}

}
