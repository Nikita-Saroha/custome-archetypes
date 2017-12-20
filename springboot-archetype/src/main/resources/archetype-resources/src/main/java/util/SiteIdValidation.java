package ${package}.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import ${package}.util.CustomValidation.ValidateSiteId;

public class SiteIdValidation implements ConstraintValidator<ValidateSiteId, String> {

	@Value("${siteIdSiteCodeMapping}")
	String siteIdSiteCodeMapping;

	@Override
	public void initialize(ValidateSiteId validateSiteId) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value.trim().length() != 12) return true;
		if (!StringUtils.isNumeric(value)) return true;
		if (null != value && siteIdSiteCodeMapping.contains(value.substring(0, 3))) {
			return true;
		}
		return false;
	}
}
