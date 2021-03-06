/**
 * Copyright 2017 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.core.instrument.binder.hystrix.deprecated10;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherCollapser;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherCommand;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisherThreadPool;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.lang.NonNullApi;
import io.micrometer.core.lang.NonNullFields;

/**
 * @author Clint Checketts
 */
@NonNullApi
@NonNullFields
public class MicrometerMetricsPublisherDeprecated10x extends HystrixMetricsPublisher {
    private final MeterRegistry registry;
    private final HystrixMetricsPublisher metricsPublisher;

    public MicrometerMetricsPublisherDeprecated10x(MeterRegistry registry, HystrixMetricsPublisher metricsPublisher) {
        this.registry = registry;
        this.metricsPublisher = metricsPublisher;
    }

    @Override
    public HystrixMetricsPublisherThreadPool getMetricsPublisherForThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixThreadPoolMetrics metrics, HystrixThreadPoolProperties properties) {
        return metricsPublisher.getMetricsPublisherForThreadPool(threadPoolKey, metrics, properties);
    }

    @Override
    public HystrixMetricsPublisherCollapser getMetricsPublisherForCollapser(HystrixCollapserKey collapserKey, HystrixCollapserMetrics metrics, HystrixCollapserProperties properties) {
        return metricsPublisher.getMetricsPublisherForCollapser(collapserKey, metrics, properties);
    }

    @Override
    public HystrixMetricsPublisherCommand getMetricsPublisherForCommand(HystrixCommandKey commandKey,
                                                                        HystrixCommandGroupKey commandGroupKey,
                                                                        HystrixCommandMetrics metrics,
                                                                        HystrixCircuitBreaker circuitBreaker,
                                                                        HystrixCommandProperties properties) {
        HystrixMetricsPublisherCommand metricsPublisherForCommand =
            metricsPublisher.getMetricsPublisherForCommand(commandKey, commandGroupKey, metrics, circuitBreaker, properties);
        return new MicrometerMetricsPublisherCommandDeprecated10x(registry, commandKey, commandGroupKey, metrics, circuitBreaker, properties, metricsPublisherForCommand);
    }
}
