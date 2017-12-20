package ${package}.util;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface CustomValidation {

	@Documented
	@Constraint(validatedBy = {})
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Pattern(regexp = "^[0-9]{12}$", message = "AccountId: AccountId Must Be 12 Digit. ")
	public @interface ValidateAccountId {
		String message() default "AccountId: AccountId Must Be 12 Digit. ";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};

		/**
		 * Defines several {@link NotNull}, {@link Pattern} annotations on the
		 * same element.
		 */
		@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
		@Retention(RUNTIME)
		@Documented
		public @interface List {
			ValidateAccountId[] value();
		}
	}

	@Documented
	@Constraint(validatedBy = { SiteIdValidation.class})
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	public @interface ValidateSiteId {

		String message() default "SiteId: Invalid Site Id. ";

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
		
	}
}
