public class FinancialForecasting {

    public static double predictFutureValue(int years, double currentValue, double growthRate) {
        if (years <= 0) {
            return currentValue;
        }
        return predictFutureValue(years - 1, currentValue * (1 + growthRate), growthRate);
    }

    public static double predictFutureValueMemoized(int years, double currentValue, double growthRate, double[] memo) {
        if (years <= 0) {
            return currentValue;
        }
        if (memo[years] != 0) {
            return memo[years];
        }
        memo[years] = predictFutureValueMemoized(years - 1, currentValue * (1 + growthRate), growthRate, memo);
        return memo[years];
    }

    public static void main(String[] args) {
        int yearsToForecast = 5;
        double initialInvestment = 10000.0;
        double annualGrowth = 0.07;

        double predictedValue = predictFutureValue(yearsToForecast, initialInvestment, annualGrowth);

        double[] memoTable = new double[yearsToForecast + 1];
        double optimizedValue = predictFutureValueMemoized(yearsToForecast, initialInvestment, annualGrowth, memoTable);
    }
}