package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.StoreInfo;
import java.util.List;

public interface StoreInfoRepository extends CrudRepository<StoreInfo, Integer> {

    // Find StoreInfo by storeInfoID
    StoreInfo findByStoreInfoID(Integer storeInfoID);

    // Find StoreInfo by policies containing a keyword (case-insensitive)
    List<StoreInfo> findByStorePoliciesContainingIgnoreCase(String keyword);

    // Delete StoreInfo by storeInfoID
    void deleteByStoreInfoID(Integer storeInfoID);

    // Get all StoreInfo entries
    List<StoreInfo> findAll();
}

