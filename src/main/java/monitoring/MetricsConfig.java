package monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;

public final class MetricsConfig {

    // Реестр, в котором хранятся все метрики приложения
    private static final PrometheusMeterRegistry REGISTRY =
            new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    // Общее количество попыток покупки билетов
    private static final Counter TOTAL_OPERATIONS =
            Counter.builder("tickets.operations")
                    .description("Общее количество операций покупки билетов")
                    .register(REGISTRY);

    // Количество успешно завершённых покупок
    private static final Counter SUCCESSFUL_PURCHASES =
            Counter.builder("tickets.purchases.success")
                    .description("Количество успешных покупок билетов")
                    .register(REGISTRY);

    // Количество неудачных покупок
    private static final Counter ERROR_PURCHASES =
            Counter.builder("tickets.purchases.error")
                    .description("Количество неудачных покупок билетов")
                    .register(REGISTRY);

    // Время выполнения операции покупки билета
    private static final Timer PURCHASE_DURATION =
            Timer.builder("tickets.purchase.duration")
                    .description("Время выполнения операции покупки билета")
                    .register(REGISTRY);

    private MetricsConfig() {
    }

    public static PrometheusMeterRegistry getRegistry() {
        return REGISTRY;
    }

    public static Counter getTotalOperations() {
        return TOTAL_OPERATIONS;
    }

    public static Counter getSuccessfulPurchases() {
        return SUCCESSFUL_PURCHASES;
    }

    public static Counter getErrorPurchases() {
        return ERROR_PURCHASES;
    }

    public static Timer getPurchaseDuration() {
        return PURCHASE_DURATION;
    }
}