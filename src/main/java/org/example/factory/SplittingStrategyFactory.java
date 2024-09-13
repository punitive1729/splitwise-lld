package org.example.factory;

import org.example.constants.SplittingStrategies;
import org.example.exceptions.SplitWiseServiceException;
import org.example.services.SplittingStrategy;
import org.example.services.impl.EqualSplittingStrategyImpl;
import org.example.services.impl.ExactSplittingStrategyImpl;
import org.example.services.impl.PercentSplittingStrategyImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SplittingStrategyFactory {
    /*

    Factory Pattern along with Strategy design pattern

    Splitting strategy factory to ensure:
    1. loose coupling
    2. extensibility
    3. new strategies can be added by implementing the common interface
    4. Open/Close principle
     */
    private final Map<String, SplittingStrategy> splittingStrategyMap = new HashMap<>();
    public SplittingStrategyFactory() {
        splittingStrategyMap.put(SplittingStrategies.EQUAL, new EqualSplittingStrategyImpl());
        splittingStrategyMap.put(SplittingStrategies.EXACT, new ExactSplittingStrategyImpl());
        splittingStrategyMap.put(SplittingStrategies.PERCENT, new PercentSplittingStrategyImpl());
    }

    public SplittingStrategy getSplittingStrategy(String strategyName) throws SplitWiseServiceException {
        if(Objects.isNull(strategyName) || strategyName.isBlank())
            throw new SplitWiseServiceException("strategy name cannot be null or blank", 400);
        if(splittingStrategyMap.containsKey(strategyName.toLowerCase()))
            return splittingStrategyMap.get(strategyName.toLowerCase());
        throw new SplitWiseServiceException("strategy name: "+strategyName+" is invalid", 400);
    }
}
