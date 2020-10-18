package com.upgrad.quora.api.controller;


import com.upgrad.quora.api.model.UserDeleteResponse;
import com.upgrad.quora.service.business.AdminService;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/user/{userId}",
            produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public ResponseEntity<UserDeleteResponse> userDelete(@PathVariable("userId") final String uUid,
                                                         @RequestHeader("authorization") final String authorization)
            throws UserNotFoundException, AuthorizationFailedException {
        String[] bearerToken = authorization.split("Bearer");
        adminService.userDelete(uUid, bearerToken[1]);
        UserDeleteResponse userDeleteResponse = new UserDeleteResponse().id(uUid).status(HttpStatus.ACCEPTED.getReasonPhrase());
        return new ResponseEntity<UserDeleteResponse>(userDeleteResponse, HttpStatus.ACCEPTED);

    }
}
