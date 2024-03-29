package xyz.sorridi.stone.common.threading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.NonNull;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A fixed & named thread pool.
 *
 * @author Sorridi
 * @since 1.0
 */
@Getter
public class Pool implements Executor
{
    private static final AtomicInteger ID = new AtomicInteger(0);

    private final ExecutorService executor;
    private final ThreadFactory threadFactory;

    private final String poolName;
    private final int numThreads;

    /**
     * Creates a new generic pool with the number of threads equal to the number of processors.
     */
    public Pool()
    {
        this(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Creates a new generic pool with the given number of threads.
     */
    public Pool(int numThreads)
    {
        this("generic-" + ID.getAndIncrement(), numThreads);
    }

    /**
     * Creates a new generic pool with the number of threads equal to the number of processors.
     *
     * @param poolName The pool name.
     */
    public Pool(@NonNull String poolName)
    {
        this(poolName, Runtime.getRuntime().availableProcessors());
    }

    public Pool(@NonNull String poolName, int numThreads)
    {
        checkArgument(numThreads > 0, Err.MUST_BE_POSITIVE.expect("threads"));

        this.numThreads = numThreads;
        this.poolName = poolName;
        this.threadFactory = new ThreadFactoryBuilder().setNameFormat(poolName + " (#%d)").build();
        this.executor = Executors.newFixedThreadPool(numThreads, threadFactory);
    }

    @Override
    public void execute(@NonNull Runnable command)
    {
        executor.execute(command);
    }

    @Override
    public String toString()
    {
        return "Pool{" +
                "executor=" + executor +
                ", threadFactory=" + threadFactory +
                ", poolName='" + poolName + '\'' +
                ", numThreads=" + numThreads +
                '}';
    }

    /**
     * Shuts down the pool.
     */
    public void shutdown()
    {
        executor.shutdown();
    }

}