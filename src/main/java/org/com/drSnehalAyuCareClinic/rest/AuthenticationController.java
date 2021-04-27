package org.com.drSnehalAyuCareClinic.rest;

import org.com.drSnehalAyuCareClinic.model.AuthenticationRequest;
import org.com.drSnehalAyuCareClinic.model.AuthenticationResponse;
import org.com.drSnehalAyuCareClinic.security.MyUserDetailsService;
import org.com.drSnehalAyuCareClinic.security.SecurityConfigurer;
import org.com.drSnehalAyuCareClinic.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authenticate")
public class AuthenticationController {

	private AuthenticationManager authenticationManager;

	@Autowired
	private SecurityConfigurer configurer;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	@CrossOrigin(exposedHeaders = "errors, content-type, Access-Control-Allow-Origin")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken
	(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager = configurer.authenticationManagerBean();
			authenticationManager.authenticate(
					new  UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
							authenticationRequest.getPassword()));
		} catch (BadCredentialsException badCredentialsException) {
			throw new Exception("Incorrect username or password");
		}
		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));	
	}
}
