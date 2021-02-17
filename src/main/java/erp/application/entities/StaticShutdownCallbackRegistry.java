package erp.application.entities;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.util.Cancellable;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.status.StatusLogger;


public class StaticShutdownCallbackRegistry implements ShutdownCallbackRegistry, LifeCycle, Runnable, Serializable{
	
	private static final long serialVersionUID = 1L;
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private final AtomicReference<State> state = new AtomicReference<State>(State.INITIALIZED);
    private final Collection<Cancellable> hooks = new CopyOnWriteArrayList<Cancellable>();
    private static final Collection<StaticShutdownCallbackRegistry> instances = new CopyOnWriteArrayList<StaticShutdownCallbackRegistry>();

    public StaticShutdownCallbackRegistry() {
        instances.add(this);
    }

    public static void invoke() {
        for (final Runnable instance : instances) {
            try {
                instance.run();
            } catch (final Throwable t) {
                LOGGER.error(SHUTDOWN_HOOK_MARKER, "Caught exception executing shutdown hook {}", instance, t);
            }
        }
    }

    @Override
    public void run() {
        if (state.compareAndSet(State.STARTED, State.STOPPING)) {
            for (final Runnable hook : hooks) {
                try {
                    hook.run();
                } catch (final Throwable t) {
                    LOGGER.error(SHUTDOWN_HOOK_MARKER, "Caught exception executing shutdown hook {}", hook, t);
                }
            }
            state.set(State.STOPPED);
        }
    }

    @Override
    public Cancellable addShutdownCallback(final Runnable callback) {
        if (isStarted()) {
            final Cancellable receipt = new Cancellable() {
                Reference<Runnable> hook = new  WeakReference<Runnable>(callback);
                @Override
                public void cancel() {
                    hook.clear();
                    hooks.remove(this);
                }
                @Override
                public void run() {
                    final Runnable hook = this.hook.get();
                    if (hook != null) {
                        hook.run();
                        this.hook.clear();
                    }
                }
                @Override
                public String toString() {
                    return String.valueOf(hook.get());
                }
            };
            hooks.add(receipt);
            return receipt;
        }
        throw new IllegalStateException("Cannot add new shutdown hook as this is not started. Current state: " +
                state.get().name());
    }

    @Override
    public void start() {
        state.set(State.STARTED);
    }

    @Override
    public void stop() {
        state.set(State.STOPPED);
    }

    public State getState() {
        return state.get();
    }

    @Override
    public boolean isStarted() {
        return state.get() == State.STARTED;
    }

    @Override
    public boolean isStopped() {
        return state.get() == State.STOPPED;
    }

	@Override
	public void initialize() {
		Object initObj = state.get() == State.INITIALIZING;
		LOG.appLogger().debug("Init object: " + initObj.toString());
	}

}
