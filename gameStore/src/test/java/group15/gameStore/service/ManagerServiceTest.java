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
	private static final String VALID_PASSWORD = "Paul12345";
    private static final boolean VALID_ISACTIVE = true;
    private static final boolean VALID_ISMANAGER = true;
    private static final Employee VALID_MANAGEREMPLOYEE = new Employee("SmithManager", "Smith1234", "smith@mail.com", true, true);

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
        Manager managerUpdated = new Manager(VALID_USERNAME, "12345Paul", VALID_EMAIL, VALID_ISACTIVE, true);
        managerToUpdate.setUserID(0);
        managerUpdated.setUserID(0);

        when(mockManagerRepo.findManagerByUserID(0)).thenReturn(managerUpdated);

        Manager updatedManager = managerService.updateManager(managerToUpdate.getUserID(), managerUpdated, manager);

        assertNotNull(updatedManager);
		assertEquals(VALID_USERNAME, updatedManager.getUsername());
		assertEquals(VALID_EMAIL, updatedManager.getEmail());
		assertEquals("12345Paul", updatedManager.getPassword());
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
        Manager manager = new Manager(VALID_MANAGEREMPLOYEE.getUsername(), VALID_MANAGEREMPLOYEE.getPassword(), VALID_MANAGEREMPLOYEE.getEmail(), VALID_MANAGEREMPLOYEE.getIsActive(), VALID_MANAGEREMPLOYEE.getIsManager());
        manager.setUserID(0);
        when(mockEmployeeRepo.findByUserID(0)).thenReturn(manager);

        Manager managerToDelete = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        managerToDelete.setUserID(0);

        managerService.deleteManager(managerToDelete, manager);

        when(mockManagerRepo.findAll()).thenReturn(new ArrayList<>());

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getAllManagers());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no managers in the system", e.getMessage());
        
    }

    @Test
    public void testDeleteInvalidManager() {
        Manager manager = new Manager(VALID_MANAGEREMPLOYEE.getUsername(), VALID_MANAGEREMPLOYEE.getPassword(), VALID_MANAGEREMPLOYEE.getEmail(), VALID_MANAGEREMPLOYEE.getIsActive(), VALID_MANAGEREMPLOYEE.getIsManager());
        manager.setUserID(0);
        when(mockEmployeeRepo.findByUserID(0)).thenReturn(manager);

        Manager managerToDelete = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        managerToDelete.setUserID(1);
        when(mockManagerRepo.findManagerByUserID(1)).thenReturn(null);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.deleteManager(managerToDelete, manager));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There is no manager with ID 1", e.getMessage());
    }

    @Test
    public void testGetManagerByValidID() {
        Manager managerToGet = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        managerToGet.setUserID(0);

        when(mockManagerRepo.findManagerByUserID(0)).thenReturn(managerToGet);

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
        when(mockManagerRepo.findManagerByUserID(2)).thenReturn(null);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getManagerByID(2));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no manager with ID %d", 2), e.getMessage());

    }

    @Test
    public void testGetManagerByValidEmail() {
        Manager managerToGet = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        managerToGet.setUserID(1);

        when(mockManagerRepo.findManagerByEmail(VALID_EMAIL)).thenReturn(managerToGet);

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
        Manager managerToGet = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        managerToGet.setEmail("sam@mail.com");

        when(mockManagerRepo.findManagerByEmail("sam@mail.com")).thenReturn(null);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getManagerByEmail(managerToGet.getEmail()));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no manager with email %s", managerToGet.getEmail()), e.getMessage());

    }

    @Test
    public void testGetManagersByValidUsername() {
        Manager managerToGet1 = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        Manager managerToGet2 = new Manager(VALID_USERNAME, VALID_PASSWORD, "paul2@mail.com", VALID_ISACTIVE, true);

        when(mockManagerRepo.findManagersByUsername(managerToGet1.getUsername())).thenAnswer((InvocationOnMock invocation) ->{
            List<Manager> managerList = new ArrayList<>();
            managerList.add(managerToGet1);
            managerList.add(managerToGet2);
            return managerList;
        });
        
        List<Manager> gottenManagers = managerService.getManagersByUsername(managerToGet1.getUsername());

        assertNotNull(gottenManagers);
		assertEquals(2, gottenManagers.size());

    }

    @Test
    public void testGetManagersByInvalidUsername() {
        when(mockManagerRepo.findManagersByUsername("invalidUsername")).thenReturn(new ArrayList<>());
        
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getManagersByUsername("invalidUsername"));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no manager with username %s", "invalidUsername"), e.getMessage());

    }

    @Test
    public void testGetAllManagersValid() {
        Manager managerToGet1 = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, true);
        Manager managerToGet2 = new Manager("PaulManager2", VALID_PASSWORD, "paul2@mail.com", VALID_ISACTIVE, true);

        when(mockManagerRepo.findAll()).thenAnswer((InvocationOnMock invocation) ->{
            List<Manager> managerList = new ArrayList<>();
            managerList.add(managerToGet1);
            managerList.add(managerToGet2);
            return managerList;
        });

        List<Manager> allManagers = managerService.getAllManagers();

        assertNotNull(allManagers);
		assertEquals(2, allManagers.size());
    }

    @Test
    public void testGetAllManagersInvalid() {
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> managerService.getAllManagers());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no managers in the system", e.getMessage());

    }

}
