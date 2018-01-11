package com.startup.controllers.api.companymembership;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Company Membership not found!")
public class CompanyMembershipNotFoundException extends RuntimeException{
}
