package services;

import models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repositories.CustomerRepository;

import java.util.List;

/**
 * Created by bartek on 08.02.16.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {
        Customer customer=customerRepository.findByeMail(eMail);
        if(customer==null)
            throw new UsernameNotFoundException("No customer present with e-mail: "+eMail);
        else
            return new CustomUserDetails(customer);
    }
}