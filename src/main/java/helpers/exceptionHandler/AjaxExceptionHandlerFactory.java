package helpers.exceptionHandler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class AjaxExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory wrapped;

	/**
	 * Construct a new full {@link AjaxExceptionHandlerFactory} around the given wrapped factory.
	 * 
	 * @param wrapped The wrapped factory.
	 */
	public AjaxExceptionHandlerFactory(final ExceptionHandlerFactory wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * Returns a new instance {@link AjaxExceptionHandler}.
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		//TODO can this instance be cached?
		return new AjaxExceptionHandler(wrapped.getExceptionHandler());
	}

	/**
	 * Returns the wrapped factory.
	 */
	@Override
	public ExceptionHandlerFactory getWrapped() {
		return wrapped;
	}
	
}
