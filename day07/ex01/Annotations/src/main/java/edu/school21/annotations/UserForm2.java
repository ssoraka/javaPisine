package edu.school21.annotations;

@HtmlForm(fileName = "user_form2.html", action = "/users", method = "post")
public class UserForm2 {

	@HtmlInput(type = "text", name = "first_name2", placeholder = "Enter First Name")
	private String firstName;

	@HtmlInput(type = "text", name = "last_name2", placeholder = "Enter Last Name")
	private String lastName;

	@HtmlInput(type = "password", name = "password2", placeholder = "Enter Password")
	private String password;

	public String getFirstName()
	{
		return firstName;
	}
}
