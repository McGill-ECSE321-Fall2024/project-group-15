package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.StoreInfo;
import jakarta.transaction.Transactional;

import java.util.List;

@SpringBootTest
public class StoreInfoRepositoryTest {

    @Autowired
    private StoreInfoRepository repo;

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        repo.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        // Clear the database after each test
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadStoreInfo() {
        // Create a new StoreInfo
        String storePolicies = "Return policy: 30 days, Exchange policy: 15 days";
        StoreInfo storeInfo = new StoreInfo(storePolicies);

        // Save the StoreInfo
        storeInfo = repo.save(storeInfo);
        int storeInfoId = storeInfo.getStoreInfoID();

        // Read the StoreInfo back from the database
        StoreInfo storeInfoFromDb = repo.findByStoreInfoID(storeInfoId);

        // Assertions
        assertNotNull(storeInfoFromDb);
        assertEquals(storeInfoId, storeInfoFromDb.getStoreInfoID());
        assertEquals(storePolicies, storeInfoFromDb.getStorePolicies());
    }

    @Test
    public void testFindStoreInfoByKeywordInPolicies() {
        // Create and save store info with different policies
        StoreInfo storeInfo1 = new StoreInfo("Return policy: 30 days");
        StoreInfo storeInfo2 = new StoreInfo("No return policy");
        repo.save(storeInfo1);
        repo.save(storeInfo2);

        // Find StoreInfo by keyword in policies
        List<StoreInfo> storeInfosWithReturn = repo.findByStorePoliciesContainingIgnoreCase("return");

        // Assertions
        assertNotNull(storeInfosWithReturn);
        assertEquals(2, storeInfosWithReturn.size());
    }

    @Test
    @Transactional
    public void testDeleteStoreInfoByStoreInfoID() {
        // Create and save a store info
        StoreInfo storeInfo = new StoreInfo("Return policy: 30 days");
        storeInfo = repo.save(storeInfo);
        int storeInfoId = storeInfo.getStoreInfoID();

        // Delete the store info by storeInfoID
        repo.deleteByStoreInfoID(storeInfoId);

        // Verify that the store info was deleted
        StoreInfo deletedStoreInfo = repo.findByStoreInfoID(storeInfoId);
        assertEquals(null, deletedStoreInfo);
    }

    @Test
    public void testFindAllStoreInfo() {
        // Create and save multiple store info entries
        StoreInfo storeInfo1 = new StoreInfo("Policy 1");
        StoreInfo storeInfo2 = new StoreInfo("Policy 2");
        repo.save(storeInfo1);
        repo.save(storeInfo2);

        // Retrieve all store info entries
        List<StoreInfo> allStoreInfos = repo.findAll();

        // Assertions
        assertNotNull(allStoreInfos);
        assertEquals(2, allStoreInfos.size());
    }
}
