package org.wintrisstech.erik.sprinkler;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ForgotPasswordServlet extends HttpServlet {

	private static final String ERROR_MESSAGE = "No user with that email was found.";
	private static final String EMAIL_ATTRIBUTE = "email";
	private static final String ERROR_ATTRIBUTE = "error";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		RequestDispatcher view = request
				.getRequestDispatcher("/view/forgot_password.jsp");
		response.setContentType("text/html");
		view.forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String email = request.getParameter("email");
		String errorMessage = null;
		if ((errorMessage = validateEmail(email)) == null) {
			User user = UserDataAccess.findUserByEmail(email);
			if (user != null) {
				String link = null;
				try {
					link = PasswordResetServlet.getPasswordResetLink(user.getId());
					// send an email to the user
					Properties props = new Properties();
					Session session = Session.getDefaultInstance(props, null);
					String messageBody = "You have recently requested to reset your password.  "
							+ "If this is not the case, you can safely disregard this message. "
							+ "Do NOT reply to this email."
							+ "\n\nYour user name is: "
							+ user.getUserName()
							+ "\n\nTo reset your password, click on the link below.\n"
							+ "http://sprinklerwiz.appspot.com"
							+ link
							+ "\n\n If clicking the link above doesn't work, please copy "
							+ "and paste the URL into a new browser window instead."
							+ "\n\nThis link will expire 24 - 48 hours after you receive this email.";

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(
							"noreply@sprinklerwiz.appspotmail.com",
							"System adminstrator"));
					message.setReplyTo(null);
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(email));
					message.setSubject("Password reset");
					message.setText(messageBody);
					Transport.send(message);
					RequestDispatcher view = request
							.getRequestDispatcher("/view/password_email_sent.jsp");
					view.forward(request, response);
					return;
				} catch (InvalidKeyException e1) {
				} catch (NoSuchAlgorithmException e1) {
				} catch (AddressException e) {
				} catch (MessagingException e) {
				}
			}
			errorMessage = ERROR_MESSAGE;
		}
		response.setContentType("text/html");
		request.setAttribute(EMAIL_ATTRIBUTE, email);
		request.setAttribute(ERROR_ATTRIBUTE, errorMessage);

		RequestDispatcher view = request
				.getRequestDispatcher("/view/login.jsp");
		view.forward(request, response);
	}

	/**
	 * Checks if an email is valid, and if it is not, returns an error message.
	 * 
	 * @param email
	 *            the email to check.
	 * @return an error message if the email is not valid, or null if it is
	 *         valid.
	 */
	private String validateEmail(String email) {
		if (User.isValidEmail(email)) {
			return null;
		} else {
			return User.EMAIL_ERROR_MESSAGE;
		}
	}
}
