package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.ManagerRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ManagerServiceTest {
    @Mock
    private ManagerRepository mockManagerRepo;
    @Mock
    private EmployeeRepository mockEmployeeRepo;
    @InjectMocks
    private ManagerService managerService;
    @InjectMocks
    private EmployeeService employeeService;

    private static final String VALID_USERNAME = "PaulManager";
	private static final String VALID_EMAIL = "paul@mail.com";
	private static final String VALID_PASSWORD = "Paul123";
    private static final boolean VALID_ISACTIVE = true;
    private static final boolean VALID_ISMANAGER = true;
    private static final Employee VALID_MANAGEREMPLOYEE = new Employee("SmithManager", "Smith123", "smith@mail.com", true, true);

    private static final String INVALID_USERNAME = "";

    private Employee createdEmployee;

    @BeforeEach
    public void setDatabase() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void clearDatabase() {
        mockEmployeeRepo.deleteAll();
        mockManagerRepo.deleteAll();
    }

    @Test
	public void testCreateValidManager() {
        Manager manager = new Manager(VALID_MANAGEREMPLOYEE.getUsername(), VALID_MANAGEREMPLOYEE.getPassword(), VALID_MANAGEREMPLOYEE.getEmail(), VALID_MANAGEREMPLOYEE.getIsActive(), VALID_MANAGEREMPLOYEE.getIsManager());
        manager.setUserID(0);
        when(mockEmployeeRepo.findByUserID(0)).thenReturn(manager);

        when(mockManagerRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Manager createdManager = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, manager);
        
        assertNotNull(createdManager);
		assertEquals(VALID_USERNAME, createdManager.getUsername());
		assertEquals(VALID_EMAIL, createdManager.getEmail());
		assertEquals(VALID_PASSWORD, createdManager.getPassword());
        assertEquals(VALID_ISACTIVE, createdManager.getIsActive());
        //IsManager
        assertEquals(VALID_ISMANAGER, createdManager.getIsManager());
        
		verify(mockManagerRepo, times(1)).save(createdManager);
    }

    @Test
	public void testCreateInvalidManager() {
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.createManager(INVALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid manager creation request: missing attributes", e.getMessage());
    }

    @Test
    public void testUpdateValidManager() {
        Manager manager = new Manager(VALID_MANAGEREMPLOYEE.getUsername(), VALID_MANAGEREMPLOYEE.getPassword(), VALID_MANAGEREMPLOYEE.getEmail(), VALID_MANAGEREMPLOYEE.getIsActive(), VALID_MANAGEREMPLOYEE.getIsManager());
        manager.setUserID(0);
        when(mockEmployeeRepo.findByUserID(0)).thenReturn(manager);

        when(mockManagerRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Manager managerToUpdate = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        Manager managerUpdated = new Manager(VALID_USERNAME, "123Paul", VALID_EMAIL, VALID_ISACTIVE, true);
        managerToUpdate.setUserID(0);
        managerUpdated.setUserID(0);

        when(mockManagerRepo.findManagerByUserID(0)).thenReturn(managerUpdated);

        Manager updatedManager = managerService.updateManager(managerToUpdate.getUserID(), managerUpdated, manager);

        assertNotNull(updatedManager);
		assertEquals(VALID_USERNAME, updatedManager.getUsername());
		assertEquals(VALID_EMAIL, updatedManager.getEmail());
		assertEquals("123Paul", updatedManager.getPassword());
        assertEquals(VALID_ISACTIVE, updatedManager.getIsActive());
        assertEquals(VALID_ISMANAGER, updatedManager.getIsManager());
        
    }

    @Test
    public void testUpdateInvalidManager() {
        Manager managerToUpdate = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        
        managerToUpdate.setPassword("");

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.updateManager(managerToUpdate.getUserID(), managerToUpdate, createdEmployee));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid manager creation request: missing attributes", e.getMessage());
    }

    @Test
    public void testDeleteValidManager() {
        //when(mockManagerRepo.save(any(Manager.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Manager managerToDelete = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);

        List<Manager> managerList = managerService.getAllManagers();

        assertNotNull(managerList);
		assertEquals(1, managerList.size());
        verify(mockManagerRepo, times(1)).save(managerToDelete);

        managerService.deleteManager(managerToDelete, createdEmployee);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getAllManagers());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no managers in the system", e.getMessage());
        
    }

    @Test
    public void testDeleteInvalidManager() {
        Manager managerToDelete = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);

        managerToDelete.setUserID(managerToDelete.getUserID() + 1);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.deleteManager(managerToDelete, createdEmployee));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The manager to delete does not exist", e.getMessage());
    }

    @Test
    public void testGetManagerByValidID() {
        Manager managerToGet = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);

        when(mockManagerRepo.findManagerByUserID(managerToGet.getUserID())).thenReturn(new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, VALID_ISMANAGER));

        Manager gottenManager = managerService.getManagerByID(managerToGet.getUserID());

        assertNotNull(gottenManager);
		assertEquals(VALID_USERNAME, gottenManager.getUsername());
		assertEquals(VALID_EMAIL, gottenManager.getEmail());
		assertEquals(VALID_PASSWORD, gottenManager.getPassword());
        assertEquals(VALID_ISACTIVE, gottenManager.getIsActive());
        assertEquals(VALID_ISMANAGER, gottenManager.getIsManager());

    }

    @Test
    public void testGetManagerByInvalidID() {
        Manager managerToGet = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);
        
        managerToGet.setUserID(managerToGet.getUserID() + 1);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getManagerByID(managerToGet.getUserID()));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no manager with ID %d", managerToGet.getUserID()), e.getMessage());

    }

    @Test
    public void testGetManagerByValidEmail() {
        Manager managerToGet = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);

        when(mockManagerRepo.findManagerByEmail(managerToGet.getEmail())).thenReturn(new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, VALID_ISMANAGER));

        Manager gottenManager = managerService.getManagerByEmail(managerToGet.getEmail());

        assertNotNull(gottenManager);
		assertEquals(VALID_USERNAME, gottenManager.getUsername());
		assertEquals(VALID_EMAIL, gottenManager.getEmail());
		assertEquals(VALID_PASSWORD, gottenManager.getPassword());
        assertEquals(VALID_ISACTIVE, gottenManager.getIsActive());
        assertEquals(VALID_ISMANAGER, gottenManager.getIsManager());

    }

    @Test
    public void testGetManagerByInvalidEmail() {
        Manager managerToGet = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);
        
        managerToGet.setEmail("sam@mail.com");

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getManagerByEmail(managerToGet.getEmail()));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no manager with email %s", managerToGet.getEmail()), e.getMessage());

    }

    @Test
    public void testGetManagersByValidUsername() {
        Manager managerToGet1 = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);
        Manager managerToGet2 = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, "paul2@mail.com", VALID_ISACTIVE, createdEmployee);

        when(mockManagerRepo.findManagersByUsername(managerToGet1.getUsername())).thenAnswer((InvocationOnMock invocation) ->{
            List<Manager> managerList = new ArrayList<>();
            managerList.add(managerToGet1);
            managerList.add(managerToGet2);
            return managerList;
        });
        
        List<Manager> gottenManagers = managerService.getManagersByUsername(managerToGet1.getUsername());

        assertNotNull(gottenManagers);
		assertEquals(2, gottenManagers.size());
        verify(mockManagerRepo, times(1)).save(managerToGet1);
        verify(mockManagerRepo, times(1)).save(managerToGet2);

    }

    @Test
    public void testGetManagersByInvalidUsername() {
        Manager managerToGet = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getManagersByUsername("invalidUsername"));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no manager with username %s", "invalidUsername"), e.getMessage());

    }

    @Test
    public void testGetAllManagersValid() {
        Manager managerToGet1 = managerService.createManager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, createdEmployee);
        Manager managerToGet2 = managerService.createManager("PaulManager2", VALID_PASSWORD, "paul2@mail.com", VALID_ISACTIVE, createdEmployee);

        when(mockManagerRepo.findAll()).thenAnswer((InvocationOnMock invocation) ->{
            List<Manager> managerList = new ArrayList<>();
            managerList.add(managerToGet1);
            managerList.add(managerToGet2);
            return managerList;
        });

        List<Manager> allManagers = managerService.getAllManagers();

        assertNotNull(allManagers);
		assertEquals(2, allManagers.size());
        verify(mockManagerRepo, times(1)).save(managerToGet1);
        verify(mockManagerRepo, times(1)).save(managerToGet2);
    }

    @Test
    public void testGetAllManagersInvalid() {
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getAllManagers());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no managers in the system", e.getMessage());

    }

}
