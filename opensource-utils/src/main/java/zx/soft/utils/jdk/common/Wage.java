package zx.soft.utils.jdk.common;

/**
 * 工资计算器
 * @author donglei
 * @date: 2016年4月17日 下午5:45:54
 */
public class Wage {

	public enum Level {
		A, B, C, D;

		@Override
		public String toString() {
			return "A";
		}
	}

	public static void main(String[] args) {
		float beforeTax = countWage(7200, Level.D, 0f);
		System.out.println("税前工资: " + beforeTax);
		// 个税起征点
		float begin = 3500;
		// 公积金
		float houses = 1031;
		// 社保
		float safes = 275.1f;

		float taxes = payTax(beforeTax - begin - houses - safes);

		System.out.println("交税:" + taxes);

		System.out.println("税后工资: " + (beforeTax - houses - safes - taxes));
	}

	/**
	 * 薪资结构=基本工资+绩效奖金+津贴+福利与保险+科技创新奖
	 * @param basic
	 * @param level
	 * @param overtime
	 * @return
	 */
	public static float countWage(int basic, Level level, float overtime) {
		float count = 0;
		// 基本工资
		count += basic;
		/**
		 * 绩效工资
		 * 月度绩效奖金额度为基本工资的20%
		 * A   150%    400元
		 * B   130%    200元
		 * C   100%    0
		 * D   80%     0
		 */
		switch (level) {
			case A:
				count += basic * 0.2 * 1.5 + 400;
				break;
			case B:
				count += basic * 0.2 * 1.3 + 200;
				break;
			case C:
				count += basic * 0.2;
				break;
			case D:
				count += basic * 0.2 * 0.8;
				break;
		}

		System.out.println("绩效工资: " + count);

		/**
		 *  保密津贴
		 *  200元/人/月
		 */
		count += 200;

		/**
		 * 工龄工资
		 */
		count += 100;

		/**
		 *  加班津贴=加班天数/30×该年度月平均工资×80%×加班津贴支付比例
		 *  以该年度月平均工资的80%为准，结合员工年度平均绩效以一定比例发放,比例如下
		 *  A   120%
		 *  B   100%
		 *  C   80%
		 *  D   0%
		 */
		float dailyWage = basic / 21.75f;
		switch (level) {
			case A:
				count += dailyWage * 1.2 * overtime;
				break;
			case B:
				count += dailyWage * overtime;
				break;
			case C:
				count += dailyWage * 0.8 * overtime;
				break;
			case D:
				count += 0;
				break;
		}
		return count;
	}

	/**
	 * 计算个人所得税
	 * @param beforeTax 工资 － 3险一金 － 个税
	 * @return
	 */
	public static float payTax(float beforeTax) {
		/**
		 * 不超过1500元的 3
		 * 超过1500元至4500元的部分 10
		 * 超过4500元至9000元的部分 20
		 * 超过9000元至35000元的部分 25
		 * 超过35000元至55000元的部分 30
		 * 超过55000元至80000元的部分 35
		 * 超过80000元的部分 45
		 */
		if (beforeTax > 80000) {
			return (beforeTax - 80000) * 0.45f + payTax(80000);
		} else if (beforeTax > 55000) {
			return (beforeTax - 55000) * 0.35f + payTax(55000);
		} else if (beforeTax > 35000) {
			return (beforeTax - 35000) * 0.3f + payTax(35000);
		} else if (beforeTax > 9000) {
			return (beforeTax - 9000) * 0.25f + payTax(9000);
		} else if (beforeTax > 4500) {
			return (beforeTax - 4500) * 0.2f + payTax(4500);
		} else if (beforeTax > 1500) {
			return (beforeTax - 1500) * 0.1f + payTax(1500);
		} else {
			return beforeTax * 0.03f;
		}
	}

}
