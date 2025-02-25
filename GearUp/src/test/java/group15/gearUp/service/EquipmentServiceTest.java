package group15.gearUp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

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

import group15.gearUp.exception.GearUpException;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Manager;
import group15.gearUp.repository.EmployeeRepository;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.ManagerRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class EquipmentServiceTest {
    @Mock
    private EquipmentRepository mockequipmentRepo;
    @Mock
    private ManagerRepository mockManagerRepo;
    @Mock
    private EmployeeRepository mockEmployeeRepo;
    @InjectMocks
    private EquipmentService equipmentService;
    @InjectMocks
    private ManagerService managerService;
    @Mock
    private Manager manager;

    private static final String VALID_TITLE = "equipment1";
    private static final String VALID_DESC = "description";
    private static final double VALID_PRICE = 49.99;
    private static final int VALID_STOCK = 1;
    private static final String VALID_IMAGE = "imagelink";
    private static final boolean VALID_ISAPPROVED = true;
    private static final Manager VALID_MANAGER = new Manager("SmithManager", "Smith123", "smith@mail.com", true, true);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void clearDatabase() {
        mockequipmentRepo.deleteAll();
        mockManagerRepo.deleteAll();
    }

    @Test
    public void testCreateValidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        when(mockManagerRepo.findManagerByUserID(0)).thenReturn(manager);
        
        when(mockequipmentRepo.save(any(Equipment.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Equipment createdEquipment = equipmentService.createEquipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager.getUserID());

        assertNotNull(createdEquipment);
		    assertEquals(VALID_TITLE, createdEquipment.getTitle());
        assertEquals(VALID_DESC, createdEquipment.getDescription());
        assertEquals(VALID_PRICE, createdEquipment.getPrice());
        assertEquals(VALID_STOCK, createdEquipment.getStock());
        assertEquals(VALID_IMAGE, createdEquipment.getImage());
        assertEquals(VALID_ISAPPROVED, createdEquipment.getIsApproved());
        assertEquals(manager, createdEquipment.getManager());

        verify(mockequipmentRepo, times(1)).save(createdEquipment);

    }

    @Test
    public void testCreateInValidEquipment() {
        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.createEquipment("", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER.getUserID()));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid Equipment creation request: missing attributes", e.getMessage());
    }

    @Test
    public void testCreateInvalidEquipmentNegativeStock() {
        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.createEquipment(VALID_TITLE, VALID_DESC, VALID_PRICE, -5, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER.getUserID()));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The price or stock of a Equipment cannot be negative", e.getMessage());
    }

    @Test
    public void testCreateEquipmentInvalidEmployee() {
      Manager invalidEmployee = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
      invalidEmployee.setUserID(0);

      GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.createEquipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, invalidEmployee.getUserID()));
		  assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		  assertEquals("The manager does not exist", e.getMessage());
    }

    @Test
    public void testUpdateValidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);

        when(mockequipmentRepo.save(any(Equipment.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Equipment equipmentToUpdate = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        Equipment equipmentUpdated = new Equipment("newequipment1", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToUpdate.setEquipmentID(0);
        equipmentUpdated.setEquipmentID(0);

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(equipmentUpdated);

        Equipment updatedEquipment = equipmentService.updateEquipment(equipmentToUpdate.getEquipmentID(), equipmentUpdated);
        
        assertNotNull(updatedEquipment);
		    assertEquals("newequipment1", updatedEquipment.getTitle());
        assertEquals(VALID_DESC, updatedEquipment.getDescription());
        assertEquals(VALID_PRICE, updatedEquipment.getPrice());
        assertEquals(VALID_STOCK, updatedEquipment.getStock());
        assertEquals(VALID_IMAGE, updatedEquipment.getImage());
        assertEquals(VALID_ISAPPROVED, updatedEquipment.getIsApproved());
        assertEquals(manager, updatedEquipment.getManager());
    }

    @Test
    public void testUpdateInvalidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        when(mockManagerRepo.findManagerByUserID(0)).thenReturn(manager);
        
        when(mockequipmentRepo.save(any(Equipment.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Equipment equipmentToUpdate = equipmentService.createEquipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager.getUserID());

        equipmentToUpdate.setTitle("");

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.updateEquipment(equipmentToUpdate.getEquipmentID(), equipmentToUpdate));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid Equipment creation request: missing attributes", e.getMessage());

    }

    @Test
    public void testDeleteValidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Equipment equipmentToDelete = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToDelete.setEquipmentID(0);

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(equipmentToDelete);

        equipmentService.deleteEquipment(equipmentToDelete);

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.getAllEquipments());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no Equipments in the system", e.getMessage());
    }

    @Test
    public void testDeleteInvalidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Equipment equipmentToDelete = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToDelete.setEquipmentID(0);

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(null);

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.deleteEquipment(equipmentToDelete));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("The Equipment to delete does not exist", e.getMessage());

    }

    @Test
    public void testDeleteInvalidEquipmentAttributes() {
        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.deleteEquipment(null));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid Equipment creation request: missing attributes", e.getMessage());

    }


    @Test
    public void testGetEquipmentByValidID() {
        Equipment equipmentToGet = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToGet.setEquipmentID(0);

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(equipmentToGet);

        Equipment gottenEquipment = equipmentService.getEquipmentByID(equipmentToGet.getEquipmentID());
        assertNotNull(gottenEquipment);
		assertEquals(VALID_TITLE, gottenEquipment.getTitle());
        assertEquals(VALID_DESC, gottenEquipment.getDescription());
        assertEquals(VALID_PRICE, gottenEquipment.getPrice());
        assertEquals(VALID_STOCK, gottenEquipment.getStock());
        assertEquals(VALID_IMAGE, gottenEquipment.getImage());
        assertEquals(VALID_ISAPPROVED, gottenEquipment.getIsApproved());
        assertEquals(manager, gottenEquipment.getManager());

    }

    @Test
    public void testGetEquipmentByInvalidID() {
        when(mockequipmentRepo.findEquipmentByEquipmentID(2)).thenReturn(null);

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.getEquipmentByID(2));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no Equipment with ID %d", 2), e.getMessage());
    }

    @Test
    public void testGetEquipmentByValidTitle() {
        Equipment equipmentToGet = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToGet.setEquipmentID(1);

        when(mockequipmentRepo.findEquipmentByTitle(VALID_TITLE)).thenReturn(equipmentToGet);

        Equipment gottenEquipment = equipmentService.getEquipmentByTitle(equipmentToGet.getTitle());
        assertNotNull(gottenEquipment);
		assertEquals(VALID_TITLE, gottenEquipment.getTitle());
        assertEquals(VALID_DESC, gottenEquipment.getDescription());
        assertEquals(VALID_PRICE, gottenEquipment.getPrice());
        assertEquals(VALID_STOCK, gottenEquipment.getStock());
        assertEquals(VALID_IMAGE, gottenEquipment.getImage());
        assertEquals(VALID_ISAPPROVED, gottenEquipment.getIsApproved());
        assertEquals(manager, gottenEquipment.getManager());
    }

    @Test
    public void testGetEquipmentByInvalidTitle() {
        when(mockequipmentRepo.findEquipmentByTitle("invalidTitle")).thenReturn(null);

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.getEquipmentByTitle("invalidTitle"));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no Equipment with title %s", "invalidTitle"), e.getMessage());

    }

    @Test
    public void testGetEquipmentsByValidPrice() {
        Equipment equipmentToGet1 = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        Equipment equipmentToGet2 = new Equipment("equipment2", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);

        when(mockequipmentRepo.findEquipmentsByPrice(equipmentToGet1.getPrice())).thenAnswer((InvocationOnMock invocation) ->{
            List<Equipment> equipmentList = new ArrayList<>();
            equipmentList.add(equipmentToGet1);
            equipmentList.add(equipmentToGet2);
            return equipmentList;
        });

        List<Equipment> gottenEquipments = equipmentService.getEquipmentsByPrice(equipmentToGet1.getPrice());

        assertNotNull(gottenEquipments);
		assertEquals(2, gottenEquipments.size());

    }

    @Test
    public void testGetEquipmentsByInvalidPrice() {
        when(mockequipmentRepo.findEquipmentsByPrice(59.99)).thenReturn(new ArrayList<>());

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.getEquipmentsByPrice(59.99));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no Equipment with price %f", 59.99), e.getMessage());
    }

    @Test
    public void testGetAllEquipmentsValid() {
        Equipment equipmentToGet1 = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        Equipment equipmentToGet2 = new Equipment("equipment2", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);

        when(mockequipmentRepo.findAll()).thenAnswer((InvocationOnMock invocation) ->{
            List<Equipment> equipmentList = new ArrayList<>();
            equipmentList.add(equipmentToGet1);
            equipmentList.add(equipmentToGet2);
            return equipmentList;
        });

        List<Equipment> gottenEquipments = equipmentService.getAllEquipments();

        assertNotNull(gottenEquipments);
		assertEquals(2, gottenEquipments.size());
    }

    @Test
    public void testGetAllEquipmentsInvalid() {
        when(mockequipmentRepo.findAll()).thenReturn(new ArrayList<>());

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.getAllEquipments());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no Equipments in the system", e.getMessage());

    }

    @Test
    public void testArchiveValidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
       
        when(mockequipmentRepo.save(any(Equipment.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Equipment equipmentToArchive = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToArchive.setEquipmentID(0);

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(equipmentToArchive);

        Equipment archivedEquipment = equipmentService.archiveEquipment(equipmentToArchive);

        assertNotNull(archivedEquipment);
		assertEquals(Date.valueOf(LocalDate.now()), archivedEquipment.getArchivedDate());
		assertEquals(false, archivedEquipment.getIsApproved());
    }
    
    @Test
    public void testArchiveInvalidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Equipment archivedEquipment = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        archivedEquipment.setEquipmentID(0);
        archivedEquipment.setArchivedDate(Date.valueOf(LocalDate.now()));

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(archivedEquipment);
        
        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.archiveEquipment(archivedEquipment));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The Equipment is already archived", e.getMessage());

    }

    @Test
    public void testUnarchiveValidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        when(mockequipmentRepo.save(any(Equipment.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Equipment equipmentToUnarchive = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToUnarchive.setEquipmentID(0);
        equipmentToUnarchive.setArchivedDate(Date.valueOf(LocalDate.now()));

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(equipmentToUnarchive);

        Equipment unarchivedEquipment = equipmentService.unarchiveEquipment(equipmentToUnarchive);

        assertNotNull(unarchivedEquipment);
		assertEquals(null, unarchivedEquipment.getArchivedDate());

    }
    
    @Test
    public void testUnarchiveInvalidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Equipment equipmentToUnarchive = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        
        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(equipmentToUnarchive);

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.unarchiveEquipment(equipmentToUnarchive));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The Equipment is already unarchived", e.getMessage());

    }

    @Test
    public void testUpdateApprovalValidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
       
        when(mockequipmentRepo.save(any(Equipment.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Equipment equipmentToUpdateApproval = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToUpdateApproval.setEquipmentID(0);

        when(mockequipmentRepo.findEquipmentByEquipmentID(0)).thenReturn(equipmentToUpdateApproval);

        Equipment updatedEquipment = equipmentService.updateEquipmentApproval(equipmentToUpdateApproval, true);

        assertNotNull(updatedEquipment);
		assertEquals(true, updatedEquipment.getIsApproved());
    }

    @Test
    public void testUpdateApprovalInvalidEquipment() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
    
        Equipment equipmentToUpdateApproval = new Equipment(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        equipmentToUpdateApproval.setTitle("");

        GearUpException e = assertThrows(GearUpException.class,
				() -> equipmentService.updateEquipmentApproval(equipmentToUpdateApproval, false));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid Equipment approval change request: missing attributes", e.getMessage());

    }
}
