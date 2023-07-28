package com.trainings.dating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.trainings.dating.controllers.UserAccountController;
import com.trainings.dating.entities.UserAccount;
import com.trainings.dating.repos.InterestRepository;
import com.trainings.dating.repos.UserAccountRepository;
import com.trainings.dating.entities.Interest;

@SpringBootTest
class DatingapiApplicationTests {

	@Mock
	UserAccountRepository userAccountRepository;
	
	@Mock
	InterestRepository interestRepository;
	
	@InjectMocks
	UserAccountController controller;
	
	//@Test
	void testRegisterUser() {
		UserAccount userAccount = new UserAccount();
		UserAccount savedUserAccount = new UserAccount();
		savedUserAccount.setId(123);
		when(userAccountRepository.save(userAccount)).thenReturn(savedUserAccount);
		UserAccount outputUserAccount = controller.registerUser(userAccount);
		assertNotNull(outputUserAccount);
		assertNotNull(outputUserAccount.getId());
		assertEquals(123, outputUserAccount.getId());
		verify(userAccountRepository).save(userAccount);
	}
	
	//@Test
	void testUpdateInterest()
	{
		Interest interest=new Interest();
		interest.setUserAccountId(123);
		when(userAccountRepository.findById(123)).thenReturn(Optional.of(new UserAccount()));
		Interest savedInterest=new Interest();
		when(interestRepository.save(interest)).thenReturn(savedInterest);
		
		Interest outputInterest = controller.updateInterest(interest);
		assertNotNull(outputInterest);
		assertNotNull(outputInterest.getId());
		assertEquals(123, outputInterest.getId());
		verify(userAccountRepository).findById(123);
		verify(interestRepository).save(interest);
	}
	
	//@Test
	void testGetUsers()
	{
		ArrayList<UserAccount> users = new ArrayList<UserAccount>();
		users.add(new UserAccount());
		users.add(new UserAccount());
		when(userAccountRepository.findAll()).thenReturn(users);
		List<UserAccount> outputUsers = controller.getUsers();
		assertNotNull(outputUsers);
		assertEquals(2, outputUsers.size());
		verify(userAccountRepository.findAll());
	}
	
	//@Test
	void testDeleteInterest()
	{
		doNothing().when(interestRepository).deleteById(123);
		controller.deleteInterest(123);
		verify(interestRepository).deleteById(123);
	}
	
	@Test
	void testFindMatches()
	{
		UserAccount userAccount = new UserAccount();
		userAccount.setId(123);
		userAccount.setAge(30);
		userAccount.setCity("NYC");
		userAccount.setCountry("USA");
		
		ArrayList<UserAccount> userAccounts = new ArrayList<UserAccount>();
		userAccounts.add(new UserAccount());
		userAccounts.add(new UserAccount());
		
		when(userAccountRepository.findById(123)).thenReturn(Optional.of(userAccount));
		when(userAccountRepository.findMatches(123, "NYC", "USA", 30)).thenReturn(userAccounts);
		List<UserAccount> outputMatches = controller.findMatches(123);
		
		verify(userAccountRepository).findById(123);
		verify(userAccountRepository).findMatches(123, "NYC", "USA", 30);
		
		assertNotNull(outputMatches);
		assertEquals(2, outputMatches.size());
	}

}
