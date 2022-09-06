package services;

import exceptions.StrangeOperationException;

public class FrodMonitor {
    public boolean check(long sum) throws StrangeOperationException {
        if (sum < 500000) {
            return true;
        }
        throw new StrangeOperationException("Операция невозможна из-за подозрительных действий: попытка произвести перевод большой суммы");
    }
}