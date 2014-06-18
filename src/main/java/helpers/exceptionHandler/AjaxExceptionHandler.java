package helpers.exceptionHandler;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.view.ViewDeclarationLanguage;
import javax.servlet.http.HttpServletRequest;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.primefaces.context.RequestContext;

/**
 * {@link ExceptionHandlerWrapper} which writes a custom XML response for the {@link AjaxErrorHandler} component.
 *
 * @author Pavol Slany / last modified by $Author$
 * @version $Revision$
 * @since 0.5
 */
public class AjaxExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger LOGGER = Logger.getLogger(AjaxExceptionHandler.class.getCanonicalName());

	private ExceptionHandler wrapped = null;

	/**
	 * Construct a new {@link AjaxExceptionHandler} around the given wrapped {@link ExceptionHandler}.
	 *
	 * @param wrapped The wrapped {@link ExceptionHandler}.
	 */
	public AjaxExceptionHandler(final ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		FacesContext context = FacesContext.getCurrentInstance();
		
		
		
		if (context.getPartialViewContext()!=null && context.getPartialViewContext().isAjaxRequest()) {
			Iterable<ExceptionQueuedEvent> exceptionQueuedEvents = getUnhandledExceptionQueuedEvents();
			if (exceptionQueuedEvents!=null && exceptionQueuedEvents.iterator()!=null) {
				Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents().iterator();
				
				if (unhandledExceptionQueuedEvents.hasNext()) {
					Throwable exception = unhandledExceptionQueuedEvents.next().getContext().getException();
					unhandledExceptionQueuedEvents.remove();

					handlePartialResponseError(context, exception);
				}

				while (unhandledExceptionQueuedEvents.hasNext()) {
					// Any remaining unhandled exceptions are not interesting. First fix the first.
					unhandledExceptionQueuedEvents.next();
					unhandledExceptionQueuedEvents.remove();
				}
			}

		}

		wrapped.handle();
	}

	private void handlePartialResponseError(final FacesContext context, final Throwable t) {
		if (context.getResponseComplete()) {
			return; // don't write anything if the response is complete
		}

		if (!context.getExternalContext().isResponseCommitted()) {
			context.getExternalContext().responseReset();
		}

		try {
			Throwable rootCause = ExceptionUtils.getRootCause(t);
			String mensajeError = null;

			// Workaround for ViewExpiredException if UIViewRoot was not restored ...
			if (context.getViewRoot() == null) {
				try {
					String uri = ((HttpServletRequest) context.getExternalContext().getRequest()).getRequestURI();
					UIViewRoot viewRoot = context.getApplication().getViewHandler().createView(context, uri);
					context.setViewRoot(viewRoot);

					// Workaround for Mojarra : if  UIViewRoot == null (VIEW is lost in session), throwed is  IllegalArgumentException instead of 'ViewExpiredException'
					if (rootCause==null && t instanceof IllegalArgumentException) {
						rootCause = new javax.faces.application.ViewExpiredException(uri);
					}

					// buildView - create component tree in view ...
					// todo: add CONTEXT-PARAM for set this feature BUILD VIEW
					String viewId = viewRoot.getViewId();
					ViewDeclarationLanguage vdl = context.getApplication().getViewHandler().getViewDeclarationLanguage(context, viewId);
					vdl.buildView(context, viewRoot);
				}
				catch (Exception tt) {
					LOGGER.log(Level.SEVERE, tt.getMessage(), tt);
				}
			}

			String errorName = (rootCause == null) ? t.getClass().getCanonicalName() : rootCause.getClass().getCanonicalName();
			LOGGER.log(Level.SEVERE, ""+t.getMessage(), t);
			
			if (t.getMessage() != null)
				mensajeError = t.getMessage().replace(errorName + ": ", "");
			else {
				mensajeError = "";
			}
			
			
			System.out.println("errorName: " + errorName);
			if(errorName.equals("javax.faces.application.ViewExpiredException")){
				 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    	 ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			}else{
				FacesMessage msg = null;  
				msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", mensajeError); 
				context.addMessage(null, msg);
				RequestContext.getCurrentInstance().update("form:growl");
			}
			
		} catch (Exception e) {
			if (LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	protected String getHostname() throws UnknownHostException {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "???unknown???";
		}
	}
}