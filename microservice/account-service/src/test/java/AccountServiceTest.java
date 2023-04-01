import com.food.dto.AccountDto;
import com.food.model.Account;
import com.food.repository.AccountRepository;
import com.food.service.impl.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountServiceImpl service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAccountAllTest(){
        List<Account> list=new ArrayList<>();
        list.add(Account.builder().accountId(UUID.randomUUID()).foodId(UUID.randomUUID()).totalCount(20.0).totalPrice(BigDecimal.ZERO).description("pizza account").build());
        Mockito.when(repository.findAll()).thenReturn(list);
        List<AccountDto> dtoList=service.getAll();
        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(list.size(),1);
        Assertions.assertEquals(list.get(0).getTotalPrice(),BigDecimal.ZERO);
        log.info("Test GetAll Account");
    }

    @Test
    void createAccountTest(){
        AccountDto dto=new AccountDto();
        dto.setAccountId(UUID.randomUUID());
        dto.setFoodId(UUID.randomUUID());
        dto.setTotalCount(10.0);
        dto.setTotalPrice(BigDecimal.ZERO);
        dto.setDescription("pizza account");
        Account account=Account.builder().accountId(UUID.randomUUID()).foodId(UUID.randomUUID()).totalCount(10.0).totalPrice(BigDecimal.ZERO).description("pizza account").build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(account);
        AccountDto response=service.create(dto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getDescription(),dto.getDescription());
        log.info("Test Create Account");
    }

    @Test
    void updateAccountTest(){
        UUID uuid=UUID.randomUUID();
        AccountDto dto=AccountDto.builder().accountId(uuid).foodId(null).totalCount(0.0).totalPrice(BigDecimal.ZERO).description("account").build();
        Optional<Account> account=Optional.of(Account.builder().accountId(uuid).totalCount(10.2).totalPrice(BigDecimal.ONE).description("account1").build());

        Mockito.when(repository.findById(uuid)).thenReturn(account);
        Mockito.when(repository.save(Mockito.any())).thenReturn(Account.builder().accountId(dto.getAccountId()).foodId(dto.getFoodId()).totalCount(dto.getTotalCount()).totalPrice(dto.getTotalPrice()).description(dto.getDescription()).build());

        AccountDto response=service.update(uuid,dto);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getDescription(),dto.getDescription());
        log.info("Test Update Account");

    }

    @Test
    void deleteExistAccountTest(){
        UUID uuid=UUID.randomUUID();
        Account account=Account.builder().accountId(uuid).foodId(null).totalCount(0.0).totalPrice(BigDecimal.ZERO).description("account").build();

        when(repository.findById(uuid)).thenReturn(Optional.of(account));
        AccountDto response=service.delete(uuid);
        verify(repository,times(1)).delete(account);
        assertNotNull(response);
        assertEquals(uuid,response.getAccountId());
        assertEquals(account.getDescription(),response.getDescription());
        log.info("Test Delete Account");

    }

    @Test
    void deleteNonExistingAccountTest() {
        final UUID uuid = UUID.randomUUID();
        when(repository.findById(uuid)).thenReturn(Optional.empty());

        AccountDto response = service.delete(uuid);
        verify(repository, times(0)).delete(any(Account.class));
        assertNull(response);
    }


}
